package com.ERP.Utilities;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertyFileUtil 
{
	public static String getValueForKey(String key) throws Throwable
	{
		Properties configProperties=new Properties();
		configProperties.load(new FileInputStream("C:\\TestWorkspace\\ERP_StockAccounting\\PropertyFile\\Environment.properties"));		
		return configProperties.getProperty(key);
		
	}
}
