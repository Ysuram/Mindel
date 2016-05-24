/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author LiyO
 */
public class miComboBoxEstado extends AbstractListModel implements ComboBoxModel {

    // Object[] con los estados del pedido
    private String[] tipos = {"Espera", "Aceptado", "Rechazado", "Entregado"};
    private String seleccionado = null;

    /**
     * Constructor que devuelve el ComboBox con el primer Item seleccionado
     */
    public miComboBoxEstado() {
        seleccionado = tipos[0];
    }

    @Override
    public int getSize() {
        return tipos.length;
    }

    @Override
    public Object getElementAt(int index) {
        return tipos[index];
    }

    @Override
    public void setSelectedItem(Object anItem) {
        seleccionado = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return seleccionado;
    }

}
