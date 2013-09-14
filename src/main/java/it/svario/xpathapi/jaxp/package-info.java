// This is free software released into the public domain (CC0 license).

/**
 * Select XML nodes using XPath and a single line of Java code.
 * 
 * <p>
 * XPathAPI offers simple methods to select nodes on XML document trees
 * using XPath expressions.
 * 
 * <p>
 * The XPathAPI way to select all the nodes that match the XPath
 * {@code //friend[@status='best']} is:
 * 
 * <pre>
 * NodeList bestFriends = XPathAPI.selectNodeList(doc, "//friend[@status='best']");
 * </pre>
 * 
 * <p>
 * That single line is equivalent to this longer and more complex piece of
 * code:
 * 
 * <pre>
 * XPathFactory xpathFactory = XPathFactory.newInstance();
 * XPath xpath = xpathFactory.newXPath();
 *
 * XPathExpression xpathExpr = xpath.compile("//friend[@status='best']");
 *
 * NodeList bestFriends = (NodeList) xpathExpr.evaluate(contextNode, XPathConstants.NODESET);
 * </pre>
 * 
 * <p>
 * XPathAPI is easier to use but is slower than ad-hoc optimized code,
 * especially when multiple XPath expressions are to be evaluated on the same
 * big XML document. You should use XPathAPI in non-time-critical code paths
 * or for fast prototyping.
 */
package it.svario.xpathapi.jaxp;
