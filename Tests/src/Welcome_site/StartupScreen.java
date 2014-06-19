package Welcome_site;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

public class StartupScreen  extends Before_Class{

	static boolean creation = true;
	
	/**
	 * Go to the page where the test is gonna run.
	 */
	@Before
	public void GotoPage(){
		try{
			chrome_driver.get(Before_Class.url_page());
			if(creation == true){
				First_Time_Profile_Creation.InicializacionInput_Button();
				First_Time_Profile_Creation.profile_name.sendKeys("Testing Profile");
				First_Time_Profile_Creation.create_profile.click();
				creation = false;
			}	
		}catch(Exception e){
			fail("Give it some error occured in the page" + e.getMessage());
		}
	}
	
	/**
	 * The test wait's until the profile 'Testing profile' is visible on the table
	 */
	@Test
	public void List_ProfileView() {
		String current_url = chrome_driver.getCurrentUrl();
		chrome_driver.get(current_url);
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Testing Profile']")));
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * The test change a profile that is in the list and wait the element with id='lblProfileName' is visible
	 */
	@Test
	public void List_ChangeProfile() {
		try{
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Testing Profile']")));
			WebElement profile = chrome_driver.findElement(By.xpath("//ul/li/a[text()='Testing Profile']"));
			profile.click();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_name")));
			}catch(Exception e){
				fail("The element with id = 'WelcomeProfile' is not showed " + e.getMessage());
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}

}
