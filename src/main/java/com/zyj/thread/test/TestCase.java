package com.zyj.thread.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.zyj.exception.ChildThreadException;
import com.zyj.thread.MultiThreadHandler;
import com.zyj.thread.parallel.MultiParallelThreadHandler;
import com.zyj.thread.parallel.ParallelTaskWithThreadPool;

public class TestCase implements Runnable {

	private String name;
	private Map<String, Object> result;
	
	public TestCase(String name, Map<String, Object> result) {
		this.name = name;
		this.result = result;
	}
	
	@Override
	public void run() {
		// 模拟线程执行1000ms
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 模拟线程1和线程3抛出异常
		if(name.equals("1") || name.equals("3"))
			throw new RuntimeException(name + ": throw exception");
		result.put(name, "complete part " + name + "!");
	}
	
	public static void main(String[] args) {
		
		System.out.println("main begin \t=================");
		Map<String, Object> resultMap = new HashMap<String, Object>(8, 1);
		MultiThreadHandler handler = new MultiParallelThreadHandler();
//		ExecutorService service = Executors.newFixedThreadPool(3);
//		MultiThreadHandler handler = new ParallelTaskWithThreadPool(service);
		TestCase task = null;
		// 启动5个子线程作为要处理的并行任务，共同完成结果集resultMap
		for(int i=1; i<=5 ; i++){
			task = new TestCase("" + i, resultMap);
			handler.addTask(task);
		}
		try {
			handler.run();
		} catch (ChildThreadException e) {
			System.out.println(e.getAllStackTraceMessage());
		}
		
		System.out.println(resultMap);
//		service.shutdown();
		System.out.println("main end \t=================");
	}
}