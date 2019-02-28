package fixture;

import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SeleniumHandlers {


    private static WebDriver driver;

    private static HashMap<String, WebElement> namedElements;
    private static List<WebElement> selectedElements;
    private static WebElement selectedElement;

    private static ExplicitWait explicitWait;
    private static ExplicitWait explicitWaitAll;
    private static long explicitWaitTimeout;

    static {
        SeleniumHandlers.namedElements = new HashMap<>();
        SeleniumHandlers.selectedElements = new ArrayList<>();
    }

    public static void openGivenWebBrowser(String browserName) throws Throwable {
        launchBrowser(browserName);
    }

    public static void openBrowser() {
        System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
        driver = new FirefoxDriver();
    }

    public static void setImplicitWaitNano(long nanos) {
        driver.manage().timeouts().implicitlyWait(nanos, TimeUnit.NANOSECONDS);
    }

    public static void setImplicitWaitMs(long ms) {
        driver.manage().timeouts().implicitlyWait(ms, TimeUnit.MILLISECONDS);
    }

    public static void setImplicitWaitSeconds(long seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void setExplicitWaitToClickable(int timout) {
        explicitWait = ExplicitWait.CLICKABLE;
        explicitWaitTimeout = timout;
    }

    public static void setExplicitWaitToPresent(int timout) {
        explicitWait = ExplicitWait.PRESENT;
        explicitWaitAll = ExplicitWait.ALL_PRESENT;
        explicitWaitTimeout = timout;
    }

    public static void setExplicitWaitToVisibility(int timout) {
        explicitWait = ExplicitWait.VISIBLE;
        explicitWaitAll = ExplicitWait.ALL_VISIBLE;
        explicitWaitTimeout = timout;
    }

    public static void clearExplicitWait() {
        explicitWait = null;
        explicitWaitAll = null;
        explicitWaitTimeout = 0;
    }

    /**
     * Opens a url
     * @param url should be a fully qualified url such as https://www.google.com rather than google.com
     */
    public static void goToUrl(String url) {
        driver.get(url);
    }

    public static void maximizeWindow() {
        driver.manage().window().maximize();
    }


    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementByCss(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.CSS, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.cssSelector(locator)) :
                selectedElement.findElement(By.cssSelector(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    /**
     * Selected elements will be stored in selectedElements and any of these can be assigned to selectedElement
     * by calling selectFromElements(int index) step definition.
     * Selenium returns an empty list if no elements found, however NoSuchElementException is thrown to
     * remain consistent with the selectElementBy... methods.
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementsByCss(String locator) throws NoSuchElementException {
        if (explicitWaitAll != null) {
            selectedElements = explicitWaitAll.applyWait(driver, SelectorMethod.CSS, locator, explicitWaitTimeout);
            return;
        }
        selectedElements = selectedElement == null ? driver.findElements(By.cssSelector(locator)) :
                selectedElement.findElements(By.cssSelector(locator));
    }

    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementByXpath(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.XPATH, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.xpath(locator)) :
                selectedElement.findElement(By.xpath(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    /**
     * Selected elements will be stored in selectedElements and any of these can be assigned to selectedElement
     * by calling selectFromElements(int index) step definition.
     * Selenium returns an empty list if no elements found, however NoSuchElementException is thrown to
     * remain consistent with the selectElementBy... methods.
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementsByXpath(String locator) throws NoSuchElementException {
        if (explicitWaitAll != null) {
            selectedElements = explicitWaitAll.applyWait(driver, SelectorMethod.XPATH, locator, explicitWaitTimeout);
            return;
        }
        selectedElements = selectedElement == null ? driver.findElements(By.xpath(locator)) :
                selectedElement.findElements(By.xpath(locator));
    }

    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementById(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.ID, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.id(locator)) :
                selectedElement.findElement(By.id(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementByTag(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.TAG, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.tagName(locator)) :
                selectedElement.findElement(By.tagName(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    /**
     * Selected elements will be stored in selectedElements and any of these can be assigned to selectedElement
     * by calling selectFromElements(int index) step definition.
     * Selenium returns an empty list if no elements found, however NoSuchElementException is thrown to
     * remain consistent with the selectElementBy... methods.
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementsByTag(String locator) throws NoSuchElementException {
        if (explicitWaitAll != null) {
            selectedElements = explicitWaitAll.applyWait(driver, SelectorMethod.TAG, locator, explicitWaitTimeout);
            return;
        }
        selectedElements = selectedElement == null ? driver.findElements(By.tagName(locator)) :
                selectedElement.findElements(By.tagName(locator));
    }

    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * If multiple elements are found the first element will be assigned to selectedElement
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementByClassName(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.CLASS, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.className(locator)) :
                selectedElement.findElement(By.className(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    /**
     * Selected elements will be stored in selectedElements and any of these can be assigned to selectedElement
     * by calling selectFromElements(int index) step definition.
     * Selenium returns an empty list if no elements found, however NoSuchElementException is thrown to
     * remain consistent with the selectElementBy... methods.
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementsByClassName(String locator) throws NoSuchElementException {
        if (explicitWaitAll != null) {
            selectedElements = explicitWaitAll.applyWait(driver, SelectorMethod.CLASS, locator, explicitWaitTimeout);
            return;
        }
        selectedElements = selectedElement == null ? driver.findElements(By.className(locator)) :
                selectedElement.findElements(By.className(locator));
    }

    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementByLinkText(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.LINK, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.linkText(locator)) :
                selectedElement.findElement(By.linkText(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    /**
     * Selected element will be stored in selectedElement which is the webElement used by default for most step defs
     * An exception is thrown if no element is found and the value of selectedElement will remain unchanged
     * @param locator a string that targets an element in the dom.
     * @throws NoSuchElementException when no element is not found
     */
    public static void selectElementByPartialLinkText(String locator, String alias) throws NoSuchElementException {
        if (explicitWait != null) {
            selectedElement = explicitWait.applyWait(driver, SelectorMethod.PARTIAL_LINK, locator, explicitWaitTimeout).get(0);
            return;
        }
        selectedElement = selectedElement == null ? driver.findElement(By.partialLinkText(locator)) :
                selectedElement.findElement(By.partialLinkText(locator));
        if (alias != null) {
            namedElements.put(alias, selectedElement);
        }
    }

    public static void selectFromDropdownByLabel(String label) {
        Select select = new Select(selectedElement);
        select.deselectAll();
        select.selectByVisibleText(label);
    }

    public static void selectFromDropdownByValue(String value) {
        Select select = new Select(selectedElement);
        select.deselectAll();
        select.selectByValue(value);
    }

    public static void selectFromDropdownByIndex(int index) {
        Select select = new Select(selectedElement);
        select.deselectAll();
        select.selectByIndex(index);
    }

    /**
     * Selects WebElement from selectedElements and assigned to selectedElement
     * @param index to select from selectedElements
     */
    public static void selectFromElements(Integer index) {
        selectedElement = selectedElements.get(index);
    }

    public static void filterSelectedElementsByXPath(String filter) {
        selectedElements.removeIf(element -> element.findElements(By.xpath(filter)).size() == 0);
    }

    public static void filterSelectedElementsByText(String filterText) {
        selectedElements.removeIf(element -> element.findElements(By.xpath(".//*[contains(text(),'" + filterText + "')]")).size() == 0);
    }

    /**
     * If the named element exists it will become the selectedElement so that actions can be performed on it
     * @param elementName alias to be selected
     */
    public static void selectNamedElement(String elementName) {
        selectedElement = namedElements.get(elementName);
    }

    public static void clickElement() {
        selectedElement.click();
    }

    public static void rightClickElement(){
        Actions actions = new Actions(driver);
        actions.contextClick(selectedElement).perform();
    }

    public static void doubleClickElement() {
        Actions actions = new Actions(driver);
        actions.doubleClick(selectedElement).perform();
    }

    public static void clickAndHold() {
        Actions actions = new Actions(driver);
        actions.clickAndHold(selectedElement).perform();
    }

    /**
     * Convenience method - Submit a form if element is contained within a form
     */
    public static void submit() {
        selectedElement.submit();
    }

    public static void hoverElement() {
        Actions actions = new Actions(driver);
        actions.moveToElement(selectedElement).perform();
    }

    public static void enterText(String text) {
        selectedElement.sendKeys(text);
    }

    public static void refresh() {
        driver.navigate().refresh();
    }

    public static void back() {
        driver.navigate().back();
    }

    public static void forward() {
        driver.navigate().forward();
    }

    public static void pauseMs(long milliseconds) {
        Actions actions = new Actions(driver);
        actions.pause(Duration.ofMillis(milliseconds));
    }

    public static void pauseSecs(long seconds) {
        Actions actions = new Actions(driver);
        actions.pause(Duration.ofSeconds(seconds));
    }

    public static void nameSelectedElement(String elementName) {
        namedElements.put(elementName, selectedElement);
    }

    // Temporary method until explicit waits are implemented
    public static void pause(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    public static void checkCurrentUrl(String expectedUrl) {
        assertEquals(expectedUrl, driver.getCurrentUrl());
    }

    public static void checkCurrentUrlContains(String urlPart) {
        assertThat(driver.getCurrentUrl(), CoreMatchers.containsString(urlPart));
    }

    public static void checkPageTitle(String pageTitle) {
        assertEquals(pageTitle, driver.getTitle());
    }

    public static void checkPageTitleContains(String expectedTitlePart) {
        assertThat(driver.getTitle(), CoreMatchers.containsString(expectedTitlePart));
    }

    public static void checkPageContainsText(String expectedText) {
        List<WebElement> elementsFound = driver.findElements(By.xpath("//*[contains(text(),'" + expectedText + "')]"));
        assertTrue("Text " + expectedText + " not found", elementsFound.size() > 0);
    }

    public static void checkPageDoesNotContainsText(String unexpectedText) {
        List<WebElement> elementsFound = driver.findElements(By.xpath("//*[contains(text(),'" + unexpectedText + "')]"));
        assertEquals(0, elementsFound.size());
    }

    public static void checkPageElementHasInnerText(String expectedText) {
        List<WebElement> elementsFound = driver.findElements(By.xpath("//*[text()='" + expectedText + "']"));
        assertTrue("Text " + expectedText + " not found", elementsFound.size() > 0);
    }

    public static void checkNoPageElementHasInnerText(String unexpectedText) {
        List<WebElement> elementsFound = driver.findElements(By.xpath("//*[text()='" + unexpectedText + "']"));
        assertEquals(0, elementsFound.size());
    }

    public static void checkInnerText(String expectedText) {
        assertEquals(expectedText, selectedElement.getText());
    }

    public static void checkInnerTextContains(String expectedSubText) {
        assertThat(selectedElement.getText(), CoreMatchers.containsString(expectedSubText));
    }

    public static void checkDescendents(String expectedText) {
        assertTrue(selectedElement == null ?
            driver.findElements(By.xpath("//*[contains(text(),'" + expectedText + "')]")).size() > 0 :
            selectedElement.findElements(By.xpath(".//*[contains(text(),'" + expectedText + "')]")).size() > 0);
    }

    public static void iCheckAttributeExists(String attribute) {
        String expectedAttribute = selectedElement.getAttribute(attribute);
        assertNotNull(expectedAttribute);
    }

    public static void checkAttributeValue(String attribute, String value) {
        assertEquals(value, selectedElement.getAttribute(attribute));
    }

    public static void checkAttributeValueContains(String attribute, String value) {
        assertThat(selectedElement.getAttribute(attribute), CoreMatchers.containsString(value));
    }

    /**
     * @param expectedCount expected number of elements in selectedElements
     */
    public static void checkNumberOfElementsFound(int expectedCount) {
        assertEquals(expectedCount, selectedElements.size());
    }

    /**
     * @param expectedCount expected number of elements in selectedElements
     */
    public static void checkNumberOfElementsFoundAtLeast(int expectedCount) {
        assertTrue(selectedElements.size() >= expectedCount);
    }

    public static void checkElementIsDisplayed() {
        assertTrue(selectedElement.isDisplayed());
    }

    public static void checkElementIsSelected() {
        assertTrue(selectedElement.isSelected());
    }

    public static void checkElementIsEnabled() {
        assertTrue(selectedElement.isEnabled());
    }

    public static void invisibilityOfElement(long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.invisibilityOf(selectedElement)));
    }

    public static void invisibilityOfAllElements(long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.invisibilityOfAllElements(selectedElements)));
    }

    public static void elementIsSelected(long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.elementSelectionStateToBe(selectedElement, true)));
    }

    public static void elementIsNotSelected(long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.elementSelectionStateToBe(selectedElement, false)));
    }

    public static void elementIsStale(long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.stalenessOf(selectedElement)));
    }

    public static void textIsPresent(String expectedText, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(selectedElement, expectedText)));
    }

    public static void textIsPresentInValue(String expectedText, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.textToBePresentInElementValue(selectedElement, expectedText)));
    }

    public static void checkTitleWithWait(String expectedTitle, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(selectedElement, expectedTitle)));
    }

    public static void checkTitleContainsWithWait(String expectedTitlePart, long timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        assertTrue(wait.until(ExpectedConditions.textToBePresentInElement(selectedElement, expectedTitlePart)));
    }

    /**
     * Clears both selectedElements list and the single selectedElement
     */
    public static void clearSelectedElements() {
        selectedElement = null;
        selectedElements = new ArrayList<>();
    }

    /**
     * Clears only named elements
     */
    public static void clearNamedElements() {
        namedElements = new HashMap<>();
    }

    /**
     * Initiates the specified browser driver
     * @param browserName name of the browser
     * @throws SeleniumException if browser is not supported
     */
    private static void launchBrowser(String browserName) throws SeleniumException {
        if(browserName.equalsIgnoreCase("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else if(browserName.equalsIgnoreCase("Chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("disable-infobars");
            System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
            driver = new ChromeDriver(options);
        }
        else {
            throw new SeleniumException("Browser " + browserName + " is not supported");
        }
    }

    public static void closeBrowser() {
        driver.close();
    }
}
