package com.zyj.thread.parallel;

import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

/**
 * 并行任务处理工具
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
	 * 根据任务数量运行任务
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
			invoke(new MultiParallelRunnable(new MultiParallelContext(task, childLatch, childThreadException)));
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
	 * 默认线程执行方法
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
	 * 在当前线程中直接运行
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
	 * 根据需要抛出子线程异常
	 * 
	 * @throws ChildThreadException
	 */
	private void throwChildExceptionIfRequired() throws ChildThreadException {
		if (childThreadException.hasException()) {
			childExceptionHandler(childThreadException);
		}
	}

	/**
	 * 默认抛出子线程异常
	 * @param e 
	 * @throws ChildThreadException
	 */
	protected void childExceptionHandler(ChildThreadException e) throws ChildThreadException {
		throw e;
	}

}
