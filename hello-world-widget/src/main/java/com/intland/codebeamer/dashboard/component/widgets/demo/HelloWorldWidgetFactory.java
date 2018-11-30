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
@Qualifier("helloWorldWidgetFactory")
public class HelloWorldWidgetFactory implements WidgetFactory<HelloWorldWidget> {

	private final Renderer<HelloWorldWidget> htmlRenderer;
	private final Renderer<HelloWorldWidget> editorRenderer;
	private final WidgetAttributeMapper widgetAttributeMapper;

	@Autowired
	public HelloWorldWidgetFactory(@Qualifier("helloWorldWidgetHtmlRenderer") final Renderer<HelloWorldWidget> htmlRenderer,
			@Qualifier("helloWorldWidgetEditorRenderer") final Renderer<HelloWorldWidget> editorRenderer,
			final WidgetAttributeMapper widgetAttributeMapper) {
		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
		this.widgetAttributeMapper = widgetAttributeMapper;
	}

	public InjectableValues getInjectableValues() {
		final InjectableValues inject = new InjectableValues.Std().addValue("helloWorldWidgetHtmlRenderer", htmlRenderer)
				.addValue("helloWorldWidgetEditorRenderer", editorRenderer);
		return inject;
	}

	public Class<HelloWorldWidget> getType() {
		return HelloWorldWidget.class;
	}

	public String getTypeName() {
		return HelloWorldWidget.class.getCanonicalName();
	}

	public HelloWorldWidget createInstance() {
		return new HelloWorldWidget(UUID.randomUUID().toString(), HelloWorldWidget.getDescriptor(), htmlRenderer, editorRenderer);
	}

	public HelloWorldWidget createInstance(final String id, final Map<String, String> attributes) {
		return createInstance(id, attributes, true);
	}

	public HelloWorldWidget createInstance(final String id, final Map<String, String> attributes, final boolean validate) {
		final Map<String, WidgetAttribute> widgetAttributes = widgetAttributeMapper.map(attributes,
				HelloWorldWidget.getDescriptor(), validate);

		return new HelloWorldWidget(id, widgetAttributes, htmlRenderer, editorRenderer);
	}

}
