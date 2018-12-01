package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.util.LinkedHashMap;
import java.util.Map;

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
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.BooleanAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.IntegerAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.StringAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.TextAttribute;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.demo.attribute.EmailAttribute;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class BasicFieldTypesWidget extends AbstractWidget {

	private static final Logger logger = Logger.getLogger(BasicFieldTypesWidget.class);

	private static final String VERSION = "1.0.0";

	public static enum Attribute implements WidgetAttributeWrapper {
		STRING_NON_REQUIRED("string_non_required", new StringAttribute("Non required string field", false, false)),
		STRING_REQUIRED("string_required", new StringAttribute("Required string field", true, false)),
		EMAIL("email", new EmailAttribute(StringUtils.EMPTY)),
		BOOLEAN("boolean", new BooleanAttribute(Boolean.TRUE, false, false)),
		INTEGER("integer", new IntegerAttribute(Integer.valueOf(2), false, false)),
		TEXT("markup", new TextAttribute(StringUtils.EMPTY, false, false));

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

		result.put(Attribute.STRING_NON_REQUIRED.getKey(), Attribute.STRING_NON_REQUIRED.getDefaultValue());
		result.put(Attribute.STRING_REQUIRED.getKey(), Attribute.STRING_REQUIRED.getDefaultValue());
		result.put(Attribute.EMAIL.getKey(), Attribute.EMAIL.getDefaultValue());
		result.put(Attribute.BOOLEAN.getKey(), Attribute.BOOLEAN.getDefaultValue());
		result.put(Attribute.INTEGER.getKey(), Attribute.INTEGER.getDefaultValue());
		result.put(Attribute.TEXT.getKey(), Attribute.TEXT.getDefaultValue());

		return result;

	}

	private final Renderer<BasicFieldTypesWidget> htmlRenderer;
	private final Renderer<BasicFieldTypesWidget> editorRenderer;

	public BasicFieldTypesWidget(@JsonProperty("id") final String id,
			@JsonProperty("attributes") final Map<String, WidgetAttribute> attributes,
			@JacksonInject("basicFieldTypesWidgetHtmlRenderer") final Renderer<BasicFieldTypesWidget> htmlRenderer,
			@JacksonInject("basicFieldTypesWidgetEditorRenderer") final Renderer<BasicFieldTypesWidget> editorRenderer) {
		super(id, attributes);

		this.htmlRenderer = htmlRenderer;
		this.editorRenderer = editorRenderer;
	}

	public String getTypeName() {
		return BasicFieldTypesWidget.class.getCanonicalName();
	}

	public String renderToHtml(final RenderingContext renderingContext) {
		return htmlRenderer.render(renderingContext, this);
	}

	public String renderEditorToHtml(final RenderingContext renderingContext) {
		return editorRenderer.render(renderingContext, this);
	}

	public String getDefaultTitleKey() {
		return "dashboard.basicFieldTypes.widget.name";
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
