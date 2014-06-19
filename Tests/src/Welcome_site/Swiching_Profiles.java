package Welcome_site;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

public class Swiching_Profiles extends Before_Class{

	static boolean creation = true;
	
	/**
	 * Go to the page where the test is gonna run.
	 */
	@Before
	public void GoToPage(){
		try{
			if(creation == true){
				chrome_driver.get(Before_Class.url_page());
				Creating_a_Profile.CreateProfile("Testing Profile");
				Creating_a_Profile.GotoCreate_page();
				Creating_a_Profile.CreateProfile("Profile Testing");
				Creating_a_Profile.GotoCreate_page();
				creation = false;
			}
		}catch(Exception e){
			fail("Give it some error occured in the page");
		}
	}
	
	/**
	 * Swich to the profile 'Testing Profile' and verifies the name of the profile in the page
	 */
	@Test
	public void SwichingTestingProfile(){ 
		try{
			ViewList();
			WebElement TestingProfile = chrome_driver.findElement(By.xpath("//ul/li/a[text()='Testing Profile']"));
			TestingProfile.click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_name")));
			System.out.println(chrome_driver.findElement(By.id("p_name")).getText());
			if(!chrome_driver.findElement(By.id("p_name")).getText().equals("Testing Profile")){
				fail("Can't swiching to the correct profile or the name of the profile is incorrect. ");
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Swich to the profile 'Profile Testing' and verifies the name of the profile in the page
	 */
	@Test
	public void SwichingProfileTesting(){ 
		try{
			ViewList();
			WebElement TestingProfile = chrome_driver.findElement(By.xpath("//ul/li/a[text()='Profile Testing']"));
			TestingProfile.click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_name")));
			System.out.println(chrome_driver.findElement(By.id("p_name")).getText());
			if(!chrome_driver.findElement(By.id("p_name")).getText().equals("Profile Testing")){
				fail("Can't swiching to the correct profile or the name of the profile is incorrect. ");
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}

	/**
	 * Refresh the page and waits the visibility of the 2 profiles in the table.
	 */
	public void ViewList(){
		try{
			chrome_driver.get(Before_Class.url_page());
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Testing Profile']")));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul/li/a[text()='Profile Testing']")));
		}catch(Exception e){
			fail("Some profile isn't visible in the table " + e.getMessage());
		}
	}

}
