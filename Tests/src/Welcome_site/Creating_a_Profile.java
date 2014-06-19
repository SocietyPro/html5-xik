package Welcome_site;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class Creating_a_Profile  extends Before_Class{

	static boolean creation = true;
	
	/**
	 * Go to the page where the test is gonna run.
	 */
	@Before
	public void GotoPage(){
		try{
			chrome_driver.get(Before_Class.url_page());
		}catch(Exception e){
			fail("Give it some error occured in the page");
		}
	}
	
	/**
	 * Search the object with id = 'bnGoToCreate'
	 */
	@Test
	public void Button_CreateProfile_List() {
		try{
			CreateProfile("Testing Profile");
			String current_url = chrome_driver.getCurrentUrl();
			chrome_driver.get(current_url);
			Thread.sleep(1000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Testing Profile']")));
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGoToCreate")));
			}catch(Exception e){
				fail("The element with id = 'btnGoToCreate' is not showed " + e.getMessage());
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * looks the visibility of the elements with id's 
	 * 	btnCreate
	 * 	btnCancel
	 * 	txtName
	 */
	@Test
	public void GoToCreatePage(){
		try{
			GotoCreate_page();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnCreate")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnCancel")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtName")));
			}catch(Exception e){
				fail("Some element with these id's are not visible  \n Elements: btnCreate,btnCancel,txtName");	
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Clicks the Cancel button and waits until the list and the button with id ='btnGoToCreate' are visible
	 */
	@Test
	public void CancelBtn(){
		try{
			GotoCreate_page();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnCancel")));
			WebElement cancelbtn = chrome_driver.findElement(By.id("btnCancel"));
			cancelbtn.click();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Testing Profile']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGoToCreate")));
			}catch(Exception e){
				fail("The list or the input 'btnGoToCreate' aren't visible " + e.getMessage());
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Gives refresh to the page and then add a new profile and waits until this profile are visible in the list
	 */
	@Test
	public void CreateNewProfile(){
		try{
			GotoCreate_page();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnCreate")));
			CreateProfile("Testing Profile 2");
			String current_url = chrome_driver.getCurrentUrl();
			chrome_driver.get(current_url);
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Testing Profile 2']")));
			}catch(Exception e){
				fail("Could not successfully create profile " + e.getMessage());
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Inicialize the objects to add a new profile.
	 * @param ProfileName -> name of the profile
	 */
	public static void CreateProfile(String ProfileName){
		First_Time_Profile_Creation.InicializacionInput_Button();
		First_Time_Profile_Creation.profile_name.sendKeys(ProfileName);
		First_Time_Profile_Creation.create_profile.click();
	}
	
	/**
	 * Gives refresh to the page and waits until the button with id ='btnGoToCreate' is visible, then click this button
	 */
	public static void GotoCreate_page(){
		String current_url = chrome_driver.getCurrentUrl();
		chrome_driver.get(current_url);
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnGoToCreate")));
		WebElement gotoCreate = chrome_driver.findElement(By.id("btnGoToCreate"));
		gotoCreate.click();
	}

}
