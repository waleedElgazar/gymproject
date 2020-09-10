/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GymProject;

import ControlDatabase.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ncm
 */
public class FXMLLoginController implements Initializable {
    
    @FXML
     TextField txt;
    @FXML
    PasswordField pass;
    @FXML
    Label la;
  private java.sql.Statement stat;
    
private void initializeDB() {
try {
Class.forName("com.mysql.jdbc.Driver");
java.sql.Connection con =
DriverManager.getConnection("jdbc:mysql://localhost/gym", "root", "");
stat = con.createStatement();
} catch (Exception ex) {
ex.printStackTrace();

}
}
    public void Login(ActionEvent event) throws IOException, SQLException {
        String username=txt.getText();
        String pass1=pass.getText();
        DataBase o=new DataBase();
        if(o.Login(username, pass1)){
            Node p=(Node) event.getSource();
           Stage p1=(Stage) p.getScene().getWindow();
           p1.close();
        Stage st=new Stage();
        Parent pr = FXMLLoader.load(getClass().getResource("FXMLHome.fxml"));
        Scene scene = new Scene(pr);
        st.setScene(scene);
        st.show();
        }else 
            la.setText("NO");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }    
    
}
