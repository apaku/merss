package de.apaku.merss.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
	private String dbUrl = "";
	@UiField TextBox newUserNameField;
	@UiField PasswordTextBox newPasswordField;
	@UiField PushButton signupButton;
	@UiField TextBox loginUserNameField;
	@UiField PasswordTextBox loginPasswordField;
	@UiField PushButton loginButton;

	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setDataBaseUrl( String dbUrl )
	{
		this.dbUrl = dbUrl;
	}

	@UiHandler("loginButton")
	void onLoginLinkClick(ClickEvent event) {

		if( dbUrl == null || dbUrl.length() == 0 ) {
			Window.alert("No Database connection available");
		}

		if( loginPasswordField.getText().length() == 0 || loginUserNameField.getText().length() == 0 ) {
			Window.alert("Need Username and Password");
		}

		final String pw = loginPasswordField.getText();

		RequestBuilder rb = new RequestBuilder( RequestBuilder.GET, "http://neo/~andreas/foobar.json" );
		try{
			rb.sendRequest("", new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				Window.alert("Response: " + response.getStatusCode() + " -- " + response.getStatusText() + " -- " + response.getText());
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Failure:" + exception);
			}
		});
		} catch( RequestException e ) {
			Window.alert("Error:" + e);
		}

	}
	@UiHandler("signupButton")
	void onSignupButtonClick(ClickEvent event) {
		if( dbUrl == null || dbUrl.length() == 0 ) {
			Window.alert("No Database connection available");
		}

		if( newPasswordField.getText().length() == 0 || newUserNameField.getText().length() == 0 ) {
			Window.alert("Need New Username and Password");
		}

		final String newpw = newPasswordField.getText();
		RequestBuilder rb = new RequestBuilder( RequestBuilder.GET, "http://neo/~andreas/foobar.json" );
		try{
			rb.sendRequest("", new RequestCallback() {

			@Override
			public void onResponseReceived(Request request, Response response) {
				Window.alert("Response: " + response.getStatusCode() + " -- " + response.getStatusText() + " -- " + response.getText());
			}

			@Override
			public void onError(Request request, Throwable exception) {
				Window.alert("Failure:" + exception);
			}
		});
		} catch( RequestException e ) {
			Window.alert("Error:" + e);
		}
	}
}
