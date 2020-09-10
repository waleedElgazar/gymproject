package TablesData;

public class RegisterDate {
   Integer id;
   String name,phone;
   Integer weight,age;
   String time;
   Integer price;
   String type,coach;

    public RegisterDate(Integer id, String name, String phone, Integer weight, Integer age, String time, Integer price, String type, String coach) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.weight = weight;
        this.age = age;
        this.time = time;
        this.price = price;
        this.type = type;
        this.coach = coach;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getAge() {
        return age;
    }

    public String getTime() {
        return time;
    }

    public Integer getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getCoach() {
        return coach;
    }
   
}
