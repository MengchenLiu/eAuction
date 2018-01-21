package com.eAuction.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class PasswordEncode {
	public static String getMD5(String password, String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((password+salt).getBytes());
			return new BigInteger(1,md.digest()).toString(16);
		} catch (Exception e) {
			System.out.println(e);
		}
		return password;
	}
}