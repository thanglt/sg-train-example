package demo.service.loader;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServiceLoader {
 
	private static Map<String, String> getParamMap(String[] paramArray) {
		Map<String, String> paramMap = new HashMap<String, String>();
		for (String param : paramArray) {
			System.out.println("param:" + param ); 
			if (param.startsWith("-")) {
				String key = StringUtils.substringBefore(param, "=");
	 			String val = StringUtils.substringAfter(param, "=");
				paramMap.put(key.substring(1).trim(), val.trim()); 
			} 
		}    
		return paramMap;      
	}   
	 
	private static boolean checkPort(int port){		
		try { 
			ServerSocket socket=new ServerSocket(port);
			socket.close();
			return true;
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		
	}

	public static void main(String[] args) {

		Map<String, String> paramMap = getParamMap(args);

		System.out.println(paramMap); 

		String strPort = paramMap.get("port");
		String webAppDir = paramMap.get("webBase");

		int port = 28080; 
		if (StringUtils.isNotBlank(strPort)) {
			port = Integer.parseInt(strPort);
		}
		if (StringUtils.isBlank(webAppDir)) {
			webAppDir = "./WebContent";
		}
		
		if(!checkPort(port)){
			System.exit(-1);
		}

		Server server = new Server(port);
		
		WebAppContext context = new WebAppContext();
		context.setResourceBase(webAppDir);
		context.setDescriptor(webAppDir + "/WEB-INF/web.xml");
		
		context.setParentLoaderPriority(true);
		server.setHandler(context);

		try {
			server.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
