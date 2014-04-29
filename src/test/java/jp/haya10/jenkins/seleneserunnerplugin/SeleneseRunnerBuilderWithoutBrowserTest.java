package jp.haya10.jenkins.seleneserunnerplugin;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import hudson.util.FormValidation;

import javax.naming.directory.InvalidAttributesException;

import org.apache.commons.lang3.tuple.Pair;
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

    @Test
    public void testGetSize() throws InvalidAttributesException {
        SeleneseRunnerBuilder b = new SeleneseRunnerBuilder(null, "", true, true, "", "", "", "1024x768", "");
        assertThat(b.getWidth(), is(1024));
        assertThat(b.getHeight(), is(768));
    }

    @Test
    public void testParseSise() throws InvalidAttributesException {
        Pair<Integer, Integer> size = null;
        size = SeleneseRunnerBuilder.parseSize("1024x768");
        assertThat(size.getLeft(), is(1024));
        assertThat(size.getRight(), is(768));

        size = SeleneseRunnerBuilder.parseSize("1x1");
        assertThat(size.getLeft(), is(1));
        assertThat(size.getRight(), is(1));

    }

    @Test(expected = InvalidAttributesException.class)
    public void testParseSiseError1() throws InvalidAttributesException {
        Pair<Integer, Integer> size = null;
        size = SeleneseRunnerBuilder.parseSize("1024x");
    }

    @Test(expected = InvalidAttributesException.class)
    public void testParseSiseError2() throws InvalidAttributesException {
        Pair<Integer, Integer> size = null;
        size = SeleneseRunnerBuilder.parseSize("x768");
    }

    @Test(expected = InvalidAttributesException.class)
    public void testParseSiseError3() throws InvalidAttributesException {
        Pair<Integer, Integer> size = null;
        size = SeleneseRunnerBuilder.parseSize("x");
    }

    @Test(expected = InvalidAttributesException.class)
    public void testParseSiseErrorAlphabet() throws InvalidAttributesException {
        Pair<Integer, Integer> size = null;
        size = SeleneseRunnerBuilder.parseSize("axa");
    }

    @Test
    public void testSizeFormat() {
        SeleneseRunnerBuilder.DescriptorImpl d = new SeleneseRunnerBuilder.DescriptorImpl();
        assertThat(d.doCheckSize("1024x768").kind, is(FormValidation.Kind.OK));
        assertThat(d.doCheckSize("1024x").kind, is(FormValidation.Kind.ERROR));
        assertThat(d.doCheckSize("x768").kind, is(FormValidation.Kind.ERROR));
        assertThat(d.doCheckSize("x").kind, is(FormValidation.Kind.ERROR));
        assertThat(d.doCheckSize("axa").kind, is(FormValidation.Kind.ERROR));
        assertThat(d.doCheckSize("").kind, is(FormValidation.Kind.ERROR));
    }

    
}
