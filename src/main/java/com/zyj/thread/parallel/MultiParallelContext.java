package com.zyj.thread.parallel;

import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

/**
 * 并行任务参数
 * @author zengyuanjun
 *
 */
public class MultiParallelContext {
	/**
	 * 运行的任务
	 */
	private Runnable task;
	/**
	 * 子线程倒计数锁
	 */
	private CountDownLatch childLatch;
	/**
	 * 子线程异常
	 */
	private ChildThreadException childException;
	
	public MultiParallelContext() {
	}
	
	public MultiParallelContext(Runnable task, CountDownLatch childLatch, ChildThreadException childException) {
		this.task = task;
		this.childLatch = childLatch;
		this.childException = childException;
	}


	public Runnable getTask() {
		return task;
	}
	public void setTask(Runnable task) {
		this.task = task;
	}
	public CountDownLatch getChildLatch() {
		return childLatch;
	}
	public void setChildLatch(CountDownLatch childLatch) {
		this.childLatch = childLatch;
	}
	public ChildThreadException getChildException() {
		return childException;
	}
	public void setChildException(ChildThreadException childException) {
		this.childException = childException;
	}
	
}
