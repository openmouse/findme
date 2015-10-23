package com.kelansi.findme.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author 
 *
 */
public class ExcelXSSFReader extends ExcelReader {

	/** 日志记录 */
	private static final Log logger = LogFactory.getLog(ExcelXSSFReader.class);

	public ExcelXSSFReader(String filename) throws IOException {
		super(filename);
	}

	public ExcelXSSFReader(String filename,InputStream in) throws IOException {
		super(filename,in);
	}
	@Override
	protected void createWorkBook() {
		try {
			workBook = new XSSFWorkbook(is);
		} catch (IOException e) {
			logger.error(String.format("%s %n %s",
					"新建ExcelXSSFReader工作薄时发生异常 ", e.getMessage()));
		}
	}
}
