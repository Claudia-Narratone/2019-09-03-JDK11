package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.TreeSingleSourcePathsImpl;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<String, DefaultWeightedEdge> graph;
	private FoodDao dao;;
	private List<String> vertici;
	
	public Model() {
		dao=new FoodDao();
	}
	
	public String creaGrafo(int c) {
		graph=new SimpleWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertici=dao.getPortionsDisplayName(c);
		List<InfoArco> tuttiArchi=dao.getTuttiArchi();
		
		Graphs.addAllVertices(graph, vertici);
		for(InfoArco a:tuttiArchi) {
			if(this.graph.vertexSet().contains(a.getVertice1()) && this.graph.vertexSet().contains(a.getVertice2())) {
				Graphs.addEdge(graph, a.getVertice1(), a.getVertice2(), a.getPeso());
			}
		}
		
		return String.format("Grafo creato con %d archi e %d vertici", this.graph.edgeSet().size(), this.graph.vertexSet().size());
		
	}
	
	public List<String> getVerticiGrafo(){
		return this.vertici;
	}
	
	public List<PorzioneAdiacente> getAdiacenti(String porzione){
		List<String> vicini=Graphs.neighborListOf(graph, porzione);
		List<PorzioneAdiacente> result=new ArrayList<PorzioneAdiacente>();
		for(String v:vicini) {
			double peso=this.graph.getEdgeWeight(this.graph.getEdge(porzione, v));
			result.add(new PorzioneAdiacente(v, peso));
		}
		return result;
	}
}
