package api.payload;

import api.utilities.DataProviders;
import api.utilities.WriteDataToExcel;

/**
 * A driver utility class to write data to an Excel sheet from the Swagger.json.
 */
public class CreateExcelFromSwagger {
	public static void main(String[] args) throws Exception {
		WriteDataToExcel.writeToExcel();
	}
}
