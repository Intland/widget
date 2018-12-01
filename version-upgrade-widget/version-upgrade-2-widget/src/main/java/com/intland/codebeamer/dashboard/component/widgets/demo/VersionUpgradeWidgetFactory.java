package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.InjectableValues;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.common.interfaces.WidgetFactory;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttributeMapper;

@Component
@Qualifier("versionUpgradeWidgetFactory")
public class VersionUpgradeWidgetFactory implements WidgetFactory<VersionUpgradeWidget> {

	private final Renderer<VersionUpgradeWidget> htmlRenderer;
	private final Renderer<VersionUpgradeWidget> editorRenderer;
	private final WidgetAttributeMapper widgetAttributeMapper;

	@Autowired
	public VersionUpgradeWidgetFactory(@Qualifier("versionUpgradeWidgetHtmlRenderer") final Renderer<VersionUpgradeWidget> htmlRenderer,
			@Qualifier("versionUpgradeWidgetEditorRenderer") final Renderer<VersionUpgradeWidget> editorRenderer,
			final WidgetAttributeMapper widgetAttributeMapper) {
		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
		this.widgetAttributeMapper = widgetAttributeMapper;
	}

	public InjectableValues getInjectableValues() {
		final InjectableValues inject = new InjectableValues.Std()
				.addValue("versionUpgradeWidgetHtmlRenderer", htmlRenderer)
				.addValue("versionUpgradeWidgetEditorRenderer", editorRenderer);
		return inject;
	}

	public Class<VersionUpgradeWidget> getType() {
		return VersionUpgradeWidget.class;
	}

	public String getTypeName() {
		return VersionUpgradeWidget.class.getCanonicalName();
	}

	public VersionUpgradeWidget createInstance() {
		return new VersionUpgradeWidget(UUID.randomUUID().toString(), VersionUpgradeWidget.getDescriptor(), htmlRenderer, editorRenderer);
	}

	public VersionUpgradeWidget createInstance(final String id, final Map<String, String> attributes) {
		return createInstance(id, attributes, true);
	}

	public VersionUpgradeWidget createInstance(final String id, final Map<String, String> attributes, final boolean validate) {
		final Map<String, WidgetAttribute> widgetAttributes = widgetAttributeMapper.map(attributes,
				VersionUpgradeWidget.getDescriptor(), validate);

		return new VersionUpgradeWidget(id, widgetAttributes, htmlRenderer, editorRenderer);
	}

}
