package org.example;

import javax.persistence.*;

@Entity //аннотация, которая указывает Hibernate, что наш класс специальный и его обьекты нужно хранить в DataBase
@Table(name = "Orders") //устанавливаем название таблице
public class Order { //класс Заказ
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Client_id_client")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "Product_id_product")
    private Product product;

    public Order() {}

    public Order(Client client, Product product) {
        this.client = client;
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "Client_ " + this.client.getName() +
                " bought the product_ " + this.product.getName() +
                "}";
    }
}
