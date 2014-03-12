package org.blackbananacoin.premature;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class HelloJgraphT {

	public static void main(String[] args) {

		// create a graph based on URL objects
		DirectedGraph<URL, DefaultEdge> hrefGraph = createHrefGraph();
		
		// note directed edges are printed as: (<v1>,<v2>)
		System.out.println(hrefGraph.toString());
		
		
		for (URL url : hrefGraph.vertexSet()) {
			Set<DefaultEdge> edgesOutVertex = hrefGraph.outgoingEdgesOf(url);
			System.out.println("[vertex]" + url + " -- out edge :" + edgesOutVertex.size());
		}

	}

	/**
	 * Creates a toy directed graph based on URL objects that represents link
	 * structure.
	 * 
	 * @return a graph based on URL objects.
	 */
	private static DirectedGraph<URL, DefaultEdge> createHrefGraph() {
		DirectedGraph<URL, DefaultEdge> g = new DefaultDirectedGraph<URL, DefaultEdge>(
				DefaultEdge.class);

		try {
			URL root = new URL("http://blackbananacoin.org");
			URL bkbcExchange = new URL("http://blackbananacoin.org/p/twd2btc/");
			URL bkbcFoo23 = new URL("http://blackbananacoin.org/p/foo23/");
			URL bkbcFoo23Haha24 = new URL(
					"http://blackbananacoin.org/p/foo23/haha24");

			// add the vertices
			g.addVertex(root);
			g.addVertex(bkbcExchange);
			g.addVertex(bkbcFoo23);
			g.addVertex(bkbcFoo23Haha24);

			// add edges to create linking structure
			g.addEdge(root, bkbcExchange);
			g.addEdge(root, bkbcFoo23);
			g.addEdge(bkbcFoo23, bkbcFoo23Haha24);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return g;
	}

}
