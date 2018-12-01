package com.intland.codebeamer.dashboard.component.widgets.demo;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.exception.ValidationErrorType;
import com.intland.codebeamer.dashboard.component.exception.WidgetFieldValidationException;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.demo.BasicFieldTypesWidget.Attribute;
import com.intland.codebeamer.remoting.DescriptionFormat;
import com.intland.codebeamer.utils.TemplateRenderer;
import com.intland.codebeamer.utils.TemplateRenderer.Parameters;
import com.intland.codebeamer.wiki.CodeBeamerWikiContext;
import com.intland.codebeamer.wiki.WikiMarkupProcessor;

@Component
@Qualifier("basicFieldTypesWidgetHtmlRenderer")
public class BasicFieldTypesWidgetHtmlRenderer implements Renderer<BasicFieldTypesWidget> {
	
	private static final String TEMPLATE = "index.vm";

	@Autowired
	private WikiMarkupProcessor wikiMarkupProcessor;
	
	public String render(final RenderingContext renderingContext, final BasicFieldTypesWidget widget) {
		
		String markup = getAttributeValue(widget, BasicFieldTypesWidget.Attribute.TEXT);

		VelocityContext context = new VelocityContext();
		context.put(BasicFieldTypesWidget.Attribute.STRING_NON_REQUIRED.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.STRING_NON_REQUIRED));
		context.put(BasicFieldTypesWidget.Attribute.STRING_REQUIRED.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.STRING_REQUIRED));
		context.put(BasicFieldTypesWidget.Attribute.EMAIL.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.EMAIL));
		context.put(BasicFieldTypesWidget.Attribute.BOOLEAN.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.BOOLEAN));
		context.put(BasicFieldTypesWidget.Attribute.INTEGER.getKey(), getAttributeValue(widget, BasicFieldTypesWidget.Attribute.INTEGER));
		context.put(BasicFieldTypesWidget.Attribute.TEXT.getKey(), convertToWiki(renderingContext, markup));
		
        return render(renderingContext, context);
	}

	private String render(final RenderingContext renderingContext, VelocityContext context) {
		try {        	
        	final TemplateRenderer templateRenderer = TemplateRenderer.getInstance();
        	String templateContent = IOUtils.toString(this.getClass().getResourceAsStream("/" + TEMPLATE), StandardCharsets.UTF_8);
        	return templateRenderer.render(templateContent, context, new Parameters(renderingContext.getRequest().getLocale(), false));
        } catch (Exception e) {
    		throw new WidgetFieldValidationException(ValidationErrorType.UNKNOWN, Arrays.asList(e.getMessage()));
        }
	}

	private String convertToWiki(final RenderingContext renderingContext, String markup) {
		
		CodeBeamerWikiContext wikiContext = new CodeBeamerWikiContext(renderingContext.getRequest(),
				renderingContext.getDashboard().getProject(), renderingContext.getDashboard(), renderingContext.getUser());

		String pageContent = wikiMarkupProcessor.processBeforePreviewing(renderingContext.getRequest(), markup, wikiContext);

		return wikiMarkupProcessor.transformToHtml(renderingContext.getRequest(), pageContent, DescriptionFormat.WIKI, false, false,
					  	renderingContext.getDashboard().getProject(), renderingContext.getDashboard(),
					  	renderingContext.getUser(), renderingContext.getRequest().getLocale(), true);
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
