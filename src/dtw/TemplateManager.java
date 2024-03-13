package dtw;

/**
 * @author <a href="mailto:gery.casiez@univ-lille.fr">Gery Casiez</a>
 */

import java.io.File;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javafx.geometry.Point2D;


public class TemplateManager
{
	private Vector<Template> theTemplates;
	
	TemplateManager() {
		theTemplates = new Vector<Template>();
	}
	
	void loadFile(String filename) {
		File fXmlFile = new File(filename);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("template");
			for (int i = 0; i < nList.getLength(); i++) {
				Node nNode = nList.item(i);
				Element eElement = (Element) nNode;
				String name = eElement.getAttribute("name").toString();
				
				Vector<Point2D> pts = new Vector<Point2D>();
				
				NodeList nListPoints = eElement.getElementsByTagName("Point");
				for (int j = 0; j < nListPoints.getLength(); j++) {
					Node nNodepoints = nListPoints.item(j);
					Element eElementPoint = (Element) nNodepoints;
					double x = Double.parseDouble(eElementPoint.getAttribute("x").toString());
					double y = Double.parseDouble(eElementPoint.getAttribute("y").toString());
					pts.add(new Point2D(x, y));
				}
				theTemplates.add(new Template(name, pts));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Vector<Template> getTemplates() {
		return theTemplates;
	}
}