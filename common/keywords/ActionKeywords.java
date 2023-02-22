package keywords;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.server.handler.GetElementText;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import executionEngine.RunTestscript;
import io.github.bonigarcia.wdm.WebDriverManager;
import utility.Constants;
import utility.Log;

public class ActionKeywords extends BasePage{

	public static WebDriver driver;
	
	public ActionKeywords() {
		// TODO Auto-generated constructor stub
		this.driver = driver;
	}
	

	public static void openBrowser(String locator, String browser) {
		Log.info("Opening Browser");
		try {
			if (browser.equalsIgnoreCase("Firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				Log.info("Firefox browser started");
			} else if (browser.equalsIgnoreCase("IE")) {
				WebDriverManager.iedriver().setup();
				driver = new InternetExplorerDriver();
				Log.info("IE browser started");
			} else if (browser.equalsIgnoreCase("Chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
				Log.info("Chrome browser started");
			}
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		} catch (Exception e) {
			Log.info("Not able to open the Browser --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public void navigate(String locator,String data) {
		try {
			Log.info("Navigating to URL");
			driver.get(data);
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		} catch (Exception e) {
			Log.info("Not able to navigate " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public static void click(String locator, String data) {
		try {
			Log.info("Clicking on Webelement " + locator);
			driver.findElement(By.xpath(locator)).click();
		} catch (Exception e) {
			Log.error("Not able to click --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public void input(String locator, String data) {
		try {
			Log.info("Entering the text in " + locator);
			waitForWebElementVisible(locator);
			getWebElement(driver,locator).clear();
			getWebElement(driver,locator).sendKeys(data);
		} catch (Exception e) {
			Log.error("Not able to Enter UserName --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}
	
	public boolean verifyText(String locator, String data) {
			Log.info("Text is  " + locator);
			waitForWebElementVisible(locator);
			//String element= driver.findElement(By.xpath(locator)).getText();
			String element= getElementText(driver,locator);
			if (data.equals(element))
	        {
	            return RunTestscript.bResult = true;
	        }
	        else return RunTestscript.bResult = false;		
	}
	

	public String getTotalPage(String locator, String data) {
		waitForAllWebElementVisible(driver,locator);
		sleepInSecond(5);
		String a = getElementText(driver, locator);
		System.out.println("check ham total page: " + a);
		return a;		
	}
	
	
	public static void waitForWebElementVisible(String locator) {
		try {
			Log.info("explicitWait in " + locator);
			WebDriverWait explicitWait = new WebDriverWait(driver, 30);
			explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
		} catch (TimeoutException e) {
			Log.error("Not able visibilityOfElementLocated by explicitWait --- " + e.getMessage());
			System.out.println("Test waitForWebElementVisible -c  ++++++:" + RunTestscript.bResult);
			RunTestscript.bResult = false;
		}
	}
	

	public static void sleep(String locator, String data) throws Exception {
		try {
			Log.info("Wait for 5 seconds");
			Thread.sleep(5000);
		} catch (Exception e) {
			Log.error("Not able to Wait --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public static void selectDropdownList(String locator, String data) throws Exception {
		try {
			Log.info("Select dropdown list");
			WebElement element = driver.findElement(By.xpath(locator));
			Select select = new Select(element);
			select.selectByVisibleText(data);
		} catch (Exception e) {
			Log.error("Can not select item in dropdown or not found item --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public static void selectDropdown(String locator, String data) {
		try {
			Log.info("Selecting to Dropdown/List " + locator);
			Select dropdown = new Select(driver.findElement(By.xpath(locator)));
			dropdown.selectByVisibleText(data);
		} catch (Exception e) {
			Log.error("Not able to select --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public static void isControlDisplayed(String locator, String data) {
		try {
			Log.info("Check WebElement is displayed " + locator);
			driver.findElement(By.xpath(locator)).isDisplayed();
		} catch (Exception e) {
			Log.error("Control not displayed --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

	public static void isControlSelected(String locator, String data) {
		try {
			Log.info("Check WebElement is displayed " + locator);
			driver.findElement(By.xpath(locator)).isSelected();
		} catch (Exception e) {
			Log.error("Control not displayed --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}		



	
	public static void closeBrowser(String locator, String data) {
		try {
			Log.info("Closing the browser");
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			Log.error("Not able to Close the Browser --- " + e.getMessage());
			RunTestscript.bResult = false;
		}
	}

}