package GymProject;

import ControlDatabase.DataBase;
import TablesData.RegisterDate;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLTrainerDataController implements Initializable {

    @FXML
    private TableView<RegisterDate> regTable;
    @FXML
    private TableColumn<RegisterDate, Integer> Cid;
    @FXML
    private TableColumn<RegisterDate, String> Cname;
    @FXML
    private TableColumn<RegisterDate, String> Cphoe;
    @FXML
    private TableColumn<RegisterDate, Integer> Cweight;
    @FXML
    private TableColumn<RegisterDate, Integer> Cage;
    @FXML
    private TableColumn<RegisterDate, String> Ctime;
    @FXML
    private TableColumn<RegisterDate, Integer> Cprice;
    @FXML
    private TableColumn<RegisterDate, String> Ctype;
    @FXML
    private TableColumn<RegisterDate, String> Ccoach;
    @FXML
    private TextField search;
    ObservableList<RegisterDate> data;
     String s="";
    public void FillFitness() {
        FillTable("type = 'Fitness'");
        s="Fitness";
    }

    public void FillBody() {
        FillTable("type = 'body builder'");
          String s="body";
    }

    public void FillWeight() {
        FillTable("type = 'Losing weight'");
        s="Losing";
    }

    public void FillAll() {
        FillTable("1");
    }
  
    public void Search() {
        search.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (search.textProperty().get().isEmpty()) {     
                if(s.equals("Fitness"))
                    FillFitness();
                else if (s.equals("body"))
                    FillBody();
                else if(s.equals("Losing"))
                    FillWeight();
                else
                    FillAll();
                    return;
                }
                ObservableList<RegisterDate> items = FXCollections.observableArrayList();
                ObservableList<TableColumn<RegisterDate, ?>> column = regTable.getColumns();
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
                regTable.setItems(items);
            }
        });
    }
    public void FillTable(String ColName) {
        try {
            ResultSet res = new DataBase().fillSomeCTable("trainer", ColName);
            data = FXCollections.observableArrayList();
            while (res.next()) {
                data.add(new RegisterDate(res.getInt(1), res.getString(2), res.getString(3), res.getInt(4), res.getInt(5), res.getString(6), res.getInt(7), res.getString(8), res.getString(9)));
            }
            regTable.setItems(data);
        } catch (SQLException ex) {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Cid.setCellValueFactory(new PropertyValueFactory<RegisterDate, Integer>("id"));
        Cname.setCellValueFactory(new PropertyValueFactory<RegisterDate, String>("name"));
        Cphoe.setCellValueFactory(new PropertyValueFactory<RegisterDate, String>("phone"));
        Cweight.setCellValueFactory(new PropertyValueFactory<RegisterDate, Integer>("weight"));
        Cage.setCellValueFactory(new PropertyValueFactory<RegisterDate, Integer>("age"));
        Ctime.setCellValueFactory(new PropertyValueFactory<RegisterDate, String>("time"));
        Cprice.setCellValueFactory(new PropertyValueFactory<RegisterDate, Integer>("price"));
        Ctype.setCellValueFactory(new PropertyValueFactory<RegisterDate, String>("type"));
        Ccoach.setCellValueFactory(new PropertyValueFactory<RegisterDate, String>("coach"));
        FillTable("1");
    }
}