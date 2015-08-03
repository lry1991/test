/**
 *  Copyright (c) by Shanghai PoleLink Information Technology Co.,Ltd. All rights reserved.
 *
 *  This software is copyright protected and proprietary to Shanghai PoleLink
 *  Information Technology. Shanghai PoleLink Information Technology Co.,Ltd
 *  grants to you only those rights as set out in the license conditions.
 *  All other rights remain with Shanghai PoleLink Information Technology Co.,Ltd.
 *
 **/
package com.polelink.Icon.Internal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureUtil {
	ExecutorService executorService;
	// 保存异步计算的Future
	private FutureContext<Object> futureContext;

	public FutureContext<Object> getFutureContext() {
		return futureContext;
	}

	public void setFutureContext(FutureContext<Object> futureContext) {
		this.futureContext = futureContext;
	}

	public FutureUtil() {
		this.executorService = Executors.newFixedThreadPool(1);
		this.futureContext = new FutureContext<Object>();
	}

	public static class FutureContext<T> {

		private List<Future<T>> futureList = new ArrayList<Future<T>>();

		public void addFuture(Future<T> future) {
			this.futureList.add(future);
		}

		public List<Future<T>> getFutureList() {
			return this.futureList;
		}
	}

	public void asyncExecute(final Object classObject, final Method method,
			final Object... args) {
		/**
		 * 开启异步计算，每个异步计算线程随机sleep几秒来模拟计算耗时。
		 */
		Future<Object> future = this.executorService
				.submit(new Callable<Object>() {
					// @Override
					public Object call() throws Exception {
						try {
							Object result = method.invoke(classObject, args);
							return result;
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				});
		// 每个异步计算的结果存放在context中
		this.futureContext.addFuture(future);
	}

	public void stopExecutorService() {
		if (this.executorService != null)
			executorService.shutdown();
	}

	public static class OutputResult implements Runnable {

		private FutureContext<Object> futureContext;

		public void setFutureContext(FutureContext<Object> futureContext) {
			this.futureContext = futureContext;
		}

		public FutureContext<Object> getFutureContext() {
			return futureContext;
		}

		// @Override
		public void run() {
			List<Future<Object>> list = this.futureContext.getFutureList();

			for (Future<Object> future : list) {
				this.outputResultFromFuture(future);
			}
		}

		private void outputResultFromFuture(Future<Object> future) {
			try {
				while (true) {
					if (future.isDone() && !future.isCancelled()) {
						break;
					} else {
						Thread.sleep(1000);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
