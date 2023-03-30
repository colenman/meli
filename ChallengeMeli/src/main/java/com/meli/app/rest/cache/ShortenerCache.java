package com.meli.app.rest.cache;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class ShortenerCache {
	private final static Map<String, String> cacheLtoS = new HashMap<String, String>();
	private final static Map<String, String> cacheStoL = new HashMap<String, String>();
	private static int secuencia = 1;
	private static String urlBase;
	static {
		
		try {
			InetAddress ip = InetAddress.getLocalHost();
			urlBase = "http://"+ip.getHostAddress()+":8080/meli/";
		} catch (UnknownHostException e) {
			urlBase = null;
		}
		
	}
	

	public static String getUrlShort(String url) {
		String value = cacheLtoS.get(url);
		if (value == null) {
			if(urlBase != null) {
				value = toProcessUrl(url);
				cacheLtoS.put(url, value);
				cacheStoL.put(value, url);	
			}else {
				value = "La urlBase no está disponible";
			}			
		}
		return value;
	}
	
	public static String getUrlOriginal(String url) {
		String value = cacheStoL.get(url);
		if (value == null) {
			value = "<No existe una url Asociada>";
		}
		return value;
	}
	
	public static void deleteUrlShort(String id) {
		String urlOriginal = getUrlOriginal(urlBase+id);
		cacheLtoS.remove(urlOriginal);
		cacheStoL.remove(urlBase+id);
	}
	
	

	private static String toProcessUrl(String url) {
		return urlBase + (convertIntToAZ(secuencia++));
	}

	private static String convertIntToAZ(int num) {
		StringBuilder sb = new StringBuilder();
		while (num > 0) {
			int rem = num % 27;
			
			char c = (char) ('A' + rem-1);
			if(rem == 0) {
				sb.append("0");	
			}else {
				sb.append(c);
			}
			
			num /= 27;
		}
		return sb.reverse().toString();
	}
	
	public static void main(String[] args) {
		for(int i = 2000;i<2100;i++) {
			System.out.println(i+": -> "+convertIntToAZ(i));	
		}
		
	}
}
