package de.apaku.merss.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.impl.client.DefaultHttpClient;

public class CouchDBProxy extends HttpServlet {
	static final String COUCHDB_BASE_URL = "http://zion:5984/";

	public CouchDBProxy() {
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpDelete( getForwardUrl( req ) ) );
	}

	private void doForwardRequest( HttpServletRequest req, HttpServletResponse resp, HttpRequestBase method )
			throws ServletException, IOException {
		@SuppressWarnings("rawtypes")
		Enumeration headers = req.getHeaderNames();
		while( headers.hasMoreElements() ) {
			String header = headers.nextElement().toString();
			method.setHeader( header, req.getHeader(header) );
		}
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse res = client.execute(method);
		resp.setStatus(res.getStatusLine().getStatusCode());
		for( Header header: res.getAllHeaders() ) {
			resp.setHeader(header.getName(), header.getValue());
		}
		InputStream is = res.getEntity().getContent();
		ServletOutputStream os = resp.getOutputStream();
		try{
			int c = -1;
			while( ( c = is.read() ) != -1 ) {
				os.write(c);
			}
		} finally {
			is.close();
			os.close();
		}
	}

	private URI getForwardUrl( HttpServletRequest req ) {
		URI u = URI.create(COUCHDB_BASE_URL +req.getPathInfo()).normalize();
		return u;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpGet( getForwardUrl( req ) ) );
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpHead( getForwardUrl( req ) ) );
	}
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpOptions( getForwardUrl( req ) ) );
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpPost( getForwardUrl( req ) ) );
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpPut( getForwardUrl( req ) ) );
	}
	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doForwardRequest( req, resp, new HttpTrace( getForwardUrl( req ) ) );
	}
}
