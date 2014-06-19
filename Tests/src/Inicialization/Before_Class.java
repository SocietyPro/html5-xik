package Inicialization;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Before_Class extends Inicialization_Driver{

	/**
	 * initializes the chrome_driver and set a preference to use Chrome as a driver to make the tests.
	 * @throws Exception
	 */
	@BeforeClass 
	public static void open() throws Exception {
		try{
			System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
		    options.addArguments("test-type");
		    options.addArguments("--start-maximized"); 
		    options.addArguments("--unlimited-storage");
		    capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			chrome_driver = new ChromeDriver(capabilities);
			Wait = new WebDriverWait(chrome_driver, 10,1);
		}catch(Exception e){
			fail("Surgio algun error al realizar la inicializacion." + e.getMessage());
		}
	}
	
	/**
	 * Close the chrome_driver after all test are finished
	 * @throws Exception
	 */
	@AfterClass
	public static void close() throws Exception {
		chrome_driver.quit();
	}
	
	/**
	 * reads the file 'url.txt' the address to which you want to go for tests
	 * @return
	 * @throws IOException
	 */
	public static String url_page() throws IOException{
		String dir_cambrian="";
		File path = new File ("url.txt");
		String url_string = path.getCanonicalPath();
		String cambia = url_string.replace('\\', '/');
		Scanner path_string = new Scanner(new File(cambia));
		dir_cambrian = path_string.nextLine();
		path_string.close();
		return dir_cambrian;
	}

}
