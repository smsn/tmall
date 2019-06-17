package com.example.tmall.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TestTmall
 */
public class TestTmall {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            // 连接 JDBC
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmall_db?useUnicode=true&characterEncoding=utf8", "tmall", "tmall");
            // 游标
            Statement s = conn.createStatement();
            for (int i = 0; i < 10; i++) {
                String sql = String.format("insert into category values (null, '测试分类%d')", i);
                // 执行
                s.execute(sql);
            }
            System.out.println("已经成功创建10条分类测试数据");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}