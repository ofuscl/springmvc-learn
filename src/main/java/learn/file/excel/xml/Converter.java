package learn.file.excel.xml;

public interface Converter {

	void definePicture(XmlSpreadsheetNode node);

	void insertPicture(XmlSpreadsheetNode node);

	void createStyle(XmlSpreadsheetNode node);

	void parseStyleAlignment(XmlSpreadsheetNode node);

	void parseStyleBorder(XmlSpreadsheetNode node);

	void parseInterior(XmlSpreadsheetNode node);

	void createFontForCellStyle(XmlSpreadsheetNode node);

	void createSheet(XmlSpreadsheetNode node);

	void parseTable(XmlSpreadsheetNode node);

	void parseColumn(XmlSpreadsheetNode node);

	void createRow(XmlSpreadsheetNode node);

	void flushRowValue();

	void createCell(XmlSpreadsheetNode node);

	void parseCellData(XmlSpreadsheetNode node);

	void parseRichTextFontStyle(XmlSpreadsheetNode node);

	/**
	 * Parse cell text.
	 * 
	 * @param text
	 *            text
	 */
	public void parseCellText(String text);

	/**
	 * Set cell text and clear cache.
	 */
	public void flushCellValue();

}
