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
 * LogicalParts.java
 *
 * Created on 26. Maerz 2005, 06:08
 */
package net.freerouting.freeroute.library;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * The logical parts contain information for gate swap and pin swap.
 *
 * @author Alfons Wirtz
 */
@SuppressWarnings("serial")
public class LogicalParts implements java.io.Serializable, Iterable<LogicalPart> {

    /**
     * The array of logical parts
     */
    private final ArrayList<LogicalPart> part_arr = new ArrayList<>();

    /**
     * Adds a logical part to the database.
     */
    public LogicalPart add(String p_name, PartPin[] p_part_pin_arr) {
        java.util.Arrays.parallelSort(p_part_pin_arr);
        LogicalPart new_part = new LogicalPart(p_name, part_arr.size() + 1, p_part_pin_arr);
        part_arr.add(new_part);
        return new_part;
    }

    /**
     * Returns the logical part with the input name or null, if no such package
     * exists.
     */
    public LogicalPart get(String p_name) {
        for (LogicalPart curr_part : this.part_arr) {
            if (curr_part != null && curr_part.name.compareToIgnoreCase(p_name) == 0) {
                return curr_part;
            }
        }
        return null;
    }

    /**
     * Returns the count of logical parts.
     */
    public int count() {
        return part_arr.size();
    }

    @Override
    public Iterator<LogicalPart> iterator() {
        return part_arr.iterator();
    }

    @Override
    public void forEach(Consumer<? super LogicalPart> action) {
        Iterable.super.forEach(action); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Spliterator<LogicalPart> spliterator() {
        return Iterable.super.spliterator(); //To change body of generated methods, choose Tools | Templates.
    }

}
