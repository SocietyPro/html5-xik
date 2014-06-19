package Welcome_site;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

public class Viewing_My_Profile_About_Info extends Before_Class{

	/**
	 * Go to the page where the test is gonna run.
	 */
	@Before
	public void GoToPage(){
		try{
			chrome_driver.get(Before_Class.url_page());
			First_Time_Profile_Creation.InicializacionInput_Button();
			First_Time_Profile_Creation.profile_name.sendKeys("Testing Profile");
			First_Time_Profile_Creation.create_profile.click();
		}catch(Exception e){
			fail("Give it some error occured in the page");
		}
	}
	
	/**
	 * Create a profile and click in about Button, it is expected that some elements are invisibles
	 */
	@Test
	public void Inputs_Hidden() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aboutTab")));
		chrome_driver.findElement(By.id("aboutTab")).click();
		try{
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtFirstName")));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtLastName")));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtMobileNumber")));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtEmail")));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtFGP")));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtBitCoinAddress")));
			Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("txtSkypeId")));
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	


}
