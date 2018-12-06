package com.intland.codebeamer.wiki.plugins;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.velocity.VelocityContext;

import com.ecyrd.jspwiki.WikiContext;
import com.intland.codebeamer.wiki.plugins.base.AbstractCodeBeamerWikiPlugin;

public class HelloWorldDemoPlugin extends AbstractCodeBeamerWikiPlugin {

	public String execute(WikiContext context, Map params) {
		VelocityContext velocityContext = new VelocityContext();

		List<ImmutablePair<String, String>> pairs = convert(params).entrySet().stream()
			.map(e -> ImmutablePair.of(e.getKey(), e.getValue()))
			.collect(Collectors.toList());

		velocityContext.put("pairs", pairs);
		return new VelocityRenderer("index.vm").render(context, velocityContext);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, String> convert(Map params) {
		return (Map<String, String>) params;
	}

}