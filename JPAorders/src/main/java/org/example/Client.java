package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // Annotation indicating that this class is an entity and its objects should be stored in the database
@Table(name="Clients") // Sets the name of the table
public class Client {
    @Id // Annotation marking this field as the Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generates the ID
    @Column(name = "id_client") // Sets the column name in the table
    private Long id; // Field for the unique identifier of the Client

    @Column(name = "name_client", nullable = false) // Sets the column name and makes the field non-nullable
    private String name; // Field for the Client's name

    @Column(name = "age_client", nullable = false) // Sets the column name and makes the field non-nullable
    private Integer age; // Field for the Client's age

    @Column(name = "phone_client", unique = true) // Sets the column name and enforces uniqueness
    private Integer phone; // Field for the Client's phone number

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>(); // A list of orders associated with the client

    public Client() {} // Default constructor

    public Client(String name, int age, int phone) { // Constructor to initialize fields
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public void addOrder(Order order) { // Adds an order to the list if not already present
        if (!orders.contains(order)) {
            orders.add(order);
            order.setClient(this); // Sets the client reference in the order
        }
    }

    //Гетери/Сетери
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override // перевизначаємо метод до Строкового виду
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
