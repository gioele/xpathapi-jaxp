// This is free software released into the public domain (CC0 license).

package it.svario.xpathapi.jaxp;

import java.util.*;
import java.util.Map.Entry;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

class NodeNamespaceContext implements NamespaceContext {
	private Map<String, String> namespaces;
	private String defaultNSURI;

	protected NodeNamespaceContext(Node node) {
		defaultNSURI = null;
		namespaces = new HashMap<String, String>();
		addCommonNamespaces();
		extractNamespacesFromNode(node);
	}

	protected NodeNamespaceContext(Node node, Map<String, String> namespaces) {
		this(node);
		this.namespaces.putAll(namespaces);
	}

	private void addCommonNamespaces() {
		namespaces.put(XMLConstants.XML_NS_PREFIX, XMLConstants.XML_NS_URI);
		namespaces.put(XMLConstants.XMLNS_ATTRIBUTE, XMLConstants.XMLNS_ATTRIBUTE_NS_URI);
	}

	private void extractNamespacesFromNode(Node node) {
		if (node.getParentNode() == null) {
			node = node.getFirstChild();
		}

		NamedNodeMap attrs = node.getAttributes();

		for (int i = 0; i < attrs.getLength(); i++) {
			Node attr = attrs.item(i);
			String name = attr.getNodeName();
			if (isDefaultNS(name) && defaultNSURI == null) {
				defaultNSURI = attr.getNodeValue();
			} else if (isPrefix(name)) {
				String prefix = prefixStringIn(name);
				String uri = attr.getNodeValue();
				if (!namespaces.containsKey(prefix)) {
					namespaces.put(prefix, uri);
				}
			}
		}

		Node parent = node.getParentNode();
		if (parent.getNodeType() == Node.ELEMENT_NODE) {
			extractNamespacesFromNode(parent);
		} else if (parent.getNodeType() == Node.DOCUMENT_NODE) {
			if (defaultNSURI == null) {
				defaultNSURI = XMLConstants.NULL_NS_URI;
			}
		}
	}

	private boolean isDefaultNS(String name) {
		return name.equals("xmlns");
	}

	private boolean isPrefix(String name) {
		return name.startsWith("xmlns:");
	}

	private String prefixStringIn(String name) {
		int startIdx = name.indexOf(":");
		return name.substring(startIdx + 1);
	}

	@Override
	public String getNamespaceURI(String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException();
		}

		if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX)) {
			return defaultNSURI;
		}

		if (namespaces.containsKey(prefix)) {
			return namespaces.get(prefix);
		}

		return XMLConstants.NULL_NS_URI;
	}

	@Override
	public String getPrefix(String namespaceURI) {
		if (namespaceURI == null) {
			throw new IllegalArgumentException();
		}

		if (namespaceURI.equals(defaultNSURI)) {
			return XMLConstants.DEFAULT_NS_PREFIX;
		}

		for (Entry<String, String> ns : namespaces.entrySet()) {
			if (ns.getValue().equals(namespaceURI)) {
				return ns.getKey();
			}
		}

		return null;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Iterator getPrefixes(String namespaceURI) {
		Set<String> prefixes = new HashSet<String>();

		String prefix = getPrefix(namespaceURI);
		if (prefix != null) {
			prefixes.add(prefix);
		}

		return prefixes.iterator();
	}
}
