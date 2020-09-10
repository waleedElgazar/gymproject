package GymProject;

import ControlDatabase.ConDataBase;
import ControlDatabase.DataBase;
import TablesData.CoachesData;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import javafx.scene.control.TableColumn;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author ncm
 */
public class FXMLCoatchesController implements Initializable {

    @FXML
    private TableView<CoachesData> coachTable;
    @FXML
    TableColumn<CoachesData, Integer> ID;
    @FXML
    TableColumn<CoachesData, String> Name;
    @FXML
    TableColumn<CoachesData, Integer> Phone;
    @FXML
    TableColumn<CoachesData, Integer> Salary;
    @FXML
    TextField TID;
    @FXML
    TextField Tname;
    @FXML
    TextField TPhone;
    @FXML
    TextField TSalary;
    DataBase o;
    @FXML
    Button Add;
    @FXML
    TextField search;
    Connection con;
    ConDataBase oC = new ConDataBase();
    int id;

    public void clear() {
        Tname.setText("");
        TPhone.setText("");
        TSalary.setText("");
    }

    public void getSelect() {
        id = coachTable.getSelectionModel().getSelectedIndex();
        if (id <= -1) {
            return;
        }
        TID.setText(ID.getCellData(id).toString());
        Tname.setText(Name.getCellData(id));
        TPhone.setText(Phone.getCellData(id).toString());
        TSalary.setText(Salary.getCellData(id).toString());
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
                ObservableList<CoachesData> items = FXCollections.observableArrayList();
                ObservableList<TableColumn<CoachesData, ?>> column = coachTable.getColumns();
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
                coachTable.setItems(items);
            }
        });
    }

    public void updata(ActionEvent e) throws SQLException {
        int id = Integer.parseInt(TID.getText());
        String name = Tname.getText();
        int phone = Integer.parseInt(TPhone.getText());
        int salary = Integer.parseInt(TSalary.getText());
        String sql = "update coaches set ID = "
                + id + " ," + "Name = '" + name + "' ," + "Phone = " + phone + " ," + " Salary = " + salary + " where ID = " + id;
        con = oC.open();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        FillTable();
        con.close();
        clear();
    }

    public void delete() {
        try {
            int id = Integer.parseInt(TID.getText());
            String sql = "delete from coaches where ID = " + id;
            con = oC.open();
            Statement stat = con.createStatement();
            stat.executeUpdate(sql);
            FillTable();
            con.close();
            clear();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCoatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public void insert(ActionEvent e) throws SQLException {
        int idx = Integer.parseInt(TID.getText());
        String name = Tname.getText();
        int phone = Integer.parseInt(TPhone.getText());
        int salary = Integer.parseInt(TSalary.getText());
        String sql = "insert into coaches values (" + idx + ",'" + name + "'," + phone + "," + salary + ")";
        con = oC.open();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        FillTable();
        con.close();
        clear();
    }
    ObservableList<CoachesData> data;

    void FillTable() {
           autonumber();
        try {
            ResultSet res = new DataBase().fillTable("coaches");
            data = FXCollections.observableArrayList();
            while (res.next()) {
                data.add(new CoachesData(res.getInt(1), res.getString(2), res.getInt(3), res.getInt(4)));
            }
            coachTable.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLCoatchesController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

      public void autonumber(){
        Integer x = new DataBase().getAutoNumber("coaches", "id");
         TID.setText(x.toString());
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ID.setCellValueFactory(new PropertyValueFactory<CoachesData, Integer>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<CoachesData, String>("name"));
        Phone.setCellValueFactory(new PropertyValueFactory<CoachesData, Integer>("phone"));
        Salary.setCellValueFactory(new PropertyValueFactory<CoachesData, Integer>("salary"));
        FillTable();
     
    }

    @FXML
    private void show(ActionEvent event) {
    }

}
