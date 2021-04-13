package jdbc;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class connectionDB {
   public static void main( String args[] ) {
      Connection c = null;
      Statement stmt = null;
      try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager
            .getConnection("jdbc:postgresql://localhost:5432/gnanaprakash",
            "postgres", "root");
         System.out.println("Opened database successfully");

         stmt = c.createStatement();
         String sql = "CREATE TABLE TRANSACTION2 " +
            "( accounts_id bigint not null, " +
        		 " foreign key(accounts_id) references accounts(id),"+
            " amount          bigint    NOT NULL, " +
            " date          text   NOT NULL, " +
            " To_id          bigint    NOT NULL, " +
            " From_id          bigint    NOT NULL, " +
            " type          text    NOT NULL, " +
            " status            text     NOT NULL) ";
           
         stmt.executeUpdate(sql);
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
      System.out.println("Table created successfully");
   }
}
