package com.nixsolutions.ponarin.sax;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class EventElementHandler extends DefaultHandler {
    private File destFile;
    private Writer writer;
    private String spaces = "    ";
    private String lineDelimeter = System.lineSeparator();
    private Map<Integer, Integer> elementsPerLevel = new HashMap<>();
    private String prevTagName;
    private int level = -1;

    @Override
    public void startDocument() throws SAXException {
        try {
            writer = new BufferedWriter(new FileWriter(destFile));
        } catch (IOException e) {
            throw new RuntimeException();
        }
        saveText("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    }

    @Override
    public void endDocument() throws SAXException {
        if (writer != null) {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {
        level++;
        prevTagName = localName + qName;
        if (elementsPerLevel.get(level) == null) {
            elementsPerLevel.put(level, 0);
        }

        elementsPerLevel.put(level + 1, 0);

        elementsPerLevel.put(level, elementsPerLevel.get(level) + 1);

        if (isEven(level)) {
            saveText(lineDelimeter);
            saveOpenTag(localName, qName, attributes);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (isEven(level)) {
            if (!qName.equals(prevTagName)) {
                saveText(lineDelimeter);

                for (int i = 0; i < level; i++)
                    saveText(spaces);
            }
            saveText("</" + localName + qName + ">");

        }
        prevTagName = null;
        level--;
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        String text = new String(ch, start, length);
        if (isEven(level) && text.trim().length() > 0) {
            saveText(text.trim());
        }
    }

    public void setDestFile(File destFile) {
        this.destFile = destFile;
    }

    private boolean isEven(int level) {
        for (int i = level; i > 0; i--) {
            if (elementsPerLevel.get(i) % 2 == 0) {
                return false;
            }
        }
        return true;
    }

    private void saveText(String text) {
        try {
            writer.write(text);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private void saveOpenTag(String localName, String qName,
            Attributes attributes) {
        for (int i = 0; i < level; i++) {
            saveText(spaces);
        }

        String tagName = localName + qName;
        saveText("<" + tagName);
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String atrName = attributes.getLocalName(i)
                        + attributes.getQName(i);
                saveText(" " + atrName + "=\"" + attributes.getValue(i) + "\"");
            }
        }
        saveText(">");
    }
}
