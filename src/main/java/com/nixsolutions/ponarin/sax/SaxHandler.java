package com.nixsolutions.ponarin.sax;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaxHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(SaxHandler.class);

    public void removeEvenElements(File source, File dest) throws Exception {
        logger.trace(
                "remove even elements for file with name: " + source.getName());
        EventElementHandler elementHandler = new EventElementHandler();
        elementHandler.setDestFile(dest);
        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxFactory.newSAXParser();
            InputStream in = new BufferedInputStream(
                    new FileInputStream(source));
            saxParser.parse(in, elementHandler);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }
}
