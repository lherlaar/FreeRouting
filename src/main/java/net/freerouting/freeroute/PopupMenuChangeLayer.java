/*
 *  Copyright (C) 2014  Alfons Wirtz  
 *   website www.freerouting.net
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License at <http://www.gnu.org/licenses/> 
 *   for more details.
 *
 * ChangeLayerMenu.java
 *
 * Created on 17. Februar 2005, 08:58
 */
package net.freerouting.freeroute;

import java.util.Locale;

/**
 * Used as submenu in a popup menu for change layer actions.
 *
 * @author Alfons Wirtz
 */
@SuppressWarnings("serial")
class PopupMenuChangeLayer extends javax.swing.JMenu {

    private final BoardFrame board_frame;

    private final LayermenuItem[] item_arr;

    /**
     * Creates a new instance of ChangeLayerMenu
     */
    PopupMenuChangeLayer(BoardFrame p_board_frame) {
        this.board_frame = p_board_frame;
        net.freerouting.freeroute.board.LayerStructure layer_structure = board_frame.board_panel.board_handling.get_routing_board().layer_structure;
        this.item_arr = new LayermenuItem[layer_structure.signal_layer_count()];
        int curr_signal_layer_no = 0;
        for (int i = 0; i < layer_structure.get_layer_count(); ++i) {
            net.freerouting.freeroute.board.Layer curr_layer = layer_structure.get_layer(i);
            if (curr_layer instanceof net.freerouting.freeroute.board.SignalLayer) {
                this.item_arr[curr_signal_layer_no] = new LayermenuItem(i);
                this.item_arr[curr_signal_layer_no].setText(curr_layer.get_name());
                ++curr_signal_layer_no;
            }
        }
        initializeComponents();
    }

    private void initializeComponents() {
        java.util.ResourceBundle resources
                = java.util.ResourceBundle.getBundle("net.freerouting.freeroute.resources.Default", Locale.getDefault());
        this.setText(resources.getString("change_layer"));
        this.setToolTipText(resources.getString("change_layer_tooltip"));
        for (int i = 0; i < this.item_arr.length; ++i) {
            this.add(this.item_arr[i]);
        }
    }

    /**
     * Disables the item with index p_no and enables all other items.
     */
    void disable_item(int p_no) {
        for (int i = 0; i < item_arr.length; ++i) {
            if (i == p_no) {
                this.item_arr[i].setEnabled(false);
            } else {
                this.item_arr[i].setEnabled(true);
            }
        }
    }

    @SuppressWarnings("serial")
    private class LayermenuItem extends javax.swing.JMenuItem {

        private final int layer_no;
        private final String message1;

        LayermenuItem(int p_layer_no) {
            java.util.ResourceBundle resources
                    = java.util.ResourceBundle.getBundle("net.freerouting.freeroute.resources.Default", Locale.getDefault());
            message1 = resources.getString("layer_changed_to") + " ";
            layer_no = p_layer_no;
            initializeComponent();
        }

        private void initializeComponent() {
            addActionListener((java.awt.event.ActionEvent evt) -> {
                final BoardPanel board_panel = board_frame.board_panel;
                if (board_panel.board_handling.change_layer_action(layer_no)) {
                    String layer_name = board_panel.board_handling.get_routing_board().layer_structure.get_name_layer(layer_no);
                    board_panel.screen_messages.set_status_message(message1 + layer_name);
                }
                // If change_layer failed the status message is set inside change_layer_action
                // because the information of the cause of the failing is missing here.
                board_panel.move_mouse();
            });
        }
    }
}
