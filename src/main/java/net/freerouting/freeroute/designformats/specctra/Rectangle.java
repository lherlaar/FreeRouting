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
 * Rectangle.java
 *
 * Created on 15. Mai 2004, 08:39
 */
package net.freerouting.freeroute.designformats.specctra;

import net.freerouting.freeroute.datastructures.IdentifierType;
import net.freerouting.freeroute.datastructures.IndentFileWriter;
import net.freerouting.freeroute.geometry.planar.FloatPoint;
import net.freerouting.freeroute.geometry.planar.IntBox;

/**
 * Describes a rectangle in a Specctra dsn file.
 *
 * @author alfons
 */
public class Rectangle extends Shape {

    public final double[] coor;

    /**
     * Creates a new instance of Rectangle p_coor is an array of dimension 4 and
     * contains the rectangle coordinates in the following order: lower left x,
     * lower left y, upper right x, uppper right y.
     */
    public Rectangle(Layer p_layer, double[] p_coor) {
        super(p_layer);
        coor = p_coor;
    }

    @Override
    public Rectangle bounding_box() {
        return this;
    }

    /**
     * Creates the smallest rectangle containing this rectangle and p_other
     */
    public Rectangle union(Rectangle p_other) {
        double[] result_coor = new double[4];
        result_coor[0] = Math.min(this.coor[0], p_other.coor[0]);
        result_coor[1] = Math.min(this.coor[1], p_other.coor[1]);
        result_coor[2] = Math.max(this.coor[2], p_other.coor[2]);
        result_coor[3] = Math.max(this.coor[3], p_other.coor[3]);
        return new Rectangle(this.layer, result_coor);
    }

    @Override
    public net.freerouting.freeroute.geometry.planar.Shape transform_to_board_rel(CoordinateTransform p_coordinate_transform) {
        int box_coor[] = new int[4];
        for (int i = 0; i < 4; ++i) {
            box_coor[i] = (int) Math.round(p_coordinate_transform.dsn_to_board(this.coor[i]));
        }

        IntBox result;
        if (box_coor[1] <= box_coor[3]) {
            // box_coor describe lower left and upper right corner
            result = new IntBox(box_coor[0], box_coor[1], box_coor[2], box_coor[3]);
        } else {
            // box_coor describe upper left and lower right corner
            result = new IntBox(box_coor[0], box_coor[3], box_coor[2], box_coor[1]);
        }
        return result;
    }

    @Override
    public net.freerouting.freeroute.geometry.planar.Shape transform_to_board(CoordinateTransform p_coordinate_transform) {
        double[] curr_point = new double[2];
        curr_point[0] = Math.min(coor[0], coor[2]);
        curr_point[1] = Math.min(coor[1], coor[3]);
        FloatPoint lower_left = p_coordinate_transform.dsn_to_board(curr_point);
        curr_point[0] = Math.max(coor[0], coor[2]);
        curr_point[1] = Math.max(coor[1], coor[3]);
        FloatPoint upper_right = p_coordinate_transform.dsn_to_board(curr_point);
        return new IntBox(lower_left.round(), upper_right.round());
    }

    /**
     * Writes this rectangle as a scope to an output dsn-file.
     */
    @Override
    public void write_scope(IndentFileWriter p_file, IdentifierType p_identifier) throws java.io.IOException {
        p_file.new_line();
        p_file.write("(rect ");
        p_identifier.write(this.layer.name, p_file);
        for (int i = 0; i < coor.length; ++i) {
            p_file.write(" ");
            p_file.write(Double.toString(coor[i]));
        }
        p_file.write(")");
    }

    @Override
    public void write_scope_int(IndentFileWriter p_file, IdentifierType p_identifier) throws java.io.IOException {
        p_file.new_line();
        p_file.write("(rect ");
        p_identifier.write(this.layer.name, p_file);
        for (int i = 0; i < coor.length; ++i) {
            p_file.write(" ");
            Integer curr_coor = (int) Math.round(coor[i]);
            p_file.write(curr_coor.toString());
        }
        p_file.write(")");
    }

    /**
     * Reads a rectangle scope from a Specctra dsn file. If p_layer_structure ==
     * null, only Layer.PCB and Layer.Signal are expected, no induvidual layers.
     */
    static Shape read_scope(Scanner p_scanner, LayerStructure p_layer_structure) throws ReadScopeException {
        try {
            Layer rect_layer = null;
            double rect_coor[] = new double[4];

            Object next_token = p_scanner.next_token();
            if (next_token == Keyword.PCB_SCOPE) {
                rect_layer = Layer.PCB;
            } else if (next_token == Keyword.SIGNAL) {
                rect_layer = Layer.SIGNAL;
            } else if (p_layer_structure != null) {
                if (!(next_token instanceof String)) {
                    throw new ReadScopeException("Shape.read_rectangle_scope: layer name string expected");
                }
                String layer_name = (String) next_token;
                int layer_no = p_layer_structure.get_no(layer_name);
                if (layer_no < 0 || layer_no >= p_layer_structure.arr.length) {
                    System.out.println("Shape.read_rectangle_scope: layer name " + layer_name
                            + " not found in layer structure ");
                } else {
                    rect_layer = p_layer_structure.arr[layer_no];
                }
            } else {
                rect_layer = Layer.SIGNAL;
            }
            // fill the the rectangle
            for (int i = 0; i < 4; ++i) {
                next_token = p_scanner.next_token();
                if (next_token instanceof Double) {
                    rect_coor[i] = (double) next_token;
                } else if (next_token instanceof Integer) {
                    rect_coor[i] = ((Number) next_token).doubleValue();
                } else {
                    throw new ReadScopeException("Shape.read_rectangle_scope: number expected");
                }
            }
            // overread the closing bracket

            next_token = p_scanner.next_token();
            if (next_token != Keyword.CLOSED_BRACKET) {
                throw new ReadScopeException("Shape.read_rectangle_scope ) expected");
            }
            if (rect_layer == null) {
                return null;
            }
            return new Rectangle(rect_layer, rect_coor);
        } catch (java.io.IOException e) {
            throw new ReadScopeException("Shape.read_rectangle_scope: IO error scanning file", e);
        }
    }
}
