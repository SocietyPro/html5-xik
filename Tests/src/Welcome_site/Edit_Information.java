package Welcome_site;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Before_Class;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class Edit_Information extends Before_Class{

	
	EditInformation_methods edit = new EditInformation_methods();
	static boolean createprofile = true;
	
	/**
	 * Go to the page where the test is gonna run.
	 */
	@Before
	public void GoToPage(){
		try{
			if(createprofile == true){
				chrome_driver.get(Before_Class.url_page());
				First_Time_Profile_Creation.InicializacionInput_Button();
				First_Time_Profile_Creation.profile_name.sendKeys("Testing Profile");
				First_Time_Profile_Creation.create_profile.click();
				createprofile = false;
			}
		}catch(Exception e){
			fail("Give it some error occured in the page");
		}
	}
	
	/**
	 * Saves all the information profile and expects all such information be visible
	 */
	@Test
	public void AllInformation() {
		try{
			edit.GotoEditPage();
			edit.InicializationInputs();
			edit.SendsKeys("test@cambrian.com", "12345678","testinfo","bitcoin","cambrianID");
			edit.Save();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='test@cambrian.com']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='12345678']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='testinfo']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='bitcoin']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='cambrianID']")));
			}catch(Exception e){
				fail("All expected information is not showed");
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Not store any data in the profile information, hope no data are shown
	 */
	@Test
	public void NoInformation(){
		try{
			edit.GotoEditPage();
			edit.InicializationInputs();
			edit.ClearInputs();
			edit.Save();
			try{
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='Email:']")));
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='PGP fingerprint:']")));
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='Bitcoin address:']")));
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='Skype ID:']")));
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='Mobile Number:']")));
			}catch(Exception e){
				fail("It shows some information and should not show anything");
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Only saves some data and expects that data to be displayed and the other not to be visible
	 */
	@Test
	public void Save_Some_Info(){
		try{
			edit.GotoEditPage();
			edit.InicializationInputs();
			edit.ClearInputs();
			edit.SendsKeys("test@cambrian.com", "","testinfo","","cambrianID");
			edit.Save();
			try{
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='test@cambrian.com']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='testinfo']")));
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='cambrianID']")));
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='Bitcoin address:']")));
				Wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//label[text()='Mobile Number:']")));
			}catch(Exception e){
				fail("It shows some information and should not show anything");
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
	
	/**
	 * Change the profile picture, makes calls to method of EditInformation class.
	 */
	@Test 
	public void Change_ProfileImage(){
		try{
			edit.GotoEditPage();
			chrome_driver.findElement(By.xpath("//input[@name='profilePicture']/ancestor::span")).click();
			File path = new File ("Image/changepicture1.jpg");
			String src_currentImage = chrome_driver.findElement(By.id("profiler")).getAttribute("src");
			String path_file = path.getCanonicalPath();
			edit.UploadImage(path_file);
			edit.Save();
			String src_imageNew  = chrome_driver.findElement(By.id("profiler")).getAttribute("src");
			if(src_currentImage.equals(src_imageNew)){
				fail("Failed to change the profile picture correctly");
			}
		}catch(Exception e){
			fail("Some element was not found to do the test. " + e.getMessage());
		}
	}
}
