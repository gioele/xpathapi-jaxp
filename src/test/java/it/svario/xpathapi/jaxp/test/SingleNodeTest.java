// This is free software released into the public domain (CC0 license).

package it.svario.xpathapi.jaxp.test;

import java.util.HashMap;
import java.util.Map;
import it.svario.xpathapi.jaxp.XPathAPI;
import org.testng.annotations.Test;
import org.w3c.dom.*;
import static org.testng.Assert.*;

@SuppressWarnings("javadoc")
public class SingleNodeTest extends TestBase {
	public SingleNodeTest() throws Exception {
		super();
	}

	@Test
	public void findsFirstNode() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//b");

		assertNotNull(node);
		assertEquals(node.getTextContent(), "aabb11");
	}

	@Test
	public void findsCorrectNode() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//b[2]");

		assertNotNull(node);
		assertEquals(node.getTextContent(), "aabb33");
	}

	@Test
	public void selectNodeWithNS() throws Exception {
		Node aNode = doc.getFirstChild().getFirstChild();
		Node bNode = aNode.getLastChild();
		Node node = XPathAPI.selectSingleNode(doc, "//k:b", bNode);

		assertNotNull(node);
	}

	@Test
	public void selectNodeWithNSMap() throws Exception {
		Map<String, String> namespaces = new HashMap<String, String>();
		namespaces.put("k", "abc");

		Node node = XPathAPI.selectSingleNode(doc, "//k:b", namespaces);

		assertNotNull(node);
	}

	@Test
	public void doNotIgnoreNS() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//a");

		assertNull(node);
	}

	@Test
	public void selectNodeWithNSDeclaredInIt() throws Exception {
		Node aNode = doc.getFirstChild().getFirstChild();
		Node node = XPathAPI.selectSingleNode(doc, "//k:a", aNode);

		assertNotNull(node);
	}

	@Test
	public void returnsNullIfNotFound() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//d");

		assertNull(node);
	}

	@Test
	public void extractNSFromRoot() throws Exception {
		String docStr = "<a xmlns='abc' xmlns:k='def'><k:b/></a>";
		Document doc = documentFromString(docStr);

		Node node = XPathAPI.selectSingleNode(doc, "//k:b");

		assertNotNull(node);
	}
}
