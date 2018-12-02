package com.intland.codebeamer.dashboard.component.widgets.demo;

import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.interfaces.WidgetInformation;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetCategory;

@Component
public class RecentlyChangedTrackerItemsWidgetInformation implements WidgetInformation {

	public String getCategory() {
		return WidgetCategory.OTHER.getName();
	}

	public String getImagePreviewUrl() {
		return "";
	}

	public String getKnowledgeBaseUrl() {
		return "";
	}

	public String getVendor() {
		return "Intland";
	}

	public String getName() {
		return "dashboard.recentlyChangedTrackerItems.widget.name";
	}

	public String getShortDescription() {
		return "dashboard.recentlyChangedTrackerItems.widget.short.description";
	}

	public RecentlyChangedTrackerItemsWidgetFactory getFactory() {
		return null;
	}

	public String getType() {
		return RecentlyChangedTrackerItemsWidget.class.getCanonicalName();
	}
}
