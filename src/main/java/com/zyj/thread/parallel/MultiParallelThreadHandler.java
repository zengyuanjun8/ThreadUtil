package com.zyj.thread.parallel;

import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

/**
 * 处理并行线程任务，run方法将等待任务列表中所有任务处理完成
 * 
 * @author zengyuanjun
 *
 */
public class MultiParallelThreadHandler extends AbstractMultiParallelThreadHandler {

	/**
	 * 无参构造器
	 */
	public MultiParallelThreadHandler() {
		super();
	}

	/**
	 * 运行任务列表中所有任务，并等待处理完成
	 */
	@Override
	public void run() throws ChildThreadException {
		if (null == taskList || taskList.size() == 0) {
			return;
		} else if (taskList.size() == 1) {
			runWithoutNewThread();
		} else if (taskList.size() > 1) {
			runInNewThread();
		}
	}

	/**
	 * 新建线程运行任务
	 * 
	 * @throws ChildThreadException
	 */
	private void runInNewThread() throws ChildThreadException {
		childLatch = new CountDownLatch(taskList.size());
		childThreadException.clearExceptionList();
		for (Runnable task : taskList) {
			invoke(new MultiParallelRunnable(task, childThreadException, childLatch));
		}
		taskList.clear();
		try {
			childLatch.await();
		} catch (InterruptedException e) {
			childThreadException.addException(e);
		}
		throwChildExceptionIfRequired();
	}

	/**
	 * 具体执行的方法
	 * 
	 * @param command
	 */
	protected void invoke(Runnable command) {
		if(command.getClass().isAssignableFrom(Thread.class)){
			Thread.class.cast(command).start();
		}else{
			new Thread(command).start();
		}
	}

	/**
	 * 不新建线程，直接在当前线程运行
	 * 
	 * @throws ChildThreadException
	 */
	private void runWithoutNewThread() throws ChildThreadException {
		try {
			taskList.get(0).run();
		} catch (Exception e) {
			childThreadException.addException(e);
		}
		throwChildExceptionIfRequired();
	}

	/**
	 * 是否有异常需要抛出
	 * 
	 * @throws ChildThreadException
	 */
	private void throwChildExceptionIfRequired() throws ChildThreadException {
		if (childThreadException.hasException()) {
			childExceptionHandler(childThreadException);
		}
	}

	/**
	 * 子线程异常处理，默认直接抛出
	 * @param e 子线程异常
	 * @throws ChildThreadException
	 */
	protected void childExceptionHandler(ChildThreadException e) throws ChildThreadException {
		throw e;
	}

}
