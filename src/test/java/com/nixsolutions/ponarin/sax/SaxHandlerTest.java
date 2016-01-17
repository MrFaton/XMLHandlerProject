package com.nixsolutions.ponarin.sax;

import org.custommonkey.xmlunit.XMLUnit;
import static org.custommonkey.xmlunit.XMLAssert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class SaxHandlerTest {
    private SaxHandler saxHandler;

    @Before
    public void setUp() {
        saxHandler = new SaxHandler();
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testRemovEevenElements() throws Exception {
        File sourceFile = new File("src/test/resources/source.xml");
        File destFile = new File("src/test/resources/resultSax.xml");
        File expectedFile = new File("src/test/resources/expected.xml");

        saxHandler.removeEvenElements(sourceFile, destFile);

        Reader expectedReader = new FileReader(expectedFile);
        Reader destReader = new FileReader(destFile);

        assertXMLEqual(expectedReader, destReader);
    }

    @Test(expected = Exception.class)
    public void testRemovEevenElementsWithNonExistsFile() throws Exception {
        File sourceFile = new File("src/test/resources/nonExists.xml");
        File destFile = new File("src/test/resources/resultDom.xml");
        saxHandler.removeEvenElements(sourceFile, destFile);
        Assert.fail("must be thrown Exception");
    }

    @Test(expected = Exception.class)
    public void testRemovEevenElementsWithInvalidFile() throws Exception {
        File sourceFile = new File("src/test/resources/sourceInvalid.xml");
        File destFile = new File("src/test/resources/resultDom.xml");
        saxHandler.removeEvenElements(sourceFile, destFile);
        Assert.fail("must be thrown Exception");
    }
}
