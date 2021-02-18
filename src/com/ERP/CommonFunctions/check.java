package com.ERP.CommonFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class check {

	public static void main(String[] args) 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\TestWorkspace\\ERP_StockAccounting\\CommonDrivers\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://projects.qedgetech.com/webapp/login.php");
		driver.findElement(By.id("btnsubmit")).click();;
		driver.manage().window().maximize();
		driver.navigate().to("http://projects.qedgetech.com/webapp/a_supplierslist.php?cmd=search&t=a_suppliers&psearch=Supplier-00000002038&psearchtype=");
		String sno=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getAttribute("value");
		System.out.println(sno);
		driver.navigate().to("http://projects.qedgetech.com/webapp/a_suppliersadd.php?showdetail=");
		String snum=driver.findElement(By.id("x_Supplier_Number")).getAttribute("value");
		System.out.println(snum);
		driver.close();
	}

}
