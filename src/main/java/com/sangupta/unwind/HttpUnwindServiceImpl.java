/**
 *
 * unwind - expand shortened urls
 * Copyright (c) 2014, Sandeep Gupta
 * 
 * http://sangupta.com/projects/jerry-http
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 		http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */

package com.sangupta.unwind;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.sangupta.jerry.http.WebInvoker;
import com.sangupta.jerry.http.WebRequest;
import com.sangupta.jerry.http.WebResponse;
import com.sangupta.jerry.util.AssertUtils;
import com.sangupta.jerry.util.UriUtils;

/**
 * Apache HTTP client library based implementation of the {@link UnwindService}
 * that checks for the provided URL to see if it is shortened, and/or redirected
 * along with the functionality to fetch the final URL.
 * 
 * @author sangupta
 * 
 */
public class HttpUnwindServiceImpl implements UnwindService {
	
	/**
	 * Holds list of all major url shorteners
	 * 
	 */
	private static final Set<String> MAJOR_SHORTENERS = new HashSet<String>();
	
	/**
	 * Pre-populated list of all major vendors
	 * 
	 */
	static {
		MAJOR_SHORTENERS.add("t.co");
		MAJOR_SHORTENERS.add("bit.ly");
		MAJOR_SHORTENERS.add("lnkd.in");
		MAJOR_SHORTENERS.add("g.co");
		MAJOR_SHORTENERS.add("goo.gl");
		MAJOR_SHORTENERS.add("bit.ly");
		MAJOR_SHORTENERS.add("is.gd");
		MAJOR_SHORTENERS.add("u.to");
		MAJOR_SHORTENERS.add("twitthis.com");
		MAJOR_SHORTENERS.add("j.mp");
		MAJOR_SHORTENERS.add("u.bb");
		MAJOR_SHORTENERS.add("qr.net");
		MAJOR_SHORTENERS.add("1url.com");
		MAJOR_SHORTENERS.add("v.gd");
		MAJOR_SHORTENERS.add("tr.im");
	}
	
	/**
	 * 
	 * @param host
	 */
	public static final void addMajorShortenerHost(final String host) {
		if(AssertUtils.isBlank(host)) {
			throw new IllegalArgumentException("Host name cannot be empty/null");
		}
		
		MAJOR_SHORTENERS.add(host.toLowerCase());
	}
	
	/**
	 * 
	 * @param host
	 */
	public static final void removeMajorShortenerHost(final String host) {
		if(AssertUtils.isBlank(host)) {
			throw new IllegalArgumentException("Host name cannot be empty/null");
		}
		
		MAJOR_SHORTENERS.remove(host.toLowerCase());
	}
	
	/**
	 * 
	 * @return
	 */
	public static final Set<String> getMajorShortenersSet() {
		return Collections.unmodifiableSet(MAJOR_SHORTENERS);
	}

	/**
	 * 
	 */
	@Override
	public boolean isMajorShortener(String url) {
		if(AssertUtils.isEmpty(url)) {
			throw new IllegalArgumentException("URL cannot be empty/null");
		}
		
		String host = UriUtils.extractHost(url);
		if(AssertUtils.isEmpty(host)) {
			throw new IllegalArgumentException("URL does not contain any valid host");
		}
		
		return MAJOR_SHORTENERS.contains(host.toLowerCase());
	}

	/**
	 * 
	 */
	@Override
	public Boolean isRedirectedURL(String url) {
		if(AssertUtils.isEmpty(url)) {
			throw new IllegalArgumentException("URL cannot be empty/null");
		}
		
		// short-cut for speed check
		if(this.isMajorShortener(url)) {
			return Boolean.TRUE;
		}
		
		// we do know - let's hit and find out the final URL
		WebResponse response = WebInvoker.executeSilently(WebRequest.head(url));
		if(response == null) {
			return null;
		}
		
		return response.hasRedirects();
	}
	
	/**
	 * @throws URISyntaxException 
	 * 
	 */
	@Override
	public URI getFinalURI(String url) throws URISyntaxException {
		if(AssertUtils.isEmpty(url)) {
			throw new IllegalArgumentException("URL cannot be empty/null");
		}
		
		WebResponse response = WebInvoker.executeSilently(WebRequest.head(url));
		if(response == null) {
			return null;
		}
		
		if(!response.hasRedirects()) {
			return new URI(url);
		}
		
		return response.getFinalURI();
	}

	/**
	 *   
	 */
	@Override
	public String getFinalURL(String url) {
		if(AssertUtils.isEmpty(url)) {
			throw new IllegalArgumentException("URL cannot be empty/null");
		}
		
		WebResponse response = WebInvoker.executeSilently(WebRequest.head(url));
		if(response == null) {
			return null;
		}
		
		if(!response.hasRedirects()) {
			return url;
		}
		
		return response.getFinalURI().toString();
	}
	
}
