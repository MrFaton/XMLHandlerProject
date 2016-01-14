package com.nixsolutions.ponarin.dom;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.nixsolutions.ponarin.utils.XmlUtils;

public class DomHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(DomHandler.class);
    private XmlUtils xmlUtils = new XmlUtils();

    public void removeEevenElements(String sourceFileName,
            String destFileName) {
        logger.trace(
                "remove even elements for file with name: " + sourceFileName);
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(getClass().getClassLoader()
                    .getResourceAsStream(sourceFileName));

            Element element = doc.getDocumentElement();

            removeEven(element);

            xmlUtils.save(doc, destFileName);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw new RuntimeException();
        }
    }

    private void removeEven(Node node) {
        List<Node> nodeList = getChildren(node);
        for (int i = 0; i < nodeList.size(); i++) {
            Node child = nodeList.get(i);
            if ((i + 1) % 2 == 0) {
                xmlUtils.removeNode(child);
            } else {
                removeEven(child);
            }
        }
    }

    private List<Node> getChildren(Node node) {
        NodeList nodeList = node.getChildNodes();
        List<Node> children = new ArrayList<Node>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node child = nodeList.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                children.add(child);
            }
        }

        return children;
    }
}
