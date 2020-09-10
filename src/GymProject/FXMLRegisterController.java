package GymProject;

import ControlDatabase.ConDataBase;
import ControlDatabase.DataBase;
import TablesData.CoachesData;
import TablesData.RegisterDate;
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

public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField Tid;
    @FXML
    private TextField Tname;
    @FXML
    private TextField Tphone;
    @FXML
    private TextField Tage;
    @FXML
    private TextField Tweight;
    @FXML
    private ComboBox Ttype;
    @FXML
    private ComboBox Tcoach;
    @FXML
    private ComboBox Ttime;
    @FXML
    private TextField Tprice;
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
    ConDataBase oC = new ConDataBase();
    Connection con;
    ObservableList<RegisterDate> data;
    int id;

   public void clear() throws SQLException {

        Tname.setText("");
        Tphone.setText("");
        Tweight.setText("");
        Tage.setText("");
        Tprice.setText("");
        fillComBox();
    }

    public void getSelect() {
        id = regTable.getSelectionModel().getSelectedIndex();
        if (id <= -1) {
            return;
        }
        Tid.setText(Cid.getCellData(id).toString());
        Tname.setText(Cname.getCellData(id));
        Tphone.setText(Cphoe.getCellData(id).toString());
        Tweight.setText(Cweight.getCellData(id).toString());
        Tage.setText(Cage.getCellData(id).toString());
        Tprice.setText(Cprice.getCellData(id).toString());
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
    public void udate() throws SQLException {
        int id = Integer.parseInt(Tid.getText());
        String name = Tname.getText();
        String phone = Tphone.getText();
        int weight = Integer.parseInt(Tweight.getText());
        int age = Integer.parseInt(Tage.getText());
        String time = Ttime.getSelectionModel().getSelectedItem().toString();
        int price = Integer.parseInt(Tprice.getText());
        String type = Ttype.getSelectionModel().getSelectedItem().toString();
        String coach = Tcoach.getSelectionModel().getSelectedItem().toString();

        String sql = "update trainer set ID = "
                + id + " ," + "Name = '" + name + "' ," + "Phone = '" + phone + "' ," + " Weight = " + weight
                + ", age = " + age + ", time = '" + time + "', price = " + price + ", type = '" + type + "' , coatches ='" + coach
                + "' where ID = " + id;
        con = oC.open();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        FillTable();
        con.close();
        clear();

    }

    public void delete() {
        try {
            int id = Integer.parseInt(Tid.getText());
            String sql = "delete from trainer where ID = " + id;
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
        int id = Integer.parseInt(Tid.getText());
        String name = Tname.getText();
        String phone = Tphone.getText();
        int weight = Integer.parseInt(Tweight.getText());
        int age = Integer.parseInt(Tage.getText());
        String time = Ttime.getSelectionModel().getSelectedItem().toString();
        int price = Integer.parseInt(Tprice.getText());
        String type = Ttype.getSelectionModel().getSelectedItem().toString();
        String coach = Tcoach.getSelectionModel().getSelectedItem().toString();
        String sql = "insert into trainer values (" + id + ", '" + name + "','" + phone + "', " + weight
                + ", " + age + ", '" + time + "', " + price + ", '" + type + "', '" + coach + "' )";
        con = oC.open();
        Statement stat = con.createStatement();
        stat.executeUpdate(sql);
        FillTable();
        autonumber();
        con.close();
        clear();
    }

    void fillComBox() throws SQLException {
        ObservableList<String> list1 = FXCollections.observableArrayList("Fitness", "Losing weight", "body builder");
        ObservableList<String> list2 = FXCollections.observableArrayList("Month", "day");
        ObservableList<String> list3 = FXCollections.observableArrayList();
        Ttype.setItems(list1);
        Ttime.setItems(list2);
        con = oC.open();
        Statement stat = con.createStatement();
        ResultSet res = stat.executeQuery("select name from coaches");
        while (res.next()) {
            list3.add(res.getString(1));
        }
        con.close();
        Tcoach.setItems(list3);

    }

    public void autonumber() {
        Integer x = new DataBase().getAutoNumber("trainer", "ID");
        Tid.setText(x.toString());
    }

    void FillTable() {
        autonumber();
        try {
            ResultSet res = new DataBase().fillTable("trainer");
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
        FillTable();
        try {
            fillComBox();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
