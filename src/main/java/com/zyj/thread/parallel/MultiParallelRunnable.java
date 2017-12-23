package com.zyj.thread.parallel;

/**
 * 并行线程对象
 * 
 * @author zengyuanjun
 *
 */
public class MultiParallelRunnable implements Runnable {
	/**
	 * 并行任务参数
	 */
	private MultiParallelContext context;

	/**
	 * 构造函数
	 * @param context
	 */
	public MultiParallelRunnable(MultiParallelContext context) {
		this.context = context;
	}

	/**
	 * 运行任务
	 */
	@Override
	public void run() {
		try {
			context.getTask().run();
		} catch (Exception e) {
			e.printStackTrace();
			context.getChildException().addException(e);
		} finally {
			context.getChildLatch().countDown();
		}
	}
	
}
