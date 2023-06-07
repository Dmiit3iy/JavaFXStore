package com.dmiit3iy.javafxStore;

import com.dmiit3iy.javafxStore.domain.*;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DataBaseHandler {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/store";
        String login = "root";
        String password = "root";
        return DriverManager.getConnection(url, login, password);
    }

    public static void addUser(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into users(username,password,name,role) values(?,?,?,?)");
        preparedStatement.setString(1, user.getUsername());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getName());
        preparedStatement.setString(4, user.getRole());
        preparedStatement.executeUpdate();
    }

    public static User getUser(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where users.username=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return new User(resultSet.getInt(1),
                resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getString(5));
    }

    public static boolean isUniqueUserName(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from users where users.name=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return true;
        }

        return false;
    }

    /**
     * Checking the product name for uniqueness
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public static boolean isUniqueProductName(String name) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product where product.name=?");
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return false;
        }
        return true;
    }

    /**
     * Method for adding a new product
     *
     * @param product
     * @throws SQLException
     */
    public static void addProduct(Product product) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into product (name,category,price) values(?,?,?)");
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, String.valueOf(product.getCategory()));
        preparedStatement.setBigDecimal(3, product.getPrice());
        preparedStatement.executeUpdate();
    }

    /**
     * Checking if a user exists
     *
     * @param name
     * @param password
     * @return
     * @throws SQLException
     */
    public static String userIsExist(String name, String password) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("select * from users where users.username=? and users.password=?");
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            return resultSet.getString(5);
        }
        return null;
    }

    /**
     * Method for getting a list of all products
     *
     * @return
     * @throws SQLException
     */
    public static ArrayList<Product> getAllProducts() throws SQLException {
        ArrayList<Product> productArrayList = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select * from product");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Product product = new Product(resultSet.getInt(1),
                    resultSet.getString(2), ProductCategory.valueOf(resultSet.getString(3)),
                    new BigDecimal(resultSet.getString(4)));
            productArrayList.add(product);
        }
        return productArrayList;
    }

    /**
     * Shopping Cart Add Method
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public static long addCartList(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into cart (id_user, date) values(?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, user.getId());
        LocalDateTime localDateTime = LocalDateTime.now();
        preparedStatement.setString(2, String.valueOf(localDateTime));
        preparedStatement.executeUpdate();
        ResultSet genKey = preparedStatement.getGeneratedKeys();
        if (genKey.next()) {
            return genKey.getLong(1);
        } else return 0;
    }

    /**
     * Method for adding shopping list to cart
     *
     * @param idCart
     * @param productArrayList
     * @throws SQLException
     */
    public static void addCartProductList(Long idCart, ObservableList<Product> productArrayList) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into cartproductlist (id_cart, id_product) values(?,?)");
        for (int i = 0; i < productArrayList.size(); i++) {
            preparedStatement.setLong(1, idCart);
            preparedStatement.setInt(2, productArrayList.get(i).getId());
            preparedStatement.executeUpdate();
        }

    }

    /**
     * Method to update product details
     *
     * @param product
     * @throws SQLException
     */
    public static void updateProduct(Product product) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("update product set name=?, category =?, price=? where id =?");
        preparedStatement.setString(1, product.getName());
        preparedStatement.setString(2, product.getCategory().name());
        preparedStatement.setBigDecimal(3, product.getPrice());
        preparedStatement.setInt(4, product.getId());
        preparedStatement.executeUpdate();
    }

    /**
     * Method for getting a product by index
     *
     * @param i
     * @return
     * @throws SQLException
     */
    public static Product getProductById(int i) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("select * from product where product.id =?");
        preparedStatement.setInt(1, i);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Product product = new Product(resultSet.getInt(1), resultSet.getString(2),
                    ProductCategory.valueOf(resultSet.getString(3)), new BigDecimal(resultSet.getString(4)));
            System.out.println(product);
            return product;
        }
        return null;
    }


    /**
     * Method for getting all purchases with date of purchase
     *
     * @param user
     * @return
     * @throws SQLException
     */
    public static ArrayList<ShoppingCarts> getAllCarts(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("select date, id_product from cart  left join cartproductlist" +
                                " on cart.idcart=cartproductlist.id_cart where cart.id_user = ?");
        preparedStatement.setInt(1, user.getId());
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<ShoppingCarts> shoppingCartsArrayList = new ArrayList<>();
        while (resultSet.next()) {
            Product product = getProductById(resultSet.getInt(2));

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(resultSet.getString(1), formatter);
            ShoppingCarts shoppingCarts =
                    new ShoppingCarts(dateTime,
                            product.getId(), product.getName(), product.getCategory(), product.getPrice());
            shoppingCartsArrayList.add(shoppingCarts);

        }
        return shoppingCartsArrayList;
    }

    /**
     *    Method for obtaining the number of purchased products (by index)
     * @return
     * @throws SQLException
     */
    public static ArrayList<Integer> countIDProduct() throws SQLException {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement
                        ("SELECT  id_product, count(id_product) as countP FROM cartproductlist group by id_product ORDER BY countP DESC");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            integerArrayList.add(resultSet.getInt(1));
        }
        return integerArrayList;
    }
}
