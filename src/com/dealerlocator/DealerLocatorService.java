package com.dealerlocator;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author dad
 *
 */
public class DealerLocatorService {
	public List<Coordinate> findClosestDealerForZip(HttpHost target, String zip) {
		String paramBase = "/dealerlocator/restService/V1/address/us/1/en///";
		// Need a little extra input checking to make sure a valid zip, but not
		// now.
		if (zip == null) {
			return null;
		}

		List<Coordinate> retVal = null;
		Document doc = null;
		HttpClient client = new DefaultHttpClient();
		// build the get
		HttpGet get = new HttpGet(paramBase + zip);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			HttpEntity entity = client.execute(target, get).getEntity();

			doc = builder.parse(entity.getContent());
			NodeList dealerLocations = doc
					.getElementsByTagName("dealerlocation");

			if (dealerLocations != null) {
				retVal = new ArrayList<Coordinate>();
				for (int i = 0; i < dealerLocations.getLength(); i++) {
					Node locationNode = dealerLocations.item(i);

					if (locationNode != null) {
						Coordinate nextCoord = new Coordinate();
						NodeList nodeList = locationNode.getChildNodes();
						int len = nodeList.getLength();
						for (int j = 0; j < len; j++) {
							Node node = nodeList.item(j);
							String value = this.getNodeValue(node);
							if ("latitude".equals(node.getNodeName())) {
								nextCoord.setxValue(Float.parseFloat(value));
							} else if ("longitude".equals(node.getNodeName())) {
								nextCoord.setyValue(Float.parseFloat(value));
							} else if ("name".equals(node.getNodeName())) {
								nextCoord.setDealerName(value);
							} else if ("street".equals(node.getNodeName())) {
								nextCoord.setAddress(value);
							} else if ("city".equals(node.getNodeName())) {
								nextCoord.setCity(value);
							} else if ("state".equals(node.getNodeName())) {
								nextCoord.setState(value);
							} else if ("zip".equals(node.getNodeName())) {
								nextCoord.setZip(value);
							}
						}
						retVal.add(nextCoord);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {

		}
		return retVal;
	}

	private String getNodeValue(Node node) {
		NodeList children = node.getChildNodes();
		if (children.getLength() > 0) {
			return children.item(0).getNodeValue();
		} else
			return null;
	}

}
