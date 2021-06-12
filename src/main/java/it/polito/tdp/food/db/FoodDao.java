package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public void getVertici(Map<Integer, Food> idMap, int p) {
		String sql="SELECT p.food_code AS v, f.display_name AS nome "
				+ "FROM `portion` p, food f "
				+ "WHERE p.food_code=f.food_code "
				+ "GROUP BY p.food_code, f.display_name "
				+ "HAVING COUNT(p.portion_id)=? "
				+ "ORDER BY nome ";
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, p);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Food f=new Food(res.getInt("v"), res.getString("nome"));
				if(!idMap.containsKey(f.getFood_code())) {
					idMap.put(f.getFood_code(), f);
				}
			
			}
			
			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Adiacenza> getArchi(Map<Integer, Food> idMap, int p){
		String sql="SELECT fc1.food_code AS f1, fc2.food_code AS f2, AVG(c.condiment_calories) AS peso "
				+ "FROM food_condiment fc1, food_condiment fc2, condiment c "
				+ "WHERE fc1.food_code<>fc2.food_code AND fc1.id<>fc2.id AND fc1.condiment_code=fc2.condiment_code "
				+ "AND fc1.condiment_code=c.condiment_code "
				+ "AND fc1.food_code IN ( "
				+ "SELECT p.food_code "
				+ "FROM `portion` p "
				+ "GROUP BY p.food_code "
				+ "HAVING COUNT(p.portion_id)=?) "
				+ "AND fc2.food_code IN ( "
				+ "SELECT p.food_code "
				+ "FROM `portion` p "
				+ "GROUP BY p.food_code "
				+ "HAVING COUNT(p.portion_id)=?) "
				+ "GROUP BY fc1.food_code, fc2.food_code "
				+ "HAVING COUNT(DISTINCT fc1.condiment_code)>0 "
				+ "ORDER BY f1 ";
		
		List<Adiacenza> result=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, p);
			st.setInt(2, p);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(idMap.containsKey(res.getInt("f1")) && idMap.containsKey(res.getInt("f2"))) {
					Food f1=idMap.get(res.getInt("f1"));
					Food f2=idMap.get(res.getInt("f2"));
					double peso=res.getDouble("peso");
					result.add(new Adiacenza(f1, f2, peso));

				}
			
			}
			
			conn.close();
			return result;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		
	}
}
