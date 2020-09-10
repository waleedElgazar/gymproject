
package ControlDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConDataBase {
    Connection con;
   String url="jdbc:mysql://localhost/gym";
   public Connection open(){ 
       try{     
       Class.forName("com.mysql.jdbc.Driver");
              con = DriverManager.getConnection(url, "root", "");        
         return con;  
       } catch (Exception ex) {
              ex.printStackTrace();
          return con;
          }
}
 public void close() throws SQLException{
 con.close();
 }
}
