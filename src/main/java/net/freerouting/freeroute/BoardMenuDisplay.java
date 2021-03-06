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
 * BoardDisplayMenu.java
 *
 * Created on 12. Februar 2005, 05:42
 */
package net.freerouting.freeroute;

import java.util.Locale;
import java.util.Map;
import static java.util.Map.entry;

/**
 * Creates the display menu of a board frame.
 *
 * @author Alfons Wirtz
 */
@SuppressWarnings("serial")
final class BoardMenuDisplay extends BoardMenu {

    /**
     * Returns a new display menu for the board frame.
     */
    static BoardMenuDisplay get_instance(BoardFrame p_board_frame) {
        BoardMenuDisplay display_menu = new BoardMenuDisplay(p_board_frame);
        java.util.ResourceBundle resources = java.util.ResourceBundle.getBundle(
                BoardMenuDisplay.class.getPackageName() + ".resources.BoardMenuDisplay",
                Locale.getDefault());

        display_menu.setText(resources.getString("display"));

        Map<SavableSubwindowKey, String> menu_items = Map.ofEntries(
                entry(SavableSubwindowKey.OBJECT_VISIBILITY, "object_visibility"),
                entry(SavableSubwindowKey.LAYER_VISIBILITY, "layer_visibility"),
                entry(SavableSubwindowKey.COLOR_MANAGER, "colors"),
                entry(SavableSubwindowKey.DISPLAY_MISC, "miscellaneous"));

        for (Map.Entry<SavableSubwindowKey, String> entry : menu_items.entrySet()) {
            javax.swing.JMenuItem menu_item = new javax.swing.JMenuItem();
            menu_item.setText(resources.getString(entry.getValue()));
            menu_item.addActionListener((java.awt.event.ActionEvent evt) -> {
                display_menu.board_frame.savable_subwindows.get(entry.getKey()).setVisible(true);
            });
            display_menu.add(menu_item);
        }

        return display_menu;
    }

    /**
     * Creates a new instance of BoardDisplayMenu
     */
    private BoardMenuDisplay(BoardFrame p_board_frame) {
        super(p_board_frame);
    }
}
