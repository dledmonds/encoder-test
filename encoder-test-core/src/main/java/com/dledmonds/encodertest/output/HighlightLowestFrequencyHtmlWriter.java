package com.dledmonds.encodertest.output;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * HtmlWriter implementation that highlights table data that occurs least
 * frequently for a given row.
 * 
 * Useful for comparing different versions of the same encoders to check for
 * version inconsistencies.
 * 
 * @author dledmonds
 */
public class HighlightLowestFrequencyHtmlWriter extends DefaultHtmlWriter {
	private List<String> tableRowData;
	private Map<String, Integer> dataFrequency;

	public HighlightLowestFrequencyHtmlWriter(Writer writer) {
		super(writer);
		this.tableRowData = new ArrayList<String>();
		this.dataFrequency = new HashMap<String, Integer>();
	}

	protected void startHeadStyle() throws IOException {
		super.startHeadStyle();
		writer.write("td.differs { background-color: lightpink; }");
	}

	public void startTableRow() throws IOException {
		this.tableRowData.clear();
		super.startTableRow();
	}

	public void endTableRow() throws IOException {
		// check data frequency
		dataFrequency.clear();
		for (String data : this.tableRowData) {
			if (dataFrequency.containsKey(data)) {
				dataFrequency.put(data, dataFrequency.get(data) + 1);
			} else {
				dataFrequency.put(data, 1);
			}
		}
		int lowestFrequency = Integer.MAX_VALUE;
		for (Entry<String, Integer> entry : dataFrequency.entrySet()) {
			if (entry.getValue() < lowestFrequency)
				lowestFrequency = entry.getValue();
		}

		for (String data : this.tableRowData) {
			if (dataFrequency.size() > 1
					&& dataFrequency.get(data) <= lowestFrequency) {
				writer.write("<td class=\"differs\">");
			} else {
				writer.write("<td>");
			}
			writer.write(encoder.encode(data));
			writer.write("</td>");
		}
		super.endTableRow();
	}

	public void addTableRowData(String data) throws IOException {
		this.tableRowData.add(data); // save up data for comparison, output on
										// endRow
	}

	public void addTableRowBaselineData(String data) throws IOException {
		writer.write("<td>");
		writer.write(encoder.encode(data));
		writer.write("</td>");
	}

}
