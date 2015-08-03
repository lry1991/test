/**
 *  Copyright (c) by Shanghai PoleLink Information Technology Co.,Ltd. All rights reserved.
 *
 *  This software is copyright protected and proprietary to Shanghai PoleLink
 *  Information Technology. Shanghai PoleLink Information Technology Co.,Ltd
 *  grants to you only those rights as set out in the license conditions.
 *  All other rights remain with Shanghai PoleLink Information Technology Co.,Ltd.
 *
 **/
package com.polelink.Ui;

import com.lexaden.breadcrumb.Breadcrumb;
import com.lexaden.breadcrumb.BreadcrumbLayout;
import com.vaadin.ui.Button.ClickEvent;


public class BreadCrumbExt extends Breadcrumb {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public int getComponentCount() {
        if (getCompositionRoot() != null) {
            final BreadcrumbLayout compositionRoot = (BreadcrumbLayout) getCompositionRoot();
            return compositionRoot.getComponentCount();
        }
        return super.getComponentCount();
    }
	

	
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		
		event.getButton().getData();
	
	ValoThemeUI.getNaviagateto(event.getButton().getData().toString());
	select(event.getButton());
//	 (ValoThemeUI)(UI.getCurrent())
    }
}
