package it.polito.tdp.food.model;

public class TestModel {

	public static void main(String[] args) {
Model m=new Model();
m.creaGrafo(1);
Food f=m.getFood("English muffin");
m.simula(5, f);
	}

}
