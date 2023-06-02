package com.dmiit3iy.javafxStore;

import com.dmiit3iy.javafxStore.domain.Product;
import com.dmiit3iy.javafxStore.domain.ProductCategory;
import com.dmiit3iy.javafxStore.domain.User;

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

}
