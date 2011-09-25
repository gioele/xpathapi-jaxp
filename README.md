XPathAPI
========

Select XML nodes using XPath and a single line of Java code.

XPathAPI offers simple methods to select nodes on XML document trees
using XPath expressions. These methods relieve you from writing many
lines of repetitive code.

With XPathAPI, many common tasks become simple one liners.

For example, the XPathAPI way to select all the nodes that match the
XPath `//friend[@status='best']` is:

	List<Node> bestFriends = XPathAPI.selectListOfNodes(doc, "//friend[@status='best']");

That single line is equivalent to the following longer and more complex
piece of code.

	XPathFactory xpathFactory = XPathFactory.newInstance();
	XPath xpath = xpathFactory.newXPath();

	XPathExpression xpathExpr = xpath.compile("//friend[@status='best']");

	NodeList bestFriendsNL = (NodeList) xpathExpr.evaluate(contextNode, XPathConstants.NODESET);

	List<Node> bestFriends = new ArrayList<Node>();
	for (int i = 0; i < bestFriendsNL.getLength(); i++) {
		Node node = nodeList.item(i);
		list.add(node);
	}


Examples
--------

### Select all the nodes that match an XPath

The XPathAPI method `selectListOfNodes` returns a `List<Node>` with all
the nodes that match the passed XPath.

	List<Node> bestFriends = XPathAPI.selectListOfNodes(doc, "//friend[@status='best']");

### Select a single node

To select a single node from a tree, you can use the `selectSingleNode`
method.

	Node me = XPathAPI.selectSingleNode(doc, "//person[@id ='" + myID + "']");

### Select text content instead of nodes

Often all you interested in is the text contained in an element or in
an attribute, not the node itself. In such cases you can get these
strings directly.

	String myName = XPathAPI.selectSingleNode(doc, "//person[@id ='" + myID + "']/@firstName");

	List<Strings> surnames = XPathAPI.selectNodeListAsStrings(doc, '//person/@surname');

### Dealing with namespaces

Normally, the only prefixes usable in an XPath are those visible from
the context node. If you want to use other prefixes, you can either
define them by hand or provide a _namespace node_ whose namespaces will
be those available in the XPath.

To get the `<k:d>` node from this document

	<a>
		<b>
			<c xmlns:k="http://ns.example.org/foobar">
				<k:d/>
			<c>
		</b>
	</a>

you can use a manual mapping

	Map<String, String> nsMap = new HashMap<String, String>();
	nsMap.put('k', 'http://ns.example.org/foobar');

	Node d = XPathAPI.selectSingleNode(doc, '//k:d', nsMap);

or you can choose `<c>` as the _namespace node_

	Node nsNode = XPathAPI.selectSingleNode(doc, '/a/b/c');

	Node d = XPathAPI.selectSingleNode(doc, '//k:d', nsNode);

The former method is more readable, the latter is more robust.


Runtime and dependencies
------------------------

XPathAPI requires the presence of a JAXP-compliant XPath processor. Any
JRE starting from J2SE 5.0 contains all the required libraries. This
means that XPathAPI works out of the box on all recent Java
installations without any additional library.

XPathAPI will also work fine (or better) if you use Saxon or Xerces.


Installation
------------

You can install XPathAPI adding it to you Maven dependencies:

	<dependency>
		<groupId>it.svario.xpathapi</groupId>
		<artifactId>xpathapi-jaxp</artifactId>
		<version>0.2</version>
	</dependency>

Alternatively, you can download the XPathAPI JAR and add it to your
Java classpath.

Maven Central (<http://search.maven.org>) has also instructions on how
to install XPathAPI using Buildr, Ivy or Groovy.


Documentation
-------------

The API documentation in JavaDoc format is available at
<http://svario.it/xpathapi/doc>.

The documentation covers 100% of the public methods.


Authors
-------

* Gioele Barabucci <gioele@svario.it>

The original core of XPathAPI has been inspired by Apache's XPathAPI.

License
-------

This is free and unencumbered software released into the public domain.
See the `UNLICENSE` file or <http://unlicense.org/> for more details.
