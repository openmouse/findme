package com.kelansi.findme.utils;

public class NumberUtils {

	public static boolean isBigDecimal(String value) {
		try {
			double n = Double.parseDouble(value);
			int temp;
			double i;
			temp = (int) n;
			i = n - temp;
			if (i == 0) {
				return false;
			} else
				return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
