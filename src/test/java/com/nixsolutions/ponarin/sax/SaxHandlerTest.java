package com.nixsolutions.ponarin.sax;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class SaxHandlerTest {
    private SaxHandler saxHandler;

    @Before
    public void setUp() {
        saxHandler = new SaxHandler();
    }

    @Test
    public void tt() throws Exception {
        File sourceFile = new File("src/test/resources/source.xml");
        File destFile = new File("src/test/resources/resultSax.xml");
        saxHandler.removeEvenElements(sourceFile, destFile);
    }

    @Test(expected = Exception.class)
    public void t2() throws Exception {
        File sourceFile = new File("src/test/resources/sourceInvalid.xml");
        File destFile = new File("src/test/resources/resultSax.xml");
        saxHandler.removeEvenElements(sourceFile, destFile);
    }
}
