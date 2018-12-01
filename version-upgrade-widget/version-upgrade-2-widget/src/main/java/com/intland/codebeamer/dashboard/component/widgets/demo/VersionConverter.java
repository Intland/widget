package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.intland.codebeamer.dashboard.component.exception.ValidationErrorType;
import com.intland.codebeamer.dashboard.component.exception.WidgetFieldValidationException;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetAttributeWrapper;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.IntegerAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;

@SuppressWarnings({"unchecked", "rawtypes"})
public class VersionConverter {

	private static final Logger logger = Logger.getLogger(VersionConverter.class);
	
	private String fromVersion;
	
	private String toVersion;

	public VersionConverter(String fromVersion, String toVersion) {
		this.fromVersion = fromVersion;
		this.toVersion = toVersion;
	}

	public void convert(Map<String, WidgetAttribute> loadedAttributes) {
		
		if(StringUtils.equals(fromVersion, "1.0.0") && StringUtils.equals(toVersion, "1.1.0")) {
			
			if(logger.isInfoEnabled()) {
				logger.info(StringUtils.repeat("*", 30));
				logger.info("Convert value of " + VersionUpgradeWidget.OldAttribute.STRING.getKey() + " to " + VersionUpgradeWidget.Attribute.INTEGER);
				logger.info(StringUtils.repeat("*", 30));
			}
			
			StringAttribute oldValue = getWidgetAttribute(loadedAttributes, VersionUpgradeWidget.OldAttribute.STRING);
			IntegerAttribute newValue = (IntegerAttribute) VersionUpgradeWidget.Attribute.INTEGER.getDefaultValue();
			loadedAttributes.put(VersionUpgradeWidget.Attribute.INTEGER.getKey(), new IntegerAttribute(convertToInteger(oldValue), newValue.isRequired(), false));
		}
		
	}

	private int convertToInteger(StringAttribute oldValue) {
		try {
			return Integer.parseInt(oldValue.getValue());
		} catch (Exception e) {
			throw new WidgetFieldValidationException(ValidationErrorType.NOT_INTEGER);
		}
	}

	private <T extends WidgetAttribute> T getWidgetAttribute(Map<String, WidgetAttribute> loadedAttributes, WidgetAttributeWrapper attribute) {
		return (T) loadedAttributes.get(attribute.getKey());
	}
	
}
