package Seetest;

import com.seetest.driverInstance.SeetestCommandBase;
import com.seetest.iospages.SampleiOSPage;
import com.web.automation.fileutils.ExcelReader;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class SampleiOSTest {

    protected String sheetPath = System.getProperty("user.dir").replace("\\", "/") + "/testdata/TestData.xlsx";
    //protected String sheetName = "CustomerInfo";
    //protected String orderInfo = "OrderInfo";
    Map<String, List<String>> testdata = null;
    List<Map<String, String>> testdata1 = null;
    private com.seetest.driverInstance.SeetestCommandBase caller;

    @BeforeSuite
    public void beforeMethod() throws IOException {

        //testdata=ExcelReader.getStoredDataFromSelectedRows(this.sheetPath,this.sheetName,1,2);
        testdata = ExcelReader.getStoredDataFromSelectedRows(this.sheetPath, "Mobiledata", 1, 2);


    }


    @BeforeSuite
    public void init() {
        caller = new SeetestCommandBase("EriBank");
    }

    @Test
    public void test1() throws IOException {

        caller.verifyElementPresent(SampleiOSPage.usernameTextField, "username Text Field");
        caller.type(SampleiOSPage.usernameTextField, testdata.get("Username").get(0), "Username");
        caller.verifyElementPresent(SampleiOSPage.passwordTextField, "password Text Field");
        caller.type(SampleiOSPage.passwordTextField, testdata.get("Password").get(0), "Password");
        caller.verifyElementPresent(SampleiOSPage.login, "login");
        caller.click(SampleiOSPage.login, "Login");

    }

}
