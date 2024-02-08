package api.utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	public String path;
	public FileInputStream fis = null;
	public FileOutputStream fileOut = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public ExcelUtil() {
		try {
			/*
			 * if (TestProperties.get("jenkins.runmode").equals("true")) { path =
			 * PathUtil.getConfDirPath() + File.separator + "HYS_TestData.xlsx"; } else {
			 */
			//path = TestProperties.get("excel.file.path");
			path=System.getProperty("user.dir")+"//testData//practice.xlsx";
			// path = PathUtil.getConfDirPath() + File.separator + "HYS_TestData.xlsx";
			// }
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets row count in a sheet
	 * 
	 * @param sheetName
	 * @return
	 */
	public synchronized int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workbook.getSheetAt(index);
			int number = sheet.getLastRowNum() + 1;
			return number;
		}
	}

	/**
	 * Gets the data from a cell using column name
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public synchronized String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}

			int index = workbook.getSheetIndex(sheetName);
			int col_Num = -1;
			if (index == -1) {
				return ""; 
			}

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
				}
			}
			if (col_Num == -1) {
				return "";
			}

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null) {
				return "";
			}
			cell = row.getCell(col_Num);

			if (cell == null) {
				return "";
			}
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					double d = cell.getNumericCellValue();
					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			} else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}

	/**
	 * Gets the data from a cell using column number
	 * 
	 * @param sheetName
	 * @param colNum
	 * @param rowNum
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public synchronized String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}
			int index = workbook.getSheetIndex(sheetName);

			if (index == -1) {
				return "";
			}

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null) {
				return "";
			}
			cell = row.getCell(colNum);
			if (cell == null) {
				return "";
			}

			/*
			 * System.out.println(cell.getCellType()); System.out.println("--------------");
			 * System.out.println(Cell.CELL_TYPE_STRING);
			 * System.out.println("--------------");
			 * System.out.println(Cell.CELL_TYPE_FORMULA);
			 */

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				return cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				// String cellText = String.valueOf(cell.getNumericCellValue());
				String cellText = NumberToTextConverter.toText(cell.getNumericCellValue());

				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;
				}
				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			} else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	/**
	 * Returns true if data is set successfully else false
	 * 
	 * @param sheetName
	 * @param colName
	 * @param rowNum
	 * @param data
	 * @return
	 */
	public synchronized boolean setCellData(String sheetName, String colName, int rowNum, String data) {
		try {
			fis = new FileInputStream(path);
			workbook = new XSSFWorkbook(fis);

			if (rowNum <= 0) {
				return false;
			}

			int index = workbook.getSheetIndex(sheetName);
			int colNum = -1;
			if (index == -1) {
				return false;
			}

			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equals(colName)) {
					colNum = i;
				}
			}
			if (colNum == -1) {
				return false;
			}
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null) {
				row = sheet.createRow(rowNum - 1);
			}

			cell = row.getCell(colNum);
			if (cell == null) {
				cell = row.createCell(colNum);
			}

			// cell style
			// CellStyle cs = workbook.createCellStyle();
			// cs.setWrapText(true);
			// cell.setCellStyle(cs);
			cell.setCellValue(data);

			fileOut = new FileOutputStream(path);

			workbook.write(fileOut);

			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Returns true if sheet is created successfully else returns false
	 * 
	 * @param sheetname
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean addSheet(String sheetname) throws IOException {
		FileOutputStream fileOut = null;
		try {
			workbook.createSheet(sheetname);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			fileOut.close();
		}
		return true;
	}

	/**
	 * Returns true if sheet is removed successfully else returns false Also returns
	 * false if sheet does not exist
	 * 
	 * @param sheetName
	 * @return
	 * @throws IOException
	 */
	public synchronized boolean removeSheet(String sheetName) throws IOException {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			return false;
		}

		FileOutputStream fileOut = null;
		try {
			workbook.removeSheetAt(index);
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			fileOut.close();
		}
		return true;
	}

	/**
	 * Returns true if Sheet exists
	 * 
	 * @param sheetName
	 * @return
	 */
	public synchronized boolean isSheetExist(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workbook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1)
				return false;
			else
				return true;
		} else
			return true;
	}

	/**
	 * Returns number of columns in a sheet
	 * 
	 * @param sheetName
	 * @return
	 */
	public synchronized int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName)) {
			return -1;
		}

		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(0);

		if (row == null) {
			return -1;
		}

		return row.getLastCellNum();

	}

	/**
	 * Returns row number of the provided cell value
	 * 
	 * @param sheetName
	 * @param colName
	 * @param cellValue
	 * @return
	 */
	public synchronized int getCellRowNum(String sheetName, String colName, String cellValue) {

		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if (getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * Returns test data for the test case provided as parameter
	 * 
	 * @param testCaseName
	 * @return
	 */
	public synchronized Object[][] getTestData(String testCaseName, String sheetName) {
		// String sheetName = "TestData";

		int testStartRowNum = 1;
		while (!getCellData(sheetName, 0, testStartRowNum).equals(testCaseName)) {
			testStartRowNum++;
		}
		int colStartRowNum = testStartRowNum + 1;
		int dataStartRowNum = testStartRowNum + 2;

		// Calculate total rows of data
		int rows = 0;
		while (!getCellData(sheetName, 0, dataStartRowNum + rows).equals("")) {
			rows++;
		}

		// Calculate total columns of data
		int cols = 0;
		while (!getCellData(sheetName, cols, colStartRowNum).equals("")) {
			cols++;
		}

		// Read the data
		Object[][] data = new Object[rows][1];
		int dataRow = 0;
		Hashtable<String, String> table = null;
		for (int rNum = dataStartRowNum; rNum < dataStartRowNum + rows; rNum++) {
			table = new Hashtable<String, String>();
			for (int cNum = 0; cNum < cols; cNum++) {
				String key = getCellData(sheetName, cNum, colStartRowNum);
				String value = getCellData(sheetName, cNum, rNum);
				table.put(key, value);
			}
			data[dataRow][0] = table;
			dataRow++;
		}
		return data;
	}

	/**
	 * Returns true if test case runmode is enabled
	 * 
	 * @param testName
	 * @return
	 */
	public synchronized boolean isRunnable(String testName) {
		String sheet = "TestCases";

		int rows = getRowCount(sheet);
		for (int r = 2; r <= rows; r++) {
			String tName = getCellData(sheet, "TC_Name", r);
			if (tName.equals(testName)) {
				String runmode = getCellData(sheet, "Run_Mode", r);
				if (runmode.contains("Y") || runmode.contains("y")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

}
