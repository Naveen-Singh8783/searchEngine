import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class ModifyNumFields {
    public static void main(String[] args) {
        try {
            File inputFile = new File("/C:/Users/91951/Desktop/searchEngine/Dataset/cran.qry.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true); // Ensure namespace awareness
            DocumentBuilder builder = factory.newDocumentBuilder();
            org.w3c.dom.Document document = builder.parse(inputFile);
            document.getDocumentElement().normalize();

            NodeList topList = document.getElementsByTagName("top");

            int newNum = 1;
            for (int i = 0; i < topList.getLength(); i++) {
                Node topNode = topList.item(i);
                if (topNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element topElement = (Element) topNode;
                    NodeList numList = topElement.getElementsByTagName("num");
                    if (numList.getLength() > 0) {
                        Node numNode = numList.item(0);
                        numNode.setTextContent(String.valueOf(newNum));
                        newNum++;
                       
                    }
                }
            }

            // Save the changes back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("cran.qry_modified.xml"));
            transformer.transform(source, result);

            System.out.println("Updated num values successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
