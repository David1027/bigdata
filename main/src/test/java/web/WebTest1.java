package web;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class WebTest1 extends WebBase {
  @Test
  public void Untitled() {
    driver.get("https://www.baidu.com/");
    driver.manage().window().setSize(new Dimension(1920, 978));
    driver.findElement(By.id("kw")).click();
    driver.close();
  }
}
