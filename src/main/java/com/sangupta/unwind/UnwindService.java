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

/**
 * A simple service that unwinds shortened URLs to their final
 * destinations.
 * 
 * @author sangupta
 *
 */
public interface UnwindService {

	/**
	 * Check if the URL is shortened via a major URL shortener. This does
	 * not hit the internet and only compares offline via a host match.
	 * 
	 * @param url
	 *            the url to be tested again
	 * 
	 * @return <code>true</code> if shortened using major shortener,
	 *         <code>false</code> otherwise
	 * 
	 * @throws IllegalArgumentException
	 *             if the url is <code>null/empty</code>
	 */
	public boolean isMajorShortener(String url);
	
	/**
	 * Check if the URL is shortened by hitting the internet and checking
	 * if we get a redirect response.
	 * 
	 * @param url
	 *            the url to be tested again
	 * 
	 * @return <code>true</code> if url is redirected to another url,
	 *         <code>false</code> otherwise. May return <code>null</code>
	 *         if we are unable to fetch the URL for any given reason
	 * 
	 * @throws IllegalArgumentException
	 *             if the url is <code>null/empty</code>
	 */
	public Boolean isRedirectedURL(String url);
	
	/**
	 * Return the final destination {@link URI} for a given URL by checking for
	 * redirects.
	 * 
	 * @param url
	 *            the url to be checked
	 * 
	 * @return the final {@link URI} where we will land up, <code>null</code> if
	 *         we could not fetch response from the server
	 * 
	 * @throws IllegalArgumentException
	 *             if the url is <code>null/empty</code>
	 * 
	 * @throws URISyntaxException
	 *             in case the url provided cannot be parsed into a valid
	 *             {@link URI} instance
	 */
	public URI getFinalURI(String url) throws URISyntaxException;
	
	/**
	 * Return the final destination URL for a given URL by checking for
	 * redirects.
	 * 
	 * @param url
	 *            the url to be checked
	 * 
	 * @return the final URL where we will land up, <code>null</code> if we
	 *         could not fetch response from the server
	 * 
	 * @throws IllegalArgumentException
	 *             if the url is <code>null/empty</code>
	 */
	public String getFinalURL(String url);
	
}
