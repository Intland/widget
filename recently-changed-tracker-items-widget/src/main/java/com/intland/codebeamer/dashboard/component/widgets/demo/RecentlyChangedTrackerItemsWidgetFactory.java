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
@Qualifier("recentlyChangedTrackerItemsWidgetFactory")
public class RecentlyChangedTrackerItemsWidgetFactory implements WidgetFactory<RecentlyChangedTrackerItemsWidget> {

	private final Renderer<RecentlyChangedTrackerItemsWidget> htmlRenderer;
	private final Renderer<RecentlyChangedTrackerItemsWidget> editorRenderer;
	private final WidgetAttributeMapper widgetAttributeMapper;

	@Autowired
	public RecentlyChangedTrackerItemsWidgetFactory(@Qualifier("recentlyChangedTrackerItemsWidgetHtmlRenderer") final Renderer<RecentlyChangedTrackerItemsWidget> htmlRenderer,
			@Qualifier("recentlyChangedTrackerItemsWidgetEditorRenderer") final Renderer<RecentlyChangedTrackerItemsWidget> editorRenderer,
			final WidgetAttributeMapper widgetAttributeMapper) {
		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
		this.widgetAttributeMapper = widgetAttributeMapper;
	}

	public InjectableValues getInjectableValues() {
		final InjectableValues inject = new InjectableValues.Std()
				.addValue("recentlyChangedTrackerItemsWidgetHtmlRenderer", htmlRenderer)
				.addValue("recentlyChangedTrackerItemsWidgetEditorRenderer", editorRenderer);
		return inject;
	}

	public Class<RecentlyChangedTrackerItemsWidget> getType() {
		return RecentlyChangedTrackerItemsWidget.class;
	}

	public String getTypeName() {
		return RecentlyChangedTrackerItemsWidget.class.getCanonicalName();
	}

	public RecentlyChangedTrackerItemsWidget createInstance() {
		return new RecentlyChangedTrackerItemsWidget(UUID.randomUUID().toString(), RecentlyChangedTrackerItemsWidget.getDescriptor(), htmlRenderer, editorRenderer);
	}

	public RecentlyChangedTrackerItemsWidget createInstance(final String id, final Map<String, String> attributes) {
		return createInstance(id, attributes, true);
	}

	public RecentlyChangedTrackerItemsWidget createInstance(final String id, final Map<String, String> attributes, final boolean validate) {
		final Map<String, WidgetAttribute> widgetAttributes = widgetAttributeMapper.map(attributes,
				RecentlyChangedTrackerItemsWidget.getDescriptor(), validate);

		return new RecentlyChangedTrackerItemsWidget(id, widgetAttributes, htmlRenderer, editorRenderer);
	}

}
