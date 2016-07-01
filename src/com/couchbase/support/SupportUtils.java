package com.couchbase.support;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.json.JsonObject;
import com.couchbase.client.java.query.N1qlQuery;
import com.couchbase.client.java.query.N1qlQueryResult;
import com.google.gson.Gson;

// Note:  These are generic utility type functions, there is nothing Spring specific
// in here.

public class SupportUtils {

	static int SCREENCOLUMNS = 80;

	private static Gson gson;

	public static String getHostname() {
		String rval = "Unable to get hostname";
		try {
			rval = InetAddress.getLocalHost().getHostName();	
		}
		catch (Exception e ){
			System.out.println(e);
		}
		return rval;
	}
	
	public static void showClasspath() {
		// Display the current classpath
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		URL[] urls = ((URLClassLoader)cl).getURLs();
		for(URL url: urls){
			System.out.println(url.getFile());
		}		
	}

	public static Gson getGson() {

		if (gson == null) {
			gson = new Gson();
		}

		return gson;

	}

	public static void printDecoration(int c, String s) {
		for (int i = 0; i < c; i++) { System.out.print(s); }
	}

	public static void printCenteredBanner(String s) {
		int numDecorations = ((SCREENCOLUMNS - (s.length() + 2)) / 2);
		printDecoration(numDecorations,"=");
		System.out.print(" " + s + " ");
		printDecoration(numDecorations,"=");		
		System.out.println();
	}

	static void logMessage(String s) {
		System.out.println("=== " + s + " ===");
	}



	static N1qlQueryResult runAQuery(Bucket bucket, String queryString) {

		N1qlQueryResult qr = bucket.query(N1qlQuery.simple(queryString)); 

		int numberOfErrors         = 0;
		int numberOfResults        = qr.allRows().size(); 
		boolean finalSuccess       = qr.finalSuccess();
		List<JsonObject> errorList = qr.errors();

		if (errorList != null) { numberOfErrors = errorList.size(); }

		System.out.println("runAQuery() finalSuccess? " + finalSuccess + " numberOfResults: " + numberOfResults + " numberOfErrors: " + numberOfErrors);

		if (errorList != null) {
			for (JsonObject eachError : errorList ) {
				System.out.println("runAQuery() error: " + eachError);
			}
		}

		return qr;
	}



	public static String getCouchbaseVersion(String host, int port) {

		// This method can take 5-6 seconds on 1 node virtual machine

		String cbVersion = "ERROR";

		boolean success = false;

		while (!success) {
			try {
				String restUrl = "http://" + host + ":" + port + "/pools";
				byte b[] = new byte[10000];
				URL u = new URL(restUrl);
				InputStream is = u.openStream();
				// int bytesRead = is.read(b);
				String result = new String(b);
				int impVerIndex = result.indexOf("implementationVersion");
				int firstCommaIndex = result.indexOf(",", impVerIndex);
				String versionTemp = result.substring(impVerIndex, firstCommaIndex);
				int colonIndex = versionTemp.indexOf(":", 0);
				String version = versionTemp.substring(colonIndex + 2, versionTemp.length() - 1);
				is.close();
				is = null;
				u = null;
				b = null;
				cbVersion = version;
				success = true;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		// In diag.log this generates a line like so
		// 10.111.110.1 - - [11/Mar/2016:23:30:45 +0000] "GET /pools HTTP/1.1" 200 769 - Java/1.8.0_25
		// 10.111.110.1 - - [11/Mar/2016:23:32:40 +0000] "GET /pools HTTP/1.1" 200 769 - Java/1.8.0_25

		return cbVersion;
	} // getCouchbaseVersion()

	// borrowed from
	// http://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
	public static String getCurrentTimeStamp() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}	
	
} // SupportUtils

// EOF
