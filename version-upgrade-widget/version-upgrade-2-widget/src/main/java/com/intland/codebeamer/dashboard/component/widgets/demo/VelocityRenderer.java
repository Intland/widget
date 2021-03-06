package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.exception.ValidationErrorType;
import com.intland.codebeamer.dashboard.component.exception.WidgetFieldValidationException;
import com.intland.codebeamer.utils.TemplateRenderer;
import com.intland.codebeamer.utils.TemplateRenderer.Parameters;

public class VelocityRenderer {

	private String template;

	public VelocityRenderer(String template) {
		this.template = template;
	}

	public String render(final RenderingContext renderingContext, VelocityContext context) {
		try {
			String template = loadTemplateFromClasspath();
			return render(renderingContext, context, template);
		} catch (Exception e) {
			throw new WidgetFieldValidationException(ValidationErrorType.UNKNOWN, Arrays.asList(e.getMessage()));
		}
	}

	private String render(final RenderingContext renderingContext, VelocityContext context, String templateContent) {
		final TemplateRenderer templateRenderer = TemplateRenderer.getInstance();
		return templateRenderer.render(templateContent, context, getLocaleParameter(renderingContext));
	}

	private Parameters getLocaleParameter(final RenderingContext renderingContext) {
		return new Parameters(renderingContext.getRequest().getLocale(), false);
	}

	private String loadTemplateFromClasspath() throws IOException {
		return IOUtils.toString(this.getClass().getResourceAsStream("/" + template), StandardCharsets.UTF_8);
	}

}
