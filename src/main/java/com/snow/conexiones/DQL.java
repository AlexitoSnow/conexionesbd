package com.snow.conexiones;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

/**
 * Clase para realizar consultas a la base de datos
 */
public class DQL {
    private Connection conexion;

    /**
     * Recibe una conexión a la base de datos especificada
     * 
     * @param conexion Conexión a la base de datos
     */
    public DQL(ConexionBD conexion) {
        try {
            this.conexion = conexion.getConexion();
        } catch (SQLException e) {
            this.conexion = null;
        }
    }

    /**
     * Obtiene los datos de una tabla con los campos especificados. Si ocurre
     * algún error con los campos, se llama al método {@code getTable(tabla)}.
     * 
     * @param tabla  Nombre de la tabla
     * @param campos Lista de campos a obtener. Si no se especifica, se obtienen
     *               todos los campos de la tabla
     * @return Lista de filas de la tabla
     */
    public List<Row> getTable(String tabla, String... campos) {
        // Obtener la lista de campos de la tabla
        if (campos.length == 0) {
            return getTable(tabla);
        }
        String sql = "SELECT " + String.join(",", campos) + " FROM " + tabla;
        List<Row> table = new LinkedList<>();
        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    Row row = new Row(campos.length);
                    for (int i = 0; i < campos.length; i++) {
                        row.setCampo(i, rs.getString(campos[i]));
                    }
                    table.add(row);
                }
            } catch (Exception e) {
                return getTable(tabla);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
        return table;
    }

    /**
     * Obtiene los datos de todas las columnas de una tabla. Si ocurre algún error,
     * se devuelve una lista vacía.
     * 
     * @param tabla Nombre de la tabla
     * @return Lista de filas de la tabla
     * @apiNote Todos los errores producidos en los otros métodos de esta clase se
     *          conllevarán a este método si no se manejan correctamente en el
     *          método que los llama
     */
    public List<Row> getTable(String tabla) {
        List<Row> table = new LinkedList<>();
        String[] campos;
        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery("SELECT * FROM " + tabla);
                campos = new String[rs.getMetaData().getColumnCount()];
                for (int i = 0; i < campos.length; i++) {
                    campos[i] = rs.getMetaData().getColumnName(i + 1);
                }
                while (rs.next()) {
                    Row row = new Row(campos.length);
                    for (int i = 0; i < campos.length; i++) {
                        row.setCampo(i, rs.getString(campos[i]));
                    }
                    table.add(row);
                }
            } catch (Exception e) {
                Row temp = new Row(0);
                table.add(temp);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
        return table;
    }

    /**
     * Obtiene los datos de una tabla con los campos especificados y el filtro
     * especificado. Si los campos están vacíos, se llama al método
     * {@code getFilteredTable(tabla, filtro)}, si el filtro tuvo un error, llama al
     * método{@code getTable(tabla, campos)}.
     * 
     * @param tabla  Nombre de la tabla
     * @param campos Lista de campos a obtener. Si no se especifica, se obtienen
     *               todos los campos de la tabla y el filtro
     * @param filtro Filtro para la consulta SQL (WHERE) Ejemplo: "id = 1 AND nombre
     *               LIKE '%a%'"
     * @apiNote Utilizar comillas simples para cadenas de texto en el filtro
     * @return Lista de filas de la tabla filtrada
     */
    public List<Row> getFilteredTable(String tabla, String filtro, String... campos) {
        // Obtener la lista de campos de la tabla
        if (campos.length == 0) {
            return getFilteredTable(tabla, filtro);
        }
        String sql = "SELECT " + String.join(",", campos) + " FROM " + tabla + " WHERE " + filtro;
        List<Row> table = new LinkedList<>();
        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    Row row = new Row(campos.length);
                    for (int i = 0; i < campos.length; i++) {
                        row.setCampo(i, rs.getString(campos[i]));
                    }
                    table.add(row);
                }
            } catch (Exception e) {
                return getTable(tabla, campos);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
        return table;
    }

    /**
     * Obtiene los datos de una tabla con el filtro aplicado. Si ocurre un error con
     * el filtro, se llama al método {@code getTable(tabla)}.
     * 
     * @param tabla  Nombre de la tabla
     * @param filtro Filtro para la consulta SQL (WHERE) Ejemplo: "id = 1 AND nombre
     *               LIKE '%a%'"
     * @apiNote Utilizar comillas simples para cadenas de texto en el filtro
     * @return Lista de filas de la tabla filtrada
     */
    public List<Row> getFilteredTable(String tabla, String filtro) {
        List<Row> table = new LinkedList<>();
        String[] campos;
        Statement st = null;
        ResultSet rs = null;
        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery("SELECT * FROM " + tabla + " WHERE " + filtro);
                campos = new String[rs.getMetaData().getColumnCount()];
                for (int i = 0; i < campos.length; i++) {
                    campos[i] = rs.getMetaData().getColumnName(i + 1);
                }
                while (rs.next()) {
                    Row row = new Row(campos.length);
                    for (int i = 0; i < campos.length; i++) {
                        row.setCampo(i, rs.getString(campos[i]));
                    }
                    table.add(row);
                }
            } catch (Exception e) {
                return getTable(tabla);
            } finally {
                try {
                    rs.close();
                    st.close();
                } catch (Exception e) {
                }
            }
        }
        return table;
    }
}