package us.lsi.p4.ej_2;

import us.lsi.common.IntegerSet;
import us.lsi.graphs.virtual.VirtualVertex;

public interface SubconjuntosVertex extends 
	//es un grafo virtaul, son grafos y vertices
	//cuales son los tipos de vertices y de aristas, y los 
	VirtualVertex<SubconjuntosVertex, SubconjuntosEdge, Integer> {
	
	Integer index();
	IntegerSet remaining();
	String toGraph();

	public static SubconjuntosVertex initial() {
		return SubconjuntosVertexI.of(0, IntegerSet.of(DatosSubconjuntos.universo()));
	}
}