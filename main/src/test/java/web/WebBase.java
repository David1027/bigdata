package web;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @title 前端自动化测试类
 * @author Lijie HelloBox@outlook.com
 */
public class WebBase  {
  protected WebDriver driver;
  protected Map<String, Object> vars;
  JavascriptExecutor js;
  private String serverUrl = "";

  @Before
  public void setUp() throws MalformedURLException {
    driver = new RemoteWebDriver(new URL(serverUrl), DesiredCapabilities.chrome());
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
  }

  @After
  public void tearDown() {
    driver.quit();
  }
}
