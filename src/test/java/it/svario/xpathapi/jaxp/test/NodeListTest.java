// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

package it.svario.xpathapi.jaxp.test;

import java.util.*;
import it.svario.xpathapi.jaxp.XPathAPI;
import org.testng.annotations.Test;
import org.w3c.dom.NodeList;
import static org.testng.Assert.*;

@SuppressWarnings("javadoc")
public class NodeListTest extends TestBase {
	public NodeListTest() throws Exception {
		super();
	}

	@Test
	public void selectNodeList() throws Exception {
		NodeList nodes = XPathAPI.selectNodeList(doc, "//b");

		assertNotNull(nodes);
		assertEquals(nodes.getLength(), 4);
	}

	@Test
	public void selectNodeListNSMap() throws Exception {
		Map<String, String> ns = new HashMap<String, String>();
		ns.put("k", "def");

		org.w3c.dom.NodeList nodes = XPathAPI.selectNodeList(doc, "//k:a/k:b", ns);

		assertNotNull(nodes);
		assertEquals(nodes.getLength(), 2);
	}
}
