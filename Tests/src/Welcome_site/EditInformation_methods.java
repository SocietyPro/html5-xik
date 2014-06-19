package Welcome_site;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import Inicialization.Inicialization_Driver;

public class EditInformation_methods extends Inicialization_Driver{
	
	WebElement email;
	WebElement mobile_number;
	WebElement FGP;
	WebElement bitcoin;
	WebElement skype_id;
	
	/*
	 * Initializes the elements of the profile where the information will be written.
	 */
	public void InicializationInputs(){
		email         = chrome_driver.findElement(By.id("txtEmail"));
		mobile_number = chrome_driver.findElement(By.id("txtMobileNumber"));
		FGP           = chrome_driver.findElement(By.id("txtFGP"));
		bitcoin       = chrome_driver.findElement(By.id("txtBitcoinAddress"));
		skype_id      = chrome_driver.findElement(By.id("txtSkypeId"));
	}
	
	/**
	 * puts the information to be sent in as parameter inputs
	 * @param email_str
	 * @param mobileNumber
	 * @param FGP_str
	 * @param bitcoin_str
	 * @param skype_str
	 */
	public void SendsKeys(String email_str, String mobileNumber, String FGP_str, String bitcoin_str, String skype_str){
		email.sendKeys(email_str);
		mobile_number.sendKeys(mobileNumber);
		FGP.sendKeys(FGP_str);
		bitcoin.sendKeys(bitcoin_str);
		skype_id.sendKeys(skype_str);
	}
	
	/**
	 * Clicked the save button
	 */
	public void Save(){
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Save Changes ']")));
		chrome_driver.findElement(By.xpath("//button[text()='Save Changes ']")).click();
	}
	
	/**
	 * Click on the button and then click on the Edit Information button
	 */
	public void GotoEditPage(){
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aboutTab")));
		chrome_driver.findElement(By.id("aboutTab")).click();
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Edit Info']")));
		chrome_driver.findElement(By.xpath("//button[text()='Edit Info']")).click();
	}
	
	/**
	 * Deletes the information that has the inputs
	 */
	public void ClearInputs(){
		email.clear();
		mobile_number.clear();
		FGP.clear();
		bitcoin.clear();
		skype_id.clear();
	}
	
	/**
	 * Select the image you want to upload
	 * @param filepath
	 * @throws Exception
	 */
	public void UploadImage(String filepath) throws Exception{
		Thread.sleep(2000);
		StringSelection ss = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
}
