package com.intland.codebeamer.wiki.plugins;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;

import com.ecyrd.jspwiki.WikiContext;
import com.intland.codebeamer.dashboard.component.exception.ValidationErrorType;
import com.intland.codebeamer.dashboard.component.exception.WidgetFieldValidationException;
import com.intland.codebeamer.utils.TemplateRenderer;
import com.intland.codebeamer.utils.TemplateRenderer.Parameters;

public class VelocityRenderer {

	private String template;

	public VelocityRenderer(String template) {
		this.template = template;
	}

	public String render(final WikiContext renderingContext, VelocityContext context) {
		try {
			String template = loadTemplateFromClasspath();
			return render(renderingContext, context, template);
		} catch (Exception e) {
			throw new WidgetFieldValidationException(ValidationErrorType.UNKNOWN, Arrays.asList(e.getMessage()));
		}
	}

	private String render(final WikiContext renderingContext, VelocityContext context, String templateContent) {
		final TemplateRenderer templateRenderer = TemplateRenderer.getInstance();
		return templateRenderer.render(templateContent, context, getLocaleParameter(renderingContext));
	}

	private Parameters getLocaleParameter(final WikiContext renderingContext) {
		return new Parameters(renderingContext.getHttpRequest().getLocale(), false);
	}

	private String loadTemplateFromClasspath() throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream("/" + template), StandardCharsets.UTF_8);
	}

}
