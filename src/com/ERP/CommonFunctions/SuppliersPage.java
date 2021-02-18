package com.ERP.CommonFunctions;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuppliersPage 
{
	WebDriver driver;
	WebDriverWait wait;
	public SuppliersPage(WebDriver driver) 
	{
		this.driver=driver;
	}
	//Elements on the page
	@FindBy(xpath="//li[@id='mi_a_suppliers']//a[@href='a_supplierslist.php'][normalize-space()='Suppliers']")
	WebElement clickSupplier;
	@FindBy(xpath="//div[@class='panel-heading ewGridUpperPanel']//span[@class='glyphicon glyphicon-plus ewIcon']")
	WebElement clickAddsupbtn;
	@FindBy(id="x_Supplier_Number")
	WebElement SNumber;
	@FindBy(id="x_Supplier_Name")
	WebElement SName;
	@FindBy(xpath="//textarea[@id='x_Address']")
	WebElement address;
	@FindBy(xpath="//input[@id='x_City']")
	WebElement city;
	@FindBy(xpath="//input[@id='x_Country']")
	WebElement country;
	@FindBy(xpath="//input[@id='x_Contact_Person']")
	WebElement CPerson;
	@FindBy(xpath="//input[@id='x_Phone_Number']")
	WebElement PNumber;
	@FindBy(xpath="//input[@id='x__Email']")
	WebElement email;
	@FindBy(id="x_Mobile_Number")
	WebElement MNumber;
	@FindBy(id="x_Notes")
	WebElement Notes;
	@FindBy(id="btnAction")
	WebElement clickadd;
	//for confirmation ok
	@FindBy(xpath="//button[normalize-space()='OK!']")
	WebElement clickconfirmok;
	@FindBy(xpath="(//button[text()='OK'])[6]")
	WebElement clickalertok;
	//for validation
	@FindBy(xpath="//span[@class='glyphicon glyphicon-search ewIcon']")
	WebElement searchpanel;
	@FindBy(id="psearch")
	WebElement searchtextbox;
	@FindBy(xpath="//button[@id='btnsubmit']")
	WebElement searchbtn;
	@FindBy(xpath="//table[@id='tbl_a_supplierslist']")
	WebElement suptable;
	
	//Method
	public String VerifySuppliersadd(String sname,String address,String city,String Country,String ContactPerson,String PhoneNo,
			String email,String MobileNo,String notes)
	{
		String res=" ";
		wait=new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(clickSupplier));
		clickSupplier.click();
		wait.until(ExpectedConditions.elementToBeClickable(clickAddsupbtn));
		clickAddsupbtn.click();
		wait.until(ExpectedConditions.visibilityOf(SNumber));
		String expsnum=SNumber.getAttribute("value");
		SName.sendKeys(sname);
		this.address.sendKeys(address);
		this.city.sendKeys(city);
		this.country.sendKeys(Country);
		CPerson.sendKeys(ContactPerson);
		PNumber.sendKeys(PhoneNo);
		this.email.sendKeys(email);
		MNumber.sendKeys(MobileNo);
		this.Notes.sendKeys(notes);
		clickadd.sendKeys(Keys.ENTER);; //add
		wait.until(ExpectedConditions.visibilityOf(clickconfirmok));
		clickconfirmok.click();
		wait.until(ExpectedConditions.visibilityOf(clickalertok));
		clickalertok.click();
		//For validation we need to search
		//wait.until(ExpectedConditions.visibilityOf(searchpanel));
		if(!searchtextbox.isDisplayed())
		searchpanel.click();
		wait.until(ExpectedConditions.visibilityOf(searchtextbox));
		searchtextbox.clear();
		searchtextbox.sendKeys(expsnum);
		searchbtn.click();
		wait.until(ExpectedConditions.visibilityOf(suptable));
		//capture supplier num from table
		String actsnum=driver.findElement(By.xpath("//table[@id='tbl_a_supplierslist']/tbody/tr[1]/td[6]/div/span/span")).getText();	
				
		if(actsnum.equals(expsnum))
		{
			res="Pass";
		}
		else
		{
			res="Fail";
		}		
		return res;
	}	
}
