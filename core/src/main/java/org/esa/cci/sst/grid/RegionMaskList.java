/*
 * Copyright (c) 2014 Brockmann Consult GmbH (info@brockmann-consult.de)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option)
 * any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, see http://www.gnu.org/licenses/
 */

package org.esa.cci.sst.grid;

import org.esa.cci.sst.common.SpatialResolution;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A list of regions.
 *
 * @author Norman Fomferra
 */
public class RegionMaskList extends ArrayList<RegionMask> {

    public static RegionMaskList parse(String value) throws ParseException, IOException {
        final StringTokenizer stringTokenizer = new StringTokenizer(value, ";");
        final RegionMaskList regionMaskList = new RegionMaskList();

        while (stringTokenizer.hasMoreTokens()) {
            final String entry = stringTokenizer.nextToken().trim();
            final int entryNo = regionMaskList.size() + 1;
            final int pos = entry.indexOf('=');
            if (pos == -1) {
                throw new ParseException(String.format("Illegal region entry %d: is missing the '=' character.", entryNo), 0);
            }
            final String name = entry.substring(0, pos).trim();
            final String mask = entry.substring(pos + 1).trim();
            if (name.isEmpty()) {
                throw new ParseException(String.format("Illegal region entry %d: Name is empty.", entryNo), 0);
            }
            if (mask.isEmpty()) {
                throw new ParseException(String.format("Illegal region entry %d: Mask is empty.", entryNo), 0);
            }
            final String[] splits = mask.split(",");
            if (splits.length == 4) {
                fromWNES(name, splits, regionMaskList);
            } else if (splits.length == 1) {
                fromMaskFile(name, splits[0], regionMaskList);
            } else {
                throw new ParseException(String.format("Illegal region entry %d: Mask is empty.", entryNo), 0);
            }
        }
        return regionMaskList;
    }

    public static void setSpatialResolution(SpatialResolution spatialResolution) {
        RegionMask.setSpatialResolution(spatialResolution);
    }

    private static void fromWNES(String name, String[] wnes, List<RegionMask> regionMasks) throws ParseException {
        final int entryNo = regionMasks.size() + 1;
        final double west;
        final double north;
        final double east;
        final double south;
        try {
            west = Double.parseDouble(wnes[0]);
            north = Double.parseDouble(wnes[1]);
            east = Double.parseDouble(wnes[2]);
            south = Double.parseDouble(wnes[3]);
        } catch (NumberFormatException e) {
            throw new ParseException(String.format("Illegal region entry %d: Failed to parse W,N,E,S coordinates.", entryNo), 0);
        }
        if (north < south) {
            throw new ParseException(String.format("Illegal region entry %d: N must not be less than S.", entryNo), 0);
        }
        regionMasks.add(RegionMask.create(name, west, north, east, south));
    }

    private static void fromMaskFile(String name, String split, List<RegionMask> regionMasks) throws IOException, ParseException {
        final int entryNo = regionMasks.size() + 1;
        final File file = new File(split);
        if (!file.exists()) {
            throw new IOException(String.format("Illegal region entry %d: Mask file not found: %s", entryNo, file));
        }
        final char[] data = new char[(int) file.length()];
        final FileReader reader = new FileReader(file);
        try {
            int read = reader.read(data);
            if (read != data.length) {
                throw new IOException(String.format("Illegal region entry %d: Failed to read mask file: %s", entryNo, file));
            }
        } finally {
            reader.close();
        }
        regionMasks.add(RegionMask.create(name, new String(data)));
    }
}
