package jp.haya10.jenkins.seleneserunnerplugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hudson.util.FormValidation;

import org.junit.Test;

public class SeleneseRunnerBuilderWithoutBrowserTest {
    @Test
    public void testBaseUrl() {
        SeleneseRunnerBuilder.DescriptorImpl d = new SeleneseRunnerBuilder.DescriptorImpl();
        assertThat(d.doCheckBaseUrl("http://www.example.com").kind, is(FormValidation.Kind.OK));
        assertThat(d.doCheckBaseUrl("https://www.example.com").kind, is(FormValidation.Kind.OK));
        assertThat(d.doCheckBaseUrl("http:////www.example.com").kind, is(FormValidation.Kind.ERROR));
        assertThat(d.doCheckBaseUrl("ftp://www.example.com").kind, is(FormValidation.Kind.ERROR));
        assertThat(d.doCheckBaseUrl("").kind, is(FormValidation.Kind.OK));
    }
}
