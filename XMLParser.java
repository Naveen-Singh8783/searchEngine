import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class XMLParser {

    // Parse the documents from cran.all.1400.xml
    public static List<Document> parseDocuments(String filePath) {
        List<Document> documents = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = dBuilder.parse(file); // Use fully qualified name
            xmlDoc.getDocumentElement().normalize();

            NodeList nodeList = xmlDoc.getElementsByTagName("doc");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("docno").item(0).getTextContent());
                    String title = element.getElementsByTagName("title").item(0).getTextContent();
                    String author = element.getElementsByTagName("author").item(0).getTextContent();
                    //String bib = element.getElementsByTagName("bib").item(0).getTextContent();
                    String body = element.getElementsByTagName("text").item(0).getTextContent();

                    documents.add(new Document(id, title, author, body));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }

    // Parse the queries from cran.qry.xml
    public static List<Query> parseQueries(String filePath) {
        List<Query> queries = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document xmlDoc = dBuilder.parse(file); // Use fully qualified name
            xmlDoc.getDocumentElement().normalize();

            NodeList nodeList = xmlDoc.getElementsByTagName("top");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int id = Integer.parseInt(element.getElementsByTagName("num").item(0).getTextContent().trim());
                    String text = element.getElementsByTagName("title").item(0).getTextContent();
                    queries.add(new Query(id, text));
                }
            }
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
        return queries;
    }
}