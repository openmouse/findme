package com.kelansi.findme.excel;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 
 *
 */
public final class ExcelReaderFactory {

	/**
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static ExcelReader getExcelReader(String filename)
			throws IOException {
		return isXLSExcelFile(filename) ? new ExcelHSSFReader(filename)
				: new ExcelXSSFReader(filename);
	}
	/**
	 * @param filename
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static ExcelReader getExcelReader(String filename,InputStream is)
			throws IOException {
		return isXLSExcelFile(filename) ? new ExcelHSSFReader(filename,is)
				: new ExcelXSSFReader(filename,is);
	}
	
	/**
	 * @param filename
	 * @return
	 */
	private static boolean isXLSExcelFile(String filename) {
		return filename.toLowerCase().endsWith("xls");
	}

}
