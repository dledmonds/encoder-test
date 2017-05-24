package com.dledmonds.encodertest.output;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultHtmlWriter implements HtmlWriter {
	protected static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"YYYY/MM/dd-HH:mm:ss");

	protected Writer writer;
	protected OutputEncoder encoder;

	public DefaultHtmlWriter(Writer writer) {
		this.writer = writer;
		encoder = new HtmlOutputEncoder();
	}

	public void startHtml() throws IOException {
		writer.write("<html>");
	}

	public void endHtml() throws IOException {
		writer.write("</html>");
		writer.flush();
	}

	public void startHead(String title) throws IOException {
		writer.write("<head>");
		writer.write("<meta charset=\"utf-8\">");
		writer.write("<title>");
		writer.write(encoder.encode(title));
		writer.write("</title>");
		// bootstrap
		writer.write("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\" integrity=\"sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u\" crossorigin=\"anonymous\">");
		startHeadStyle();
		endHeadStyle();
	}

	public void endHead() throws IOException {
		writer.write("</head>");
	}

	protected void startHeadStyle() throws IOException {
		writer.write("<style>");
		writer.write("td { text-align: center; }");
	}

	protected void endHeadStyle() throws IOException {
		writer.write("</style>");
	}

	public void startBody(String title) throws IOException {
		writer.write("<body>");
		writer.write("<div class=\"container\">");
		writer.write("<h1>");
		writer.write(encoder.encode(title));
		writer.write("</h1>");
		writer.write("<p>Hover over column header for more information</p>");
	}

	public void endBody() throws IOException {
		writer.write("<p>Generated on " + DATE_FORMAT.format(new Date())
				+ "</p>");
		writer.write("</div>");
		writer.write("</body>");
	}

	public void startTable() throws IOException {
		writer.write("<table class=\"table table-condensed\"");
	}

	public void endTable() throws IOException {
		writer.write("</table>");
	}

	public void startTableRow() throws IOException {
		writer.write("<tr>");
	}

	public void endTableRow() throws IOException {
		writer.write("</tr>");
	}

	public void addTableRowHeader(String data, String description)
			throws IOException {
		writer.write("<th>");
		if (description != null)
			writer.write("<span title=\"" + encoder.encode(description) + "\">");
		writer.write(encoder.encode(data));
		if (description != null)
			writer.write("</span>");
		writer.write("</th>");
	}

	public void addTableRowData(String data) throws IOException {
		addTableRowData(data, true);
	}

	public void addTableRowData(String data, boolean encode) throws IOException {
		writer.write("<td>");
		writer.write(encode ? encoder.encode(data) : data);
		writer.write("</td>");
	}

	public void addTableRowBaselineData(String data) throws IOException {
		addTableRowData(data);
	}

}
