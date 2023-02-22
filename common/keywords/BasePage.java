package keywords;

import java.util.List;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;



public class BasePage {
	
	public static BasePage getBasePage(WebDriver driver) {
		return new BasePage();
	}
	public void getUrlPage(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}

	public String getTitlePage(WebDriver driver) {
		return driver.getTitle();
	}

	public String getUrlPage(WebDriver driver) {
		return driver.getCurrentUrl();
	}


	protected int getOptionsSize(WebDriver driver,String xpathLocator) {
		return getWebElementList(driver, xpathLocator).size();
	}	
	protected int getOptionsSizeDynamic(WebDriver driver,String locatorType,String...dynamicValue) {
		return getWebElementList(driver, getXpathDynamic(locatorType, dynamicValue)).size();
	}	
	
	public String getElementAttribute(WebDriver driver, String xpathLocator, String attributeName) {
		return getWebElement(driver, xpathLocator).getAttribute(attributeName);
	}
	public String getElementAttributeDynamic(WebDriver driver, String locatorType, String attributeName,String...dynamicValue) {
		return getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)).getAttribute(attributeName);
	}
	
	public String getElementText(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).getText();
	}
	public String getElementTextDynamic(WebDriver driver, String locatorType,String...dynamicValue) {		
		return getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)).getText();
	}
	
	public String getElementCssValue(WebDriver driver, String xpathLocator, String propertyName) {
		return getWebElement(driver, xpathLocator).getCssValue(propertyName);
	}
	
	
	public String getElementCssValueDynamic(WebDriver driver, String locatorType, String propertyName,String...dynamicValue) {
		return getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)).getCssValue(propertyName);
	}

	public String getHexaColorFromRGBA(String rgbaValue) {
		return Color.fromString(rgbaValue).asHex();
	}

	public int getElementSize(WebDriver driver, String xpathLocator) {
		return getWebElementList(driver, xpathLocator).size();
	}
	public int getElementSizeDynamic(WebDriver driver, String locatorType,String...dynamicValue) {
		return getWebElementList(driver, getXpathDynamic(locatorType, dynamicValue)).size();
	}	
	
	public static By getByXpath(String xpathLocator) {
		return By.xpath(xpathLocator);
	}
	
	private By getByLocators(String locatorType) {
		By by =null;
		System.out.println("1.locatorType: " + locatorType);
		if(locatorType.startsWith("id=")||locatorType.startsWith("ID=")||locatorType.startsWith("Id=")) {
			by = By.id(locatorType.substring(3));
		}else if(locatorType.startsWith("class=")||locatorType.startsWith("CLASS=")||locatorType.startsWith("Class=")){
			by = By.className(locatorType.substring(6));			
		}else if(locatorType.startsWith("name=")||locatorType.startsWith("NAME=")||locatorType.startsWith("Name=")){
			by = By.name(locatorType.substring(5));			
		}else if(locatorType.startsWith("css=")||locatorType.startsWith("CSS=")||locatorType.startsWith("Css=")){
			by = By.cssSelector(locatorType.substring(4));			
		}else if(locatorType.startsWith("xpath=")||locatorType.startsWith("XPATH=")||locatorType.startsWith("Xpath=")){
			by = By.xpath(locatorType.substring(6));
			System.out.println("2.getBy: " +by);

		}else {
			throw new RuntimeException("Locator Type is not Supported");
		}		
		return by;		
	}
	
	public String getXpathDynamic(String locatorType, String...dynamicValue) {
		if(locatorType.startsWith("Xpath=")||locatorType.startsWith("XPATH=")||locatorType.startsWith("xpath=")||locatorType.startsWith("xPath=")) {
			locatorType = String.format(locatorType,(Object[]) dynamicValue);
		}
		return locatorType;
	}
	
	public WebElement getWebElement(WebDriver driver, String locatorType) {
		return driver.findElement(getByXpath(locatorType));
		//driver.findElement(By.Xpath(""));
	}
	
	public WebElement getWebElementDynamic(WebDriver driver, String locatorType) {		
		return driver.findElement(getByLocators(locatorType));
	}
	public List<WebElement> getWebElementList(WebDriver driver, String xpathLocator) {
		return driver.findElements(getByXpath(xpathLocator));
	}
	
	public void clickToElement(WebDriver driver, String xpathLocator) {
		getWebElement(driver, xpathLocator).click();
	}
	
	public void clickToElementDynamic(WebDriver driver, String locatorType,String...dynamicValue) {
		getWebElementDynamic(driver,getXpathDynamic(locatorType, dynamicValue)).click();
	}	
	
	protected void sendKeyToElement(WebDriver driver, String locatorType, String textValue) {
		WebElement element = getWebElement(driver, locatorType);
		element.clear();
		element.sendKeys(textValue);
	}
	
	protected void sendKeyToElementDynamic(WebDriver driver, String locatorType,String textValue, String...dynamicValue) {
		WebElement element = getWebElementDynamic(driver, getXpathDynamic(locatorType, dynamicValue));
		element.clear();
		element.sendKeys(textValue);
	}
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	
	public void isDropdownMultiple(WebDriver driver, String xpathLocator) {
		Select select = new Select(getWebElement(driver, xpathLocator));
		select.isMultiple();
	}

	public void selectCustomDropDown(WebDriver driver, String parentXpath, String childXpath, String expectedText){
		getWebElement(driver, parentXpath).click();
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);		
		List<WebElement> allItemm = explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));	
		for (WebElement webElement : allItemm) {
			if (webElement.getText().trim().equals(expectedText)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", webElement);
				webElement.click();
				break;
			}
		}	
	}	


	
	protected void sendKeyToElementDynamic111(WebDriver driver, String locatorType,String textValue, String...dynamicValue) {
		WebElement element = getWebElementDynamic(driver, getXpathDynamic(locatorType, dynamicValue));
		element.clear();
		element.sendKeys(textValue);
	}
	
	

	protected void selectDefaultDropDown(WebDriver driver, String xpathLocator, String textValue) {
		Select DropDown = new Select(getWebElement(driver, xpathLocator));
		DropDown.selectByValue(textValue);
	}
	
	protected String getTextDefaultDropDown(WebDriver driver, String xpathLocator) {
		Select DropDown = new Select(getWebElement(driver, xpathLocator));
		return DropDown.getFirstSelectedOption().getText();
	}
	
	protected void selectDefaultDropDownDynamic(WebDriver driver, String locatorType, String textValue, String...dynamicValue) {
		Select DropDown = new Select(getWebElementDynamic(driver, getXpathDynamic(locatorType, dynamicValue)));
		DropDown.selectByValue(textValue);
	}	
	public void checkToDefaultCheckBoxRadio(WebDriver driver, String xpathLocator) {
		WebElement element = getWebElement(driver, xpathLocator);
		if (!element.isSelected()) {
			element.click();
		}
	}

	public void uncheckToDefaultCheckBox(WebDriver driver, String xpathLocator) {
		WebElement element = getWebElement(driver, xpathLocator);
		if (element.isSelected()) {
			element.click();
		}
	}

	public boolean isElementDisplayed(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isDisplayed();
	}
	public boolean isElementDisplayedDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		return getWebElementDynamic(driver, getXpathDynamic(locatorType, dynamicValue)).isDisplayed();
	}
	public boolean isElementSelected(WebDriver driver, String locatorType) {
		return getWebElement(driver, locatorType).isSelected();
	}
	public boolean isElementSelectedDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		return getWebElementDynamic(driver, getXpathDynamic(locatorType, dynamicValue)).isSelected();
	}

	public boolean isElementEnabled(WebDriver driver, String xpathLocator) {
		return getWebElement(driver, xpathLocator).isEnabled();
	}
	
	public boolean isElementEnabledDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		return getWebElementDynamic(driver, getXpathDynamic(locatorType, dynamicValue)).isEnabled();
	}
	
	public void waitForWebElementVisible(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(xpathLocator)));
	}
	
	public void waitForWebElementVisibleDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByLocators(getXpathDynamic(locatorType, dynamicValue))));
	}		
	
	public void waitForAllWebElementVisible(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByXpath(xpathLocator)));
	}	
	public void waitForAllWebElementVisibleDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(getByLocators(getXpathDynamic(locatorType, dynamicValue))));
	}		
	
	public void waitForWebElementInvisible(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(xpathLocator)));
	}
	
	public void waitForWebElementInvisibleDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByLocators(getXpathDynamic(locatorType, dynamicValue))));
	}		
	
	public void waitForAllWebElementInisible(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getWebElementList(driver, xpathLocator)));
	}
	
	public void waitForAllWebElementInisibleDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.invisibilityOfAllElements(getWebElementList(driver, getXpathDynamic(locatorType, dynamicValue))));
	}	
	
	public void waitForWebElementClickable(WebDriver driver, String xpathLocator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(xpathLocator)));
	}
	public void waitForWebElementClickableDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByLocators(getXpathDynamic(locatorType, dynamicValue))));
	}
	
	
	public void switchToFrameIframe(WebDriver driver, String xpathLocator) {
		driver.switchTo().frame(getWebElement(driver, xpathLocator));
	}

	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	public Alert waitForAlertPresence(WebDriver driver) {
		// Hàm này củng đã check alert
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		return explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert(WebDriver driver) {
		// Hàm xử lý khi alert xuất hiện
		// driver.switchTo().alert().accept();
		// waitForAlertPresence bản chất đã có alert nên ko cần goi lại switchto. alert
		waitForAlertPresence(driver).accept();
	}

	public void dismissAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}

	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}

	public void sendkeyToAlert(WebDriver driver, String valueText) {
		waitForAlertPresence(driver).sendKeys(valueText);
	}

	public void switchToWindowByID(WebDriver driver, String windownID) {
		Set<String> allWindownIDs = driver.getWindowHandles();
		for (String id : allWindownIDs) {
			if (!id.equals(windownID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String tabTitle) {
		Set<String> allWindownIDs = driver.getWindowHandles();
		for (String id : allWindownIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(tabTitle)) {
				break;
			}
		}
	}

	public void closeAllTabWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindownIDs = driver.getWindowHandles();
		for (String id : allWindownIDs) {
			if (!id.equals(parentID)) {
				driver.switchTo().window(id);
				driver.close();
			}
			driver.switchTo().window(parentID);
		}
	}
	
	public void hoverMoveToWebElement(WebDriver driver, String xpathLocator) {
		Actions action = new Actions(driver);
		action.moveToElement(getWebElement(driver, xpathLocator)).perform();
	}
	
	public void pressKeyToElement(WebDriver driver, String locatorType, Keys key, String... dynamicValue) {
		Actions action = new Actions(driver);
		action.sendKeys(getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)),key).perform();
	}
	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}
	
	public void clickToWebElementByJS(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, xpathLocator));
	}

	public void clickToWebElementByJSDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].click();", getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)));
	}
	
	public void scrolltoWebElement(WebDriver driver, String xpathLocator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, xpathLocator));
	}
	public void scrolltoWebElementDynamic(WebDriver driver, String locatorType, String...dynamicValue) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)));
	}
		
	public void removeAtributeInDOM(WebDriver driver, String xpathLocator,String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, xpathLocator));
	}
	
	public void removeAtributeInDOMDynamic(WebDriver driver, String attributeRemove,String locatorType, String...dynamicValue) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", getWebElement(driver, getXpathDynamic(locatorType, dynamicValue)));
	}
	
	public int getRandomNumber() {
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(9999);
	}
	
	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
}  
