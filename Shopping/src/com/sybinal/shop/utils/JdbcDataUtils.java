package com.sybinal.shop.utils;

import java.util.StringTokenizer;

public class JdbcDataUtils {
	public static String escapeWildcard(String original, char escapeChar) {
		if (original == null) {
			return "";
		}
		StringBuffer buff = new StringBuffer(original.length());
		for (StringTokenizer st = new StringTokenizer(original, "_%"+ escapeChar, true); st.hasMoreTokens();) {
			String token = st.nextToken();
			char char0 = token.charAt(0);
			if (char0 == '_' || char0 == '%' || char0 == escapeChar) {
				// These characters need to be escaped.
				buff.append(escapeChar).append(char0);
			} else {
				// Otherwise, this part is secure.
				buff.append(token);
			}
		}

		return buff.toString();
	}

}
