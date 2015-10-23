package com.kelansi.findme.excel;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author 
 *
 */
public class ExcelHSSFReader extends ExcelReader {

	/** 日志记录 */
	private static final Log logger = LogFactory.getLog(ExcelHSSFReader.class);

	public ExcelHSSFReader(String filename) throws IOException {
		super(filename);
	}
	public ExcelHSSFReader(String filename,InputStream is) throws IOException {
		super(filename,is);
	}
	@Override
	protected void createWorkBook() {
		try {
			workBook = new HSSFWorkbook(is);
		} catch (IOException e) {
			logger.error(String.format("%s %n %s", "新建HSSFWorkbook工作薄时发生异常 ",
					e.getMessage()));

		}
	}
}
