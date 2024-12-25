package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.Scanner;

public class Application {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("JPATest2");
            em = emf.createEntityManager();
            try {
                while (true) {
                    System.out.println("1: add client");
                    System.out.println("2: delete client");
                    System.out.println("3: change client");
                    System.out.println("4: view clients");
                    System.out.println("5: add product");
                    System.out.println("6: delete product");
                    System.out.println("7: change product");
                    System.out.println("8: view product");
                    System.out.println("9: create order");
                    System.out.println("10: view orders");

                    System.out.print("-> ");

                    String s = sc.nextLine(); //читаємо з консолв відповідь від користувача
                    switch (s) { //ловимо відповіді від користувача
                        case "1":
                            addClient(sc);
                            break;
                        case "2":
                            deleteClient(sc);
                            break;
                        case "3":
                            changeClient(sc);
                            break;
                        case "4":
                            viewClients();
                            break;
                        case "5":
                            addProduct(sc);
                            break;
                        case "6":
                            deleteProduct(sc);
                            break;
                        case "7":
                            changeProduct(sc);
                            break;
                        case "8":
                            viewProducts();
                            break;
                        case "9":
                            createOrder(sc);
                            break;
                        case "10":
                            viewOrders();
                            break;

                        default:
                            return;
                    }
                }
            } finally { //обов'язковий блок для виконання
                sc.close(); //зачиняємо потік вводу даних з консолі від користувача
                em.close(); //зачиняємо з'єднання з DataBase
                emf.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    private static void addClient(Scanner sc) { //метод для додавання Client в таблицю
        System.out.print("Enter client name: ");
        String name = sc.nextLine();

        System.out.print("Enter client age: ");
        String strAge = sc.nextLine();
        int age = Integer.parseInt(strAge);

        System.out.print("Enter client phone: ");
        String strPhone = sc.nextLine();
        int phone = Integer.parseInt(strPhone);

        em.getTransaction().begin();
        try {
            Client client = new Client(name, age, phone);
            em.persist(client);
            em.getTransaction().commit();

            System.out.println(client.getId());
        } catch (Exception exception) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteClient(Scanner sc) { //метод для видалення Client з таблиці
        System.out.print("Enter client id: ");
        String sId = sc.nextLine();
        long id = Long.parseLong(sId);

        Client client = em.getReference(Client.class, id);
        if (client == null) {
            System.out.println("Client not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(client);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeClient(Scanner sc) { //метод для зміни Client в таблиці
        System.out.print("Enter client name: ");
        String name = sc.nextLine();

        System.out.print("Enter new age: ");
        String sAge = sc.nextLine();
        int age = Integer.parseInt(sAge);

        System.out.print("Enter new phone: ");
        String strPhone = sc.nextLine();
        int phone = Integer.parseInt(strPhone);

        Client client = null;
        try {
            String queryClient = "SELECT x FROM Client x WHERE x.name = :name";
            Query query = em.createQuery(queryClient, Client.class);
            query.setParameter("name", name);
            client = (Client) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Client not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }
        em.getTransaction().begin();
        try {
            client.setAge(age);
            client.setPhone(phone);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewClients() { //метод для демонстрації всіх Clients з таблиці
        String queryClients = "SELECT c FROM Client c";
        Query query = em.createQuery(queryClients, Client.class);
        List<Client> list = (List<Client>) query.getResultList();

        for (Client client : list)
            System.out.println(client);
    }

    private static void addProduct(Scanner sc) { //метод для додавання Product в таблицю
        System.out.print("Enter product name: ");
        String name = sc.nextLine();

        System.out.print("Enter product brand: ");
        String brand = sc.nextLine();

        System.out.print("Enter product price: ");
        String strPrice = sc.nextLine();
        double price = Double.parseDouble(strPrice);

        em.getTransaction().begin();
        try {
            Product product = new Product(name, brand, price);
            em.persist(product);
            em.getTransaction().commit();

            System.out.println(product.getId());
        } catch (Exception exception) {
            em.getTransaction().rollback();
        }
    }

    private static void deleteProduct(Scanner sc) { //метод по видаленню Product з таблиці
        System.out.print("Enter product id: ");
        String strId = sc.nextLine();
        long id = Long.parseLong(strId);

        Product product = em.getReference(Product.class, id);
        if (product == null) {
            System.out.println("Product not found!");
            return;
        }

        em.getTransaction().begin();
        try {
            em.remove(product);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void changeProduct(Scanner sc) { //метод для змінт Product в таблиці
        System.out.print("Enter product name: ");
        String name = sc.nextLine();

        System.out.print("Enter new brand: ");
        String brand = sc.nextLine();

        System.out.print("Enter new price: ");
        String strPrice = sc.nextLine();
        double price = Double.parseDouble(strPrice);

        Product product = null;
        try {
            String queryProduct = "SELECT x FROM Product x WHERE x.name = :name";
            Query query = em.createQuery(queryProduct, Product.class);
            query.setParameter("name", name);
            product = (Product) query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println("Product not found!");
            return;
        } catch (NonUniqueResultException ex) {
            System.out.println("Non unique result!");
            return;
        }
        em.getTransaction().begin();
        try {
            product.setBrand(brand);
            product.setPrice(price);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void viewProducts() { //метод для демонстрації всіх продуктів в таблиці
        String queryProducts = "SELECT p FROM Product p";
        Query query = em.createQuery(queryProducts, Product.class);
        List<Product> list = (List<Product>) query.getResultList();

        for (Product product : list)
            System.out.println(product);
    }

    private static void createOrder(Scanner sc) { //метод для створення замовлення
        System.out.print("Enter you name: ");
        String name = sc.nextLine();

        String queryClient = "SELECT c FROM Client c WHERE c.name = :cName";
        TypedQuery<Client> typedQuery = em.createQuery(queryClient, Client.class);
        typedQuery.setParameter("cName", name);
        Client client = typedQuery.getSingleResult();

        System.out.print("Enter name of product: ");
        String pName = sc.nextLine();

        String queryProduct = "SELECT p FROM Product p WHERE p.name = :pName";
        TypedQuery<Product> typedQuery1 = em.createQuery(queryProduct, Product.class);
        typedQuery1.setParameter("pName", pName);
        Product product = typedQuery1.getSingleResult();

        em.getTransaction().begin();
        try {
            Order order = new Order(client, product);
            product.addOrder(order);
            client.addOrder(order);
            em.persist(order);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }
    private static void viewOrders() { //метод для демонстрації замовлень
        String typedQueryOrder = "SELECT o FROM Order o";
        TypedQuery<Order> typedQuery = em.createQuery(typedQueryOrder, Order.class);
        List<Order> list = typedQuery.getResultList();
        for (Order o : list) {
            System.out.println(o.toString());
        }
    }

}

