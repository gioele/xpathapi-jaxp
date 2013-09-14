// This is free software released into the public domain (CC0 license).

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
	public void doesNotReplacesNonTemplates() throws Exception {
		Node node = XPathAPI.selectSingleNode(doc, "//b[contains(text(), '{}')]");

		assertNull(node);
	}
}
