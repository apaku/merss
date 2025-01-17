package de.apaku.merss.client;

/**
 * Copyright (c) 2012, Andreas Pakulat <apaku@gmx.de>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * @author Andreas Pakulat <apaku@gmx.de>
 */

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class MeRss implements EntryPoint {

	private static final String CONFIG_URL = GWT.getHostPageBaseURL() + "configuration.json";

	private final native Configuration asConfiguration( String json ) /*-{
		return eval(json);
	}-*/;

	@Override
	public void onModuleLoad() {
		Window.enableScrolling(false);
		Window.setMargin("0px");
		final Login login = new Login();
		RootLayoutPanel.get().add(login);
		login.loginButton.setEnabled(false);
		login.signupButton.setEnabled(false);

		String baseUrl = CONFIG_URL;
		if( !GWT.isProdMode() ) {
			// In development mode we need to add the query from the Window.location
			baseUrl += Window.Location.getQueryString();
		}
		final String url = URL.encode(baseUrl);
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
		try{
			builder.sendRequest("", new RequestCallback() {

				@Override
				public void onResponseReceived(Request request, Response response) {
					if( 200 == response.getStatusCode() ) {
						try{
							Configuration c = JsonUtils.safeEval(response.getText());
							login.setDataBaseUrl(c.getDataBaseUrl());
							login.loginButton.setEnabled(true);
							login.signupButton.setEnabled(true);
						}catch(Exception e ){
							System.err.println(e);
						}
					} else {
						Window.alert("Error retrieving configuration ("+url+": " + response.getStatusCode() + "/" + response.getStatusText() );
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					Window.alert("Error retrieving configuration ("+url+": "+ exception );
				}
			});
		} catch( RequestException e ) {
			Window.alert("Couldn't retrieve configuration");
		}
	}

}
