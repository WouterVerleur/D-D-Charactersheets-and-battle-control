/*
 * Copyright (C) 2018 Wouter
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
package com.wouter.dndbattle.utils;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JProgressBar;
import javax.swing.Painter;

/**
 *
 * @author Wouter
 */
public class PbPainter implements Painter<JProgressBar> {

    private static final int offset = 1;

    private final Color color;

    public PbPainter(Color color) {
        this.color = color;
    }

    @Override
    public void paint(Graphics2D g, JProgressBar pb, int width, int height) {
        g.setColor(color);
        g.fillRect(offset, offset, width - 2 * offset, height - 2 * offset);
    }

}
