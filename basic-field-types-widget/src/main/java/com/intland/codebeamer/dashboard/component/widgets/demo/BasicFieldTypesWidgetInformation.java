package com.intland.codebeamer.dashboard.component.widgets.demo;

import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.interfaces.WidgetInformation;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetCategory;

@Component
public class BasicFieldTypesWidgetInformation implements WidgetInformation {

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
		return "dashboard.basicFieldTypes.widget.name";
	}

	public String getShortDescription() {
		return "dashboard.basicFieldTypes.widget.short.description";
	}

	public BasicFieldTypesWidgetFactory getFactory() {
		return null;
	}

	public String getType() {
		return BasicFieldTypesWidget.class.getCanonicalName();
	}
}
