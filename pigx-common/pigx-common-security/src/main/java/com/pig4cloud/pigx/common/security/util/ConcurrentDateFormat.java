/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.common.security.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 参考tomcat8中的并发DateFormat
 * <p>
 * {@link SimpleDateFormat}的线程安全包装器。
 * 不使用ThreadLocal，创建足够的SimpleDateFormat对象来满足并发性要求。
 *
 * @author L.cm
 */
public class ConcurrentDateFormat {
	private final String format;
	private final Locale locale;
	private final TimeZone timezone;
	private final Queue<SimpleDateFormat> queue = new ConcurrentLinkedQueue<>();

	private ConcurrentDateFormat(String format, Locale locale, TimeZone timezone) {
		this.format = format;
		this.locale = locale;
		this.timezone = timezone;
		SimpleDateFormat initial = createInstance();
		queue.add(initial);
	}

	public static ConcurrentDateFormat of(String format) {
		return new ConcurrentDateFormat(format, Locale.getDefault(), TimeZone.getDefault());
	}

	public static ConcurrentDateFormat of(String format, TimeZone timezone) {
		return new ConcurrentDateFormat(format, Locale.getDefault(), timezone);
	}

	public static ConcurrentDateFormat of(String format, Locale locale, TimeZone timezone) {
		return new ConcurrentDateFormat(format, locale, timezone);
	}

	public String format(Date date) {
		SimpleDateFormat sdf = queue.poll();
		if (sdf == null) {
			sdf = createInstance();
		}
		String result = sdf.format(date);
		queue.add(sdf);
		return result;
	}

	public Date parse(String source) throws ParseException {
		SimpleDateFormat sdf = queue.poll();
		if (sdf == null) {
			sdf = createInstance();
		}
		Date result = sdf.parse(source);
		queue.add(sdf);
		return result;
	}

	private SimpleDateFormat createInstance() {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
		sdf.setTimeZone(timezone);
		return sdf;
	}
}
