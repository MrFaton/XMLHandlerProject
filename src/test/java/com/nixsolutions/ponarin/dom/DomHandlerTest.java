package com.nixsolutions.ponarin.dom;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;

import static org.custommonkey.xmlunit.XMLAssert.*;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class DomHandlerTest {
    private DomHandler domHandler;

    @Before
    public void setUp() {
        domHandler = new DomHandler();
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testRemovEevenElements() throws Exception {
        File sourceFile = new File("src/test/resources/source.xml");
        File destFile = new File("src/test/resources/resultDom.xml");
        File expectedFile = new File("src/test/resources/expectedDom.xml");

        domHandler.removeEevenElements(sourceFile, destFile);

        Reader expectedReader = new FileReader(expectedFile);
        Reader destReader = new FileReader(destFile);

        assertXMLEqual(expectedReader, destReader);
    }
}
