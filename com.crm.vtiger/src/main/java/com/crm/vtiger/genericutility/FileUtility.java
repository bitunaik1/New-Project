package com.crm.vtiger.genericutility;

import java.io.FileInputStream;
import java.util.Properties;

public class FileUtility {
	public String getData(String key)throws Throwable {
		FileInputStream fis=new FileInputStream(IPathConstants.PROPERTIESFILE_PATH);
		Properties pobj=new Properties();
		pobj.load(fis);
		return pobj.getProperty(key);
	}
}
