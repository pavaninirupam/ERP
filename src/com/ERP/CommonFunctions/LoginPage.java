package com.ERP.CommonFunctions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage 
{
	WebDriver driver;
	WebDriverWait wait;
	//Constructor to avoid overloading of driver
	public LoginPage(WebDriver driver)
	{
		this.driver=driver;	
	}
	//Elements on the page
	@FindBy(id="btnreset")
	WebElement resetbtn;
	@FindBy(id="username")
	WebElement uname;
	@FindBy(id="password")
	WebElement pword;
	@FindBy(id="btnsubmit")
	WebElement loginbtn;
	
	//Method
	public String VerifyLogin(String username,String password)
	{
		String res=" ";
		wait=new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable(resetbtn));
		resetbtn.click();
		wait.until(ExpectedConditions.visibilityOf(uname));
		uname.sendKeys(username);
		wait.until(ExpectedConditions.visibilityOf(pword));
		pword.sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(loginbtn));
		loginbtn.click();
		String Expected="dashboard";
		String Actual=driver.getCurrentUrl();
		if(Actual.contains(Expected))
			res="Login successful";
			else
				res="Login Fail";
		return res;
	}
}
