package com.web.automation.fileutils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLParser {

    static DocumentBuilderFactory docBuilderFactory = null;
    static DocumentBuilder docBuilder = null;
    static Document doc = null;
    int cellIndex = 0;
    int rowIndex = 0;
    String transType = null;

    public String getString(String filePath, String xmlKey) throws Throwable {


        try {
            docBuilderFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(new File(filePath));

            // normalize text representation
            doc.getDocumentElement().normalize();
            NodeList listOfRows = doc.getElementsByTagName("Row");
            int totalRows = listOfRows.getLength();
            System.out.println("Total no of Rows : " + totalRows);

            for (int s = 0; s < totalRows; s++) {
                Node firstRowNode = listOfRows.item(s);
                if (firstRowNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element firstoRowElement = (Element) firstRowNode;

                    //-------
                    NodeList firstNameList = firstoRowElement.getElementsByTagName("Cell");
                    for (int t = 0; t < firstNameList.getLength(); t++) {
                        Node secondnode = firstNameList.item(t);
                        if (secondnode.getNodeType() == Node.ELEMENT_NODE) {

                            Element secondElement = (Element) secondnode;

                            String cellValue = secondElement.getElementsByTagName("Data").item(0).getTextContent();
                            System.out.println("First Name : " + secondElement.getElementsByTagName("Data").item(0).getTextContent());
                            if (cellValue.equalsIgnoreCase(xmlKey)) {
                                cellIndex = t;
                                rowIndex = s;
                                break;
                            }
                        }
                        //------
                    }

                }//end of if clause
                break;

            }//end of for loop with s var
            Node resultNode = listOfRows.item(rowIndex + 1);
            if (resultNode.getNodeType() == Node.ELEMENT_NODE) {


                Element firstoRowElement = (Element) resultNode;

                //-------
                NodeList firstNameList = firstoRowElement.getElementsByTagName("Cell");

                Node secondnode = firstNameList.item(cellIndex);
                if (secondnode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) secondnode;
                    transType = eElement.getElementsByTagName("Data").item(0).getTextContent();
                }
            }

        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line " + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        return transType;

    }
}