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
public class miComboBox extends AbstractListModel implements ComboBoxModel{

    private String[] tipos = {"Carniceria", "Pescaderia", "Charcuteria", "Limpieza", "Fruteria", 
        "Congelados", "Panaderia", "Higiene", "Frio"};
    private String seleccionado = null;

    public miComboBox() {
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
