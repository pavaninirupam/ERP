package com.ERP.DriverFactory;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.ERP.CommonFunctions.CustomersPage;
import com.ERP.CommonFunctions.LoginPage;
import com.ERP.CommonFunctions.SuppliersPage;
import com.ERP.Utilities.ExcelFileUtil;

public class DatadrivenDriverScript 
{
	WebDriver driver;
	String inputpath="C:\\TestWorkspace\\ERP_StockAccounting\\TestInput\\inputdata.xlsx";
	String outputpath="C:\\TestWorkspace\\ERP_StockAccounting\\TestOutput\\DatadrivenResults.xlsx";
	@BeforeMethod
	public void Setup()
	{
		System.setProperty("webdriver.chrome.driver", "C:\\TestWorkspace\\ERP_StockAccounting\\CommonDrivers\\chromedriver.exe");
		driver=new ChromeDriver();
		driver.get("http://projects.qedgetech.com/webapp/login.php");
		driver.manage().window().maximize();
		LoginPage login=PageFactory.initElements(driver, LoginPage.class);
		String results=login.VerifyLogin("admin", "master");
		Reporter.log(results,true);
	}
	@Test(priority=1)
	public void Suppliercreation() throws IOException, EncryptedDocumentException, InvalidFormatException
	{
		SuppliersPage sup=PageFactory.initElements(driver, SuppliersPage.class);
		ExcelFileUtil excel=new ExcelFileUtil(inputpath);
		//count the number of rows;
		int rc=excel.rowCount("Suppliers");
		//count no of cells in 1st row
		int cc=excel.colCount("Suppliers");
		Reporter.log("No of rows :: " +rc+ "  "+"No of columns:: "+cc, true);
		for(int i=1;i<=rc;i++)
		{
			String sname=excel.getCellData("Suppliers", i, 0);
			String address=excel.getCellData("Suppliers", i, 1);
			String city=excel.getCellData("Suppliers", i, 2);
			String country=excel.getCellData("Suppliers", i, 3);
			String cperson=excel.getCellData("Suppliers", i, 4);
			String pnumber=excel.getCellData("Suppliers", i, 5);
			String email=excel.getCellData("Suppliers", i, 6);
			String mnumber=excel.getCellData("Suppliers", i, 7);
			String notes=excel.getCellData("Suppliers", i, 8);
			String results=sup.VerifySuppliersadd(sname, address, city, country, cperson, pnumber, email, mnumber, notes);
			excel.setcelldata("Suppliers", i, 9, results, outputpath);
		}
		
	}
	@Test(priority=2)
	public void Customercreation() throws IOException, EncryptedDocumentException, InvalidFormatException
	{
		CustomersPage sup=PageFactory.initElements(driver, CustomersPage.class);
		ExcelFileUtil excel=new ExcelFileUtil(inputpath);
		//count the number of rows;
		int rc=excel.rowCount("Customers");
		//count no of cells in 1st row
		int cc=excel.colCount("Customers");
		Reporter.log("No of rows :: " +rc+ "  "+"No of columns:: "+cc, true);
		for(int i=1;i<=rc;i++)
		{
			String sname=excel.getCellData("Customers", i, 0);
			String address=excel.getCellData("Customers", i, 1);
			String city=excel.getCellData("Customers", i, 2);
			String country=excel.getCellData("Customers", i, 3);
			String cperson=excel.getCellData("Customers", i, 4);
			String pnumber=excel.getCellData("Customers", i, 5);
			String email=excel.getCellData("Customers", i, 6);
			String mnumber=excel.getCellData("Customers", i, 7);
			String notes=excel.getCellData("Customers", i, 8);
			String results=sup.VerifyCustomeradd(sname, address, city, country, cperson, pnumber, email, mnumber, notes);
			excel.setcelldata("Customers", i, 9, results, outputpath);
		}
		
	}
	@AfterMethod
	public void TearDown()
	{
		driver.close();
	}

}
