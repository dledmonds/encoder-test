package com.dledmonds.encodingtest.wrapper;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.el.ExpressionEvaluator;
import javax.servlet.jsp.el.VariableResolver;

public class CoutDummyPageContext extends PageContext {

	private Writer writer;

	CoutDummyPageContext(Writer writer) {
		this.writer = writer;
	}

	@Override
	public void forward(String arg0) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public Exception getException() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletRequest getRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletResponse getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void handlePageException(Exception arg0) throws ServletException,
			IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handlePageException(Throwable arg0) throws ServletException,
			IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void include(String arg0) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void include(String arg0, boolean arg1) throws ServletException,
			IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void initialize(Servlet arg0, ServletRequest arg1,
			ServletResponse arg2, String arg3, boolean arg4, int arg5,
			boolean arg6) throws IOException, IllegalStateException,
			IllegalArgumentException {
		// TODO Auto-generated method stub

	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object findAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration getAttributeNamesInScope(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAttributesScope(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ExpressionEvaluator getExpressionEvaluator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JspWriter getOut() {
		return new CoutDummyJspWriter(writer);
	}

	@Override
	public VariableResolver getVariableResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAttribute(String arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String arg0, Object arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	class CoutDummyJspWriter extends JspWriter {
		private Writer writer;

		protected CoutDummyJspWriter(Writer writer) {
			super(64, true);
			this.writer = writer;
		}

		@Override
		public void clear() throws IOException {
		}

		@Override
		public void clearBuffer() throws IOException {
		}

		@Override
		public void close() throws IOException {
			writer.close();
		}

		@Override
		public void flush() throws IOException {
			writer.flush();
		}

		@Override
		public int getRemaining() {
			return 0;
		}

		@Override
		public void newLine() throws IOException {
			writer.write("\n");
		}

		@Override
		public void print(boolean arg0) throws IOException {
			writer.write(Boolean.toString(arg0));
		}

		@Override
		public void print(char arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(int arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(long arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(float arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(double arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(char[] arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(String arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void print(Object arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println() throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(boolean arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(char arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(int arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(long arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(float arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(double arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(char[] arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(String arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void println(Object arg0) throws IOException {
			// TODO Auto-generated method stub

		}

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			// TODO Auto-generated method stub

		}

	}

}