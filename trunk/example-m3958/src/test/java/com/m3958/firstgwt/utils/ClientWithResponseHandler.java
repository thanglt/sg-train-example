package com.m3958.firstgwt.utils;

/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.SortedMap;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This example demonstrates the use of the {@link ResponseHandler} to simplify
 * the process of processing the HTTP response and releasing associated resources.
 */
public class ClientWithResponseHandler {

    public final static void main(String[] args) throws Exception {

    	 HttpClient httpclient = new DefaultHttpClient();

    	 // Prepare a request object
    	 HttpGet httpget = new HttpGet("http://www.fhjs.gov.cn/images/qzgg.jpg");

    	 // Execute the request
    	 HttpResponse response = httpclient.execute(httpget);

    	 // Examine the response status
    	 System.out.println(response.getStatusLine());

    	 // Get hold of the response entity
    	 HttpEntity entity = response.getEntity();
    	 
    	 SortedMap sm = Charset.availableCharsets();
    	 
    	 String destFile = "e:/hclient.out";
    	 FileOutputStream fos = new FileOutputStream(destFile);

    	 // If the response does not enclose an entity, there is no need
    	 // to worry about connection release
    	 if (entity != null) {
    		 Header h = entity.getContentEncoding();
    		 
    	     InputStream instream = entity.getContent();
    	     try {
					byte[] b = new byte[1024];
					while (true) {
						int i = instream.read(b);
						if (i == -1)
							break;
						fos.write(b, 0, i);
					}
					instream.close();
					fos.close();

//    	         BufferedReader reader = new BufferedReader(
//    	                 new InputStreamReader(instream,Charset.forName("GB2312")));
//    	         // do something useful with the response
//    	         String line;
//    	         while((line = reader.readLine()) != null){
//    	        	 System.out.println(line); 
//    	         }
    	         

    	     } catch (IOException ex) {

    	         // In case of an IOException the connection will be released
    	         // back to the connection manager automatically
    	         throw ex;

    	     } catch (RuntimeException ex) {

    	         // In case of an unexpected exception you may want to abort
    	         // the HTTP request in order to shut down the underlying
    	         // connection and release it back to the connection manager.
    	         httpget.abort();
    	         throw ex;

    	     } finally {

    	         // Closing the input stream will trigger connection release
    	         instream.close();

    	     }

    	     // When HttpClient instance is no longer needed,
    	     // shut down the connection manager to ensure
    	     // immediate deallocation of all system resources
    	     httpclient.getConnectionManager().shutdown();
    	 }
    }

}

