/*
 * Copyright (C) 2011 Brockmann Consult GmbH (info@brockmann-consult.de)
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

package org.esa.cci.sst.data;

/**
 * Public {@link Column} API.
 *
 * @author Ralf Quast.
 */
public interface Item {

    String getName();

    String getType();

    boolean isUnsigned();

    int getRank();

    String getDimensions();

    String getUnit();

    String getFlagMasks();

    String getFlagMeanings();

    String getFlagValues();

    Number getAddOffset();

    Number getScaleFactor();

    Number getFillValue();

    String getStandardName();

    Number getValidMin();

    Number getValidMax();

    String getLongName();

    Sensor getSensor();

    String getRole();
}
