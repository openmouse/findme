package com.kelansi.findme.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.StringUtils;

/**
 * @author
 *
 */
public abstract class ExcelReader {

	private static final int NAME_MAX_LENGTH = 255;
	private static final int BARCODE_MAX_LENGTH = 20;
	private static final String UUID_PATTERN = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";
	private static final String INTEGER_PATTERN = "[0-9]+";
	private static final String BAR_CODE_PATTERN = "[0-9]{0," + BARCODE_MAX_LENGTH + "}";
//	private static final String NAME_PATTERN = "[\\/\\[\\]\\s()（）【】“”0-9a-zA-Z-+.*\u4E00-\u9FA5]*"
	private static final String NAME_PATTERN = "[^<^>]*";
	
	/** 导入Excel的名字 */
	protected String filename = null;

	/** 文件输入流 */
	protected InputStream is = null;

	/** Excel 工作薄 */
	protected Workbook workBook = null;

	/** 日志记录 */
	private static final Log logger = LogFactory.getLog(ExcelReader.class);
	
	private DecimalFormat df = new DecimalFormat("#");//转换成整型
	
	private DecimalFormat decimal_df = new DecimalFormat("0.#######");//转换成6位小数

	public ExcelReader(String filename) throws IOException {
		this.filename = filename;
		this.is = new FileInputStream(new File(filename));
		createWorkBook();
	}

	public ExcelReader(String filename,InputStream in) throws IOException {
		this.filename = filename;
		this.is = in;
		createWorkBook();
	}
	/**
	 * 读取品牌工作表的内容.
	 * 
	 * @return 返回品牌列表
	 */
	public List<String> readBrandSheet() {
		List<String> brands = new ArrayList<String>();
		/**
		 * 导入文件的第一张sheet保存的是品牌的信息， 品牌信息只有一列
		 */
		Sheet brandSheet = workBook.getSheetAt(0);
		for (int rowIndex = 1, rowCount = brandSheet.getLastRowNum(); rowIndex <= rowCount; rowIndex++) {
			Row row = brandSheet.getRow(rowIndex);
			if (null != row && row.getLastCellNum() > 0) {
				Cell cell = row.getCell(0);
				brands.add(cellValue(cell));
			}
		}
		return brands;
	}

	protected abstract void createWorkBook();


	/**
	 * 读取规格工作表的内容.
	 * 
	 * @return 返回规格Map信息
	 */
	public Map<String, List<String>> readSpecificationSheet() {
		/**
		 * 导入文件的第三张sheet保存的是规格及其值的信息，
		 */
		Sheet brandSheet = workBook.getSheetAt(2);
		Row firstRow = brandSheet.getRow(0);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (int columnIndex = 0, columnCount = firstRow.getLastCellNum(); columnIndex < columnCount; columnIndex++) {
			List<String> rowValuesList = new ArrayList<String>();
			for (int rowIndex = 1, rowCount = brandSheet.getLastRowNum(); rowIndex <= rowCount; rowIndex++) {
				Row row = brandSheet.getRow(rowIndex);
				Cell cell = row.getCell(columnIndex);
				// 按照字符串类单元格读取内容
				if (cell != null) {
					rowValuesList.add(cellValue(cell));
				}
			}
			if(!CollectionUtils.isEmpty(rowValuesList)){
				map.put(firstRow.getCell(columnIndex).getStringCellValue(),
						rowValuesList);
			}
		}
		return map;
	}

