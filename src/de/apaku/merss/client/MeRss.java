package de.apaku.merss.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MeRss implements EntryPoint {

	@Override
	public void onModuleLoad() {
		Label l = new Label("Hello World");
		RootPanel.get().add(l);
	}

}
