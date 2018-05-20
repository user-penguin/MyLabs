/*
Для работы нужен драйвер ojdbc6.jar
*/
package sample;

import javax.annotation.Resource;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class connect {
    private String host;
    private int port;
    private String sid;
    private String user;
    private String pwd;
    private Connection connection;
    private Statement stmt;

    // подключение и инициализация
    connect (String host, int port, String sid, String user, String pwd) {
        Locale.setDefault(Locale.ENGLISH);
        this.host = host;
        this.port = port;
        this.sid = sid;
        this.user = user;
        this.pwd = pwd;

        // подключение драйвера
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String url = String.format("jdbc:oracle:thin:@%s:%d:%s", host, port, sid);

            // Попытка соединения с драйвером. Каждый из
            // зарегистрированных драйверов будет загружаться, пока
            // не будет найден тот, который сможет обработать этот URL
            connection = DriverManager.getConnection(url, user, pwd);
            stmt = connection.createStatement();

            // Получить DatabaseMetaData объект и показать информацию о соединении
            DatabaseMetaData dma = connection.getMetaData();

            // Печать сообщения об успешном соединении
            System.out.println("Connected to " + dma.getURL());
            System.out.println("Driver " + dma.getDriverName());
            System.out.println("Version " + dma.getDriverVersion());
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // insert, update, delete
    public boolean query(String sql, String msg, String title) {
        try {
            this.stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Ошибка!", JOptionPane.ERROR_MESSAGE);
        }
        return true;
    }

    public ArrayList<String> resultSelect(String sql, String[] listField){
        ResultSet rs;
        ArrayList<String> req = new ArrayList<>();
        try {
            rs = this.stmt.executeQuery(sql);
        // обработка запроса
            while (rs.next()) {
                for (int i = 0; i < listField.length; i++) {
                    System.out.print(rs.getString(listField[i]) + " ");
                    req.add(rs.getString(listField[i]));
                }
                System.out.println("");
            }
        // обработка запроса
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            System.out.println("что-то пошло не так с запросом");
        }
        return req;
    }

    // select
    public boolean select(String sql, String[] listField) {
        ResultSet rs;
        try {
            rs = this.stmt.executeQuery(sql);
// обработка запроса
            while (rs.next()) {
                for (int i = 0; i < listField.length; i++) {
                    System.out.print(rs.getString(listField[i]) + " ");
                }
                System.out.println("");
            }
// обработка запроса
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public boolean connectClose() {
        // Закрыть соединение
        try {
            this.connection.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e, "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
