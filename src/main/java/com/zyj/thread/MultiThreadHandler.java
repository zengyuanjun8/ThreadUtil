package com.zyj.thread;

import com.zyj.exception.ChildThreadException;

/**
 * 多任务处理
 * @author zengyuanjun
 */
public interface MultiThreadHandler {
	/**
	 * 添加任务
	 * @param tasks 
	 */
	void addTask(Runnable... tasks);
	/**
	 * 执行任务
	 * @throws ChildThreadException 
	 */
	void run() throws ChildThreadException;
}
