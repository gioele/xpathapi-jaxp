// This is free software released into the public domain (CC0 license).

package it.svario.xpathapi.jaxp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

/**
 * The container for the various static methods exposed by the XPathAPI.
 * 
 * <p>
 * See {@linkplain it.svario.xpathapi.jaxp the XPathAPI package documentation}
 * for an overview of XPathAPI and examples of how to use these methods.
 * 
 * @see it.svario.xpathapi.jaxp
 */
public class XPathAPI {
	/**
	 * Selects the first node that matches the given XPath expression.
	 * 
	 * <p>
	 * Any "{@code {}}" place-holder in the XPath expression is replaced with
	 * the content of the respective replacement string supplied in
	 * {@code args}. Please note that no escaping is performed on the
	 * replacement strings, beware of single and double quotes.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectSingleNode(Node, String, Map, String...)}
	 * or {@link #selectSingleNode(Node, String, Node, String...)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return the first matching node or null in case the XPath evaluated to
	 *         an empty node set
	 * 
	 * @throws XPathException
	 */
	public static Node selectSingleNode(Node contextNode, String xpathString, String... args) throws XPathException {
		return selectSingleNode(contextNode, xpathString, contextNode, args);
	}

	/**
	 * Selects the first node that matches the given XPath expression, taking
	 * additional namespace from the {@code namespaces} mapping.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectSingleNode(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not only those
	 * available in {@code contextNode}, but also the ones defined in the
	 * {@code namespaces} mapping.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return the first matching node or null in case the XPath evaluated to
	 *         an empty node set
	 * 
	 * @throws XPathException
	 */
	public static Node selectSingleNode(Node contextNode, String xpathString, Map<String, String> namespaces, String... args) throws XPathException {
		NodeList nodes = selectNodeList(contextNode, xpathString, namespaces, args);
		return selectFirstNode(nodes);
	}

	/**
	 * Selects the first node that matches the given XPath expression, taking
	 * into account all namespaces found in {@code namespaceNode}.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectSingleNode(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not those
	 * available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return the first matching node or null in case the XPath evaluated to
	 *         an empty node set
	 * 
	 * @throws XPathException
	 */
	public static Node selectSingleNode(Node contextNode, String xpathString, Node namespaceNode, String... args) throws XPathException {
		NodeList nodes = selectNodeList(contextNode, xpathString, namespaceNode, args);
		return selectFirstNode(nodes);
	}

	private static Node selectFirstNode(NodeList nodes) {
		if (nodes.getLength() == 0) {
			return null;
		}

		return nodes.item(0);
	}

	/**
	 * Returns the textual content of the first node that matches the given
	 * XPath expression.
	 * 
	 * <p>
	 * Any "{@code {}}" place-holder in the XPath expression is replaced with
	 * the content of the respective replacement string supplied in
	 * {@code args}. Please note that no escaping is performed on the
	 * replacement strings, beware of single and double quotes.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use
	 * {@link #selectSingleNodeAsString(Node, String, Map, String...)} or
	 * {@link #selectSingleNodeAsString(Node, String, Node, String...)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return the content of the selected node or null in case the XPath
	 *         evaluated to an empty node set
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectSingleNode(Node, String, String...)
	 */
	public static String selectSingleNodeAsString(Node contextNode, String xpathString, String... args) throws XPathException {
		return selectSingleNodeAsString(contextNode, xpathString, contextNode, args);
	}

	/**
	 * Returns the textual content of the first node that matches the given
	 * XPath expression, taking into account the namespace mappings defined in
	 * {@code namespaces}.
	 * 
	 * <p>
	 * Basically, this method is equivalent to
	 * 
	 * <pre>
	 * Node node = selectSingleNode(contextNode, xpathString, namespaces);
	 * return node.getTextContent();
	 * </pre>
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return the content of the selected node or null in case the XPath
	 *         evaluated to an empty node set
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectSingleNode(Node, String, Map, String...)
	 */
	public static String selectSingleNodeAsString(Node contextNode, String xpathString, Map<String, String> namespaces, String... args) throws XPathException {
		Node node = selectSingleNode(contextNode, xpathString, namespaces, args);
		return selectNodeAsString(node);
	}

