package com.polelink.servlet;

import com.polelink.Ui.ValoThemeUI;
import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.ui.UI;

public class PlatformUIProvider extends UIProvider {

	@Override
	public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
		String path = event.getRequest().getPathInfo();
		if (path.startsWith("/mobile")) {
			return ValoThemeUI.class;
		} else {

			return ValoThemeUI.class;
		}
	}

	@Override
	public String getTheme(UICreateEvent event) {
		// mobile or pike?
		return "licm";
	}

	@Override
	public String getWidgetset(UICreateEvent event) {
		return "com.polelink.Internal.LicmWidgetset";
	}
}