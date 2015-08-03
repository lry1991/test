package com.polelink.Icon.Internal;

import java.net.URL;
import java.util.Iterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.osgi.framework.BundleContext;

import com.polelink.entity.User;


public class HibernateUtil {
	private final static SessionFactory FACTORY = buildSessionFactory();
	private static SessionFactory buildSessionFactory() {
		URL configURL = Activatior.context.getBundle().getEntry("hibernate.cfg.xml");
		Configuration cfg = new Configuration().configure(configURL);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(cfg.getProperties()).buildServiceRegistry();
		SessionFactory factory = cfg.buildSessionFactory(serviceRegistry);
		return factory;
	}
	
	public static SessionFactory getSessionFactory() {
		return FACTORY;
	}
	
	public static Session openSession() {
		return FACTORY.openSession();
	}
	
	public static void close(Session session) {
		if(session!=null) session.close();
	}
	
	public static void main(String[] args) {
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
}
