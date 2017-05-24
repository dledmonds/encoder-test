package com.dledmonds.encodertest.output;

import java.io.IOException;

public interface HtmlWriter {
	public void startHtml() throws IOException;

	public void startHead(String title) throws IOException;

	public void startBody(String title) throws IOException;

	public void startTable() throws IOException;

	public void startTableRow() throws IOException;

	public void addTableRowHeader(String data, String description)
			throws IOException;

	public void addTableRowData(String data) throws IOException;

	public void addTableRowData(String data, boolean encode) throws IOException;

	public void addTableRowBaselineData(String data) throws IOException;

	public void endTableRow() throws IOException;

	public void endTable() throws IOException;

	public void endHead() throws IOException;

	public void endBody() throws IOException;

	public void endHtml() throws IOException;
}
