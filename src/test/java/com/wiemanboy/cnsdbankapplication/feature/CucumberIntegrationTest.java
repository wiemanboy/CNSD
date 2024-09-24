package com.wiemanboy.cnsdbankapplication.feature;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        glue = {"com/wiemanboy/cnsdbankapplication/feature"}
)
public class CucumberIntegrationTest {

}
