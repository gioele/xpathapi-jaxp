// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

package it.svario.xpathapi.jaxp.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import it.svario.xpathapi.jaxp.XPathAPI;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

@SuppressWarnings("javadoc")
public class NodeListAsStringsTest extends TestBase {
	public NodeListAsStringsTest() throws Exception {
		super();
	}

	@Test
	public void selectNodeListAsStrings() throws Exception {
		List<String> strings = XPathAPI.selectNodeListAsStrings(doc, "//b");

		assertNotNull(strings);
		assertEquals(strings.size(), 4);
	}

	@Test
	public void selectNodeListAsStringsNSMap() throws Exception {
		Map<String, String> ns = new HashMap<String, String>();
		ns.put("k", "def");

		List<String> strings = XPathAPI.selectNodeListAsStrings(doc, "//k:a/k:b", ns);

		assertNotNull(strings);
		assertEquals(strings.size(), 2);
		assertEquals(strings.get(0), "kkaakkbb11");
		assertEquals(strings.get(1), "");
	}
}
