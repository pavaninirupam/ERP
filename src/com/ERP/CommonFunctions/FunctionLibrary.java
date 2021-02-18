package com.ERP.CommonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.ERP.Utilities.PropertyFileUtil;

public class FunctionLibrary 
{
	public static WebDriver driver;
	static WebElement searchbtn;
	static WebElement searchtextbox;
	public static void search(){
		searchbtn=driver.findElement(By.xpath("//button[@id='btnsubmit']"));
		searchtextbox=driver.findElement(By.id("psearch"));
		WebElement searchpanel=driver.findElement(By.xpath("//span[@class='glyphicon glyphicon-search ewIcon']"));		
		if(!searchtextbox.isDisplayed())
			searchpanel.click();
			WebDriverWait wait=new WebDriverWait(driver,10);		
			wait.until(ExpectedConditions.visibilityOf(searchtextbox));
			searchtextbox.clear();		
	}
	public static WebDriver startBrowser()
	{
		try {
			if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("chrome"))
			{
				Reporter.log("Starting in Chrome Browser",true);
				System.setProperty("webdriver.chrome.driver", "C:\\TestWorkspace\\ERP_StockAccounting\\CommonDrivers\\chromedriver.exe");
				driver=new ChromeDriver();
			}
			else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
			{
				Reporter.log("Starting in Firefox Browser",true);
				System.setProperty("webdriver.gecko.driver", "C:\\TestWorkspace\\ERP_StockAccounting\\CommonDrivers\\geckodriver.exe");
				driver=new FirefoxDriver();
			}
			else if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("internetexplorer"))
			{
				Reporter.log("Starting in IE Browser",true);
				System.setProperty("webdriver.ie.driver", "C:\\TestWorkspace\\ERP_StockAccounting\\CommonDrivers\\IEDriverServer.exe");
				driver=new FirefoxDriver();
			}
			else
			{
				Reporter.log("Browser not matching",true);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return driver;		
	}
	public static void openApplication() throws Throwable
	{
		driver.get(PropertyFileUtil.getValueForKey("Url"));
		driver.manage().window().maximize();
	}
	public static WebElement locating(String locatortype,String Locatorvalue)
	{
		WebElement webele = null;
		String ltype=locatortype.toLowerCase();
		switch(ltype)
		{
		case "id":
			webele=driver.findElement(By.id(Locatorvalue));
			break;
		case "name":
			webele=driver.findElement(By.name(Locatorvalue));
			break;
		case "xpath": 
			webele=driver.findElement(By.xpath(Locatorvalue));
			break;		
		case "cssselector":
			webele=driver.findElement(By.cssSelector(Locatorvalue));
		default:
			System.out.println("Locator not found");
		}
		return webele;
	}
	public static void waitForElement(String locatortype,String locatorvalue,String waitTime) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		WebElement waitelement=FunctionLibrary.locating(locatortype, locatorvalue);
		System.out.println(waitelement.getText());
		//int wt=Integer.parseInt(waitTime);
		WebDriverWait wait=new WebDriverWait(driver,Integer.parseInt(waitTime));
		wait.until(ExpectedConditions.elementToBeClickable(waitelement));
	}

	public static void  typeAction(String locatortype,String locatorvalue,String testdata) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		WebElement element=FunctionLibrary.locating(locatortype, locatorvalue);
		element.clear();
		element.sendKeys(testdata);
	}
	public static void clickAction(String locatortype,String locatorvalue) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		WebElement element=FunctionLibrary.locating(locatortype, locatorvalue);
		element.sendKeys(Keys.ENTER);
	}
	public static void titleValidation(String Exp_title)
	{
		String act_title=driver.getTitle();
		Assert.assertEquals(act_title, Exp_title,"Title is Not matching");
	}
	public static void closeBrowser()
	{
		driver.close();
	}
	public static String captureData(String locatortype,String locatorvalue) throws IOException
	{
		String data=FunctionLibrary.locating(locatortype, locatorvalue).getAttribute("value")	;
		FileWriter fw=new FileWriter("C:\\TestWorkspace\\ERP_StockAccounting\\CaptureData\\Capturedata.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(data);
		bw.flush();
		bw.close();
		return data;
	}
	public static void validatedata(String exp,String act)
	{
		Assert.assertEquals(act, exp,"fail");
	}
	public static void pagedown()
	{
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("window.scrollBy(0,250)");
	}
	
	@SuppressWarnings({ "unused", "resource" })
	public static void VerifyTableData(String testData) throws IOException
	{
		int colnum1=Integer.parseInt(testData);
		search();
		java.io.FileReader fr=new java.io.FileReader("C:\\TestWorkspace\\ERP_StockAccounting\\CaptureData\\Capturedata.txt");
		BufferedReader br=new BufferedReader(fr);
		String exp_data=br.readLine();		
		searchtextbox.sendKeys(exp_data);
		searchbtn.click();
		WebElement table =driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']"));
		//count no of rows in a table
		List<WebElement> rows =table.findElements(By.tagName("tr"));
		for(int i=1; i<rows.size();i++)
		{
			String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+colnum1+"]/div/span/span")).getText();
			Assert.assertEquals(act_data, exp_data);
			System.out.println(act_data+"    "+exp_data);
			break;
		}
		
	}
	public static void buttonclickAction(String locatortype,String locatorvalue) throws EncryptedDocumentException, InvalidFormatException, IOException
	{
		WebElement element=FunctionLibrary.locating(locatortype, locatorvalue);
		element.click();
	}
	public static void mouseHower(String locatortype,String locatorvalue)
	{
		WebElement element=FunctionLibrary.locating(locatortype, locatorvalue);
		Actions ms=new Actions(driver);
		ms.moveToElement(element).build().perform();
	}
	public static void mouseclick(String locatortype,String locatorvalue)
	{
		WebElement element=FunctionLibrary.locating(locatortype, locatorvalue);
		Actions ms=new Actions(driver);
		ms.moveToElement(element).click().build().perform();
	}
	@SuppressWarnings("unused")
	public static void verifySCTableData(String testData){
		search();
		searchtextbox.sendKeys(testData);
		searchbtn.click();
		WebElement table =driver.findElement(By.xpath("//*[@id='tbl_a_stock_categorieslist']"));
		List<WebElement> rows =table.findElements(By.tagName("tr"));
		for(int i=1; i<rows.size();i++){
			String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_categorieslist']/tbody/tr["+i+"]/td[4]/div/span/span")).getText();
			Assert.assertEquals(act_data, testData);
			System.out.println(act_data+"    "+testData);
			break;
		}
	}
		@SuppressWarnings("unused")
		public static void VerifyCustTableData(String testData){			
			search();
			searchtextbox.sendKeys(testData);
			searchbtn.click();
			WebElement table =driver.findElement(By.xpath("//*[@id='tbl_a_customerslist']"));
			List<WebElement> rows =table.findElements(By.tagName("tr"));
			for(int i=1; i<rows.size();i++){
				String act_data=driver.findElement(By.xpath("//table[@id='tbl_a_customerslist']/tbody/tr["+i+"]/td[5]/div/span/span")).getText();
				Assert.assertEquals(act_data, testData);
				System.out.println(act_data+"    "+testData);
				break;
			}
		
	}

}
