package com.lolrewqsda.zahlenraten.backend;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class XMLLoader {
    Document document;
    DocumentBuilder builder;
    DocumentBuilderFactory factory;
    File xmlFile;
    public XMLLoader(File xmlFile) throws ParserConfigurationException, IOException, SAXException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        this.xmlFile = xmlFile;
    }
    public ArrayList<XMLEntry> loadXML() throws IOException, SAXException {
        document = builder.parse(xmlFile);
        document.getDocumentElement().normalize();
        ArrayList<XMLEntry> entryArrayList = new ArrayList<>();
        NodeList nodeList = document.getElementsByTagName("entry");
        if (nodeList.getLength() != 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                long score = Long.parseLong(element.getElementsByTagName("score").item(0).getTextContent());
                long round = Long.parseLong(element.getElementsByTagName("round").item(0).getTextContent());
                System.out.println("score:" + score);
                entryArrayList.add(new XMLEntry(round, score));
            }
        }
        return entryArrayList;
    }

    public XMLEntry getLastRound(File file) throws Exception {
        XMLLoader xmlLoader = new XMLLoader(file);
        ArrayList<XMLEntry> loadedXML = xmlLoader.loadXML();
        return loadedXML.getLast();
    }
}
