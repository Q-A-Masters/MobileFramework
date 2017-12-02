package Seetest;

import com.seetest.androidpages.SampleAndroidPage;
import com.seetest.driverInstance.SeetestCommandBase;
import com.web.automation.fileutils.ExcelReader;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SampleAndroidTest {

    protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
    //protected String sheetName = "CustomerInfo";
//protected String orderInfo = "OrderInfo";
    Map<String, List<String>> testdata = null;
    List<Map<String, String>> testdata1 = null;
    private com.seetest.driverInstance.SeetestCommandBase caller;
    private String department, category, itemName, itemSize, itemPrice;

    @BeforeSuite
    public void beforeMethod() throws IOException {

        //testdata=ExcelReader.getStoredDataFromSelectedRows(this.sheetPath,this.sheetName,1,2);
        testdata = ExcelReader.getStoredDataFromSelectedRows(this.sheetPath, "Mobiledata", 1, 2);

        String username = testdata.get("USERNAME").get(0);
        String password = testdata.get("USERNAME").get(0);
    }


    public void init() {
        caller = new SeetestCommandBase("EriBank");
    }

    @Test
    public void test1() throws IOException {

        caller.verifyElementPresent(SampleAndroidPage.usernameTextField, "username Text Field");
        caller.type(SampleAndroidPage.usernameTextField, testdata.get("Username").get(0), "Username");
        caller.verifyElementPresent(SampleAndroidPage.passwordTextField, "password Text Field");
        caller.type(SampleAndroidPage.passwordTextField, testdata.get("Password").get(0), "Password");
        caller.verifyElementPresent(SampleAndroidPage.login, "login");
        caller.click(SampleAndroidPage.login, "Login");

    }

    @Test
    public void test2() throws IOException {
        caller.verifyElementPresent(SampleAndroidPage.test, "test");

    }
}
