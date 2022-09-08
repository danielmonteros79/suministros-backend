package com.sa.su.util;

public class StringFormatUtil {
	private static String complete(int length, char with) {
	    StringBuilder sb = new StringBuilder(length);
	    while (sb.length() < length) {
	        sb.append(with);
	    }
	    return sb.toString();
	}

	public static String leftComplete(String value, int length, char with) {
	    StringBuilder result = new StringBuilder(length);
	    					//maximo entre 0 y la cantidad de char a agregar.  <- tamano deseado - tamano del string
	    result.append(complete(Math.max(0, length - value.length()), with));
	    result.append(value);

	    return result.toString().substring(0, length);
	}
	
	public static String rightComplete(String value, int length, char with) {
	    StringBuilder result = new StringBuilder(length);
	    result.append(value);
	    					//maximo entre 0 y la cantidad de char a agregar.  <- tamano deseado - tamano del string
	    result.append(complete(Math.max(0, length - value.length()), with));

	    return result.toString().substring(0, length);
	}
}
