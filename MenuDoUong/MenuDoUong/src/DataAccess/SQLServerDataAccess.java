/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccess;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author Thanh Hieu
 */
public class SQLServerDataAccess {

    Connection cnn;
    Statement stm;
    PreparedStatement ps;

    public SQLServerDataAccess() {
        try {
            String DriverClass, DriverURL;
            String user = "sa";
            String pwd = "sa";
            String databaseName = "QUANLYCAFE";
            String serverName = "localhost";
            String IntegratedSecurity = "IntegratedSecurity=true";

            DriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            DriverURL = "jdbc:sqlserver://" + serverName + ":1433;databaseName = " + databaseName
                    + ";user=" + user + ";password = " + pwd + ";encrypt=true;trustServerCertificate=true;" + IntegratedSecurity;
            Class.forName(DriverClass);
            this.cnn = DriverManager.getConnection(DriverURL);
            this.stm = this.cnn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String sql) {
        ResultSet rs = null;
        try {
            rs = this.stm.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public ResultSet getResultSet(String SQL, Object[] param) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = this.cnn.prepareStatement(SQL);
            int i = 1;
            for (Object value : param) {
                ps.setObject(i, value);
                i++;
            }
            rs = ps.executeQuery();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int executeUpdateQuery(String sql) {
        int k = 0;
        try {
            k = this.stm.executeUpdate(sql);
            return k;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    //INSERT INTO TBLOAINHANVIEN(IDLOAINHANVIEN,TENLOAINHANVIEN) VALUES(?,?)
    public int executeUpdateQuery(String sql, Object[] param) {
        int k = 0;
        try {
            int i = 1;
            PreparedStatement ps = this.cnn.prepareStatement(sql);
            for (Object value : param) {
                ps.setObject(i, value);
                i++;
            }
            k = ps.executeUpdate();
            ps.close();
            return k;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int execute_StoredProcedure(String NameStoredProcedures, Object[] param) {
        try {
            int k = 0;
            CallableStatement cst = this.cnn.prepareCall("{call " + NameStoredProcedures + "}");
            int i = 1;
            for (Object value : param) {
                cst.setObject(i, value);
                i++;
            }
            k = this.ps.executeUpdate();
            this.ps.close();
            return k;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet getResultSet_StoredProcedures(String NameStoredProcedures, Object[] param) {
        ResultSet rs = null;
        CallableStatement ps = null;
        try {
            ps = cnn.prepareCall("{call " + NameStoredProcedures + "}");
            if (param != null) {
                int i = 1;
                for (Object value : param) {
                    ps.setObject(i, value);
                    i++;
                }
            }
            rs = ps.executeQuery();
            ps.close();
            return rs;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
