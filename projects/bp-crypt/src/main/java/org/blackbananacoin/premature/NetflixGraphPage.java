package org.blackbananacoin.premature;

import static com.netflix.nfgraph.spec.NFPropertySpec.COMPACT;
import static com.netflix.nfgraph.spec.NFPropertySpec.HASH;
import static com.netflix.nfgraph.spec.NFPropertySpec.MULTIPLE;
import static com.netflix.nfgraph.spec.NFPropertySpec.SINGLE;

import com.netflix.nfgraph.OrdinalIterator;
import com.netflix.nfgraph.build.NFBuildGraph;
import com.netflix.nfgraph.compressed.NFCompressedGraph;
import com.netflix.nfgraph.spec.NFGraphSpec;
import com.netflix.nfgraph.spec.NFNodeSpec;
import com.netflix.nfgraph.spec.NFPropertySpec;
import com.netflix.nfgraph.util.OrdinalMap;

public class NetflixGraphPage {
	
	String nodePage = "Page";

	NFGraphSpec pageSchema = new NFGraphSpec(
			new NFNodeSpec(nodePage,
			   new NFPropertySpec("link", nodePage, MULTIPLE | COMPACT)));
	
	OrdinalMap<String> pageOrdinals = new OrdinalMap<String>();

	public NFCompressedGraph initGraph() {
		NFBuildGraph buildGraph = new NFBuildGraph(pageSchema);

		int homeOrdinal = pageOrdinals.add("HOME");
		int aboutOrdinal = pageOrdinals.add("ABOUT");
		int companyOrdinal = pageOrdinals.add("COMPANY");
		int faqOrdinal = pageOrdinals.add("FAQ");

		buildGraph.addConnection(nodePage, homeOrdinal, "link",aboutOrdinal);
		buildGraph.addConnection(nodePage, homeOrdinal, "link",companyOrdinal);
		buildGraph.addConnection(nodePage, aboutOrdinal, "link",faqOrdinal);
		
		NFCompressedGraph compressedGraph = buildGraph.compress();
		return compressedGraph;
	}
	
	public void testGraph(NFCompressedGraph  compressedGraph, String pageName){
		int homeOrdinal = pageOrdinals.get(pageName);

		OrdinalIterator iter = compressedGraph.getConnectionIterator(nodePage, homeOrdinal, "link");

		int currentOrdinal = iter.nextOrdinal();

		while(currentOrdinal != OrdinalIterator.NO_MORE_ORDINALS) {
			System.out.println(pageOrdinals.get(currentOrdinal) + " link in " + pageName);
			currentOrdinal = iter.nextOrdinal();
		}
	}

	public static void main(String[] args) {
		
		NetflixGraphPage app = new NetflixGraphPage();
		NFCompressedGraph g = app.initGraph();
		
		System.out.println(g.toString());
		app.testGraph(g,"HOME");
		app.testGraph(g,"ABOUT");

	}

}
