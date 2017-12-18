package com.zyj.thread.parallel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;
import com.zyj.thread.MultiThreadHandler;

/**
 * 处理并行线程任务
 * @author zengyuanjun
 */
public abstract class AbstractMultiParallelThreadHandler implements MultiThreadHandler {
	/**
	 * 倒计数锁，判断是否所有任务线程执行结束
	 */
	protected CountDownLatch childLatch;
	
	/**
	 * 任务列表，保存所有任务
	 */
	protected List<Runnable> taskList;
	
	/**
	 * 记录子线程异常
	 */
	protected ChildThreadException childThreadException;

	public AbstractMultiParallelThreadHandler() {
		taskList = new ArrayList<Runnable>();
		childThreadException = new ChildThreadException();
	}

	public void setCountDownLatch(CountDownLatch latch) {
		this.childLatch = latch;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addTask(Runnable... tasks) {
		if (null == tasks) {
			taskList = new ArrayList<Runnable>();
		}
		for (Runnable task : tasks) {
			taskList.add(task);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public abstract void run() throws ChildThreadException;

}
