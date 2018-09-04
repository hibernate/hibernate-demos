package org.hibernate.demo.message.post.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TagHelper {

	private static final Pattern TAG_PATTERN = Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");

	public static List<String> readTagFromBody(String body) {
		final List<String> tagValues = new ArrayList<String>();
		final Matcher matcher = TAG_PATTERN.matcher(body);
		while (matcher.find()) {
			tagValues.add(matcher.group(1));
		}
		return tagValues;
	}
}
