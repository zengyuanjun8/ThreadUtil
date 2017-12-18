package com.zyj.thread.parallel;

import java.util.concurrent.ExecutorService;

import com.zyj.exception.ChildThreadException;

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

	@Override
	protected void invoke(Runnable command) {
		if(null != service){
			service.execute(command);
		}else{
			super.invoke(command);
		}
	}

	@Override
	protected void childExceptionHandler(ChildThreadException e) throws ChildThreadException {
		System.out.println("roll back!");
	}

}
