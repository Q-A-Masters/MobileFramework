package com.seetest.driverInstance;

import com.experitest.appium.SeeTestAndroidDriver;
import com.experitest.appium.SeeTestCapabilityType;
import com.experitest.appium.SeeTestIOSDriver;
import com.seetest.propertyfilereader.PropertyFileReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class SeetestDriverInstance {

    protected static AppiumDriver<WebElement> driver = null;
    private String host;
    private int port;
    private String deviceName;
    private String platform;
    private int appTimeOut;
    private String reportDirectory = "reports";
    private String reportFormat = "xml";

    public SeetestDriverInstance(String Application) {

        try {

            init();
            String remoteUrl = "http://" + getHost() + ":" + getPort() + "/wd/hub";
            if ("Android".equalsIgnoreCase(getPlatform())) {
                driver = (AppiumDriver<WebElement>) new SeeTestAndroidDriver<WebElement>(new URL(remoteUrl), this.generateAndroidCapabilities(Application));
            } else if ("iOS".equalsIgnoreCase(getPlatform())) {
                driver = (AppiumDriver<WebElement>) new SeeTestIOSDriver<WebElement>(new URL(remoteUrl), this.generateiOSCapabilities(Application));
            } else {
                throw new Exception("Given platform is not implemented.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static AppiumDriver<WebElement> getDriver() {
        return driver;
    }

    public void setDriver(AppiumDriver<WebElement> driver) {
        SeetestDriverInstance.driver = driver;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatfrom(String Platform) {
        this.platform = Platform;
    }

    public int getappTimeOut() {
        return appTimeOut;
    }

    public void setappTimeOut(int timeOut) {
        this.appTimeOut = timeOut;
    }

    public void tearDown() {
        driver.quit();
    }

    private void init() {
        PropertyFileReader handler = new PropertyFileReader("properties/Execution.properties");
        setHost(handler.getproperty("HOST_IP"));
        setPort(Integer.parseInt(handler.getproperty("HOST_PORT")));
        setDeviceName(handler.getproperty("DEVICE_NAME"));
        setPlatfrom(handler.getproperty("PLATFORM_NAME"));
        setappTimeOut(Integer.parseInt(handler.getproperty("APP_TIMEOUT")));
    }

    /**
     * @param application
     * @return Android capabilities
     */
    protected DesiredCapabilities generateAndroidCapabilities(String application) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(SeeTestCapabilityType.REPORT_DIRECTORY, reportDirectory);
        capabilities.setCapability(SeeTestCapabilityType.REPORT_FORMAT, reportFormat);

        if (application.equalsIgnoreCase("Eribank")) {
            capabilities.setCapability(MobileCapabilityType.UDID, getDeviceName());

            //	capabilities.setCapability("app", System.getProperty("user.dir") + "/apps/android/EriBank.apk");
            capabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\IN01513\\AppData\\Roaming\\appiumstudio\\apk\\com.experitest.ExperiBank.LoginActivity.2.apk");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_WAIT_ACTIVITY, "com.experitest.ExperiBank.LoginActivity");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
            capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");

        } else {
            //To implement the Logger
        }
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, getappTimeOut());
        return capabilities;
    }

    /**
     * @param application
     * @return iOS capabilities
     */
    protected DesiredCapabilities generateiOSCapabilities(String application) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);
        capabilities.setCapability(SeeTestCapabilityType.INSTRUMENT_APP, true);
        if (application.equalsIgnoreCase("Eribank")) {
            //capabilities.setCapability("app", System.getProperty("user.dir") + "/apps/ios/EriBank.ipa");
            capabilities.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        } else {
            //To implement the Logger
        }
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, getappTimeOut());
        return capabilities;
    }

}
