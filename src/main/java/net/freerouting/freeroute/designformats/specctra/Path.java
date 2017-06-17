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
 * Path.java
 *
 * Created on 30. Juni 2004, 09:28
 */
package net.freerouting.freeroute.designformats.specctra;

import net.freerouting.freeroute.datastructures.IdentifierType;
import net.freerouting.freeroute.datastructures.IndentFileWriter;

/**
 * Class for writing path scopes from dsn-files.
 *
 * @author alfons
 */
public abstract class Path extends Shape {

    static Path read_scope(Scanner p_scanner, LayerStructure p_layer_structure) throws ReadScopeException {
        throw new UnsupportedOperationException();
    }

    public final double width;
    public final double[] coordinate_arr;

    /**
     * Creates a new instance of Path
     */
    Path(LayerInfo p_layer, double p_width, double[] p_coordinate_arr) {
        super(p_layer);
        width = p_width;
        coordinate_arr = p_coordinate_arr;
    }

    /**
     * Writes this path as a scope to an output dsn-file.
     */
    @Override
    public abstract void write_scope(IndentFileWriter p_file, IdentifierType p_identifier) throws java.io.IOException;

}
