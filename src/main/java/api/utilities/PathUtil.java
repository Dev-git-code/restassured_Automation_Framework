package api.utilities;

import java.io.File;

/**
 * Utility class that provides implementation for path calculation
 * 
 * */
public class PathUtil {

	/**
	 * Retrieves the path of the configuration directory
	 * 
	 * @return path of the configuration directory
	 */
	public static String getConfDirPath() {
		return getProjectHomeDirPath() + "conf";
	}

	/**
	 * Retrieves the path of the target directory
	 * 
	 * @return path of the target directory
	 */
	public static String getTargetDirPath() {
		return getProjectHomeDirPath() + "target";
	}

	/**
	 * Retrieves the path of the downloads directory
	 * 
	 * @return path of the downloads directory
	 */
	public static String getDownloadsDirPath() {
		return getProjectHomeDirPath() + "downloads";
	}

	/**
	 * Retrieves the path of the uploads directory
	 * 
	 * @return path of the uploads directory
	 */
	public static String getUploadsDirPath() {
		return getProjectHomeDirPath() + "uploads";
	}

	/**
	 * Retrieves the path of the home directory of the project
	 * 
	 * @return path of the home directory of the project
	 */
	public static String getProjectHomeDirPath() {
		String currentDirPath = new File(".").getAbsolutePath();
		return currentDirPath.substring(0, currentDirPath.length() - 1);
	}

	/**
	 * Retrieves the path of the auto it script directory
	 * 
	 * @return path of the auto it script directory
	 */
	public static String getClientAutomateDir() {
		return getProjectHomeDirPath() + "clientautomate";
	}
}
