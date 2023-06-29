package medicalcenter.util;

import java.util.Arrays;

public final class Strings
{
	/**
	 * The empty String {@code ""}.
	 */
	public static final String EMPTY = "";

	/**
	 * A String for a space character.
	 */
	public static final String SPACE = " ";

	/**
	 * The maximum size to which the padding constant(s) can expand.
	 */
	private static final int PAD_LIMIT = 8192;


	private Strings() {
		throw new UnsupportedOperationException("Utility classes are not instantiable");
	}


	public static int length(final CharSequence str) {
		return str == null ? 0 : str.length();
	}


	public static boolean isEmpty(final CharSequence cs) {
		return length(cs) == 0;
	}


	public static String center(final String str, final int size) {
		return center(str, size, SPACE);
	}


	public static String center(String str, final int size, String padStr) {
		if (str == null || size <= 0) {
			return str;
		}
		if (isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int len = length(str);
		final int pads = size - len;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		str = leftPad(str, len + pads / 2, padStr);
		return rightPad(str, size, padStr);
	}


	public static String leftPad(final String str, final int size) {
		return leftPad(str, size, ' ');
	}


	public static String leftPad(final String str, final int size, final char padChar) {
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return repeat(padChar, pads).concat(str);
	}


	public static String leftPad(final String str, final int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int padLen = length(padStr);
		final int strLen = length(str);
		final int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		}
		if (pads < padLen) {
			return padStr.substring(0, pads).concat(str); //NOSONAR
		}
		final char[] padding = new char[pads];
		final char[] padChars = padStr.toCharArray(); //NOSONAR
		for (int i = 0; i < pads; i++) {
			padding[i] = padChars[i % padLen];
		}
		return new String(padding).concat(str);
	}


	public static String rightPad(final String str, final int size) {
		return rightPad(str, size, ' ');
	}


	public static String rightPad(final String str, final int size, final char padChar) {
		if (str == null) {
			return null;
		}
		final int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(repeat(padChar, pads));
	}


	public static String rightPad(final String str, final int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (isEmpty(padStr)) {
			padStr = SPACE;
		}
		final int padLen = length(padStr);
		final int strLen = length(str);
		final int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		}
		if (pads < padLen) {
			return str.concat(padStr.substring(0, pads)); //NOSONAR
		}
		final char[] padding = new char[pads];
		final char[] padChars = padStr.toCharArray(); //NOSONAR
		for (int i = 0; i < pads; i++) {
			padding[i] = padChars[i % padLen];
		}
		return str.concat(new String(padding));
	}


	public static String repeat(final char ch, final int repeat) {
		if (repeat <= 0) {
			return EMPTY;
		}
		final char[] buf = new char[repeat];
		Arrays.fill(buf, ch);
		return new String(buf);
	}


	public static String repeat(final String str, final int repeat) {
		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		return str.repeat(repeat);
	}
}