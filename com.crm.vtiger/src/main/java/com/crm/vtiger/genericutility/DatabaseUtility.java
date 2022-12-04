package com.crm.vtiger.genericutility;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.cj.jdbc.Driver;
public class DatabaseUtility {
	public Connection connection;
	public ResultSet result;
public void createConnectionToDB() {
	try {
		Driver driverRef=new Driver();
		DriverManager.registerDriver(driverRef);
	connection=DriverManager.getConnection(IPathConstants.MYSQL_URL,IPathConstants.MYSQL_USERNAME,IPathConstants.MYSQL_PASSWORD);
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
}
public ResultSet getAllData(String query){
	try {
		result = connection.createStatement().executeQuery(query);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return result;
}
public boolean insertData(String query){
	boolean flag=false;
	int result = 0;
	try {
		result = connection.createStatement().executeUpdate(query);
	} catch (SQLException e) {
		e.printStackTrace();
	}
	if(result==1) {
		System.out.println("data inserted successfully");
		flag=true;
		return flag;
	}
	else {
		System.out.println("data has not inserted.");
	}
	return flag;
}
public void closeConnection(){
	try {
		connection.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
}
}
