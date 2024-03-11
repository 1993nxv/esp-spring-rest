package com.algaworks.algafood.util;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

public class ResourceUtils {
	
	public static String getContentFromFile(String fileName) {
		try {
			InputStream stream = ResourceUtils.class
					.getClassLoader()
					.getResourceAsStream(fileName);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
