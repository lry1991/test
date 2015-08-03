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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class IconSelector extends VerticalLayout {
	private boolean multiSelect;
	private List<FontAwesome> selectedIcons = new ArrayList<FontAwesome>();
	private Map<FontAwesome, CheckBox> selectedMap = new HashMap<FontAwesome, CheckBox>();
	private Map<FontAwesome, CheckBox> checkBoxMap = new HashMap<FontAwesome, CheckBox>();
	int pageCount;
	int totalCount;
	int pageSize = 80;
	int curentPage = 1;
	GridLayout gridLayout = new GridLayout();
	HorizontalLayout pageLayout;

	// private Map<Integer, List<FontAwesome>> pageMap = new
	// HashMap<Integer,List<FontAwesome>>();

	public IconSelector(boolean multiSelect) {
		this.multiSelect = multiSelect;
		gridLayout.setColumns(4);
		gridLayout.setRows(20);
		gridLayout.setSizeFull();
		this.setSpacing(true);
		this.setSizeFull();
		buildCheckBoxLayout();
		buildPageLayout();
		refreshIcon(curentPage);
		this.addComponent(gridLayout);
		this.addComponent(pageLayout);
		this.setExpandRatio(gridLayout, 9);
		this.setExpandRatio(pageLayout, 1);

	}

	private void buildCheckBoxLayout() {
		// pageCount= FontAwesome.values().length/80+1;
		totalCount = FontAwesome.values().length;
		pageCount = (int) Math.ceil((double) totalCount / (double) pageSize);
		for (FontAwesome icon : FontAwesome.values()) {
			List<FontAwesome> list = new ArrayList<FontAwesome>();
			final CheckBox checkBox = new CheckBox(icon.name());
			checkBox.setIcon(icon);
			checkBox.setValue(false);
			checkBox.setData(icon);
			// this.addComponent(checkBox);
			checkBox.addValueChangeListener(new ValueChangeListener() {

				@Override
				public void valueChange(
						com.vaadin.data.Property.ValueChangeEvent event) {
					// TODO Auto-generated method stub
					selectIcon(checkBox);
				}
			});
			checkBoxMap.put(icon, checkBox);

		}
	}

	private void selectIcon(CheckBox checkBox) {
		FontAwesome icon = (FontAwesome) checkBox.getData();
		if (checkBox.getValue()) {
			if (multiSelect) {
				if (!selectedIcons.contains(icon)) {
					selectedIcons.add(icon);
					selectedMap.put(icon, checkBox);
				}
			} else {
				selectedIcons.clear();
				selectedMap.clear();
				selectedIcons.add(icon);
				selectedMap.put(icon, checkBox);
			}
		} else {
			if (selectedIcons.contains(icon)) {
				selectedIcons.remove(icon);
				selectedMap.remove(icon);
			}
		}
	}

	public List<FontAwesome> getSelectedIcons() {
		return selectedIcons;
	}

	public void setSelectedIcons(List<FontAwesome> selectedIcons) {
		if (selectedMap.size() > 0) {
			for (FontAwesome icon : selectedMap.keySet()) {
				CheckBox cb = selectedMap.get(icon);
				cb.setValue(false);
			}
			selectedIcons.clear();
			selectedMap.clear();
		}
		if (selectedIcons != null && selectedIcons.size() > 0) {
			for (FontAwesome icon : selectedIcons) {
				CheckBox cb = checkBoxMap.get(icon);
				if (cb != null) {
					cb.setValue(true);
				}
			}
		}
	}

	public void buildPageLayout() {
		pageLayout=new HorizontalLayout();
		pageLayout.setSpacing(true);
		Button preButton = new Button("<");
		final Label label = new Label();
		preButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(curentPage==1){
					return;
				}else{
					curentPage=curentPage-1;
					refreshIcon(curentPage);
					label.setValue(curentPage + "/" + pageCount);
				}
				
			}
		});

		Button nextButton = new Button(">");
		nextButton.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(curentPage==pageCount){
					return;
				}else{
					curentPage=curentPage+1;
					refreshIcon(curentPage);
					label.setValue(curentPage + "/" + pageCount);
				}
			}
		});
		pageLayout.addComponent(preButton);
		pageLayout.addComponent(label);
		label.setValue(curentPage + "/" + pageCount);
		pageLayout.addComponent(nextButton);

	

	}

	public void refreshIcon(int page) {
		gridLayout.removeAllComponents();
//		for (FontAwesome awesome : FontAwesome.values()) {

			int start = (page - 1) * pageSize + 1;
			if(page==pageCount){
				for (int i = start; i < FontAwesome.values().length; i++) {

					gridLayout.addComponent(checkBoxMap.get(FontAwesome.values()[i]));
				}
			}else{
			for (int i = start; i < (start + pageSize); i++) {

				gridLayout.addComponent(checkBoxMap.get(FontAwesome.values()[i]));
			}
			}

//		}
	}

}