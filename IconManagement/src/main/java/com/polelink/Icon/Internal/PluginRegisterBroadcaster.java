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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PluginRegisterBroadcaster implements Serializable {
	static ExecutorService executorService = Executors
			.newSingleThreadExecutor();

	public interface PluginRegisterListener {
		public void registerPlugin( IPlugin instance);


	}


	public static void listenerChannel(final String destName) {

	}

	private static LinkedList<PluginRegisterListener> listeners = new LinkedList<PluginRegisterListener>();

	public static synchronized void register(PluginRegisterListener listener) {
		listeners.add(listener);
	}

	public static synchronized void unregister(PluginRegisterListener listener) {
		listeners.remove(listener);
	}

	public static synchronized void registerPlugin(final IPlugin instance) {
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				for (PluginRegisterListener listener : listeners) {
					listener.registerPlugin(instance);
				}
			}
		});
	}
}
