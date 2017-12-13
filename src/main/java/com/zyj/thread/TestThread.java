package com.zyj.thread;

import com.zyj.exception.ChildThreadException;

public class TestThread implements Runnable {

	private String name;
	
	public TestThread(String name) {
		this.name = name;
	}
	
	@Override
	public void run() {
		throw new RuntimeException(name + ": throw exception");
		
	}
	
	public static void main(String[] args) {
		
		System.out.println("main begin =================");
		MultiThreadHandler handler = new MultiParallelThreadHandler();
		TestThread task = null;
		for(int i=1; i<=5 ; i++){
			task = new TestThread("Thread - " + i);
			handler.addTask(task);
		}
		try {
			handler.run();
		} catch (ChildThreadException e) {
			System.out.println("exception count: " + e.getExceptionList().size());
			e.printAllStackTrace();
		}
		System.out.println("main end =================");
	}
}