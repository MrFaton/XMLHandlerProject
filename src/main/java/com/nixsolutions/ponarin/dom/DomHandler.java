package com.nixsolutions.ponarin.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class DomHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(DomHandler.class);

    public void removeEvenElements(File sourse, File dest) throws Exception {
        logger.trace(
                "remove even elements for file with name: " + sourse.getName());
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(sourse);

            Element element = doc.getDocumentElement();

            removeEven(element);

            save(doc, dest);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    private void removeEven(Node node) {
        List<Node> nodeList = getChildren(node);
        for (int i = 0; i < nodeList.size(); i++) {
            Node child = nodeList.get(i);
            if ((i + 1) % 2 == 0) {
                child.getParentNode().removeChild(child);
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

    private void save(Document doc, File dest) throws FileNotFoundException {
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLs = (DOMImplementationLS) impl.getFeature("LS",
                "3.0");
        LSSerializer ser = implLs.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);
        LSOutput out = implLs.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(new FileOutputStream(dest));
        ser.write(doc, out);
    }
}
