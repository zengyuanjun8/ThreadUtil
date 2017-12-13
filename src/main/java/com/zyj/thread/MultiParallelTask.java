package com.zyj.thread;

import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

public class MultiParallelTask extends Thread{
	
	private Runnable task;
	private CountDownLatch countDownLatch;
	private ChildThreadException exception;
	
	public MultiParallelTask(Runnable task, CountDownLatch counter, ChildThreadException e) {
		this.task = task;
		this.countDownLatch = counter;
		this.exception = e;
	}
	
	@Override
	public void run(){
		try {
			task.run();
		} catch (Exception e) {
			exception.addException(e);
		}finally{
			countDownLatch.countDown();
		}
	}
}
