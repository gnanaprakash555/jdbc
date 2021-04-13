package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class SqlDataAccess {
	static String name;
	static int age;
	static String dob;
	static long aadhar;
	static long  phone;
	static String email;
	static String ifsc;
	static String a_ifsc;
	static String pass;
	static long amount=0;
	static public void display(int a_id) throws Exception
	{
		Connection c = null;
	      Statement stmt = null;
		 Class.forName("org.postgresql.Driver");
       c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
       c.setAutoCommit(false);
       stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery( "SELECT * FROM accounts;" );
        while ( rs.next() ) {
        	 int id = rs.getInt("id");
       	  if(id==a_id) {
       		 name  = rs.getString("name");
       		 age  = rs.getInt("age");
       		 dob  = rs.getString("dob");
       		 aadhar  = rs.getLong("aadhar_no");
       		 phone  = rs.getLong("phone_no");
       		 email = rs.getString("email");
       		 ifsc  = rs.getString("ifsc");
       	     amount= rs.getInt("amount");
       	  System.out.println( "NAME= " + name);
       	  System.out.println( "AGE = " + age );
       	  System.out.println( "DOB = " + dob );
       	  System.out.println( "AADHAR_NO = " + aadhar);
       	  System.out.println( "PHONE_NO= " + phone );
       	  System.out.println( "EMAIL= " + email);
       	  System.out.println( "IFSC = " + ifsc );
       	  System.out.println( "AMOUNT = " + amount );
       	  break;
       	  }
        	
        }
		
        stmt.close();
        c.commit();
        c.close();
		
		
	}
	static public void createAccount(int id1)throws Exception
	{
		Scanner sc=new Scanner(System.in);
		Connection c = null;
	      Statement stmt = null;
		 Class.forName("org.postgresql.Driver");
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
        c.setAutoCommit(false);
         stmt = c.createStatement();
        
        System.out.print("ENTER YOUR NAME");
       String name1=sc.nextLine();
        System.out.print("ENTER YOUR AGE");
        age=sc.nextInt();
        System.out.println("ENTER YOUR DATE OF BIRTH");
        dob=sc.next();
        System.out.println("ENTER YOUR AADHAR_NO");
        aadhar=sc.nextLong();
        System.out.println("ENTER YOUR PHONE_NO");
        phone=sc.nextLong();
        System.out.println("ENTER YOUR EMAIL_ID");
        email=sc.next();
        System.out.println("ENTER YOUR IFSC_CODE");
        ifsc=sc.next();
        System.out.println("ENTER YOUR PASSWORD FOR YOUR ACCOUNT");
        pass=sc.next();
        String sql = "INSERT INTO accounts(id,name,age,dob,aadhar_no,phone_no,email,ifsc,password,amount)VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pt=c.prepareStatement(sql);
        pt.setInt(1,id1);
        pt.setString(2,name1);
        pt.setInt(3,age);
        pt.setString(4,dob);
        pt.setLong(5,aadhar);
        pt.setLong(6,phone);
        pt.setString(7,email);
        pt.setString(8,ifsc);
        pt.setString(9,pass);
        pt.setLong(10,0);
         pt.executeUpdate();
         stmt.close();
         c.commit();
         c.close();
         System.out.println("ACCOUNT CREATED SUCCESSFULLY");
		
	}
	static public void deposit(int a_id,int amt)throws Exception{
		 
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String m=String.valueOf(formatter.format(date)); 
		Connection c = null;
	
		Statement stmt = null;
		try {
		 Class.forName("org.postgresql.Driver");
     c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
     c.setAutoCommit(false);
     stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM accounts;" );
     
      while ( rs.next() ) {
      	 int id = rs.getInt("id");
      	 if(a_id==id)
      	 {
      		  amount= rs.getInt("amount")+amt; 
      		
      		PreparedStatement s=c.prepareStatement("update accounts set amount=? where id=?");  
      	 
            s.setLong(1,amount);
            s.setInt(2,a_id);  
            s.executeUpdate();
            System.out.println("deposited successfully");
           String sql = "INSERT INTO transaction1(accounts_id,amount,type,date)VALUES (?,?,?,?)";
            
	        PreparedStatement pt=c.prepareStatement(sql);
      		  pt.setInt(1,a_id);
      		  pt.setLong(2,amt);
      		  pt.setString(3,"deposit");
      		  pt.setString(4,m);
      		 s.executeUpdate();
      		 pt.executeUpdate();
      		 s.close();
      		 pt.close();
      		 
      	   break;
      	 }
      	 
      }
      
      rs.close();
      stmt.close();
	      c.commit();
	      c.close();
		 } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
      System.out.println("DEPOSITED SUCCESSFULLY");
	}
	static public void withdraw(int a_id,int amt)throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String m=String.valueOf(formatter.format(date)); 
		Connection c = null;
	      Statement stmt = null;
		 Class.forName("org.postgresql.Driver");
     c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
     c.setAutoCommit(false);
     stmt = c.createStatement();
      ResultSet rs = stmt.executeQuery( "SELECT * FROM accounts;" );
      while ( rs.next() ) {
      	 int id = rs.getInt("id");
      	 if(a_id==id)
      	 {
      		  amount= rs.getInt("amount")-amt; 
      		PreparedStatement s=c.prepareStatement("update accounts set amount=? where id=?");  
            s.setLong(1,amount);
            s.setInt(2,a_id);  
            s.executeUpdate();
            String sql = "INSERT INTO transaction1(accounts_id,amount,type,date)VALUES (?,?,?,?)";
	        PreparedStatement pt=c.prepareStatement(sql);
      		  pt.setInt(1,a_id);
      		  pt.setLong(2,amt);
      		  pt.setString(3,"withdraw");
      		  pt.setString(4,m);
      		pt.executeUpdate();
      	 }
      	 
      }
      rs.close();
      stmt.close();
      c.commit();
      c.close();
      System.out.println("AMOUNT WITHDRAWN  SUCCESSFULLY");
      
	}
	static public void deleteAccount(int id) throws Exception{
		Connection c = null;
	      Statement stmt = null;
		 Class.forName("org.postgresql.Driver");
   c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
   c.setAutoCommit(false);
   stmt = c.createStatement();
   PreparedStatement s=c.prepareStatement("delete from transaction2 where accounts_id = ?");  
   s.setLong(1,id);
   PreparedStatement s1=c.prepareStatement("delete from accounts where id = ?");  
   s1.setLong(1,id);
   PreparedStatement s2=c.prepareStatement("delete from transaction1 where accounts_id = ?");  
   s2.setLong(1,id);
   s.executeUpdate();
   s2.executeUpdate();
   s1.executeUpdate();
   c.commit();
   stmt.close();
   c.close();	
   System.out.println("ACCOUNT DELETED SUCCESSFULLY");
	}
	static public void transferAccount(int id1,int id2,int amt) throws Exception{
		Scanner sc=new Scanner(System.in);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		String m=String.valueOf(formatter.format(date)); 
		  Connection c = null;
	      Statement stmt = null;
		  Class.forName("org.postgresql.Driver");
     c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
     c.setAutoCommit(false);
     stmt = c.createStatement();
     ResultSet rs = stmt.executeQuery( "SELECT * FROM accounts;" );
      while ( rs.next() ) {
    	  int id = rs.getInt("id");
    	  if(id==id2) {
    		  ifsc  = rs.getString("ifsc");
    		  break;
    	  }
      }
      System.out.println(ifsc);
      do {
    	  System.out.println("ENTER IFSC FOR " +id2);
    	   a_ifsc=sc.next();
      if(a_ifsc.equals(ifsc))
      {
    	  
    	  ResultSet rs1 = stmt.executeQuery( "SELECT * FROM accounts;" );
      while ( rs1.next() ) {
      	 int id = rs1.getInt("id");
      	
      	 if(id==id1||id==id2)
      	 {
      		 if(id==id1) {
      			 amount=rs1.getLong("amount")-amt;
      			PreparedStatement s=c.prepareStatement("update accounts set amount=? where id=?");  
                s.setLong(1,amount);
                s.setInt(2,id1);  
                s.executeUpdate();
                String sql = "INSERT INTO transaction2(accounts_id,amount,date,to_id,from_id,type,status)VALUES (?,?,?,?,?,?,?)";
                PreparedStatement pt=c.prepareStatement(sql);
                pt.setInt(1,id1);
        		  pt.setLong(2,amt);
        		  pt.setString(3,m);
        		  pt.setInt(4,id2);
        		  pt.setInt(5,id1);
        		  pt.setString(6,"tranfer");
        		  pt.setString(7,"debited");
        		pt.executeUpdate();
      		 }
      		 else
      		 {
      			 amount=rs1.getLong("amount")+amt;
       			PreparedStatement s=c.prepareStatement("update accounts set amount=? where id=?");  
                 s.setLong(1,amount);
                 s.setInt(2,id2);  
                 s.executeUpdate();
                 String sql = "INSERT INTO transaction2(accounts_id,amount,date,to_id,from_id,type,status)VALUES (?,?,?,?,?,?,?)";
                 PreparedStatement pt=c.prepareStatement(sql);
                 pt.setInt(1,id2);
         		  pt.setLong(2,amt);
         		  pt.setString(3,m);
         		  pt.setInt(4,id2);
         		  pt.setInt(5,id1);
         		  pt.setString(6,"tranfer");
         		  pt.setString(7,"credited");
         		pt.executeUpdate();
      		 }
      		
      	 }
      	
      }
      	 
      }
      else
      {
    	  System.out.println("INCORRECT PASSWORD");
      }
      }
      while(a_ifsc.equals(ifsc)==false);
     
      rs.close();
      stmt.close();
      c.commit();
      c.close();
      System.out.println("AMOUNT TRANSFERED SUCCESSFULLY");
	}
	static void menu() {
		System.out.println("PRESS 1 FOR EDITING NAME");
		System.out.println("PRESS 2 FOR EDITING AGE");
		System.out.println("PRESS 3 FOR EDITING DOB");
		System.out.println("PRESS 4 FOR EDITING AADHAR_NO");
		System.out.println("PRESS 5 FOR EDITING PHONE_NO");
		System.out.println("PRESS 6 FOR EDITING EMAIL_ID");
		System.out.println("PRESS 7 FOR EDITING IFSC_CODE");
		System.out.println("PRESS 0 FOR Exit");

		
	}
	static public void editAccount(int a_id) throws Exception{
		Scanner sc=new Scanner(System.in);
		  Connection c = null;
	      Statement stmt = null;
		  Class.forName("org.postgresql.Driver");
     c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gnanaprakash", "postgres", "root");  
     c.setAutoCommit(false);
     stmt = c.createStatement();
		int opt;
		do {
			menu();
			 opt=sc.nextInt();
			 
			 switch(opt)
			 {
			 case 1:
				 PreparedStatement s=c.prepareStatement("update accounts set name=? where id=?");
				 System.out.println("ENTER YOUR NAME");
				 name=sc.next();
				 System.out.print(c);
	                s.setString(1,name);
	                s.setInt(2,a_id);  
	                s.executeUpdate();
	                stmt.close();
	                c.commit();
	                System.out.println("YOUR NAME UPDATED SUCCESSFULLY");
		            
	                break;
			case 2:
				 PreparedStatement s1=c.prepareStatement("update accounts set age=? where id=?");
				 System.out.println("ENTER YOUR AGE");
				 age=sc.nextInt();
	                s1.setInt(1,age);
	                s1.setInt(2,a_id);  
	                s1.executeUpdate();
	                c.commit();
	               stmt.close();
	               System.out.println("YOUR AGE UPDATED SUCCESSFULLY");
	               break;
			 case 3:
				 PreparedStatement s2=c.prepareStatement("update accounts set dob=? where id=?");
				 System.out.println("ENTER YOUR DATE OF BIRTH");
				 dob=sc.next();
	                s2.setString(1,dob);
	                s2.setInt(2,a_id);  
	                s2.executeUpdate();
	                c.commit();
		               stmt.close();
		               System.out.println("YOUR DOB UPDATED SUCCESSFULLY");
	                break;
			 case 4:
				 PreparedStatement s3=c.prepareStatement("update accounts set aadhar_no=? where id=?");
				 System.out.println("ENTER YOUR AADHAR_NO");
				 aadhar=sc.nextLong();
	                s3.setLong(1,aadhar);
	                s3.setInt(2,a_id);  
	                s3.executeUpdate();
	                stmt.close();
	                c.commit();
	                System.out.println("YOUR AADHAR_NO UPDATED SUCCESSFULLY");
	               
	                break;
			 case 5:
				 PreparedStatement s4=c.prepareStatement("update accounts set phone_no=? where id=?");
				 System.out.println("ENTER YOUR PHONE_NO");
				 phone=sc.nextLong();
	                s4.setLong(1,phone);
	                s4.setInt(2,a_id);  
	                s4.executeUpdate();
	                stmt.close();
	                c.commit();
	                System.out.println("YOUR PHONE_NO UPDATED SUCCESSFULLY");
	                break;
			 case 6:
				 PreparedStatement s5=c.prepareStatement("update accounts set email_id=? where id=?");
				 System.out.println("ENTER YOUR EMAIL_ID");
				 email=sc.next();
	                s5.setString(1,email);
	                s5.setInt(2,a_id);  
	                s5.executeUpdate();
	                stmt.close();
	                c.commit();
	                break;
			 case 7:
				 PreparedStatement s6=c.prepareStatement("update accounts set ifsc=? where id=?");
				 System.out.println("ENTER YOUR IFSC_CODE");
				 ifsc=sc.next();
	                s6.setString(1,ifsc);
	                s6.setInt(2,a_id);  
	                s6.executeUpdate();
	                stmt.close();
	                c.commit();
	                System.out.println("YOUR IFSC_CODE UPDATED SUCCESSFULLY");
	                break;
			 case 8:
				 PreparedStatement s7=c.prepareStatement("update accounts set password=? where id=?");
				 System.out.println("ENTER YOUR PASSWORD");
				 pass=sc.next();
	                s7.setString(1,pass);
	                s7.setInt(2,a_id);  
	                s7.executeUpdate();
	                stmt.close();
	                c.commit();
	                System.out.println("YOUR PASSWORD UPDATED SUCCESSFULLY");
	                break;
			 case 0:
				 System.out.println("THANK YOU");
				 c.close();
				 break;
			 }
		}while(opt!=0);
		
        
       
	}
	public static void main(String[] args) throws Exception{
		
	deposit(3,10);
	}

}
