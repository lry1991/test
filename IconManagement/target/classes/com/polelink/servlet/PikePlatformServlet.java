/**
 *  Copyright (c) by Shanghai PoleLink Information Technology Co.,Ltd. All rights reserved.
 *
 *  This software is copyright protected and proprietary to Shanghai PoleLink
 *  Information Technology. Shanghai PoleLink Information Technology Co.,Ltd
 *  grants to you only those rights as set out in the license conditions.
 *  All other rights remain with Shanghai PoleLink Information Technology Co.,Ltd.
 **/

package com.polelink.servlet;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.polelink.Ui.ValoThemeUI;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.ServiceException;
import com.vaadin.server.SessionDestroyEvent;
import com.vaadin.server.SessionDestroyListener;
import com.vaadin.server.SessionInitEvent;
import com.vaadin.server.SessionInitListener;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.WrappedSession;

/**
 * An implementation of VaadinServlet that uses SimpleUI as its base UI.
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/pike" }, asyncSupported = true, initParams = {
		@WebInitParam(name = "UIProvider", value = "com.polelink.servlet.PlatformUIProvider"),
		@WebInitParam(name = "org.atmosphere.cpr.sessionSupport", value = "true"),
		@WebInitParam(name = "org.atmosphere.cpr.sessionFrom", value = "pike"),
		@WebInitParam(name = "org.atmosphere.cpr.annotation.useBytecodeProcessor", value = "true"),
		@WebInitParam(name = "org.atmosphere.cpr.broadcaster.maxProcessingThreads", value = "10"),
		@WebInitParam(name = "org.atmosphere.cpr.broadcaster.maxAsyncWriteThreads", value = "10") })
// heartbeatInterval = 300 means 5mins heartbeat
@VaadinServletConfiguration(ui = ValoThemeUI.class, productionMode = true, widgetset = "com.polelink.Internal.LicmWidgetset", heartbeatInterval =300)
public class PikePlatformServlet extends VaadinServlet implements
		SessionInitListener, SessionDestroyListener {

	public PikePlatformServlet() {// 加这个为了初始化SessionSupport
		super();
		// AtmosphereSessionListener listener = new AtmosphereSessionListener();
	}

	@Override
	protected VaadinServletService createServletService(
			DeploymentConfiguration deploymentConfiguration)
			throws ServiceException {
		VaadinServletService servletService = super
				.createServletService(deploymentConfiguration);
		servletService.addSessionInitListener(new SessionInitListener() {
			@Override
			public void sessionInit(SessionInitEvent sessionInitEvent)
					throws ServiceException {
				sessionInitEvent.getSession().addUIProvider(
						new PlatformUIProvider());
			}
		});
		return servletService;
	}

	@Override
	protected void servletInitialized() throws ServletException {
		super.servletInitialized();
		getService().addSessionInitListener(this);
		getService().addSessionDestroyListener(this);
		

	}

	@Override
	public void sessionInit(SessionInitEvent event) throws ServiceException {

	}

	@Override
	public void sessionDestroy(SessionDestroyEvent event) {


	}
}