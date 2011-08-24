// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

package it.svario.xpathapi.jaxp.test;

import java.util.HashMap;
import java.util.Map;
import it.svario.xpathapi.jaxp.XPathAPI;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@SuppressWarnings("javadoc")
public class SingleNodeAsStringTest extends TestBase {
	public SingleNodeAsStringTest() throws Exception {
		super();
	}
	
	@Test
	public void convertOnlyTheFirstNode() throws Exception {
		String text = XPathAPI.selectSingleNodeAsString(doc, "//b");

		assertNotNull(text);
		assertEquals(text, "aabb11");
	}

	@Test
	public void findsCorrectNode() throws Exception {
		String text = XPathAPI.selectSingleNodeAsString(doc, "//b[2]");

		assertNotNull(text);
		assertEquals(text, "aabb33");
	}

	@Test
	public void selectNodeWithNSMap() throws Exception {
		Map<String, String> namespaces = new HashMap<String, String>();
		namespaces.put("k", "def");

		String text = XPathAPI.selectSingleNodeAsString(doc, "//k:b", namespaces);

		assertNotNull(text);
		assertEquals(text, "kkaakkbb11");
	}

	@Test
	public void returnsNullIfNotFound() throws Exception {
		String text = XPathAPI.selectSingleNodeAsString(doc, "//d");

		assertNull(text);
	}
}
