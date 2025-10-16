package config;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SetUp {
    public Properties prop;
    @BeforeTest
   public void setup() throws IOException {
       prop = new Properties();
       FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config.properties");
       prop.load(fileInputStream);
   }
   @AfterMethod
    public void reload() throws IOException {
        prop = new Properties();
        FileInputStream fileInputStream = new FileInputStream("./src/test/resources/config.properties");
        prop.load(fileInputStream);
    }
}