	/**
	 * Returns the textual content of the first node that matches the given
	 * XPath expression, taking into account all namespaces found
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * Basically, this method is equivalent to
	 * 
	 * <pre>
	 * Node node = selectSingleNode(contextNode, xpathString, namespaceNode);
	 * return node.getTextContent();
	 * </pre>
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return the content of the selected node or null in case the XPath
	 *         evaluated to an empty node set
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectSingleNode(Node, String, Node, String...)
	 */
	public static String selectSingleNodeAsString(Node contextNode, String xpathString, Node namespaceNode, String... args) throws XPathException {
		Node node = selectSingleNode(contextNode, xpathString, namespaceNode, args);
		return selectNodeAsString(node);
	}

	private static String selectNodeAsString(Node node) {
		if (node == null) {
			return null;
		}

		return node.getTextContent();
	}

	/**
	 * Selects all the nodes that match the given XPath expression (returns a
	 * {@code org.w3c.dom.NodeList} list).
	 * 
	 * <p>
	 * Any "{@code {}}" place-holder in the XPath expression is replaced with
	 * the content of the respective replacement string supplied in
	 * {@code args}. Please note that no escaping is performed on the
	 * replacement strings, beware of single and double quotes.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectNodeList(Node, String, Map, String...)} or
	 * {@link #selectNodeList(Node, String, Node, String...)}.
	 * 
	 * <p>
	 * It is better to use the
	 * {@link #selectListOfNodes(Node, String, String...)} method because it
	 * returns a {@code List<Node>} instead of a legacy
	 * {@code org.w3c.dom.NodeList}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectListOfNodes(Node, String, String...)
	 */
	public static NodeList selectNodeList(Node contextNode, String xpathString, String... args) throws XPathException {
		return selectNodeList(contextNode, xpathString, contextNode, args);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account all namespaces found in {@code namespaceNode} (returns a
	 * {@code org.w3c.dom.NodeList} list).
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeList(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not those
	 * available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * It is better to use the
	 * {@link #selectListOfNodes(Node, String, Node, String...)} method
	 * because it returns a {@code List<Node>} instead of a legacy
	 * {@code org.w3c.dom.NodeList}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectListOfNodes(Node, String, Node, String...)
	 */
	public static NodeList selectNodeList(Node contextNode, String xpathString, Node namespaceNode, String... args) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(namespaceNode);
		return selectNodeList(contextNode, xpathString, nsContext, args);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account the namespace mappings defined in {@code namespaces}
	 * (returns a {@code org.w3c.dom.NodeList} list).
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeList(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not only those
	 * available in {@code contextNode}, but also the ones defined in the
	 * {@code namespaces} mapping.
	 * 
	 * <p>
	 * It is better to use the
	 * {@link #selectListOfNodes(Node, String, Map, String...)} method because
	 * it returns a {@code List<Node>} instead of a legacy
	 * {@code org.w3c.dom.NodeList}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectListOfNodes(Node, String, Map, String...)
	 */
	public static NodeList selectNodeList(Node contextNode, String xpathString, Map<String, String> namespaces, String... args) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(contextNode, namespaces);
		return selectNodeList(contextNode, xpathString, nsContext, args);
	}

	private static NodeList selectNodeList(Node contextNode, String xpathString, NamespaceContext nsContext, String... args) throws XPathException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		xpath.setNamespaceContext(nsContext);

		xpathString = replacePlaceholders(xpathString, args);

		XPathExpression xpathExpr = xpath.compile(xpathString);

		NodeList nodes = (NodeList) xpathExpr.evaluate(contextNode, XPathConstants.NODESET);

		return nodes;
	}

	/**
	 * Selects all the nodes that match the given XPath expression (returns a
	 * {@code List<Node>} list).
	 * 
	 * <p>
	 * Any "{@code {}}" place-holder in the XPath expression is replaced with
	 * the content of the respective replacement string supplied in
	 * {@code args}. Please note that no escaping is performed on the
	 * replacement strings, beware of single and double quotes.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectListOfNodes(Node, String, Map, String...)}
	 * or {@link #selectListOfNodes(Node, String, Node, String...)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 */
	public static List<Node> selectListOfNodes(Node contextNode, String xpathString, String... args) throws XPathException {
		return selectListOfNodes(contextNode, xpathString, contextNode, args);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account all namespaces found in {@code namespaceNode} (returns a
	 * {@code List<Node>} list).
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectListOfNodes(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not those
	 * available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 */
	public static List<Node> selectListOfNodes(Node contextNode, String xpathString, Node namespaceNode, String... args) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(namespaceNode);
		return selectListOfNodes(contextNode, xpathString, nsContext, args);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account the namespace mappings defined in {@code namespaces}
	 * (returns a {@code List<Node>} list).
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectListOfNodes(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not only those
	 * available in {@code contextNode}, but also the ones defined in the
	 * {@code namespaces} mapping.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 */
	public static List<Node> selectListOfNodes(Node contextNode, String xpathString, Map<String, String> namespaces, String... args) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(contextNode, namespaces);
		return selectListOfNodes(contextNode, xpathString, nsContext, args);
	}

	private static List<Node> selectListOfNodes(Node contextNode, String xpathString, NamespaceContext nsContext, String... args) throws XPathException {
		xpathString = replacePlaceholders(xpathString, args);
		NodeList nodeList = selectNodeList(contextNode, xpathString, nsContext);

		int listLength = nodeList.getLength();
		List<Node> list = new ArrayList<Node>(listLength);

		for (int i = 0; i < listLength; i++) {
			Node node = nodeList.item(i);
			list.add(node);
		}

		return list;
	}

	/**
	 * Returns a list with the textual content of all the nodes that match the
	 * given XPath expression.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use
	 * {@link #selectNodeListAsStrings(Node, String, Map, String...)} or
	 * {@link #selectNodeListAsStrings(Node, String, Node, String...)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return a list with the textual content of the matching nodes
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, String...)
	 */
	public static List<String> selectNodeListAsStrings(Node contextNode, String xpathString, String... args) throws XPathException {
		return selectNodeListAsStrings(contextNode, xpathString, contextNode, args);
	}

	/**
	 * Returns a list with the textual content of all the nodes that match the
	 * given XPath expression, taking into account the namespace mappings
	 * defined in {@code namespaces}.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeListAsStrings(Node, String, String...)}, but the
	 * namespace prefixes that can be used in the XPath expression are not
	 * only those available in {@code contextNode}, but also the ones defined
	 * in the {@code namespaces} mapping.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return a list with the textual content of the matching nodes
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, Map, String...)
	 */
	public static List<String> selectNodeListAsStrings(Node contextNode, String xpathString, Map<String, String> namespaces, String... args) throws XPathException {
		NodeList nodeList = selectNodeList(contextNode, xpathString, namespaces, args);

		return nodeListAsStringList(nodeList);
	}

	/**
	 * Returns a list with the textual content of all the nodes that match the
	 * given XPath expression, taking into account all namespaces found
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeListAsStrings(Node, String, String...)}, but the
	 * namespace prefixes that can be used in the XPath expression are not
	 * those available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return a list with the textual content of the matching nodes
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, Node, String...)
	 */
	public static List<String> selectNodeListAsStrings(Node contextNode, String xpathString, Node namespaceNode, String... args) throws XPathException {
		NodeList nodeList = selectNodeList(contextNode, xpathString, namespaceNode, args);

		return nodeListAsStringList(nodeList);
	}

	private static List<String> nodeListAsStringList(NodeList nodeList) {
		List<String> list = new ArrayList<String>(nodeList.getLength());
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			list.add(node.getTextContent());
		}

		return list;
	}

	/**
	 * Returns an iterator over all the nodes that match the given XPath
	 * expression.
	 * 
	 * <p>
	 * Same as {@link #selectNodeList(Node, String, String...)} but returns a
	 * {@code NodeIterator} instead of a simple {@code NodeList}.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use
	 * {@link #selectNodeIterator(Node, String, Node, String...)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return an iterator over all the nodes that match the given XPath
	 *         expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, String...)
	 */
	public static NodeIterator selectNodeIterator(Node contextNode, String xpathString, String... args) throws XPathException {
		return selectNodeIterator(contextNode, xpathString, contextNode, args);
	}

	/**
	 * Returns an iterator over all the nodes that match the given XPath
	 * expression, taking into account all namespaces found in
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * Same as {@link #selectNodeList(Node, String, Node, String...)} but
	 * returns a {@code NodeIterator} instead of a simple {@code NodeList}.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeIterator(Node, String, String...)}, but the namespace
	 * prefixes that can be used in the XPath expression are not those
	 * available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * @param args an optional array of strings used to replace the
	 *            "{@code {}}" place-holders in {@code xpathString}
	 * 
	 * @return an iterator over all the nodes that match the given XPath
	 *         expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, Node, String...)
	 */
	public static NodeIterator selectNodeIterator(Node contextNode, String xpathString, Node namespaceNode, String... args) throws XPathException {
		NodeList nodes = selectNodeList(contextNode, xpathString, namespaceNode, args);
		return new NodeListIterator(nodes);
	}

	private static String replacePlaceholders(String string, String... args) {
		for (String arg : args) {
			string = string.replace("{}", arg);
		}

		return string;
	}
}
