package com.bht.banhuitong.client;

import java.util.Date;

public class Test {

	/*public static void main(String[] args) {
		  
		  System.out.println(addDay(new Date(),365));
		}*/
	
	public static void main(String[] args) {
		String errorMsg = "org.xx.armory.services.ServiceException: @@@2@@@ 您没有权限，请联系管理员分配权限！\r\n" + 
				"	at com.bht.banhuitong.filter.SecurityFilter.doFilter(SecurityFilter.java:68)\r\n" + 
				"	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:241)\r\n" + 
				"	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:208)\r\n" + 
				"	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:220)\r\n" + 
				"	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:122)\r\n" + 
				"	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:501)\r\n" + 
				"	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:171)\r\n" + 
				"	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:103)\r\n" + 
				"	at org.apache.catalina.valves.AccessLogValve.invoke(AccessLogValve.java:950)\r\n" + 
				"	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:116)\r\n" + 
				"	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:408)\r\n" + 
				"	at org.apache.coyote.http11.AbstractHttp11Processor.process(AbstractHttp11Processor.java:1070)\r\n" + 
				"	at org.apache.coyote.AbstractProtocol$AbstractConnectionHandler.process(AbstractProtocol.java:611)\r\n" + 
				"	at org.apache.tomcat.util.net.JIoEndpoint$SocketProcessor.run(JIoEndpoint.java:314)\r\n" + 
				"	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)\r\n" + 
				"	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)\r\n" + 
				"	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n" + 
				"	at java.lang.Thread.run(Thread.java:745)";
		
		int start = errorMsg.indexOf("@@@")+3;
		int end = errorMsg.lastIndexOf("@@@");
		String errorCode = errorMsg.substring(errorMsg.indexOf("@@@")+3).trim();
		errorCode = errorCode.substring(0,errorCode.indexOf("@@@")).trim();
		if(errorCode.matches("[0-9]+")) {
			System.out.println(errorCode);
		}else {
			System.out.println(0);
		}
	}

	
	public static Date addDay(Date date, int addDay){
		if(date == null){
			date = new Date();
		}
		
		System.out.println(date);
		System.out.println(date.getTime());
		System.out.println(addDay*24*60*60*1000);
		System.out.println(365*24*60*60*1000);
		System.out.println(date.getTime()-addDay*24*60*60*1000);
		return new Date(date.getTime()-365*24*60*60*1000);
	}
}
