// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

package it.svario.xpathapi.jaxp.test;

import it.svario.xpathapi.jaxp.XPathAPI;
import org.testng.annotations.Test;
import org.w3c.dom.Node;
import static org.testng.Assert.*;

@SuppressWarnings("javadoc")
public class TemplateSubstitutionTest extends TestBase {
	public TemplateSubstitutionTest() throws Exception {
		super();
	}

	@Test
	public void replacesTemplates() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//b[contains(text(), '{}')]", "bb33");

		assertNotNull(node);
		assertEquals(node.getTextContent(), "aabb33");
	}

	@Test
	public void doNotReplacesNonTemplates() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//b[contains(text(), '{}')]");

		assertNull(node);
	}
}
