package ada.edu.demo.webtest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebInterfaceTests {

	@Autowired
	private WebDriver webDriver;

	@LocalServerPort
	private int port;

	@Test
	@Order(1)
	@DisplayName("Create a user")
	public void CreateUser() {
		webDriver.get("http://localhost:"+port+"/student/new");

		WebElement studentIdInput = webDriver.findElement(By.id("studentId"));
		WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
		WebElement lastNameInput = webDriver.findElement(By.id("lastName"));
		WebElement emailInput = webDriver.findElement(By.id("email"));

		// Check if such a field exists
		assertNotNull(firstNameInput);

		try {
			studentIdInput.sendKeys("1");
			Thread.sleep(2000);
			firstNameInput.sendKeys("Nigar");
			Thread.sleep(2000);
			lastNameInput.sendKeys("Salayeva");
			Thread.sleep(2000);
			emailInput.sendKeys("ns@ada.edu.az");
			Thread.sleep(2000);
		}
		catch (Exception ex) {
			System.out.println(ex);
		}

		// Find and submit the form (assuming there's a submit button with a specific attribute)
		WebElement submitButton = webDriver.findElement(By.id("submit"));
		submitButton.click();
	}

	@Test
	@Order(2)
	@DisplayName("Check the created user")
	public void CheckUser() {
		// Check if the student is added
		webDriver.get("http://localhost:"+port+"/student/list");
		List<WebElement> bodyElementFName = webDriver.findElements(By.xpath("//*[contains(text(), 'Nigar')]"));
		List<WebElement> bodyElementLName = webDriver.findElements(By.xpath("//*[contains(text(), 'Salayeva')]"));
		System.out.println("Element result"+bodyElementLName);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// Check if the text "Nigar" is present in the page content
		assert(bodyElementFName.size() == 1);
		assert(bodyElementLName.size() == 1);
	}
	

    @Test
    @Order(3)
    @DisplayName("Update user info")
    public void UpdateUser(){
        webDriver.get("http://localhost:" + port + "/student/update?id=1");
        WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
        WebElement lastNameInput = webDriver.findElement(By.id("lastName"));
        WebElement emailInput = webDriver.findElement(By.id("email"));

        // Check if such a field exists
        assertNotNull(firstNameInput);
        assertNotNull(lastNameInput);
        assertNotNull(emailInput);

        try {
            // clear the Nigar's name and write Madina
            firstNameInput.clear();
            firstNameInput.sendKeys("Madina");
            Thread.sleep(2000);
            
            // clear the Nigar's surname and write Rustamova
            lastNameInput.clear();
            lastNameInput.sendKeys("Rustamova");
            Thread.sleep(2000);

            // clear the Nigar's mail and write Madina's
            emailInput.clear();
            emailInput.sendKeys("mr@ada.edu.az");
            Thread.sleep(2000);

            // Choose WM1, SAD, ITPM courses from list
            webDriver.findElement(By.cssSelector("input[name='courses'][value='1']")).click();
            Thread.sleep(1000); //till this command take WM1 course
            webDriver.findElement(By.cssSelector("input[name='courses'][value='3']")).click();
            Thread.sleep(1000); //till this command take SAD course
            webDriver.findElement(By.cssSelector("input[name='courses'][value='4']")).click();
            Thread.sleep(1000); //till this command take ITPM course
            
            // Click submit button
            webDriver.findElement(By.id("submit")).click();
        } catch (Exception ex) {
            System.out.println(ex);
        }
	}

    @Test
    @Order(4)
    @DisplayName("Check if updated user saved")
    public void CheckUpdatedUser(){
        webDriver.get("http://localhost:" + port + "/student/list");

        // WebElement firstName = webDriver.findElement(By.xpath("//tr[td='1']/td[1]"));
        WebElement firstName = webDriver.findElement(By.xpath(".//td[contains(text(), 'Madina')]"));
        WebElement lastName = webDriver.findElement(By.xpath(".//td[contains(text(), 'Rustamova')]"));
        WebElement creditsCell = webDriver.findElement(By.xpath("//tr[td='1']/td[5]"));

        try {
            // Get the text content and convert it to an integer
            String creditsText = creditsCell.getText();
            int credits = Integer.parseInt(creditsText);

            // Check if searched (updated) Name is there
            assertNotNull(firstName);
            assertNotNull(lastName);

            // Check if credits are co
            assertNotNull(creditsCell);
            assertEquals(18, credits); // Check if total sum of the credits is equal to 18
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}




