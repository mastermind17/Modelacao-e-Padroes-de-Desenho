package soccerapp.webapi.services.http;

import javax.servlet.http.HttpServletRequest;

public class HttptUtils {

	public static int getLastSegment(HttpServletRequest req){
		String path = req.getPathInfo();
		String[] parts = path.split("/");
		return Integer.valueOf(parts[parts.length-1]);
	}

	public static int getLastSegment(String href){
		String[] parts = href.split("/");
		return Integer.valueOf(parts[parts.length-1]);
	}
}
