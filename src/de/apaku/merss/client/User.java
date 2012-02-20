package de.apaku.merss.client;

import com.google.gwt.core.client.JavaScriptObject;

public class User extends JavaScriptObject {
	protected User() {
	}
	
	public final native String getHashedPassword() /*-{ return this.hashedPassword; }-*/;

}
