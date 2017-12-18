package com.zyj.thread.parallel;

import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

/**
 * 并行线程任务，记录任务运行时的信息
 * 
 * @author zengyuanjun
 *
 */
public class MultiParallelRunnable implements Runnable {
	/**
	 * 运行的任务
	 */
	private Runnable task;
	/**
	 * 子线程倒计数锁，用于通知主线程执行任务
	 */
	private CountDownLatch childLatch;
	/**
	 * 子线程异常，用于记录子线程的异常
	 */
	private ChildThreadException exception;

	/**
	 * 构造器
	 * @param task 任务
	 * @param e 子线程异常
	 * @param childLatch 倒计数锁
	 */
	public MultiParallelRunnable(Runnable task, ChildThreadException e, CountDownLatch childLatch) {
		this.task = task;
		this.childLatch = childLatch;
		this.exception = e;
	}

	/**
	 * 运行任务，记录异常，锁计数减少
	 */
	@Override
	public void run() {
		try {
			task.run();
		} catch (Exception e) {
			e.printStackTrace();
			exception.addException(e);
		} finally {
			childLatch.countDown();
		}
	}
	
}
