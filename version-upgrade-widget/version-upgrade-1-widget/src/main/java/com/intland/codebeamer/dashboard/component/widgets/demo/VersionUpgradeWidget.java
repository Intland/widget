package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.AbstractWidget;
import com.intland.codebeamer.dashboard.component.widgets.common.WidgetAttributeWrapper;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class VersionUpgradeWidget extends AbstractWidget {

	private static final Logger logger = Logger.getLogger(VersionUpgradeWidget.class);

	private static final String VERSION = "1.0.0";

	public static enum Attribute implements WidgetAttributeWrapper {
		STRING("string", new StringAttribute("String field", false, false));

		private String key;

		private WidgetAttribute<?> defaultValue;

		private Attribute(String key, WidgetAttribute<?> defaultValue) {
			this.key = key;
			this.defaultValue = defaultValue;
		}

		public String getKey() {
			return key;
		}

		public WidgetAttribute<?> getDefaultValue() {
			return defaultValue;
		}

	}

	public static Map<String, WidgetAttribute> getDescriptor() {
		final Map<String, WidgetAttribute> result = new LinkedHashMap<String, WidgetAttribute>();
		result.put(Attribute.STRING.getKey(), Attribute.STRING.getDefaultValue());

		return result;

	}

	private final Renderer<VersionUpgradeWidget> htmlRenderer;
	private final Renderer<VersionUpgradeWidget> editorRenderer;

	public VersionUpgradeWidget(@JsonProperty("id") final String id,
			@JsonProperty("attributes") final Map<String, WidgetAttribute> attributes,
			@JacksonInject("versionUpgradeWidgetHtmlRenderer") final Renderer<VersionUpgradeWidget> htmlRenderer,
			@JacksonInject("versionUpgradeWidgetEditorRenderer") final Renderer<VersionUpgradeWidget> editorRenderer) {
		super(id, attributes);

		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
		
		if(logger.isInfoEnabled()) {
			String loadedAttributes = attributes.entrySet().stream()
				.map(e -> StringUtils.repeat(StringUtils.SPACE, 10) +  e.getKey() + " - " + e.getValue())
				.collect(Collectors.joining(System.lineSeparator()));
			
			logger.info(StringUtils.repeat("*", 30));
			logger.info("Loaded attributes:" + System.lineSeparator() + loadedAttributes);
			logger.info(StringUtils.repeat("*", 30));
		}
	}

	public String getTypeName() {
		return VersionUpgradeWidget.class.getCanonicalName();
	}

	public String renderToHtml(final RenderingContext renderingContext) {
		return htmlRenderer.render(renderingContext, this);
	}

	public String renderEditorToHtml(final RenderingContext renderingContext) {
		return editorRenderer.render(renderingContext, this);
	}

	public String getDefaultTitleKey() {
		return "dashboard.versionUpgrade.widget.name";
	}

	public String getVersion() {
		return VERSION;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
