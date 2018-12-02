package com.intland.codebeamer.dashboard.component.widgets.demo;

import static com.intland.codebeamer.utils.MessageFormatterUtil.format;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.intland.codebeamer.controller.QueriesResult;
import com.intland.codebeamer.dashboard.component.common.RenderingContext;
import com.intland.codebeamer.dashboard.component.common.interfaces.Renderer;
import com.intland.codebeamer.dashboard.component.widgets.common.attribute.WidgetAttribute;
import com.intland.codebeamer.dashboard.component.widgets.demo.RecentlyChangedTrackerItemsWidget.Attribute;
import com.intland.codebeamer.manager.CbQLManager;
import com.intland.codebeamer.persistence.dao.PaginatedDtoList;
import com.intland.codebeamer.persistence.dto.TrackerItemDto;
import com.intland.codebeamer.persistence.dto.UserDto;
import com.intland.codebeamer.utils.velocitytool.TextFormatter;

@Component
@Qualifier("recentlyChangedTrackerItemsWidgetHtmlRenderer")
public class RecentlyChangedTrackerItemsWidgetHtmlRenderer implements Renderer<RecentlyChangedTrackerItemsWidget> {

	private static final Logger logger = Logger.getLogger(RecentlyChangedTrackerItemsWidgetHtmlRenderer.class);
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private CbQLManager cbqlManager;
	
	public String render(final RenderingContext renderingContext, final RecentlyChangedTrackerItemsWidget widget) {
		List<Integer> projectIds = getAttributeValue(widget, RecentlyChangedTrackerItemsWidget.Attribute.PROJECT);
		List<Integer> trackerIds = getAttributeValue(widget, RecentlyChangedTrackerItemsWidget.Attribute.TRACKER);

		String projectList = projectIds.stream().map(String::valueOf).collect(Collectors.joining(","));
		String trackerList = trackerIds.stream().map(String::valueOf).collect(Collectors.joining(","));
		
		String queryString = format("project.id IN ({}) AND tracker.id IN ({}) AND modifiedAt >= -0d AND modifiedAt <= +0d", projectList, trackerList) ;
		QueriesResult result = cbqlManager.runQuery(renderingContext.getRequest(), queryString, new CbQLManager.PagingParams(5, 0));

		if (StringUtils.isNotBlank(result.getErrorMessage())) {
			VelocityContext context = new VelocityContext();
			context.put("errorMessage", result.getErrorMessage());
			return new VelocityRenderer("error.vm").render(renderingContext, context);
		}

		PaginatedDtoList<TrackerItemDto> pageResult = result.getTrackerItems();

		if(logger.isInfoEnabled()) {
			StringBuilder logBuilder = new StringBuilder(80)
					.append("Query:").append(queryString)
					.append(", Page ").append(pageResult.getPageNumber())
					.append(", Size ").append(pageResult.getObjectsPerPage())
					.append(", Items ").append(pageResult.getList() != null ? pageResult.getList().size() : 0)
					.append(", Total").append(pageResult.getFullListSize());
		
			logger.info(StringUtils.repeat("*", 30));
			logger.info(logBuilder);
			logger.info(StringUtils.repeat("*", 30));
		}
		
		UserDto currentUser = renderingContext.getUser();

		VelocityContext context = new VelocityContext();
		context.put("textFormatter", new TextFormatter(currentUser.getLocale(), true, applicationContext));
		context.put("currentUser", currentUser);
		context.put("trackerItems", pageResult.getList());

		return new VelocityRenderer("index.vm").render(renderingContext, context);
	}

	@SuppressWarnings("unchecked")
	private <V> V getAttributeValue(final RecentlyChangedTrackerItemsWidget widget, Attribute stringValidated) {
		WidgetAttribute<?> attributeValue = getAttribute(widget, stringValidated);
		return attributeValue == null ? null : (V) attributeValue.getValue();
	}

	@SuppressWarnings("unchecked")
	private <T extends WidgetAttribute<?>> T getAttribute(final RecentlyChangedTrackerItemsWidget widget, Attribute stringValidated) {
		return (T) widget.getAttributes().get(stringValidated.getKey());
	}

}
