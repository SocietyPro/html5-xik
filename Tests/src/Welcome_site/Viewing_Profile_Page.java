package Welcome_site;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

public class Viewing_Profile_Page extends Before_Class{

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
	 * Search the object with id = 'AboutTab'
	 */
	@Test
	public void BtnAbout() {
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aboutTab")));
		}catch(Exception e){
			fail("The object with id='AboutTab' doesn't is visible. " + e.getMessage());
		}
		
	}

}
