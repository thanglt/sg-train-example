package com.m3958.firstgwt.utils;

/**
 *@author <weijosn@gmail.com>
 * 
 *@version 1.0
 */
public class TokenBean {
	private static final String[] CHARS = { "0", "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
			"x", "y", "z" };
	private static final int CHARLEN = 36;
	private String seed = null;
	private String randomKey = null;
	private int len = 30;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	private void generator() {
		char c[] = new char[len];
		for (int i = 0; i < len; i++) {
			int r = (int) (Math.random() * CHARLEN);
			c[i] = r % 2 == 0 ? CHARS[r].toUpperCase().charAt(0) : CHARS[r]
					.charAt(0);
		}
		if (seed != null) {
			char b[] = seed.toCharArray();
			for (int i = 0; i < b.length; i++) {
				int r = (int) (Math.random() * limit(b[i]));
				c[i] = r % 2 != 0 ? CHARS[r].toUpperCase().charAt(0) : CHARS[r]
						.charAt(0);
			}
		}
		randomKey = new String(c);
	}

	public String getRandomKey() {
		this.generator();
		return randomKey;
	}

	/**
	 * 
	 * 
	 * @param seek
	 * @return
	 */
	private int limit(int seek) {
		if (seek > CHARLEN) {
			seek = seek - CHARLEN;
			return limit(seek);
		}
		return seek;
	}
}