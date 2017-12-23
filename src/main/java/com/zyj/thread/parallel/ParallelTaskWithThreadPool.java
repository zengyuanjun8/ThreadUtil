package com.zyj.thread.parallel;

import java.util.concurrent.ExecutorService;

/**
 * 使用线程池运行并行任务
 * @author zengyuanjun
 *
 */
public class ParallelTaskWithThreadPool extends MultiParallelThreadHandler {
	private ExecutorService service;
	
	public ParallelTaskWithThreadPool() {
	}
	
	public ParallelTaskWithThreadPool(ExecutorService service) {
		this.service = service;
	}

	public ExecutorService getService() {
		return service;
	}

	public void setService(ExecutorService service) {
		this.service = service;
	}

	/**
	 * 使用线程池运行
	 */
	@Override
	protected void invoke(Runnable command) {
		if(null != service){
			service.execute(command);
		}else{
			super.invoke(command);
		}
	}

}
