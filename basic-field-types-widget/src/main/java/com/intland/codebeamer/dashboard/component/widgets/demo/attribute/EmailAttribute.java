package com.intland.codebeamer.dashboard.component.widgets.demo.attribute;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.log4j.Logger;

import com.intland.codebeamer.dashboard.component.exception.ValidationErrorType;
import com.intland.codebeamer.dashboard.component.exception.WidgetFieldValidationException;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;

public class EmailAttribute extends StringAttribute {

	private static final Logger logger = Logger.getLogger(EmailAttribute.class);
	
	public EmailAttribute(String value) {
		super(value, false, true);
	}

	@Override
	public WidgetAttribute<String> convert(String email) {
		if (StringUtils.isEmpty(email) || EmailValidator.getInstance().isValid(email)) {
			return super.convert(email);
		}
		 
		logger.warn(String.format("'%s' is not valid email address", email));
		throw new WidgetFieldValidationException(ValidationErrorType.UNKNOWN, Arrays.asList("Error"));
	}

}