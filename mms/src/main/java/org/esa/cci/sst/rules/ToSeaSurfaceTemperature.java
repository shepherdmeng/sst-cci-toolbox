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

package org.esa.cci.sst.rules;

import org.esa.cci.sst.data.ColumnBuilder;
import org.esa.cci.sst.tools.Constants;

/**
 * Rescaling applicable to sea surface temperature columns.
 *
 * @author Ralf Quast
 */
final class ToSeaSurfaceTemperature extends AbstractRescalingToShort {

    ToSeaSurfaceTemperature() {
        super(0.001, 293.15);
    }

    @Override
    protected final void configureTargetColumn(ColumnBuilder targetColumnBuilder) {
        targetColumnBuilder
                .unit(Constants.UNIT_SEA_SURFACE_TEMPERATURE)
                .validMin(-22000.0)
                .validMax(31850.0)
                .longName(null);
    }
}
