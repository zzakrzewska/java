package zad1;

import java.sql.*;
import java.util.ArrayList;

public class Database {

	private final TravelData travelData;
	private final String url;
	
	public Database(String url, TravelData travelData) {
		this.travelData = travelData;
		this.url = url;
	}
	
	public void create() {
		
		try {
			Connection connection = DriverManager.getConnection(url);
			
			if(connection != null)
				connection.getMetaData();
			
			assert connection != null;
			Statement statement = connection.createStatement();
			statement.execute("CREATE TABLE IF NOT EXISTS travelData (data TEXT NOT NULL);");
			
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO travelData (data) VALUES (?)");
			
			for(Travel travel : travelData.getTravels()) {
				preparedStatement.setString(1, travel.toString());
				preparedStatement.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void showGui() {
		String[] languages = {"pl", "en"};
		new GUI(travelData, languages);
	}
	
}

