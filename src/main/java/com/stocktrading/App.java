package com.stocktrading;
import java.sql.*;

public class App {

    public static void available() {

        String url = "jdbc:mysql://localhost:3306/Stock?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        String sql = "SELECT symbol, company, price FROM av_stock";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("_________________________________________");
            System.out.println(" Symbol\t\tCompany\t\tPrice");
            System.out.println("_________________________________________");

            while (rs.next()) {
                String symbol = rs.getString("symbol");
                String company = rs.getString("company");
                float price = rs.getFloat("price");

                System.out.printf("%-10s %-15s $%.2f%n", symbol, company, price);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static float boughtStock(String symbol) {

        String url = "jdbc:mysql://localhost:3306/Stock?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        String sql = "SELECT price FROM av_stock WHERE symbol = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, symbol);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getFloat("price");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
    public static void updatePort(String symbol,int quantity,float price,String user) {
        String url = "jdbc:mysql://localhost:3306/Stock?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        String checkSql = "SELECT stock,quantity,price,value FROM Portfolio WHERE `user`=? AND stock=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1,user);
            ps.setString(2,symbol);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                String sql="INSERT INTO Portfolio(stock,quantity,price,value,`user`) VALUES (?,?,?,?,?)";
                PreparedStatement ps1=conn.prepareStatement(sql);
                ps1.setString(1,symbol);
                ps1.setInt(2,quantity);
                ps1.setFloat(3,price);
                ps1.setFloat(4,price*quantity);
                ps1.setString(5,user);
                ps1.executeUpdate();
                System.out.println("Stock added to portfolio");
            }
            else {
                int oldQ=rs.getInt("quantity");
                int newQty = oldQ + quantity;
                String updateSql = "UPDATE Portfolio SET quantity=?, value=? WHERE `user`=? AND stock=?";
                PreparedStatement ps2 = conn.prepareStatement(updateSql);
                ps2.setInt(1, newQty);
                ps2.setFloat(2, newQty * price);
                ps2.setString(3, user);
                ps2.setString(4, symbol);
                ps2.executeUpdate();
                System.out.println("Portfolio updated");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int quantityAv(String user,String symbol){
        String url = "jdbc:mysql://localhost:3306/Stock?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        String sql = "SELECT quantity FROM Portfolio WHERE `user`=? AND stock=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,user);
            ps.setString(2,symbol);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public static void viewPort(String user) {

        String url = "jdbc:mysql://localhost:3306/Stock?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        String sql = "SELECT * FROM Portfolio WHERE `user`=?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1,user);
            ResultSet rs=ps.executeQuery();
            boolean hasData=false;
                System.out.println("______________________________________________");
                System.out.println(" Stock\t\tQuantity\t\tPrice\t\tValue");
                while (rs.next()) {
                    hasData=true;
                    String stock = rs.getString("stock");
                    int q = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    float value = rs.getFloat("value");
                    System.out.printf(" %-10s %-12d %-12.2f $%.2f%n", stock, q, price, value);
                }
                if(!hasData) {
                    System.out.println("\t\t\tNo data found!\n");
                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatedPort(String symbol,int quantity,float price,String user) {
        String url = "jdbc:mysql://localhost:3306/Stock?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "";
        String checkSql = "SELECT stock,quantity,price,value FROM Portfolio WHERE `user`=? AND stock=?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(checkSql)) {
            ps.setString(1,user);
            ps.setString(2,symbol);
            ResultSet rs = ps.executeQuery();
            if(!rs.next()) {
                String sql="INSERT INTO Portfolio(stock,quantity,price,value,`user`) VALUES (?,?,?,?,?)";
                PreparedStatement ps1=conn.prepareStatement(sql);
                ps1.setString(1,symbol);
                ps1.setInt(2,quantity);
                ps1.setFloat(3,price);
                ps1.setFloat(4,price*quantity);
                ps1.setString(5,user);
                ps1.executeUpdate();
                System.out.println("Stock added to portfolio");
            }
            else {
                int oldQ=rs.getInt("quantity");
                int newQty = oldQ - quantity;
                String updateSql = "UPDATE Portfolio SET quantity=?, value=? WHERE `user`=? AND stock=?";
                PreparedStatement ps2 = conn.prepareStatement(updateSql);
                ps2.setInt(1, newQty);
                ps2.setFloat(2, newQty * price);
                ps2.setString(3, user);
                ps2.setString(4, symbol);
                ps2.executeUpdate();
                System.out.println("Portfolio updated");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}