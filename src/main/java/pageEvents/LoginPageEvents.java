package pageEvents;

import org.testng.Assert;

import pageObjects.LoginPageElements;
import utils.ElementFetch;

public class LoginPageEvents 
{
	
	ElementFetch ele=new ElementFetch();
	public void verifyIfLoginPageLoaded()
	{
		Assert.assertTrue(ele.getWebElement("XPATH",LoginPageElements.loginButton).isDisplayed(),"Element Not Found");
	}
	
	public void enterCredentials()
	{
		ele.getWebElement("XPATH",LoginPageElements.userName).sendKeys("standard_user");
		ele.getWebElement("XPATH",LoginPageElements.password).sendKeys("secret_sauce");
		}

}
