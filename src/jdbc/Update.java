package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Update {

public static void main(String[] args)throws Exception{
	Connection c = null;
    Statement stmt = null;
    c.clearWarnings();
	 Class.forName("org.postgresql.Driver");
 c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "root");  

 System.out.print(c);
 
}
}