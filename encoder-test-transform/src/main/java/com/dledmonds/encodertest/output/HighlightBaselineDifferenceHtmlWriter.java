package com.dledmonds.encodertest.output;

import java.io.IOException;
import java.io.Writer;

import com.dledmonds.encodertest.utils.CharacterUtils;

/**
 * HtmlWriter implementation that highlights table data that is different to a
 * set baseline. The baseline will be set to the original unencoded data by
 * default, so this class will highlight any table data that changes as a result
 * of encoding.
 * 
 * Useful for comparing different encoders side by side
 * 
 * @author dledmonds
 */
public class HighlightBaselineDifferenceHtmlWriter extends DefaultHtmlWriter {
	private String baselineData;

	public HighlightBaselineDifferenceHtmlWriter(Writer writer) {
		super(writer);
	}

	protected void startHeadStyle() throws IOException {
		super.startHeadStyle();
		writer.write("td.differs { background-color: gold; }");
	}

	public void endTableRow() throws IOException {
		this.baselineData = null;
		super.endTableRow();
	}

	public void addTableRowData(String data) throws IOException {
		if (this.baselineData != null && !this.baselineData.equals(data)) {
			writer.write("<td class=\"differs\">");
		} else {
			writer.write("<td>");
		}
		writer.write(encoder.encode(CharacterUtils.toPrintableString(data)));
		writer.write("</td>");
	}

	public void addTableRowBaselineData(String data) throws IOException {
		this.baselineData = data;
		super.addTableRowData(data);
	}

}
