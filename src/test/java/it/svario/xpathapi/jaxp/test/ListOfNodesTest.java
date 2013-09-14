// This is free software released into the public domain (CC0 license).

package it.svario.xpathapi.jaxp.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.svario.xpathapi.jaxp.XPathAPI;
import org.testng.annotations.Test;
import org.w3c.dom.Node;

@SuppressWarnings("javadoc")
public class ListOfNodesTest extends TestBase {
	public ListOfNodesTest() throws Exception {
		super();
	}

	@Test
	public void selectListOfNodes() throws Exception {
		List<Node> nodes = XPathAPI.selectListOfNodes(doc, "//b");

		assertNotNull(nodes);
		assertEquals(nodes.size(), 4);
	}

	@Test
	public void selectListOfNodesNSMap() throws Exception {
		Map<String, String> ns = new HashMap<String, String>();
		ns.put("k", "def");

		List<Node> nodes = XPathAPI.selectListOfNodes(doc, "//k:a/k:b", ns);

		assertNotNull(nodes);
		assertEquals(nodes.size(), 2);
	}
}
