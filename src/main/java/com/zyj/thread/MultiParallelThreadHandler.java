package com.zyj.thread;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import com.zyj.exception.ChildThreadException;

public class MultiParallelThreadHandler extends AbstractMultiParallelThreadHandler {

	private ChildThreadException childThreadException;

	public MultiParallelThreadHandler() {
		taskList = new ArrayList<Runnable>();
		childThreadException = new ChildThreadException();
	}

	@Override
	public void addTask(Runnable... tasks) {
		for (Runnable task : tasks) {
			taskList.add(task);
		}
	}

	@Override
	public void run() throws ChildThreadException {
		if (taskList.size() > 0) {
			countDownLatch = new CountDownLatch(taskList.size());
			childThreadException.clearExceptionList();
			MultiParallelTask parallelTask = null;
			for (Runnable task : taskList) {
				parallelTask = new MultiParallelTask(task, countDownLatch, childThreadException);
				parallelTask.start();
			}
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				childThreadException.addException(e);
			}
			taskList.clear();
			if (childThreadException.hasException()) {
				throw childThreadException;
			}
		}
	}

}
