package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<Food, DefaultWeightedEdge> graph;
	private Map<Integer, Food> idMap;
	private Simulator sim;
	
	
	private FoodDao dao;
	
	public Model() {
		this.dao=new FoodDao();
	
	}
	
	public String creaGrafo(int p) {
		this.graph=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap=new HashMap<>();
		this.dao.getVertici(idMap, p);
		Graphs.addAllVertices(graph, idMap.values());
		//System.out.println(graph.vertexSet().size());
		List<Adiacenza> archi=this.dao.getArchi(idMap, p);
		for(Adiacenza a: archi) {
			Food f1=a.getF1();
			Food f2=a.getF2();
			double peso=a.getPeso();
			
			if(graph.containsVertex(f1) && graph.containsVertex(f2)) {
				DefaultWeightedEdge e=graph.getEdge(f1, f2);
				if(e==null) {
					//se non c'è già, lo aggiungo
					Graphs.addEdge(graph, f1, f2, peso);
				}
			}
		}
		
		//System.out.println(graph.edgeSet().size());
		String result="Grafo creato. Num vertici: "+graph.vertexSet().size()+" Num archi: "+graph.edgeSet().size();
		return result;
		
	}
	
	public List<Food2> getTop5(Food f){
		List<Food> vicini=Graphs.neighborListOf(graph, f);
		List<Food2> result=new ArrayList<>();
		for(Food c: vicini) {
			double peso=graph.getEdgeWeight(graph.getEdge(c, f));
			result.add(new Food2(c, peso));
		}
		Collections.sort(result);
		return result;
	}
	public List<Food> getVertici(){
		List<Food> vertici=new ArrayList<>(graph.vertexSet());
		Collections.sort(vertici);
		return vertici;
		
	}
	
	public void simula(int k, Food f) {
		sim=new Simulator();
		sim.setSimulator(graph,k ,f);
		sim.init();
		sim.sim();
		
	}
	
	public Food getFood(String nome) {
		List<Food> l=this.getVertici();
		for(Food f: l) {
			if(f.getDisplay_name().equals(nome))
				return f;
		}
		return null;
	}

}
