/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TablesData;


public class UserData {
Integer id ;
String Name , UserName,Password,type;

    public UserData(Integer id, String Name, String UserName, String Password, String type) {
        this.id = id;
        this.Name = Name;
        this.UserName = UserName;
        this.Password = Password;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getUserName() {
        return UserName;
    }

    public String getPassword() {
        return Password;
    }

    public String getType() {
        return type;
    }

}
