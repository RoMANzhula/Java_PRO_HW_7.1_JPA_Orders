package org.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity // анотація, яка вказує Hibernate, що наш клас спеціальний та його об'єкти потрібно зберігати у DataBase
@Table(name = "Products") // встановлюємо таблицю назву
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product")
    private long id;

    @Column(name = "name_product", nullable = false)
    private String name;

    @Column(name = "brand_product")
    private String brand;

    @Column(name = "price_product", nullable = false)
    private Double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<Order> orders = new ArrayList<>();

    public Product() {}

    public Product(String name, String brand, Double price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
    }

    public void addOrder(Order order) {
        if (!orders.contains(order)) {
            orders.add(order);
            order.setProduct(this);
        }
    }

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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                '}';
    }
}
