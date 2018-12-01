package com.intland.codebeamer.dashboard.component.widgets.demo;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.demo.VersionUpgradeWidget.Attribute;

@Component
@Qualifier("versionUpgradeWidgetHtmlRenderer")
public class VersionUpgradeWidgetHtmlRenderer implements Renderer<VersionUpgradeWidget> {

	private static final Logger logger = Logger.getLogger(VersionUpgradeWidgetHtmlRenderer.class);
	
	@Autowired
	private MessageSource messageSource;
	
	public String render(final RenderingContext renderingContext, final VersionUpgradeWidget widget) {		
		if(!widget.isValid()) {
			return renderError(renderingContext);
		}
		
		return renderWidget(renderingContext, widget);
	}

	private String renderWidget(final RenderingContext renderingContext, final VersionUpgradeWidget widget) {
		VelocityContext context = new VelocityContext();
		context.put(VersionUpgradeWidget.Attribute.INTEGER.getKey(), getAttributeValue(widget, VersionUpgradeWidget.Attribute.INTEGER));
		return new VelocityRenderer("index.vm").render(renderingContext, context);
	}

	private String renderError(final RenderingContext renderingContext) {
		VelocityContext context = new VelocityContext();
		context.put("errorMessage", getMessage(renderingContext, "dashboard.versionUpgrade.widget.error"));
		return new VelocityRenderer("error.vm").render(renderingContext, context);
	}

	private String getMessage(final RenderingContext renderingContext, String code) {
		return messageSource.getMessage(code, new Object[0], renderingContext.getRequest().getLocale());
	}

	@SuppressWarnings("unchecked")
	private <V> V getAttributeValue(final VersionUpgradeWidget widget, Attribute stringValidated) {
		WidgetAttribute<?> attributeValue = getAttribute(widget, stringValidated);
		return attributeValue == null ? null : (V) attributeValue.getValue();
	}

	@SuppressWarnings("unchecked")
	private <T extends WidgetAttribute<?>> T getAttribute(final VersionUpgradeWidget widget, Attribute stringValidated) {
		return (T) widget.getAttributes().get(stringValidated.getKey());
	}

}
