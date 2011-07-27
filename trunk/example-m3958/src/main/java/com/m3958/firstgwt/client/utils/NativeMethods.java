package com.m3958.firstgwt.client.utils;

public class NativeMethods {
	
	public static final native void redirect(String url)/*-{
		$wnd.location = url;
	}-*/;

	public static final native void reload()/*-{
		$wnd.location.reload();
	}-*/;

	
	public static final native void redirectToLogin()/*-{
		if($wnd.location.href.indexOf("codesvr") == -1){
			$wnd.location = "/Login.html";
		}else{
			$wnd.location = "http://127.0.0.1:8888/Login.html?gwt.codesvr=127.0.0.1:9997";
		}
	}-*/;
	
	public static final native void redirectToHomePage()/*-{
	if($wnd.location.href.indexOf("codesvr") == -1){
		$wnd.location = "/";
	}else{
		$wnd.location = "http://127.0.0.1:8888/?gwt.codesvr=127.0.0.1:9997";
	}
	}-*/;
	
	public static final native String getTimeZone()/*-{
		var timezone = $wnd.jzTimezoneDetector.determine_timezone().timezone;
		return timezone.olson_tz;
	}-*/;
	
}
