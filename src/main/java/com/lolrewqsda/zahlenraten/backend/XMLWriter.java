package com.lolrewqsda.zahlenraten.backend;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class XMLWriter {
    public static void writeXML(File xmlFile, ArrayList<XMLEntry> entries) {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("scoreboard");
            document.appendChild(root);

            for (XMLEntry entry : entries){
                Element elementEntry = document.createElement("entry");
                Element score = document.createElement("score");
                Element round = document.createElement("round");
                Element date = document.createElement("date");
                score.appendChild(document.createTextNode(String.valueOf(entry.tries)));
                round.appendChild(document.createTextNode(String.valueOf(entry.round)));
                date.appendChild(document.createTextNode(entry.date.toString()));
                elementEntry.appendChild(score);
                elementEntry.appendChild(round);
                elementEntry.appendChild(date);
                root.appendChild(elementEntry);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource(document);

            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            System.out.println("XML file created successfully: " + xmlFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}