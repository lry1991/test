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

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.http.HttpService;

import com.polelink.entity.Student;
import com.polelink.entity.User;
import com.polelink.servlet.PikePlatformServlet;
import com.polelink.servlet.testServlet;

public class Activatior implements BundleActivator{

	public static BundleContext context;
	private static HttpService httpService;
	
	
	
	@Override
	public void start(BundleContext context) throws Exception {
		this.context=context;
		Session session = null;
		try {
			/**
			 * 如果使用iterator方法返回列表，对于hibernate而言，它仅仅只是发出取id列表的sql
			 * 在查询相应的具体的某个学生信息时，会发出相应的SQL去取学生信息
			 * 这就是典型的N+1问题
			 * 存在iterator的原因是，有可能会在一个session中查询两次数据，如果使用list每一次都会把所有的对象查询上来
			 * 而是要iterator仅仅只会查询id，此时所有的对象已经存储在一级缓存(session的缓存)中，可以直接获取
			 */
			session = HibernateUtil.openSession();
			Iterator<com.polelink.entity.User> stus = session.createQuery("from User")
					.setFirstResult(0).setMaxResults(50).iterate();
			for(;stus.hasNext();) {
				User stu = stus.next();
				System.out.println(stu.getNickname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub
//		httpService.unregister("/lee");
	}


}
