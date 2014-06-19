package Welcome_site;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class First_Time_Profile_Creation extends Before_Class{
	
	static WebElement profile_name;
	static WebElement create_profile;
	JavascriptExecutor js;
	
	/**
	 * Before performing each test, calls the method InicializacionInput_Button and clear the input profile name
	 */
	 @Before
	public void ClearInput(){
		try{
			chrome_driver.get(Before_Class.url_page());
			InicializacionInput_Button();
			profile_name.clear();
		}catch(Exception e){
			fail("Give it some error occured clear the input 'txtName' " + e.getMessage());
		}
	}

	 
	/**
	 * Responsible for creating the profile 'Testing profile' (believe it by clicking the button 'btnCreate') after creating it
	 * sees Can not find visible fields 'txtName' and 'btnCreate' and the label 'lblProfileName' be visible
	 */
	@Test
	public void Profile_Create_Click() {
		try{
			WaitingElements();
			InicializacionInput_Button();
			profile_name.sendKeys("Testing profile");
			create_profile.click();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_name")));
			}catch(Exception e){
				fail("Could not successfully create profile " + e.getMessage());
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Responsible for creating the profile 'Testing profile' (believe in giving input enter 'txtName') after creating it sees that 
	 * the fields 'txtName' and  'btnCreate' aren't visible and the field 'lblProfileName' is visible
	 */
	@Test
	public void Create_Profile_Enter(){
		try{
			WaitingElements();
			InicializacionInput_Button();
			Actions builder = new Actions(chrome_driver);
			builder.sendKeys(profile_name, "Testing Profile Enter" + Keys.RETURN).perform();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("p_name")));
			}catch(Exception e){
				fail("Could not successfully create profile " + e.getMessage());
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	* Try to create profiles with invalid characters that are the list verify that for each of these 
	* Characters and displays an error if not shown is concatenated to a string for display in the fail.
	 */
	@Test 
	public void Create_profile_Invalid_characters(){
		try{
			WaitingElements();
			InicializacionInput_Button();
			List<String> Invalid_Characters =  Arrays.asList(">", ":","*","?","|","/","\\","+","-",".","#","%","&","|",
								";","@");
			String charactersInvalid        = "";
			boolean error 					= true;
			for(String character: Invalid_Characters){
				profile_name.sendKeys(character);
				try{
					Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("InvalidCharErr")));
				}catch(Exception e){
					charactersInvalid = charactersInvalid + character + ", ";
					error             = false;
				}
				profile_name.clear();
			}
			if(error == false){
				fail("The error message is not showed for the following characters \n" + "Characters without validation: " 
					 + charactersInvalid);
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}

	
	/**
	 * Initializes the fields necessary to perform the tests.
	 */
	public static void InicializacionInput_Button(){
		profile_name   = chrome_driver.findElement(By.id("txtName"));
		create_profile = chrome_driver.findElement(By.id("btnCreate"));
	}
	
	/**
	 * Wait until elements are visibles
	 */
	public void WaitingElements(){
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("txtName")));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnCreate")));
	}
}
