package jp.haya10;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hudson.model.Result;
import hudson.model.FreeStyleProject;

import java.io.File;

import jp.vmi.selenium.webdriver.WebDriverManager;

import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

public class SeleneseRunnerBuilderTest extends HudsonTestCase {
    @Test
    public void testRunSelenese() throws Exception {
        FreeStyleProject p = createFreeStyleProject();
        String file = TestUtils.getScriptFile(this.getClass(), "Simple");
        p.getBuildersList().add(
            new SeleneseRunnerBuilder(file, WebDriverManager.FIREFOX));

        assertThat(new File(file).exists(), is(true));
        try {
            assertBuildStatus(Result.SUCCESS, p.scheduleBuild2(0).get());
        } finally {
            for (String log : p.getLastBuild().getLog(100)) {
                System.out.println(log);
            }
        }
    }
}
