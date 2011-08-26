// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

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
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectSingleNode(Node, String, Map)} or
	 * {@link #selectSingleNode(Node, String, Node)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * 
	 * @return the first matching node or null in case the XPath evaluated to
	 *         an empty node set
	 * 
	 * @throws XPathException
	 */
	public static Node selectSingleNode(Node contextNode, String xpathString) throws XPathException {
		return selectSingleNode(contextNode, xpathString, contextNode);
	}

	/**
	 * Selects the first node that matches the given XPath expression, taking
	 * additional namespace from the {@code namespaces} mapping.
	 * 
	 * <p>
	 * This function behaves like {@link #selectSingleNode(Node, String)}, but
	 * the namespace prefixes that can be used in the XPath expression are not
	 * only those available in {@code contextNode}, but also the ones defined
	 * in the {@code namespaces} mapping.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * 
	 * @return the first matching node or null in case the XPath evaluated to
	 *         an empty node set
	 * 
	 * @throws XPathException
	 */
	public static Node selectSingleNode(Node contextNode, String xpathString, Map<String, String> namespaces) throws XPathException {
		NodeList nodes = selectNodeList(contextNode, xpathString, namespaces);
		return selectFirstNode(nodes);
	}

	/**
	 * Selects the first node that matches the given XPath expression, taking
	 * into account all namespaces found in {@code namespaceNode}.
	 * 
	 * <p>
	 * This function behaves like {@link #selectSingleNode(Node, String)}, but
	 * the namespace prefixes that can be used in the XPath expression are not
	 * those available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * 
	 * @return the first matching node or null in case the XPath evaluated to
	 *         an empty node set
	 * 
	 * @throws XPathException
	 */
	public static Node selectSingleNode(Node contextNode, String xpathString, Node namespaceNode) throws XPathException {
		NodeList nodes = selectNodeList(contextNode, xpathString, namespaceNode);
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
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectSingleNodeAsString(Node, String, Map)} or
	 * {@link #selectSingleNodeAsString(Node, String, Node)}.
	 * 
	 * <pre>
	 * Node node = selectSingleNode(contextNode, xpathString);
	 * return node.getTextContent();
	 * </pre>
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * 
	 * @return the content of the selected node or null in case the XPath
	 *         evaluated to an empty node set
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectSingleNode(Node, String)
	 */
	public static String selectSingleNodeAsString(Node contextNode, String xpathString) throws XPathException {
		return selectSingleNodeAsString(contextNode, xpathString, contextNode);
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
	 * 
	 * @return the content of the selected node or null in case the XPath
	 *         evaluated to an empty node set
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectSingleNode(Node, String, Map)
	 */
	public static String selectSingleNodeAsString(Node contextNode, String xpathString, Map<String, String> namespaces) throws XPathException {
		Node node = selectSingleNode(contextNode, xpathString, namespaces);
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
	 * 
	 * @return the content of the selected node or null in case the XPath
	 *         evaluated to an empty node set
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectSingleNode(Node, String, Node)
	 */
	public static String selectSingleNodeAsString(Node contextNode, String xpathString, Node namespaceNode) throws XPathException {
		Node node = selectSingleNode(contextNode, xpathString, namespaceNode);
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
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectNodeList(Node, String, Map)} or
	 * {@link #selectNodeList(Node, String, Node)}.
	 * 
	 * <p>
	 * It is better to use the {@link #selectListOfNodes(Node, String)} method
	 * because it returns a {@code List<Node>} instead of a legacy
	 * {@code org.w3c.dom.NodeList}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectListOfNodes(Node, String)
	 */
	public static NodeList selectNodeList(Node contextNode, String xpathString) throws XPathException {
		return selectNodeList(contextNode, xpathString, contextNode);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account all namespaces found in {@code namespaceNode} (returns a
	 * {@code org.w3c.dom.NodeList} list).
	 * 
	 * <p>
	 * This function behaves like {@link #selectNodeList(Node, String)}, but
	 * the namespace prefixes that can be used in the XPath expression are not
	 * those available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * It is better to use the {@link #selectListOfNodes(Node, String, Node)}
	 * method because it returns a {@code List<Node>} instead of a legacy
	 * {@code org.w3c.dom.NodeList}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectListOfNodes(Node, String, Node)
	 */
	public static NodeList selectNodeList(Node contextNode, String xpathString, Node namespaceNode) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(namespaceNode);
		return selectNodeList(contextNode, xpathString, nsContext);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account the namespace mappings defined in {@code namespaces}
	 * (returns a {@code org.w3c.dom.NodeList} list).
	 * 
	 * <p>
	 * This function behaves like {@link #selectNodeList(Node, String)}, but
	 * the namespace prefixes that can be used in the XPath expression are not
	 * only those available in {@code contextNode}, but also the ones defined
	 * in the {@code namespaces} mapping.
	 * 
	 * <p>
	 * It is better to use the {@link #selectListOfNodes(Node, String, Map)}
	 * method because it returns a {@code List<Node>} instead of a legacy
	 * {@code org.w3c.dom.NodeList}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectListOfNodes(Node, String, Map)
	 */
	public static NodeList selectNodeList(Node contextNode, String xpathString, Map<String, String> namespaces) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(contextNode, namespaces);
		return selectNodeList(contextNode, xpathString, nsContext);
	}

	private static NodeList selectNodeList(Node contextNode, String xpathString, NamespaceContext nsContext) throws XPathException {
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();

		xpath.setNamespaceContext(nsContext);

		XPathExpression xpathExpr = xpath.compile(xpathString);

		NodeList nodes = (NodeList) xpathExpr.evaluate(contextNode, XPathConstants.NODESET);

		return nodes;
	}

	/**
	 * Selects all the nodes that match the given XPath expression (returns a
	 * {@code List<Node>} list).
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectListOfNodes(Node, String, Map)} or
	 * {@link #selectListOfNodes(Node, String, Node)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 */
	public static List<Node> selectListOfNodes(Node contextNode, String xpathString) throws XPathException {
		return selectListOfNodes(contextNode, xpathString, contextNode);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account all namespaces found in {@code namespaceNode} (returns a
	 * {@code List<Node>} list).
	 * 
	 * <p>
	 * This function behaves like {@link #selectListOfNodes(Node, String)},
	 * but the namespace prefixes that can be used in the XPath expression are
	 * not those available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 */
	public static List<Node> selectListOfNodes(Node contextNode, String xpathString, Node namespaceNode) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(namespaceNode);
		return selectListOfNodes(contextNode, xpathString, nsContext);
	}

	/**
	 * Selects all the nodes that match the given XPath expression, taking
	 * into account the namespace mappings defined in {@code namespaces}
	 * (returns a {@code List<Node>} list).
	 * 
	 * <p>
	 * This function behaves like {@link #selectListOfNodes(Node, String)},
	 * but the namespace prefixes that can be used in the XPath expression are
	 * not only those available in {@code contextNode}, but also the ones
	 * defined in the {@code namespaces} mapping.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces a mapping between namespace prefixes and URIs
	 * 
	 * @return all the nodes that match the given XPath expression
	 * 
	 * @throws XPathException
	 */
	public static List<Node> selectListOfNodes(Node contextNode, String xpathString, Map<String, String> namespaces) throws XPathException {
		NamespaceContext nsContext = new NodeNamespaceContext(contextNode, namespaces);
		return selectListOfNodes(contextNode, xpathString, nsContext);
	}

	private static List<Node> selectListOfNodes(Node contextNode, String xpathString, NamespaceContext nsContext) throws XPathException {
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
	 * required, use {@link #selectNodeListAsStrings(Node, String, Map)} or
	 * {@link #selectNodeListAsStrings(Node, String, Node)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * 
	 * @return a list with the textual content of the matching nodes
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String)
	 */
	public static List<String> selectNodeListAsStrings(Node contextNode, String xpathString) throws XPathException {
		return selectNodeListAsStrings(contextNode, xpathString, contextNode);
	}

	/**
	 * Returns a list with the textual content of all the nodes that match the
	 * given XPath expression, taking into account the namespace mappings
	 * defined in {@code namespaces}.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeListAsStrings(Node, String)}, but the namespace
	 * prefixes that can be used in the XPath expression are not only those
	 * available in {@code contextNode}, but also the ones defined in the
	 * {@code namespaces} mapping.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaces
	 * 
	 * @return a list with the textual content of the matching nodes
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, Map)
	 */
	public static List<String> selectNodeListAsStrings(Node contextNode, String xpathString, Map<String, String> namespaces) throws XPathException {
		NodeList nodeList = selectNodeList(contextNode, xpathString, namespaces);

		return nodeListAsStringList(nodeList);
	}

	/**
	 * Returns a list with the textual content of all the nodes that match the
	 * given XPath expression, taking into account all namespaces found
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * This function behaves like
	 * {@link #selectNodeListAsStrings(Node, String)}, but the namespace
	 * prefixes that can be used in the XPath expression are not those
	 * available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * 
	 * @return a list with the textual content of the matching nodes
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, Node)
	 */
	public static List<String> selectNodeListAsStrings(Node contextNode, String xpathString, Node namespaceNode) throws XPathException {
		NodeList nodeList = selectNodeList(contextNode, xpathString, namespaceNode);

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
	 * Same as {@link #selectNodeList(Node, String)} but returns a
	 * {@code NodeIterator} instead of a simple {@code NodeList}.
	 * 
	 * <p>
	 * The only namespaces prefixes usable in the XPath expression are those
	 * available in {@code contextNode}. If other additional prefixes are
	 * required, use {@link #selectNodeIterator(Node, String, Node)}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * 
	 * @return an iterator over all the nodes that match the given XPath
	 *         expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String)
	 */
	public static NodeIterator selectNodeIterator(Node contextNode, String xpathString) throws XPathException {
		return selectNodeIterator(contextNode, xpathString, contextNode);
	}

	/**
	 * Returns an iterator over all the nodes that match the given XPath
	 * expression, taking into account all namespaces found in
	 * {@code namespaceNode}.
	 * 
	 * <p>
	 * Same as {@link #selectNodeList(Node, String, Node)} but returns a
	 * {@code NodeIterator} instead of a simple {@code NodeList}.
	 * 
	 * <p>
	 * This function behaves like {@link #selectNodeIterator(Node, String)},
	 * but the namespace prefixes that can be used in the XPath expression are
	 * not those available in {@code contextNode}, but those available in
	 * {@code namespaceNode}.
	 * 
	 * @param contextNode the node from which the XPath expression is
	 *            evaluated
	 * @param xpathString the XPath expression to evaluate
	 * @param namespaceNode the node from which all the namespace declarations
	 *            will be taken
	 * 
	 * @return an iterator over all the nodes that match the given XPath
	 *         expression
	 * 
	 * @throws XPathException
	 * 
	 * @see #selectNodeList(Node, String, Node)
	 */
	public static NodeIterator selectNodeIterator(Node contextNode, String xpathString, Node namespaceNode) throws XPathException {
		NodeList nodes = selectNodeList(contextNode, xpathString, namespaceNode);
		return new NodeListIterator(nodes);
	}
}
