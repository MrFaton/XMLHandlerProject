package com.nixsolutions.ponarin.sax;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SaxHandler {
    public void removeEvenElements(File source, File dest) throws Exception {
        EventElementHandler elementHandler = new EventElementHandler();
        elementHandler.setDestFile(dest);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        InputStream in = new BufferedInputStream(new FileInputStream(source));
        saxParser.parse(in, elementHandler);
    }
}
