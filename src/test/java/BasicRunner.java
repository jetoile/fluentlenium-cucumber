import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author : Mathilde Lemee
 */
@RunWith(Cucumber.class)
@Cucumber.Options(features = "classpath:webapp", format = {"pretty", "html:target/cucumber", "json:target/cucumber.json"})
public class BasicRunner {


}
