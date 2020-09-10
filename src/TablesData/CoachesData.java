package TablesData;
public class CoachesData {
Integer id;
String name ;
Integer phone;
Integer salary;

    public CoachesData(Integer id, String name, Integer phone, Integer salary) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPhone() {
        return phone;
    }

    public Integer getSalary() {
        return salary;
    }


}
