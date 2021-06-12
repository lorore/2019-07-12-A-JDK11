package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {

	
	Graph<Food, DefaultWeightedEdge> graph;
	int k;
	Food partenza;
	List<Food2> listaIniziale;
	PriorityQueue<Event> queue;
	List<Food> cibiCucinati;
	double minutiTot;
	
	public void setSimulator(Graph<Food, DefaultWeightedEdge> graph, int k, Food partenza) {
		this.graph=graph;
		this.k=k;
		this.partenza=partenza;	
	}
	
	public void init() {
		queue=new PriorityQueue<>();
		cibiCucinati=new ArrayList<>();
		this.listaIniziale=this.getTopK(partenza);

		if(k>listaIniziale.size())
			this.k=listaIniziale.size();
		
		
		for(int i=0; i<this.k; i++) {
			Food2 vicino=this.listaIniziale.get(i);
			Event e=new Event(vicino.getCalorie(), vicino.getF());
			queue.add(e);
			cibiCucinati.add(vicino.getF());

		}
	
	}
	
	
	public void sim() {
		Event e;
		while((e = this.queue.poll()) != null) {
			Food f=e.getF();
			this.minutiTot=e.getT();
			Food2 migliorVicino=this.getTop(f);
			if(migliorVicino!=null && !cibiCucinati.contains(migliorVicino.getF())) {
				queue.add(new Event(e.getT()+migliorVicino.getCalorie(), migliorVicino.getF()));
				cibiCucinati.add(migliorVicino.getF());
			}
			
		}
		
		System.out.println("Minuti tot: "+this.minutiTot+ " num cibi cucinati: "+this.cibiCucinati.size());
	}
	
	
	private List<Food2> getTopK(Food f){
		List<Food> vicini=Graphs.neighborListOf(graph, f);
		List<Food2> result=new ArrayList<>();
		for(Food c: vicini) {
			double peso=graph.getEdgeWeight(graph.getEdge(c, f));
			result.add(new Food2(c, peso));
		}
		Collections.sort(result);
		return result;
	}
	
	private Food2 getTop(Food f){
		List<Food> vicini=Graphs.neighborListOf(graph, f);
		List<Food2> result=new ArrayList<>();
		for(Food c: vicini) {
			double peso=graph.getEdgeWeight(graph.getEdge(c, f));
			result.add(new Food2(c, peso));
		}
		Collections.sort(result);
		if(result.isEmpty())
			return null;
		return result.get(0);
	}
	
}
