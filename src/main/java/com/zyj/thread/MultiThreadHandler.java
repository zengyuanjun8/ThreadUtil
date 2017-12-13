package com.zyj.thread;

import com.zyj.exception.ChildThreadException;

public interface MultiThreadHandler {
	void addTask(Runnable... tasks);
	void run() throws ChildThreadException;
}
