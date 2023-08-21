package base;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.Constants;

public class BaseTest 
{
	public static WebDriver driver;
	public ExtentSparkReporter sparkreporter;
	public ExtentReports extent;
	public ExtentTest logger;
	@BeforeTest
		public void beforeTestMethod()
		{

			sparkreporter=new ExtentSparkReporter(System.getProperty("user.dir")+File.separator+"reports"+File.separator+"reports.html");
			extent=new ExtentReports();
			extent.attachReporter(sparkreporter);
			sparkreporter.config().setTheme(Theme.DARK);
			//extent.setSystemInfo("HostName", null);
			//extent.setSystemInfo("userName", null);
			sparkreporter.config().setDocumentTitle("Automation Report");
			sparkreporter.config().setReportName("SauceDemo Automation Report");
		}

		@BeforeMethod
		
		@Parameters("browser")
		public void setUp(String browser,Method testMethod)
		{
			logger=extent.createTest(testMethod.getName());
			setupDriver(browser); 
			driver.get("https://www.saucedemo.com/");
			driver.manage().window().maximize();
			
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
         }
		public static void setupDriver(String browser)
		{
			if(browser.equalsIgnoreCase("chrome"))
			{
				WebDriverManager.chromedriver().setup();
				driver=new ChromeDriver();
			}
			if(browser.equalsIgnoreCase("firefox"))
			{
				WebDriverManager.chromedriver().setup();
				driver=new FirefoxDriver();
			}
			if(browser.equalsIgnoreCase("edge"))
			{
				WebDriverManager.chromedriver().setup();
				driver=new EdgeDriver();
			}
		}

		
		@AfterMethod
		public void afterMethod(ITestResult result)
		{
			if(result.getStatus()==ITestResult.FAILURE)
			{
				logger.log(Status.FAIL,MarkupHelper.createLabel(result.getName()+" - Test Case Failed",ExtentColor.RED));
				logger.log(Status.FAIL,MarkupHelper.createLabel(result.getThrowable()+" - Test Case Failed",ExtentColor.RED));
			}
			else if(result.getStatus()==ITestResult.SKIP)
			{
				logger.log(Status.SKIP,MarkupHelper.createLabel(result.getName()+" - Test Case Skipped",ExtentColor.ORANGE));
			}
			else if(result.getStatus()==ITestResult.SUCCESS)
			{
				logger.log(Status.PASS,MarkupHelper.createLabel(result.getName()+" - Test Case PASS",ExtentColor.GREEN));
			}

			driver.quit();
		
	}

		@AfterTest
		public void afterTest()
		{
			extent.flush();

		}
}












