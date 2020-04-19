/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cardex.util;

import com.cardex.model.CardModel;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Administrator
 */
@Service("commonOperations")
public class CommonOperations {

    StringBuilder stringBuilder = new StringBuilder();

    public Set<Map<String, String>> getXMLData(final String xmlInput, final String tag) {
System.out.println("am in get xml data >>> ");
        Document doc = null;
        DocumentBuilder documentbuilder;

        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xmlInput));
        Set<Map<String, String>> tagSet = new HashSet<Map<String, String>>();
        try {
            documentbuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = documentbuilder.parse(is);

            NodeList list = doc.getElementsByTagName(tag);
            for (int temp = 0; temp < list.getLength(); temp++) {
                Map<String, String> tagMap = new LinkedHashMap<String, String>();
                Node nNode = list.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    NodeList nodeList = eElement.getElementsByTagName("*");
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        tagMap.put(
                                nodeList.item(i).getNodeName(),
                                getTagValues(nodeList.item(i).getNodeName(),
                                        eElement));
                    }
                    tagSet.add(tagMap);
                }
            }
        } catch (SAXException ex) {
            ex.getStackTrace();
        } catch (IOException ex) {
            ex.getStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.getStackTrace();
        }
//        System.out.println("print out set data ==> " + tagSet.iterator().next().get("solId"));
        return tagSet;
    }

    public String getTagValues(final String tagname, final Element elt) {
        NodeList nlList = elt.getElementsByTagName(tagname);

        if (nlList != null && nlList.getLength() > 0) {
            NodeList subList = nlList.item(0).getChildNodes();
            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }

    public String readData(Set<Map<String, String>> data) {
        Map<String, String> dataMap = data.iterator().next();

        if (dataMap != null) {
            String code = dataMap.get("faultcode");
            String message = dataMap.get("faultstring");

            stringBuilder.append(code);
            stringBuilder.append("#");
            stringBuilder.append(message);
            stringBuilder.append("#");
        }
        return stringBuilder.toString();
    }

    public List<CardModel> readSuccess(Set<Map<String, String>> data) {
//        Map<String, String> dataMap = data.iterator().next();
        List<CardModel> cardList = new ArrayList<>();
        Iterator iterator = data.iterator();

        while (iterator.hasNext()) {
            Map<String, String> dataMap = (Map<String, String>) iterator.next();
            CardModel cardModel = new CardModel();
            
            if (dataMap != null) {
               cardModel.setSolId(dataMap.get("solId"));
                cardModel.setCardId(dataMap.get("cardId"));
                cardModel.setCardPan(dataMap.get("cardPan"));
                cardModel.setAccountName(dataMap.get("accountName"));
                cardModel.setRequestDate(dataMap.get("requestDate"));
                cardModel.setPrintedBy(dataMap.get("printedBy"));
                cardModel.setPrintedDate(dataMap.get("printedDate"));
                cardModel.setStatus(dataMap.get("status"));
                
                cardList.add(cardModel);
                
//                stringBuilder.append(solId);
//                stringBuilder.append("#");
//                stringBuilder.append(cardPan);
//                stringBuilder.append("#");
//
//                stringBuilder.append(accountName);
//                stringBuilder.append("#");
//                stringBuilder.append(requestDate);
//                stringBuilder.append("#");
//
//                stringBuilder.append(printedBy);
//                stringBuilder.append("#");
//                stringBuilder.append(printedDate);
//   
//                stringBuilder.append("#");
//                stringBuilder.append(status);
//                stringBuilder.append("#");
            }
        }
        return cardList;
    }
}
