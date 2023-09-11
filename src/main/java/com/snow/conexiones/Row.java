package com.snow.conexiones;

import java.util.LinkedList;
import java.util.List;

/**
 * Almacena las filas de una tabla en una lista de cadenas
 */
public class Row {
    private String[] campos;

    Row(int nCampos) {
        this.campos = new String[nCampos];
    }

    public String getCampo(int i) {
        return this.campos[i];
    }

    void setCampo(int i, String valor) {
        this.campos[i] = valor;
    }

    @Override
    public String toString() {
        List<String> str = new LinkedList<>();
        for (int i = 0; i < campos.length; i++) {
            str.add(campos[i]);
        }
        return str.toString();
    }
}