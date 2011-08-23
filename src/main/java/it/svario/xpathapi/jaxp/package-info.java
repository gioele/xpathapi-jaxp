// This is free and unencumbered software released into the public domain.
// See the `UNLICENSE` file or <http://unlicense.org/> for more details.

/**
 * A simple to use XPath API inspired by Apache's XPathAPI (JAXP version).
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
