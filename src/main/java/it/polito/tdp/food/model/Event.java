package it.polito.tdp.food.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	
	Double t;
	Food f;
	public Event(double t, Food f) {
		super();
		this.t = t;
		this.f = f;
	}
	public Double getT() {
		return t;
	}
	public void setT(Double t) {
		this.t = t;
	}
	public Food getF() {
		return f;
	}
	public void setF(Food f) {
		this.f = f;
	}
	@Override
	public int compareTo(Event o) {
		return Double.compare(this.t, o.t);
	}
	
	

}
