package jp.haya10.jenkins.seleneserunnerplugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hudson.FilePath;
import hudson.model.Result;
import hudson.model.FreeStyleProject;

import java.io.File;

import jp.haya10.jenkins.seleneserunnerplugin.SeleneseRunnerBuilder;
import jp.vmi.selenium.webdriver.WebDriverManager;

import org.junit.Test;
import org.jvnet.hudson.test.HudsonTestCase;

public class SeleneseRunnerBuilderTest extends HudsonTestCase {
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
}
