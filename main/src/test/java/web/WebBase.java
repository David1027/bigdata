package web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebDriverBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @title 前端自动化测试类
 * @author Lijie HelloBox@outlook.com
 */
public class WebBase {
  protected WebDriver driver;
  protected Map<String, Object> vars;
  JavascriptExecutor js;
  private String serverUrl = "";

  @Before
  public void setUp() throws MalformedURLException {
    WebDriver driver = new RemoteWebDriver(new URL(serverUrl), DesiredCapabilities.firefox());
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }
}
