package com.dmiit3iy.javafxStore;

import com.dmiit3iy.javafxStore.domain.Product;
import com.dmiit3iy.javafxStore.domain.ProductCategory;
import com.dmiit3iy.javafxStore.domain.User;
import javafx.collections.ObservableList;

import java.math.BigDecimal;
import java.sql.*;
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
     * Проверка имени продукта на уникальность
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
     * Метод добавления нового продукта
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

    public static long addCartList(User user) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into cart (id_user) values(?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, user.getId());
        preparedStatement.executeUpdate();
        ResultSet genKey = preparedStatement.getGeneratedKeys();
        if (genKey.next()) {
            return genKey.getLong(1);
        } else return 0;
    }

    public static void addCartProductList(Long idCart, ObservableList<Product> productArrayList) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement =
                connection.prepareStatement("insert into cartproductlist (id_cart, id_product) values(?,?)");
        for (int i=0;i< productArrayList.size();i++) {
            preparedStatement.setLong(1,idCart);
            preparedStatement.setInt(2,productArrayList.get(i).getId());
            preparedStatement.executeUpdate();
        }

    }

}
