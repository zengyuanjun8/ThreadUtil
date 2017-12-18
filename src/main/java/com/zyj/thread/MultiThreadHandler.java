package com.zyj.thread;

import com.zyj.exception.ChildThreadException;

/**
 * 多线程任务处理
 * @author zengyuanjun
 */
public interface MultiThreadHandler {
	/**
	 * 添加任务
	 * @param tasks 任务
	 */
	void addTask(Runnable... tasks);
	/**
	 * 运行任务
	 * @throws ChildThreadException 子线程异常
	 */
	void run() throws ChildThreadException;
}
