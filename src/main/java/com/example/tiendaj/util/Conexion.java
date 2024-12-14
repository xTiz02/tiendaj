package com.example.tiendaj.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Conexion {
    private static String url = "jdbc:mysql://localhost:3306/tienda_comercial?useUnicode=true&characterEncoding=UTF-8";
    private static String user = "root";
    private static String password = "ImNotlol12$$%%";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    static{
        try{
            Class.forName(driver);//cargar el driver de mysql para que se pueda conectar
        }catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Connection getConexion(){

        Connection con = null;
        try{
            con = DriverManager.getConnection(url,user,password);
        }catch(SQLException e){
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, e);
        }
        return con;
    }







}


