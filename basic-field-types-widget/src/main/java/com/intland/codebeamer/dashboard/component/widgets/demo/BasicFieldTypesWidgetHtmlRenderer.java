package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.io.StringWriter;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.demo.BasicFieldTypesWidget.Attribute;

@Component
@Qualifier("basicFieldTypesWidgetHtmlRenderer")
public class BasicFieldTypesWidgetHtmlRenderer implements Renderer<BasicFieldTypesWidget> {

	private static final String TEMPLATE = "index.vm";
	
	private VelocityEngine velocityEngine;

	public BasicFieldTypesWidgetHtmlRenderer() {
		velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
	}

	public String render(final RenderingContext renderingContext, final BasicFieldTypesWidget widget) {
		
		VelocityContext context = new VelocityContext();
		context.put(BasicFieldTypesWidget.Attribute.STRING_NON_REQUIRED.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.STRING_NON_REQUIRED));
		context.put(BasicFieldTypesWidget.Attribute.STRING_REQUIRED.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.STRING_REQUIRED));
		context.put(BasicFieldTypesWidget.Attribute.EMAIL.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.EMAIL));
		context.put(BasicFieldTypesWidget.Attribute.BOOLEAN.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.BOOLEAN));
		context.put(BasicFieldTypesWidget.Attribute.INTEGER.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.INTEGER));
		context.put(BasicFieldTypesWidget.Attribute.TEXT.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.TEXT));
		
		return render(context);
	}

	private String render(VelocityContext context) {
		StringWriter writer = new StringWriter();
		velocityEngine.getTemplate(TEMPLATE).merge(context, writer);
		return writer.toString();
	}

	@SuppressWarnings("unchecked")
	private <V> V getAttributeValue(final BasicFieldTypesWidget widget, Attribute stringValidated) {
		WidgetAttribute<?> attributeValue  = getAttribute(widget, stringValidated);
		return attributeValue == null ? null : (V) attributeValue.getValue();
	}
	
	@SuppressWarnings("unchecked")
	private <T extends WidgetAttribute<?>> T getAttribute(final BasicFieldTypesWidget widget, Attribute stringValidated) {
		return (T) widget.getAttributes().get(stringValidated.getKey());
	}

}
