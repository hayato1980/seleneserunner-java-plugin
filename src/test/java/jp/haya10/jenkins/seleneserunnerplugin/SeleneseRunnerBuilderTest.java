package jp.haya10.jenkins.seleneserunnerplugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hudson.FilePath;
import hudson.model.Result;
import hudson.model.FreeStyleProject;

import java.io.File;

import jp.vmi.selenium.webdriver.DriverOptions;
import jp.vmi.selenium.webdriver.WebDriverManager;

import org.junit.Assume;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.jvnet.hudson.test.HudsonTestCase;
import org.openqa.selenium.WebDriverException;

public class SeleneseRunnerBuilderTest extends HudsonTestCase {

    private static boolean noDisplay = false;

    @Test
    public void testRunSelenese() throws Exception {
        FreeStyleProject p = createFreeStyleProject();
        String file = TestUtils.getScriptFile(this.getClass(), "Simple");
        p.getBuildersList().add(
            new SeleneseRunnerBuilder(file, WebDriverManager.FIREFOX, true, ""));

        assertThat(new File(file).exists(), is(true));
        try {
            assertBuildStatus(Result.SUCCESS, p.scheduleBuild2(0).get());
        } finally {
            for (String log : p.getLastBuild().getLog(100)) {
                System.out.println(log);
            }
        }

        FilePath screenshot = p.getSomeWorkspace().child("screenshots");
        assertThat(screenshot.list().isEmpty(), is(false));
    }

    /**
     * Check Firefox connected.
     */
    @Override
    protected void setUp() throws Exception {
        if (noDisplay)
            throw new AssumptionViolatedException("no display specified");

        setupWebDriverManager();
        try {
            WebDriverManager.getInstance().get();
        } catch (WebDriverException e) {
            if (e.getMessage().contains("no display specified")) {
                noDisplay = true;
                Assume.assumeNoException(e);
            }
        }
    }

    private void setupWebDriverManager() {
        WebDriverManager manager = WebDriverManager.getInstance();
        manager.setWebDriverFactory(WebDriverManager.FIREFOX);
        manager.setDriverOptions(new DriverOptions());
    }
}
