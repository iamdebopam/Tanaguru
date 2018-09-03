package org.tanaguru.selenese;

import jp.vmi.selenium.selenese.Parser;
import jp.vmi.selenium.selenese.Runner;
import jp.vmi.selenium.selenese.Selenese;
import jp.vmi.selenium.selenese.TestProject;
import jp.vmi.selenium.selenese.command.CommandFactory;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.tanaguru.crawler.util.CrawlUtils;
import org.tanaguru.entity.subject.WebResource;
import org.tanaguru.scenarioloader.AbstractScenarioLoader;
import org.tanaguru.selenese.tools.ProfileFactoryImpl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class SeleneseLoaderImpl extends AbstractScenarioLoader implements WebDriverEventListener {

    private static final Logger LOGGER = Logger.getLogger(SeleneseLoaderImpl.class);
    private ProfileFactoryImpl profileFactory;

    public SeleneseLoaderImpl(
            WebResource webResource,
            String scenario,
            ProfileFactoryImpl profileFactory) {
        super(webResource, scenario);
        this.profileFactory = profileFactory;
    }

    @Override
    public void run() {
        LOGGER.debug("Launch Scenario "   + super.scenario);

        try {
            //Initialize webdriver
            ClassLoader classLoader = getClass().getClassLoader();
            File geckodriver = new File(classLoader.getResource("geckodriver").toURI());
            geckodriver.setExecutable(true);
            System.setProperty("webdriver.gecko.driver",
                    geckodriver.getPath());
            FirefoxBinary ffBinary = new FirefoxBinary();

            FirefoxOptions ffOptions = new FirefoxOptions();
            ffOptions.setBinary(ffBinary);
            ffOptions.setAcceptInsecureCerts(true);
            ffOptions.setHeadless(true);
            ffOptions.setProfile(profileFactory.getOnlineProfile());

            RemoteWebDriver ffDriver = new FirefoxDriver(ffOptions);
            EventFiringWebDriver eventDriver = new EventFiringWebDriver(ffDriver);
            eventDriver.register(this);

            //Initialize runner
            Runner runner = new Runner();
            runner.setDriver(eventDriver);

            //Get scenario
            Selenese script = null;
            InputStream input = new ByteArrayInputStream(scenario.getBytes());
            CommandFactory commandFactory = new CommandFactory(runner);
            script =  Parser.parse("scenario.side", input, commandFactory);
            input.close();

            //Run scenario
            TestProject project = (TestProject)script;
            for(Selenese test : project.getSeleneseList()){
                runner.execute(test);
            }
        } catch (URISyntaxException e) {
            LOGGER.error(e.getMessage());
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {

    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        try {
            String getDoctypeStr = IOUtils.toString(SeleneseLoaderImpl.class.getClassLoader()
                        .getResourceAsStream("getDoctype.js"));
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String jsCommand = getDoctypeStr + "return getDoctype();";
            String doctype = (String) js.executeScript(jsCommand);
            String source = doctype + driver.getPageSource();
            String charset = CrawlUtils.extractCharset(IOUtils.toInputStream(source));

            super.fireNewSSP(driver.getCurrentUrl(), source, null, jsScriptMap, charset);
       } catch (IOException e) {
           e.printStackTrace();
       }
    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }


    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {

    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

    }
}
