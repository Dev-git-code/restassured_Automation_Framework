package api.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import org.json.simple.JSONObject;

/**
 * A utility class to write data to an Excel sheet.
 */
public class WriteDataToExcel {
	
    // Create a new workbook
    static XSSFWorkbook workbook = new XSSFWorkbook();

    // Create a new spreadsheet within the workbook
    static XSSFSheet spreadsheet = workbook.createSheet("User Data");

    // Create a row object to represent a row in the spreadsheet
    static XSSFRow row;
    
	// Initialize row index
    static Integer j = 1;
    
    // Create a map to store the data to be written to the spreadsheet
    static Map<Integer, Object[]> excelData = new TreeMap<Integer, Object[]>();
    
    /**
     * Write JSON data to the Excel sheet.
     *
     * @param jsonMap A map containing JSON data.
     */
	private static void writeJsonDataToExcel(Map<String,JSONObject> jsonMap) {
		 // Add jsonMap data to the excelData map
        for (Map.Entry<String, JSONObject> entry : jsonMap.entrySet()) {
            String reqKey = entry.getKey();
            Object[] reqKeyObject = new Object[1];
            String testKey = "test" + reqKey.replace(" ", "");
            reqKeyObject[0] = testKey;
            JSONObject jsonBody = entry.getValue();
            int n = jsonBody.size();
            Object[] keyObject = new Object[n];
            Object[] valueObject = new Object[n];
            int i = 0;
            for (Iterator<?> bodyIterator = jsonBody.keySet().iterator(); bodyIterator.hasNext();) {
                String key = (String) bodyIterator.next();
                keyObject[i] = key;
                String value = (String) jsonBody.get(key);
                valueObject[i] = value;
                i++;
            }

            excelData.put(j++, reqKeyObject);
            excelData.put(j++, keyObject);
            excelData.put(j++, valueObject);
            excelData.put(j++, new Object[] { "" });
            excelData.put(j++, new Object[] { "" });
        }

	}
	
	/**
     * Write a map to the Excel sheet.
     *
     * @param map A map containing data to be written to the Excel sheet.
     */
	private static void writeMapToExcel(Map<String,String> map) {
		// Add map data to the excelData map
        excelData.put(j++, new Object[] { "" });
        excelData.put(j++, new Object[] { "" });
        excelData.put(j++, new Object[] { "Routes" });
        excelData.put(j++, new Object[] { "RouteName", "URL" });
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String routeKey = entry.getKey();
            String routeBody = entry.getValue();
            excelData.put(j++, new Object[] { routeKey, routeBody });
        }
        excelData.put(j++, new Object[] { "" });
        excelData.put(j++, new Object[] { "" });
	}
	
	/**
     * Write data to the Excel sheet.
     */
	private static void writeDataToSheet() {
		 // Get the keys of the excelData map
        Set<Integer> keyid = excelData.keySet();

        // Create cell styles for headers
        CellStyle columnNameCellStyle = spreadsheet.getWorkbook().createCellStyle();
        columnNameCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        columnNameCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle methodNameCellStyle = spreadsheet.getWorkbook().createCellStyle();
        methodNameCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        methodNameCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Initialize row index
        int rowid = 0;
        int emptyRowCount = 0;
        // Write data to the spreadsheet
        
        for (Integer key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = excelData.get(key);
            int cellid = 0;
            int headingId = -1;
            int nameId = -1;
            
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if (emptyRowCount == 3) {
                    headingId = rowid;
                    emptyRowCount = 0;
                }
                if (emptyRowCount == 2) {
                    nameId = rowid;
                    emptyRowCount++;
                }

                if (obj.toString().isEmpty()) {
                    emptyRowCount++;
                }
                if (rowid == headingId)
                    cell.setCellStyle(columnNameCellStyle);
                if (rowid == nameId)
                    cell.setCellStyle(methodNameCellStyle);
                cell.setCellValue((String) obj);
            }
        }
	}
	
	/**
     * Write the workbook to a file.
     *
     * @param excelPath The path where the Excel file will be saved.
     * @throws IOException If an I/O error occurs while writing the file.
     */
	private static void writeWorkbookToFile(String excelPath) throws IOException {
		// Write the workbook to a file
        FileOutputStream out = new FileOutputStream(new File(excelPath));
        workbook.write(out);
        out.close();
	}

	/**
     * Write data to an Excel file.
     *
     * @param jsonFilePath The path to the JSON file containing data.
     * @param excelPath    The path where the Excel file will be saved.
     * @throws Exception If an error occurs during the writing process.
     */
    public static void writeToExcel(String jsonFilePath,String excelPath) throws Exception {
    
        // Get data from data providers
        Map<String, JSONObject> requestBodyMap = DataProviders.getRequestBody(jsonFilePath);
        Map<String, String> routesMap = DataProviders.getRoutesFromSwaggerJson(jsonFilePath);
        Map<String, JSONObject> paramMap = DataProviders.getParamMap(jsonFilePath);

      
        writeMapToExcel(routesMap);
        writeJsonDataToExcel(paramMap);
        writeJsonDataToExcel(requestBodyMap);
        
        // insert data into excel
        writeDataToSheet();
        writeWorkbookToFile(excelPath);  
    }
}
