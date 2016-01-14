package com.nixsolutions.ponarin.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;

public class XmlUtils {
    public void save(Document doc, String name) throws FileNotFoundException {
        DOMImplementation impl = doc.getImplementation();
        DOMImplementationLS implLs = (DOMImplementationLS) impl.getFeature("LS",
                "3.0");
        LSSerializer ser = implLs.createLSSerializer();
        ser.getDomConfig().setParameter("format-pretty-print", true);
        LSOutput out = implLs.createLSOutput();
        out.setEncoding("UTF-8");
        out.setByteStream(new FileOutputStream(name));
        ser.write(doc, out);
    }

    public void removeNode(Node node) {
        node.getParentNode().removeChild(node);
    }
}
