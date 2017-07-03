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
 * BoardToolbarPanel.java
 *
 * Created on 15. Februar 2005, 09:44
 */
package net.freerouting.freeroute;

import java.util.Locale;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 * Implements the toolbar panel of the board frame.
 *
 * @author Alfons Wirtz
 */
@SuppressWarnings("serial")
final class BoardToolbar extends javax.swing.JPanel {

    private final BoardFrame board_frame;
    private final javax.swing.JToggleButton select_button;
    private final javax.swing.JToggleButton route_button;
    private final javax.swing.JToggleButton drag_button;
    final javax.swing.JFormattedTextField unit_factor_field;
    final javax.swing.JComboBox<net.freerouting.freeroute.board.Unit> unit_combo_box;

    /**
     * Creates a new instance of BoardToolbarPanel
     */
    BoardToolbar(BoardFrame p_board_frame) {
        Locale locale = Locale.getDefault();
        this.board_frame = p_board_frame;

        java.util.ResourceBundle resources
                = java.util.ResourceBundle.getBundle("net.freerouting.freeroute.resources.BoardToolbar", locale);

        this.setLayout(new java.awt.BorderLayout());

        // create the left toolbar
        final javax.swing.JToolBar left_toolbar = new javax.swing.JToolBar();
        final javax.swing.ButtonGroup toolbar_button_group = new javax.swing.ButtonGroup();
        this.select_button = new javax.swing.JToggleButton();
        this.route_button = new javax.swing.JToggleButton();
        this.drag_button = new javax.swing.JToggleButton();
        final javax.swing.JLabel jLabel1 = new javax.swing.JLabel();

        left_toolbar.setMaximumSize(new java.awt.Dimension(1_200, 23));
        toolbar_button_group.add(select_button);
        select_button.setSelected(true);
        select_button.setText(resources.getString("select_button"));
        select_button.setToolTipText(resources.getString("select_button_tooltip"));
        select_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.set_select_menu_state();
        });

        left_toolbar.add(select_button);

        toolbar_button_group.add(route_button);
        route_button.setText(resources.getString("route_button"));
        route_button.setToolTipText(resources.getString("route_button_tooltip"));
        route_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.set_route_menu_state();
        });

        left_toolbar.add(route_button);

        toolbar_button_group.add(drag_button);
        drag_button.setText(resources.getString("drag_button"));
        drag_button.setToolTipText(resources.getString("drag_button_tooltip"));
        drag_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.set_drag_menu_state();
        });

        left_toolbar.add(drag_button);

        jLabel1.setMaximumSize(new java.awt.Dimension(30, 10));
        jLabel1.setMinimumSize(new java.awt.Dimension(3, 10));
        jLabel1.setPreferredSize(new java.awt.Dimension(30, 10));
        left_toolbar.add(jLabel1);

        this.add(left_toolbar, java.awt.BorderLayout.WEST);

        // create the middle toolbar
        final javax.swing.JToolBar middle_toolbar = new javax.swing.JToolBar();

        final javax.swing.JButton autoroute_button = new javax.swing.JButton(
                resources.getString("autoroute_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/actions/system-run.png")));
        autoroute_button.setToolTipText(resources.getString("autoroute_button_tooltip"));
        autoroute_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        autoroute_button.setHorizontalTextPosition(AbstractButton.CENTER);
        autoroute_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.start_batch_autorouter();
        });

        middle_toolbar.add(autoroute_button);

        final javax.swing.JLabel separator_2 = new javax.swing.JLabel();
        separator_2.setMaximumSize(new java.awt.Dimension(10, 10));
        separator_2.setPreferredSize(new java.awt.Dimension(10, 10));
        separator_2.setRequestFocusEnabled(false);
        middle_toolbar.add(separator_2);

        final javax.swing.JButton undo_button = new javax.swing.JButton(
                resources.getString("undo_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/actions/edit-undo.png")));
        undo_button.setToolTipText(resources.getString("undo_button_tooltip"));
        undo_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        undo_button.setHorizontalTextPosition(AbstractButton.CENTER);
        undo_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.cancel_state();
            board_frame.board_panel.board_handling.undo();
            board_frame.refresh_windows();
        });

        middle_toolbar.add(undo_button);

        final javax.swing.JButton redo_button = new javax.swing.JButton(
                resources.getString("redo_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/actions/edit-redo.png")));
        redo_button.setToolTipText(resources.getString("redo_button_tooltip"));
        redo_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        redo_button.setHorizontalTextPosition(AbstractButton.CENTER);
        redo_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.redo();
        });

        middle_toolbar.add(redo_button);

        final javax.swing.JLabel separator_1 = new javax.swing.JLabel();
        separator_1.setMaximumSize(new java.awt.Dimension(10, 10));
        separator_1.setPreferredSize(new java.awt.Dimension(10, 10));
        middle_toolbar.add(separator_1);

        final javax.swing.JButton incompletes_button = new javax.swing.JButton(
                resources.getString("incompletes_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/status/dialog-warning.png")));
        incompletes_button.setToolTipText(resources.getString("incompletes_button_tooltip"));
        incompletes_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        incompletes_button.setHorizontalTextPosition(AbstractButton.CENTER);
        incompletes_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.toggle_ratsnest();
        });

        middle_toolbar.add(incompletes_button);

        final javax.swing.JButton violation_button = new javax.swing.JButton(
                resources.getString("violations_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/status/dialog-error.png")));
        violation_button.setToolTipText(resources.getString("violations_button_tooltip"));
        violation_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        violation_button.setHorizontalTextPosition(AbstractButton.CENTER);
        violation_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.toggle_clearance_violations();
        });

        middle_toolbar.add(violation_button);

        final javax.swing.JLabel separator_3 = new javax.swing.JLabel();
        separator_3.setMaximumSize(new java.awt.Dimension(10, 10));
        separator_3.setPreferredSize(new java.awt.Dimension(10, 10));
        separator_3.setRequestFocusEnabled(false);
        middle_toolbar.add(separator_3);

        final javax.swing.JButton display_all_button = new javax.swing.JButton(
                resources.getString("display_all_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/actions/view-fullscreen.png")));
        display_all_button.setToolTipText(resources.getString("display_all_button_tooltip"));
        display_all_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        display_all_button.setHorizontalTextPosition(AbstractButton.CENTER);
        display_all_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.zoom_all();
        });

        middle_toolbar.add(display_all_button);

        final javax.swing.JButton display_region_button = new javax.swing.JButton(
                resources.getString("display_region_button"),
                new ImageIcon(BoardToolbar.class.getResource("/icons/32/actions/view-restore.png")));
        display_region_button.setToolTipText(resources.getString("display_region_button_tooltip"));
        display_region_button.setVerticalTextPosition(AbstractButton.BOTTOM);
        display_region_button.setHorizontalTextPosition(AbstractButton.CENTER);
        display_region_button.addActionListener((java.awt.event.ActionEvent evt) -> {
            board_frame.board_panel.board_handling.zoom_region();
        });

        middle_toolbar.add(display_region_button);

        this.add(middle_toolbar, java.awt.BorderLayout.CENTER);

        // create the right toolbar
        final javax.swing.JToolBar right_toolbar = new javax.swing.JToolBar();
        final javax.swing.JLabel unit_label = new javax.swing.JLabel();
        java.text.NumberFormat number_format = java.text.NumberFormat.getInstance(locale);
        number_format.setMaximumFractionDigits(7);
        this.unit_factor_field = new javax.swing.JFormattedTextField(number_format);
        this.unit_combo_box = new javax.swing.JComboBox<>();
        final javax.swing.JLabel jLabel4 = new javax.swing.JLabel();

        right_toolbar.setAutoscrolls(true);
        unit_label.setText(resources.getString("unit_button"));
        right_toolbar.add(unit_label);

        unit_factor_field.setHorizontalAlignment(SwingConstants.CENTER);
        unit_factor_field.setValue(1);
        unit_factor_field.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == '\n') {
                    Object input = unit_factor_field.getValue();
                    if (input instanceof Number) {
                        double input_value = ((Number) input).doubleValue();
                        if (input_value > 0) {
                            board_frame.board_panel.board_handling.change_user_unit_factor(input_value);
                        }
                    }
                    double unit_factor = board_frame.board_panel.board_handling.coordinate_transform.user_unit_factor;
                    unit_factor_field.setValue(unit_factor);

                    board_frame.refresh_windows();
                }
            }
        });

        right_toolbar.add(unit_factor_field);

        unit_combo_box.setModel(new javax.swing.DefaultComboBoxModel<>(net.freerouting.freeroute.board.Unit.values()));
        unit_combo_box.setFocusTraversalPolicyProvider(true);
        unit_combo_box.setInheritsPopupMenu(true);
        unit_combo_box.setOpaque(false);
        unit_combo_box.addActionListener((java.awt.event.ActionEvent evt) -> {
            net.freerouting.freeroute.board.Unit new_unit = (net.freerouting.freeroute.board.Unit) unit_combo_box.getSelectedItem();
            board_frame.board_panel.board_handling.change_user_unit(new_unit);
            board_frame.refresh_windows();
        });

        right_toolbar.add(unit_combo_box);

        jLabel4.setMaximumSize(new java.awt.Dimension(30, 14));
        jLabel4.setPreferredSize(new java.awt.Dimension(30, 14));
        right_toolbar.add(jLabel4);

        this.add(right_toolbar, java.awt.BorderLayout.EAST);
    }

    /**
     * Sets the selected button in the menu button button group
     */
    void hilight_selected_button() {
        net.freerouting.freeroute.interactive.InteractiveState interactive_state = this.board_frame.board_panel.board_handling.get_interactive_state();
        if (interactive_state instanceof net.freerouting.freeroute.interactive.RouteMenuState) {
            this.route_button.setSelected(true);
        } else if (interactive_state instanceof net.freerouting.freeroute.interactive.DragMenuState) {
            this.drag_button.setSelected(true);
        } else if (interactive_state instanceof net.freerouting.freeroute.interactive.SelectMenuState) {
            this.select_button.setSelected(true);
        }
    }
}
