package it.polito.tdp.food.model;

public class Food2 implements Comparable<Food2>{
	
	Food f;
	double calorie;
	public Food2(Food f, double calorie) {
		super();
		this.f = f;
		this.calorie = calorie;
	}
	public Food getF() {
		return f;
	}
	public void setF(Food f) {
		this.f = f;
	}
	public double getCalorie() {
		return calorie;
	}
	public void setCalorie(double calorie) {
		this.calorie = calorie;
	}
	@Override
	public String toString() {
		return f.getDisplay_name()+" "+calorie;
		}
	@Override
	public int compareTo(Food2 o) {
		if(Double.compare(calorie, o.calorie)==0)
				return this.f.getDisplay_name().compareTo(o.f.getDisplay_name());
		else
			return -Double.compare(calorie, o.calorie);
		
	}
	
	

}
