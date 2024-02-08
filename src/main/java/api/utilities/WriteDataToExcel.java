package api.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import org.json.simple.JSONObject;

/**
 * A utility class to write data to an Excel sheet.
 */
public class WriteDataToExcel {

    /**
     * Writes data to an Excel sheet.
     * 
     * @throws Exception if any error occurs during writing
     */
    public static void writeToExcel() throws Exception {
        // Create a new workbook
        XSSFWorkbook workbook = new XSSFWorkbook();

        // Create a new spreadsheet within the workbook
        XSSFSheet spreadsheet = workbook.createSheet("User Data");

        // Create a row object to represent a row in the spreadsheet
        XSSFRow row;

        // Create a map to store the data to be written to the spreadsheet
        Map<Integer, Object[]> userData = new TreeMap<Integer, Object[]>();

        // Get data from data providers
        Map<String, JSONObject> requestBodyMap = DataProviders.getRequestBody();
        Map<String, String> routesMap = DataProviders.getRoutesFromSwaggerJson();
        Map<String, JSONObject> paramMap = DataProviders.getParamMap();

        // Initialize row index
        Integer j = 1;

        // Add headers and routes data to the userData map
        userData.put(j++, new Object[] { "" });
        userData.put(j++, new Object[] { "" });
        userData.put(j++, new Object[] { "Routes" });
        userData.put(j++, new Object[] { "RouteName", "URL" });
        for (Map.Entry<String, String> entry : routesMap.entrySet()) {
            String routeKey = entry.getKey();
            String routeBody = entry.getValue();
            userData.put(j++, new Object[] { routeKey, routeBody });
        }
        userData.put(j++, new Object[] { "" });
        userData.put(j++, new Object[] { "" });

        // Add parameters data to the userData map
        for (Map.Entry<String, JSONObject> entry : paramMap.entrySet()) {
            String reqKey = entry.getKey();
            Object[] reqKeyObject = new Object[1];
            String testKey = "test" + reqKey.replace(" ", "");
            reqKeyObject[0] = testKey;
            JSONObject reqBody = entry.getValue();
            int n = reqBody.size();
            Object[] keyObject = new Object[n];
            Object[] valueObject = new Object[n];
            int i = 0;
            for (Iterator<?> bodyIterator = reqBody.keySet().iterator(); bodyIterator.hasNext();) {
                String key = (String) bodyIterator.next();
                keyObject[i] = key;
                String value = (String) reqBody.get(key);
                valueObject[i] = value;
                i++;
            }

            userData.put(j++, reqKeyObject);
            userData.put(j++, keyObject);
            userData.put(j++, valueObject);
            userData.put(j++, new Object[] { "" });
            userData.put(j++, new Object[] { "" });
        }

        // Add request body data to the userData map
        for (Map.Entry<String, JSONObject> entry : requestBodyMap.entrySet()) {
            String reqKey = entry.getKey();
            Object[] reqKeyObject = new Object[1];
            String testKey = "test" + reqKey.replace(" ", "");
            reqKeyObject[0] = testKey;
            JSONObject reqBody = entry.getValue();
            int n = reqBody.size();
            Object[] keyObject = new Object[n];
            Object[] valueObject = new Object[n];
            int i = 0;
            for (Iterator<?> bodyIterator = reqBody.keySet().iterator(); bodyIterator.hasNext();) {
                String key = (String) bodyIterator.next();
                keyObject[i] = key;
                String value = (String) reqBody.get(key);
                valueObject[i] = value;
                i++;
            }

            userData.put(j++, reqKeyObject);
            userData.put(j++, keyObject);
            userData.put(j++, valueObject);
            userData.put(j++, new Object[] { "" });
            userData.put(j++, new Object[] { "" });
        }

        // Get the keys of the userData map
        Set<Integer> keyid = userData.keySet();

        // Create cell styles for headers
        CellStyle headerCellStyle = spreadsheet.getWorkbook().createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        CellStyle headerCellStyle2 = spreadsheet.getWorkbook().createCellStyle();
        headerCellStyle2.setFillForegroundColor(IndexedColors.LIGHT_GREEN.index);
        headerCellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Initialize row index
        int rowid = 0;
        int cnt = 0;
        // Write data to the spreadsheet
        for (Integer key : keyid) {
            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = userData.get(key);
            int cellid = 0;
            int headingId = -1;
            int nameId = -1;
            
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if (cnt == 3) {
                    headingId = rowid;
                    cnt = 0;
                }
                if (cnt == 2) {
                    nameId = rowid;
                    cnt++;
                }

                if (obj.toString().isEmpty()) {
                    cnt++;
                }
                if (rowid == headingId)
                    cell.setCellStyle(headerCellStyle);
                if (rowid == nameId)
                    cell.setCellStyle(headerCellStyle2);
                cell.setCellValue((String) obj);
            }
        }

        // Write the workbook to a file
        FileOutputStream out = new FileOutputStream(new File(
        		"C:\\Users\\DELL\\3D Objects\\automation framework\\RestAssuredAutomation\\testData\\practice.xlsx"));
        //"C:\\Users\\DELL\\3D Objects\\automation framework\\RestAssuredAutomation\\testData\\practice.xlsx"
        //"C:\\Users\\DELL\\Documents\\practice\\practice.xlsx"
        workbook.write(out);
        out.close();
    }
}
