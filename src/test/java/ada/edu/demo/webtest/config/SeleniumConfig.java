package ada.edu.demo.webtest.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
// import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Bean
    public WebDriver webDriver() {
        
        // Configure Chrome to run in headless mode
        // FirefoxOptions options = new FirefoxOptions();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
 
        // WebDriverManager.firefoxdriver().setup();
        WebDriverManager.chromedriver().setup();

        // return new FirefoxDriver(options);
        return new ChromeDriver(options);
    }
}
