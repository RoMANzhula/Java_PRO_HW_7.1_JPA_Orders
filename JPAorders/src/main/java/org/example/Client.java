package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity //аннотация, которая указывает Hibernate, что наш класс специальный и его обьекты нужно хранить в DataBase
@Table(name="Clients") //устанавливаем название таблице
public class Client {
    @Id //аннотация, с помощью которой задаем PrimaryKey
    @GeneratedValue(strategy = GenerationType.IDENTITY) //автогенерация номера ID
    @Column(name = "id_client") //устанавливаем имя для колонки таблицы
    private Long id; //поле - идинтификатор нового Клиента

    @Column(name = "name_client", nullable = false) //устанавливаем имя для колонки таблицы, поле не может быть пустым
    private String name; //поле - имя Клиента

    @Column(name = "age_client", nullable = false) //устанавливаем имя для колонки таблицы, поле не может быть пустым
    private Integer age; //поле - возраст Клиента

    @Column(name = "phone_client", unique = true) //устанавливаем имя для колонки таблицы, поле должно быть уникальным
    private Integer phone; //поле - телефон Клиента

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    public Client() {} //конструктор по умолчанию

    public Client(String name, int age, int phone) { //конструктор класса с инициализацией полей
        this.name = name;
        this.age = age;
        this.phone = phone;
    }

    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
            order.setClient(this);
        }
    }

    //Геттеры и Сеттеры
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

    @Override //переопределяем метод к Строковому виду
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
