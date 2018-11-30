package com.intland.codebeamer.dashboard.component.widgets.demo;

import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.interfaces.WidgetInformation;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetCategory;

@Component
public class HelloWorldWidgetInformation implements WidgetInformation {

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
		return "dashboard.helloWorld.widget.name";
	}

	public String getShortDescription() {
		return "dashboard.helloWorld.widget.short.description";
	}

	public HelloWorldWidgetFactory getFactory() {
		return null;
	}

	public String getType() {
		return HelloWorldWidget.class.getCanonicalName();
	}
}
