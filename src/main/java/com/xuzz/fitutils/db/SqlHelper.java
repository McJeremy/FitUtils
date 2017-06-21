package com.xuzz.fitutils.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by win10 on 2017/6/21.
 */
public class SqlHelper {
    private static String DBDRIVER;
    private static String DBURL;
    private static String DBUSER;
    private static String DBPASS;

    // 定义需要的变量
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    public static Connection getConn() {
        return conn;
    }

    public static PreparedStatement getPstmt() {
        return pstmt;
    }

    public static ResultSet getRs() {
        return rs;
    }

    // 加载驱动，只需要加载一次
    static {
        FileInputStream fis = null;

        try {
            // 从dbinfo.properties文件中读取配置信息
            Properties pp = new Properties();
            fis = new FileInputStream(
                    "E:\\myeclipse10code\\UserLoginUp\\dbinfo.properties");
            pp.load(fis);
            DBDRIVER = pp.getProperty("driver");
            DBURL = pp.getProperty("url");
            DBUSER = pp.getProperty("user");
            DBPASS = pp.getProperty("pass");

            Class.forName(DBDRIVER);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            fis = null;
        }
    }

    // 得到连接
    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // 关闭资源
    public static void close(ResultSet rs, PreparedStatement pstmt,
                             Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                pstmt = null;
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }

    // 先写一个update/delete/insert
    // sql格式： update 表名 set 字段名=？ where 字段=？
    public static void executeUpdate1(String sql, String[] parameters) {
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            // 给？赋值
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setString(i + 1, parameters[i]);
                }
            }
            // 执行
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            close(rs, pstmt, conn);
        }
    }

    // 如果有多个update/delete/insert语句【需要考虑事务】
    public static void executeUpdate2(String[] sqls, String[][] parameters) {
        try {
            conn = getConnection();
            // 因为这时用户传入的可能是多个sql语句
            conn.setAutoCommit(false);
            if (sqls != null) {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt = conn.prepareStatement(sqls[i]);
                    if (parameters[i] != null) {
                        for (int j = 0; j < parameters[i].length; j++) {
                            pstmt.setString(j + 1, parameters[i][j]);
                        }
                    }
                    pstmt.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            // 回滚
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            close(rs, pstmt, conn);
        }
    }

    // 统一的select
    public static ResultSet executeQuery(String sql, String[] parameters) {

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            if (parameters != null) {
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setString(i + 1, parameters[i]);
                }
            }
            System.out.println(pstmt);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            // 如果这里关闭了资源，就没法使用ResultSet了，也就没法return了
            // close(rs, pstmt, conn);
        }

        return rs;
    }

    //修改后的统一的select (ResultSet-->ArrayList)
    public static ArrayList executeQuery1(String sql,String[] parameters){
        ArrayList list = new ArrayList();
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);


            if(parameters!=null){
                for (int i = 0; i < parameters.length; i++) {
                    pstmt.setString(i+1, parameters[i]);
                }
            }
            System.out.println(pstmt);
            rs = pstmt.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int column = rsmd.getColumnCount();//这里可以得到你查询语句返回的总的列数

            while (rs.next()) {
                Object[] ob = new Object[column];//对象数组，表示一行数据
                for (int i = 0; i < column; i++) {
                    ob[i] = rs.getObject(i+1);
                }
                list.add(ob);
            }

            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally{
            //关闭资源
            close(rs, pstmt, conn);
        }

    }
}
