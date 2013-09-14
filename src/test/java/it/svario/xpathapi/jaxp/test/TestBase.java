// This is free software released into the public domain (CC0 license).

package it.svario.xpathapi.jaxp.test;

import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

@SuppressWarnings("javadoc")
public abstract class TestBase {
	protected Document doc;

	public TestBase() throws Exception {
		String docStr = "<root>" +
		                "<k:a xmlns:k='abc'><b>aabb11</b><k:b>aabb22</k:b><b>aabb33</b></k:a>" +
		                "<c><b>ccbb</b></c>" +
		                "<b>bb</b>" +
		                "<k:a xmlns:k='def'><k:b>kkaakkbb11</k:b><k:b/></k:a>" +
		                "</root>";

		doc = documentFromString(docStr);
	}

	protected Document documentFromString(String docStr) throws Exception {
		DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();
		df.setNamespaceAware(true);

		DocumentBuilder docBuilder = df.newDocumentBuilder();

		InputSource stream = new InputSource();
		stream.setCharacterStream(new StringReader(docStr));

		return docBuilder.parse(stream);
	}
}
