// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

package it.svario.xpathapi.jaxp.test;

import it.svario.xpathapi.jaxp.XPathAPI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.annotations.Test;
import org.w3c.dom.*;
import static org.testng.Assert.*;

@SuppressWarnings("javadoc")
public class NodeIterator extends TestBase {
	public NodeIterator() throws Exception {
		super();
	}

	@Test
	public void selectNodeIterator() throws Exception {
		String[] expectedTexts = { "aabb11", "aabb33", "ccbb", "bb" };
		org.w3c.dom.traversal.NodeIterator it = XPathAPI.selectNodeIterator(doc, "//b");

		assertNotNull(it);

		List<String> foundTexts = new ArrayList<String>();

		Node node;
		while ((node = it.nextNode()) != null) {
			foundTexts.add(node.getTextContent());
		}

		assertEquals(foundTexts.toArray(new String[0]), expectedTexts);

		foundTexts.clear();

		while ((node = it.previousNode()) != null) {
			foundTexts.add(node.getTextContent());
		}

		Collections.reverse(foundTexts);
		assertEquals(foundTexts.toArray(new String[0]), expectedTexts);
	}
}
