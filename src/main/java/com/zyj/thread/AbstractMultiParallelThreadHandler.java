package com.zyj.thread;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

public abstract class AbstractMultiParallelThreadHandler implements MultiThreadHandler {

	protected CountDownLatch countDownLatch;
	protected List<Runnable> taskList;

	public abstract void addTask(Runnable... tasks);

	public abstract void run() throws ChildThreadException;

}
