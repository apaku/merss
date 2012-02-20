package de.apaku.merss.client;

import com.google.gwt.core.client.JavaScriptObject;

public class Configuration extends JavaScriptObject {
	protected Configuration() {
	}

	public final native String getDataBaseUrl() /*-{ return this.dbUrl; }-*/;
}
