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

package org.esa.cci.sst.aggregate;

import org.esa.cci.sst.cell.CellAccumulator;

/**
 * A "same month" (daily, monthly) / regional aggregation
 * that accumulates daily, monthly / 5º,90º cells ({@link SpatialAggregationCell}, {@link org.esa.cci.sst.cell.CellAggregationCell}).
 *
 * @author Norman Fomferra
 */
public interface SameMonthAggregation<C extends AggregationCell> extends RegionalAggregation, CellAccumulator<C> {
    /**
     * Accumulates the given cell using the given fractional sea coverage as weight.
     *
     * @param cell        The cell to be accumulated.
     * @param seaCoverage The fractional sea coverage.
     */
    @Override
    void accumulate(C cell, double seaCoverage);
}
