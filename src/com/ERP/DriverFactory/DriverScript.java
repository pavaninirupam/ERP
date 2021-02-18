package com.ERP.DriverFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ERP.CommonFunctions.FunctionLibrary;
import com.ERP.Utilities.ExcelFileUtil;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DriverScript {
	
	String inputpath="C:\\TestWorkspace\\ERP_StockAccounting\\TestInput\\HybridTest.xlsx";
	String outputpath="C:\\TestWorkspace\\ERP_StockAccounting\\TestOutput\\hybridResults.xlsx";
	ExtentReports report;
	ExtentTest test;
	
	public void startTest()throws Throwable{
		//creating excel object to access excel utilities
		ExcelFileUtil xl= new ExcelFileUtil(inputpath);
		
		//iterate all rows in Mastertestcases sheet
		System.out.println("No of rows in Master Test cases sheet is:  "+xl.rowCount("MasterTestCases"));
		for(int i=1;i<=xl.rowCount("MasterTestCases");i++){
			String moduleStatus="";
			if(xl.getCellData("MasterTestCases", i, 2).equalsIgnoreCase("Y")){
				//Define Module Name
				String TCModule=xl.getCellData("MasterTestCases", i, 1);
				//date generation
				Date d=new Date();
				SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
				//set path for generating reports
				report= new ExtentReports("./ExtentReports/"+sdf.format(d)+TCModule+".html");
		
				//iterate all rows in TCModule
				System.out.println("No of rows in " +TCModule+" sheet is:  "+xl.rowCount(TCModule));
				for(int j=1; j<=xl.rowCount(TCModule);j++){
					//test case starts here
					test=report.startTest(TCModule);		
					//read all cells from TCModule
					String Description=xl.getCellData(TCModule, j, 0);
					System.out.println("Performing  :"+Description);
					String Function_Name=xl.getCellData(TCModule, j, 1);
					String Locator_Type=xl.getCellData(TCModule, j, 2);
					String Locator_Value=xl.getCellData(TCModule, j, 3);
					String Test_Data=xl.getCellData(TCModule, j, 4);
					//calling the test step functions
					try {
						if(Function_Name.equalsIgnoreCase("startBrowser")){
							FunctionLibrary.startBrowser();
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("openApplication")){
							FunctionLibrary.openApplication();
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("waitForElement")){
							FunctionLibrary.waitForElement( Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("typeAction")){
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("clickAction")){
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("closeBrowser")){
							FunctionLibrary.closeBrowser();
							test.log(LogStatus.INFO, Description);
							
						}
						else if(Function_Name.equalsIgnoreCase("captureData")){
							FunctionLibrary.captureData(Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("VerifyTableData")) {
							FunctionLibrary.VerifyTableData(Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("buttonclickAction")) {
							FunctionLibrary.buttonclickAction(Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("mouseHower")) {
							FunctionLibrary.mouseHower(Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("mouseclick")) {
							FunctionLibrary.mouseclick(Locator_Type, Locator_Value);
							test.log(LogStatus.INFO, Description);
						}
						else if(Function_Name.equalsIgnoreCase("verifySCTableData")) {
							FunctionLibrary.verifySCTableData(Test_Data);
							test.log(LogStatus.INFO, Description);
						}
						
						//write as pass into status cell in TCModule
						xl.setcelldata(TCModule, j, 5, "Pass", outputpath);
						test.log(LogStatus.PASS, Description);
						moduleStatus="true";
					}catch(Exception e){
						System.out.println(e.getMessage());
						//write as fail into status cell in TCModule
						xl.setcelldata(TCModule, j, 5, "Fail", outputpath);	
						test.log(LogStatus.FAIL, Description);
						moduleStatus="false";
					}
					if(moduleStatus.equalsIgnoreCase("true")){
						xl.setcelldata("MasterTestCases", i, 3, "Pass", outputpath);	
					}
					else if(moduleStatus.equalsIgnoreCase("false")){
						xl.setcelldata("MasterTestCases", i, 3, "Fail", outputpath);
					}	
					report.endTest(test);
					report.flush();
				}
			}else{
				//write as blocked in to master testcases sheet
				xl.setcelldata("MasterTestCases", i, 3, "Blocked", outputpath);
			}
		}
	}
}
