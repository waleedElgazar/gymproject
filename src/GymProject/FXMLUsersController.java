/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GymProject;

import ControlDatabase.ConDataBase;
import ControlDatabase.DataBase;
import TablesData.CoachesData;
import TablesData.UserData;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ncm
 */
public class FXMLUsersController implements Initializable {
    @FXML
    private TextField Tid;
    @FXML
    private TextField Tname;
    @FXML
    private TextField Tuser;
    @FXML
    private TextField Tpass;
    @FXML
    private TextField Ttype;

    @FXML
    private TextField search;
    @FXML
    private TableView<UserData> userTable;
    @FXML
    private TableColumn<UserData, Integer> Cid;
    @FXML
    private TableColumn<UserData, String> Cname;
    @FXML
    private TableColumn<UserData, String> Cuser;
    @FXML
    private TableColumn<UserData, String> Cpass;
    @FXML
    private TableColumn<UserData, String> Ctype;
  ObservableList<UserData> data;
    Connection con;
    ConDataBase oC = new ConDataBase();
     public void clear() {
        Tname.setText("");
        Tuser.setText("");
        Tpass.setText("");
        Ttype.setText("");
     }
     public void Search() {
        search.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (search.textProperty().get().isEmpty()) {
                    FillTable();
                    return;
                }
                FillTable();
                ObservableList<UserData> items = FXCollections.observableArrayList();
                ObservableList<TableColumn<UserData, ?>> column = userTable.getColumns();
                for (int row = 0; row < data.size(); ++row) {
                    for (int col = 0; col < column.size(); ++col) {
                        TableColumn colData = column.get(col);
                        String value = colData.getCellData(data.get(row)).toString();
                        value = value.toLowerCase();
                        if (value.contains(search.getText().toLowerCase()) && value.startsWith(search.getText().toLowerCase())) {
                            items.add(data.get(row));
                            break;
                        }
                    }
                }
                userTable.setItems(items);
            }
        });
    }
    public void updata(ActionEvent e) throws SQLException {
        int id = Integer.parseInt(Tid.getText());
        String name = Tname.getText();
        String user = Tuser.getText();
        String pass = Tpass.getText();
        String type = Ttype.getText();
        String sql = "update usertable set ID = "
                + id + " ," + "Name = '" + name + "' ," + "UserName = '" +user + "' ," + " Password = '" + pass +
                "' , Type = '"+type+"' where ID = " + id;
        con = oC.open();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        FillTable();
        con.close();
      //  clear();
    }
    
    public void delete() {
        try {
            int id = Integer.parseInt(Tid.getText());
            String sql = "delete from usertable where ID = " + id;
            con = oC.open();
            Statement stat = con.createStatement();
            stat.executeUpdate(sql);
            FillTable();
            con.close();
           // clear();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCoatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public void insert() throws SQLException {
        int idx = Integer.parseInt(Tid.getText());
        String name = Tname.getText();
        String user  = Tuser.getText();
        String pass  = Tpass.getText();
        String type = Ttype.getText();
        String sql ="insert into usertable values ("+idx+", '"+name +"', '"+user+"', '" + pass+"', '"+type +"')";
        con=oC.open();
        Statement stat=con.createStatement();
        stat.executeUpdate(sql);
        FillTable();
        con.close();
       // clear();
    }
  int id; 
  public void getSelect() {
        id = userTable.getSelectionModel().getSelectedIndex();
        if (id <= -1) {
            return;
        }
        Tid.setText(Cid.getCellData(id).toString());
        Tname.setText(Cname.getCellData(id));
        Tuser.setText(Cuser.getCellData(id));
        Tpass.setText(Cpass.getCellData(id).toString());
        Ttype.setText(Ctype.getCellData(id).toString());
    }
    void FillTable() {
           autonumber();
  
        try {
            ResultSet res = new DataBase().fillTable("usertable");
            data = FXCollections.observableArrayList();
            while (res.next()) {
                data.add(new UserData(res.getInt(1), res.getString(2), res.getString(3),res.getString(4),res.getString(5)));
            }
            userTable.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCoatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      public void autonumber(){
        Integer x = new DataBase().getAutoNumber("usertable", "id");
         Tid.setText(x.toString());
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     Cid.setCellValueFactory(new PropertyValueFactory<UserData, Integer>("id"));
        Cname.setCellValueFactory(new PropertyValueFactory<UserData, String>("Name"));
        Cuser.setCellValueFactory(new PropertyValueFactory<UserData, String>("UserName"));
       Cpass.setCellValueFactory(new PropertyValueFactory<UserData, String>("Password"));
        Ctype.setCellValueFactory(new PropertyValueFactory<UserData, String>("type"));
        FillTable();
    }    
    
}
