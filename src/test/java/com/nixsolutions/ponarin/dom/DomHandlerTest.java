package com.nixsolutions.ponarin.dom;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

import static org.custommonkey.xmlunit.XMLAssert.*;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class DomHandlerTest {
    private DomHandler domHandler;

    @Before
    public void setUp() {
        domHandler = new DomHandler();
    }
    
    @Test
    public void te() {
        File file = new File("resultDom.xml");
        System.out.println(file.exists());
        
        File file2 = new File("src/test/resources/source.xml");
        System.out.println(file2.exists());
    }

    @Test
    public void testRemovEevenElements() throws Exception {
        domHandler.removeEevenElements("source.xml", "resultDom.xml");

        Document source = getDocument("source.xml");
        Document result = getDocument("resultDom.xml");

        assertXMLEqual(source, result);

        // domHandler.removeEevenElements("sourceWrong.xml", "resultDom.xml");
    }

    private Document getDocument(String name) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(getClass().getClassLoader().getResourceAsStream(name));
    }
}
