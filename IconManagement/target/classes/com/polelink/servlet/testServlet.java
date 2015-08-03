/**
 *  Copyright (c) by Shanghai PoleLink Information Technology Co.,Ltd. All rights reserved.
 *
 *  This software is copyright protected and proprietary to Shanghai PoleLink
 *  Information Technology. Shanghai PoleLink Information Technology Co.,Ltd
 *  grants to you only those rights as set out in the license conditions.
 *  All other rights remain with Shanghai PoleLink Information Technology Co.,Ltd.
 *
 **/
package com.polelink.servlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import com.polelink.Ui.ValoThemeUI;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

@WebServlet(urlPatterns = { "/le" }, asyncSupported = true, initParams = {
		@WebInitParam(name = "org.atmosphere.cpr.sessionSupport", value = "true"),
		@WebInitParam(name = "org.atmosphere.cpr.sessionFrom", value = "pike"),
		@WebInitParam(name = "org.atmosphere.cpr.annotation.useBytecodeProcessor", value = "true"),
		@WebInitParam(name = "org.atmosphere.cpr.broadcaster.maxProcessingThreads", value = "10"),
		@WebInitParam(name = "org.atmosphere.cpr.broadcaster.maxAsyncWriteThreads", value = "10") })
@VaadinServletConfiguration(ui = ValoThemeUI.class, productionMode = true, widgetset = "com.polelink.Internal.LicmWidgetset", heartbeatInterval = 300,closeIdleSessions=true)
public class testServlet  extends VaadinServlet{
	
}
