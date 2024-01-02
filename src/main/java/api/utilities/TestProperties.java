package api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

import hyworks.automation.framework.base.AutomationException;

public class TestProperties {

	private static Properties properties = null;

	static {
		String vcVersion;
		FileInputStream fis = null;
		properties = new Properties();
		try {
			fis = new FileInputStream(PathUtil.getConfDirPath()
					+ File.separator + "setup.properties");
			properties.load(fis);
			vcVersion = (String) properties.get("vc.default.version");
			if (vcVersion == null || vcVersion.isEmpty()) {
				vcVersion = "55";
			}
		} catch (FileNotFoundException e1) {
			vcVersion = "55";
		} catch (IOException e) {
			vcVersion = "55";
		} finally {
			properties.clear();
		}
		File confDirectory = new File(PathUtil.getConfDirPath());
		List<File> propertiesFiles = (List<File>) FileUtils.listFiles(
				confDirectory, new String[] { "properties" }, true);
		for (File propertiesFile : propertiesFiles) {
			try {
				fis = new FileInputStream(propertiesFile);
				if (propertiesFile.getName().contains("testbedvc")
						&& !propertiesFile.getName().contains(vcVersion)) {
					continue;
				}
				properties.load(fis);
			} catch (IOException e) {
				// To do
			}
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) throws AutomationException {
		String value = (String) properties.get(key);
		if (value == null) {
			throw new AutomationException("key " + key
					+ " does not exits in properties files");
		}
		return value;
	}

	public static void modify(String key, String newValue)
			throws AutomationException, IOException {

		FileOutputStream fout = new FileOutputStream(PathUtil.getConfDirPath()
				+ File.separator + "setup.properties");
		properties.setProperty(key, newValue);
		properties.store(fout, null);
		fout.close();

	}

	public static List<String> getKeysWithPrefix(String prefix) {
		List<String> resultKeys = new ArrayList<String>();
		Enumeration<Object> keys = properties.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix)) {
				resultKeys.add(key);
			}
		}
		return resultKeys;
	}

	public static List<String> getKeysWithSuffix(String suffix) {
		List<String> resultKeys = new ArrayList<String>();
		Enumeration<Object> keys = properties.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.endsWith(suffix)) {
				resultKeys.add(key);
			}
		}
		return resultKeys;
	}

	public static List<String> getKeysWithPrefixSuffix(String prefix,
			String suffix) {
		List<String> resultKeys = new ArrayList<String>();
		Enumeration<Object> keys = properties.keys();
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			if (key.startsWith(prefix) && key.endsWith(suffix)) {
				resultKeys.add(key);
			}
		}
		return resultKeys;
	}

	public static boolean containsKey(String key) {
		String value = (String) properties.get(key);
		if (value == null) {
			return false;
		}
		return true;
	}

	public static int getInt(String key) throws AutomationException {
		int valueInt = 0;
		String value = (String) properties.get(key);
		if (value == null) {
			throw new AutomationException("key " + key
					+ " does not exits in properties files");
		}
		try {
			valueInt = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new AutomationException("key " + key
					+ " does not exits in properties files in Integer format");
		}
		return valueInt;
	}

}
