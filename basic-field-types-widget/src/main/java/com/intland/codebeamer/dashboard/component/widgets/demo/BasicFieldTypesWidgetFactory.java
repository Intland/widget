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
@Qualifier("basicFieldTypesWidgetFactory")
public class BasicFieldTypesWidgetFactory implements WidgetFactory<BasicFieldTypesWidget> {

	private final Renderer<BasicFieldTypesWidget> htmlRenderer;
	private final Renderer<BasicFieldTypesWidget> editorRenderer;
	private final WidgetAttributeMapper widgetAttributeMapper;

	@Autowired
	public BasicFieldTypesWidgetFactory(@Qualifier("basicFieldTypesWidgetHtmlRenderer") final Renderer<BasicFieldTypesWidget> htmlRenderer,
			@Qualifier("basicFieldTypesWidgetEditorRenderer") final Renderer<BasicFieldTypesWidget> editorRenderer,
			final WidgetAttributeMapper widgetAttributeMapper) {
		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
		this.widgetAttributeMapper = widgetAttributeMapper;
	}

	public InjectableValues getInjectableValues() {
		final InjectableValues inject = new InjectableValues.Std()
				.addValue("basicFieldTypesWidgetHtmlRenderer", htmlRenderer)
				.addValue("basicFieldTypesWidgetEditorRenderer", editorRenderer);
		return inject;
	}

	public Class<BasicFieldTypesWidget> getType() {
		return BasicFieldTypesWidget.class;
	}

	public String getTypeName() {
		return BasicFieldTypesWidget.class.getCanonicalName();
	}

	public BasicFieldTypesWidget createInstance() {
		return new BasicFieldTypesWidget(UUID.randomUUID().toString(), BasicFieldTypesWidget.getDescriptor(), htmlRenderer, editorRenderer);
	}

	public BasicFieldTypesWidget createInstance(final String id, final Map<String, String> attributes) {
		return createInstance(id, attributes, true);
	}

	public BasicFieldTypesWidget createInstance(final String id, final Map<String, String> attributes, final boolean validate) {
		final Map<String, WidgetAttribute> widgetAttributes = widgetAttributeMapper.map(attributes,
				BasicFieldTypesWidget.getDescriptor(), validate);

		return new BasicFieldTypesWidget(id, widgetAttributes, htmlRenderer, editorRenderer);
	}

}
