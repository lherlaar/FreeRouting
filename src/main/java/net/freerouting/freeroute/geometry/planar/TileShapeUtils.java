/*
 * Copyright (C) 2017 Robert Antoni Buj Gelonch {@literal <}rbuj{@literal @}fedoraproject.org{@literal >}
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.freerouting.freeroute.geometry.planar;

/**
 *
 * @author robert
 */
public final class TileShapeUtils {
    /**
     * creates a Simplex as intersection of the halfplanes defined by an array
     * of directed lines
     */
    public static TileShape get_instance(Line[] p_line_arr) {
        Simplex result = SimplexUtils.get_instance(p_line_arr);
        return result.simplify();
    }

    /**
     * Creates a TileShape from a Point array, who forms the corners of the
     * shape of a convex polygon. May work only for IntPoints.
     */
    public static TileShape get_instance(Point[] p_convex_polygon) {
        Line[] line_arr = new Line[p_convex_polygon.length];
        for (int j = 0; j < line_arr.length - 1; ++j) {
            line_arr[j] = new Line(p_convex_polygon[j], p_convex_polygon[j + 1]);
        }
        line_arr[line_arr.length - 1]
                = new Line(p_convex_polygon[line_arr.length - 1], p_convex_polygon[0]);
        return get_instance(line_arr);
    }

    /**
     * creates a half_plane from a directed line
     */
    public static TileShape get_instance(Line p_line) {
        Line[] lines = new Line[1];
        lines[0] = p_line;
        return SimplexUtils.get_instance(lines);
    }

    /**
     * Creates a normalized IntOctagon from the input values. For the meaning of
     * the parameter shortcuts see class IntOctagon.
     */
    public static IntOctagon get_instance(int p_lx, int p_ly, int p_rx,
            int p_uy, int p_ulx, int p_lrx,
            int p_llx, int p_urx) {
        IntOctagon oct = new IntOctagon(p_lx, p_ly, p_rx, p_uy, p_ulx,
                p_lrx, p_llx, p_urx);
        return oct.normalize();
    }

    /**
     * creates a boxlike convex shape
     */
    public static IntOctagon get_instance(int p_lower_left_x,
            int p_lower_left_y,
            int p_upper_right_x,
            int p_upper_right_y) {
        IntBox box = new IntBox(p_lower_left_x, p_lower_left_y,
                p_upper_right_x, p_upper_right_y);
        return box.to_IntOctagon();
    }

    /**
     * creates the smallest IntOctagon containing p_point
     */
    public static IntBox get_instance(Point p_point) {
        return p_point.surrounding_box();
    }
}