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

import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.Enumeration;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;

public abstract class BundleRegisterAcitivator implements BundleActivator{

	public static BundleContext context;
	private static HttpService httpService;
	
	public void registerBundle(Bundle bundle) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException{
		FutureUtil futureUtil=new FutureUtil();

		futureUtil.asyncExecute(this,
				BundleRegisterAcitivator.class

				.getDeclaredMethod("asnycRegisterBundle", new Class[] {

						Bundle.class }), new Object[] {
			bundle });

		// 启动异步计算结果输出线程，该线程扫描异步计算Futrue的状态，如果已经完成，则输出异步计算结果

		FutureUtil.OutputResult output = new FutureUtil.OutputResult();

		output.setFutureContext(futureUtil.getFutureContext());

		Thread resultThread = new Thread(output);

		resultThread.start();

		futureUtil.stopExecutorService();
	}
	
	public void asnycRegisterBundle(Bundle bundle){
		ClassLoader classLoader = BundleUtil.getBundleClassLoader(bundle);
		Enumeration<URL> classUrls = bundle.findEntries("/", "*.class",
				true);
		if (classUrls == null || !classUrls.hasMoreElements())
			return;
		
		while (classUrls.hasMoreElements()) {
			URL url = classUrls.nextElement();
			String startSymbo = "url:bundle://";
			// url:bundle://pike.box-123-0-0/com/polelink/pike/box/data/MyConverterFactory.class
			int beginInx = url.toString().indexOf("/",
					startSymbo.length() + 1);
			int endInx = url.toString().lastIndexOf(".class");
			if (endInx < 0 || endInx <= beginInx)
				endInx = url.toString().length();
			String className = url.toString().substring(beginInx + 1,
					endInx);
			className = className.replace("/", ".");
			Class clazz = null;
				if (className.indexOf("$") > 0)// 说明是内部类不处理
					continue;
				try {
					clazz = classLoader.loadClass(className);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (clazz == null)
					continue;
				if (Modifier.isAbstract(clazz.getModifiers()))// 如果是抽象类不处理
					continue;
				
				if (IPlugin.class.isAssignableFrom(clazz)
						&& !clazz.isInterface()) {
					try {
						System.out.println("register----------------------------");
						PluginRegisterBroadcaster.registerPlugin((IPlugin)clazz.newInstance());
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
		}
		
	}




}
