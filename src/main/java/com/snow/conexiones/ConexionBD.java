package com.snow.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Plantilla de conexión a distintas bases de datos relacionales
 * 
 * @author AlexitoSnow
 */
public class ConexionBD {

    protected String servidor, puerto, bd, usuario, contrasena;
    protected Gestor gestor;

    /**
     * Constructor para conexiones remotas
     * 
     * @apiNote Se asume que la conexión está configurada correctamente en el
     *          servidor
     * 
     * @param servidor   Nombre del servidor
     * @param puerto     Puerto de conexión
     * @param bd         Nombre de la base de datos
     * @param usuario    Usuario de la base de datos
     * @param contrasena Contraseña del usuario
     */
    public ConexionBD(Gestor DBMS, String servidor, String puerto, String bd, String usuario,
            String contrasena) {
        this.servidor = servidor;
        this.puerto = puerto;
        this.bd = bd;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.gestor = DBMS;
    }

    /**
     * Constructor para conexiones locales
     * 
     * @apiNote Se asume que la conexión está configurada correctamente en el
     *          servidor y el servidor es localhost
     * @apiNote Para conexiones a SQL Server, se requiere el nombre del servidor,
     *          utilizar el constructor para conexiones remotas en su lugar
     * 
     * @param puerto     Puerto de conexión
     * @param bd         Nombre de la base de datos
     * @param usuario    Usuario de la base de datos
     * @param contrasena Contraseña del usuario
     */
    public ConexionBD(Gestor DBMS, String puerto, String bd, String usuario, String contrasena) {
        this(DBMS, "localhost", puerto, bd, usuario, contrasena);
    }

    /**
     * Establece la conexión a la base de datos según el gestor especificado
     * 
     * @return Conexión a la base de datos, null si no se pudo establecer la
     *         conexión
     * @throws SQLException
     */
    public Connection getConexion() throws SQLException {
        Connection conexion = null;
        try {
            switch (gestor) {
                case MYSQL:
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    break;
                case POSTGRESQL:
                    Class.forName("org.postgresql.Driver");
                    break;
                case SQLSERVER:
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    break;
                default:
                    return conexion;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e);
            return conexion;
        }
        try {
            String URL;
            switch (gestor) {
                case MYSQL:
                    URL = "jdbc:mysql://" + servidor + ":" + puerto + "/" + bd;
                    break;
                case POSTGRESQL:
                    URL = "jdbc:postgresql://" + servidor + ":" + puerto + "/" + bd;
                    break;
                case SQLSERVER:

                    URL = String.format("jdbc:sqlserver://%s:%s;database=%s;user=%s;password=%s", servidor, puerto,
                            bd, usuario, contrasena);
                    break;
                default:
                    URL = "";
            }
            conexion = gestor.equals(Gestor.SQLSERVER) ? DriverManager.getConnection(URL)
                    : DriverManager.getConnection(URL, usuario, contrasena);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            return conexion;
        }
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public void close() {
        try {
            if (getConexion() == null) {
                System.out.println("No hay conexión");
            } else {
                getConexion().close();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public void setBd(String baseDatosNombre) {
        this.bd = baseDatosNombre;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }
}