	/**
	 * 读取商品工作表的内容
	 * 
	 * @return 返回商品内容信息
	 */
	public List<String[]> readProductSheet() {
		List<String[]> products = new ArrayList<String[]>();
		/**
		 * 导入文件的第四张sheet保存的是商品的信息，
		 */
		Sheet productSheet = workBook.getSheetAt(3);
		// 第一行为标题栏
		int columnCount = productSheet.getRow(0).getLastCellNum();

		for (int rowIndex = 1, rowCount = productSheet.getLastRowNum(); rowIndex <= rowCount; rowIndex++) {
			Row row = productSheet.getRow(rowIndex);
			if ( row != null && row.getLastCellNum() != -1) {
				Cell cell = null;
				// 序号，一级目录，二级目录，三级目录，品牌，商品名称，商品条码，销售单位，销售规格(规格名称*数量*),进销项税, 保质期，长宽高，体积，重量
				//销售因子,	最小SKU规格之值	,最小SKU规格之单位,	箱装数量,	箱装单位,是否同步到物流或nc
				String[] values = new String[columnCount];
				for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
					cell = row.getCell(columnIndex);
					if (cell != null) {
						if (9 == columnIndex ) { 
							// 进销项税以及体积单独处理
							if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
								values[columnIndex] = String.valueOf(cell.getNumericCellValue());
							} else {
								values[columnIndex] = cellValue(cell);
							}
						} else {
							if(15 == columnIndex || 14 == columnIndex|| 13 == columnIndex|| 12 == columnIndex|| 11 == columnIndex){
								values[columnIndex] = cellValueWithDecimal(cell);
							}else{
								values[columnIndex] = cellValue(cell);
							}
						}
					}
				}
				if (!isEmptyRowValues(values)) {
					products.add(values);
				}
			}
		}
		return products;
	}

	/**
	 * @param cell
	 *            单元格
	 * @return 返回某个单元格的字符串内容
	 */
	private String cellValue(Cell cell) {
		if (null == cell)
			return null;

		String result = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			result = String.valueOf(df.format(cell.getNumericCellValue()));
			if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式  
                SimpleDateFormat sdf = null;  
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat  
                        .getBuiltinFormat("h:mm")) {  
                    sdf = new SimpleDateFormat("HH:mm");  
                } else {// 日期  
                    sdf = new SimpleDateFormat("yyyy-MM-dd");  
                }  
                Date date = cell.getDateCellValue();  
                result = sdf.format(date);  
            } else if (cell.getCellStyle().getDataFormat() == 58) {  
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)  
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
                double value = cell.getNumericCellValue();  
                Date date = org.apache.poi.ss.usermodel.DateUtil  
                        .getJavaDate(value);  
                result = sdf.format(date);  
            } 
			break;
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			result = String.valueOf(df.format(cell.getNumericCellValue()));
			break;
		default:
			// 其它类型用String的方式来获取单元格的内容
			result = cell.getStringCellValue();
			break;
		}
		if( result == null || "".equals(result.trim())) {
			return null;
		}
		return result.trim();
	}
	
	/**
	 * @param cell
	 *            单元格
	 * @return 返回某个单元格的字符串内容
	 */
	private String cellValueWithDecimal(Cell cell) {
		if (null == cell)
			return null;

		String result = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			result = String.valueOf(decimal_df.format(cell.getNumericCellValue()));
			break;
		case Cell.CELL_TYPE_STRING:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BLANK:
			result = cell.getStringCellValue();
			break;
		case Cell.CELL_TYPE_FORMULA:
			result = String.valueOf(decimal_df.format(cell.getNumericCellValue()));
			break;
		default:
			// 其它类型用String的方式来获取单元格的内容
			result = cell.getStringCellValue();
			break;
		}
		if( result == null || "".equals(result.trim())) {
			return null;
		}
		return result.trim();
	}

	/**
	 * 关闭文件流
	 * 
	 * @throws IOException
	 */
	public void close() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				logger.error("Excel文件流关闭时错误。");
			} finally {
				is = null;
			}
		}
	}

	/**
	 * 处理销售区域单元格
	 * 
	 * @param cell
	 * @param values
	 * @param columnIndex
	 */
	protected void handleSaleSpecification(Cell cell, String[] values,
			int columnIndex) {
		String cellValue = cellValue(cell);
		if (!StringUtils.isEmpty(cellValue)) {
			String[] splitValues = cellValue.split("\\*");
			values[columnIndex] = splitValues[0];
			if (splitValues.length > 2) {
				values[columnIndex + 2] = splitValues[2];
				values[columnIndex + 1] = splitValues[1];
			} else if (splitValues.length > 1) {
				values[columnIndex + 1] = splitValues[1];
			}
		}
	}

	/**
	 * @param sheetIndex
	 *            指定一个sheet的序号，0代表第一张sheet，依次类推
	 * @return 返回某一个sheet的Row列表
	 */
	public List<Row> readSheet(int sheetIndex) {
		List<Row> rows = new ArrayList<Row>();
		Sheet sheet = workBook.getSheetAt(sheetIndex);
		for (int rowIndex = 0, rowCount = sheet.getLastRowNum(); rowIndex <= rowCount; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			// 不加入空行
			if (row != null) {
				rows.add(row);
			}
		}
		return rows;
	}
	
	/**
	 * @param sheetIndex
	 *            指定一个sheet的序号，0代表第一张sheet，依次类推
	 * @return 返回某一个sheet的Row列表
	 */
	public List<Row> readSheet(Sheet sheet) {
		List<Row> rows = new ArrayList<Row>();
		for (int rowIndex = 0, rowCount = sheet.getLastRowNum(); rowIndex <= rowCount; rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			//读取最原本的excel内容，避免歧义，包含空行,调用时如果存在空行给出提示。
			rows.add(row);
		}
		return rows;
	}
	
	/**
	 * 获取sheet
	 * @param sheetIndex
	 *            指定一个sheet的序号，0代表第一张sheet，依次类推
	 * @return 返回某一个sheet
	 */
	public Sheet getSheet(int sheetIndex) {
		Sheet sheet = workBook.getSheetAt(sheetIndex);
		return sheet;
	}
	/**
	 * 根据sheet页名称找sheet
	 * @param sheetName
	 *            
	 * @return 返回某一个sheet
	 */
	public Sheet getSheetByName(String sheetName) {
		return workBook.getSheet(sheetName);
	}
	
	/**
	 * 判断行的值是否有效
	 * @param values
	 * @return
	 */
	private boolean isEmptyRowValues(String[] values) {
		if (null == values)
			return true;
		for (String value : values) {
			if (!StringUtils.isEmpty(value)
					&& !StringUtils.isEmpty(value.trim())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 判断行是否为空行
	 * @param row
	 * @return
	 */
	public boolean isEmptyRowValues(Row row) {
		if (null == row)
			return true;
		for(int cellIndex = 0,cellCount = row.getLastCellNum();cellIndex <cellCount;cellIndex++){
			String value = cellValue(row.getCell(cellIndex));
			if (!StringUtils.isEmpty(value)
					&& !StringUtils.isEmpty(value.trim())) {
				return false;
			}
		}
		return true;
	}

}
