package com.intland.codebeamer.dashboard.component.widgets.demo;

import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.demo.VersionUpgradeWidget.Attribute;

@Component
@Qualifier("versionUpgradeWidgetHtmlRenderer")
public class VersionUpgradeWidgetHtmlRenderer implements Renderer<VersionUpgradeWidget> {

	public String render(final RenderingContext renderingContext, final VersionUpgradeWidget widget) {
		VelocityContext context = new VelocityContext();
		context.put(VersionUpgradeWidget.Attribute.STRING.getKey(), getAttributeValue(widget, VersionUpgradeWidget.Attribute.STRING));

		return new VelocityRenderer("index.vm").render(renderingContext, context);
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
