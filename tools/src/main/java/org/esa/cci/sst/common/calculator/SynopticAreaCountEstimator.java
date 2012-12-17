/*
 * SST_cci Tools
 *
 * Copyright (C) 2011-2013 by Brockmann Consult GmbH
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

package org.esa.cci.sst.common.calculator;

import org.esa.cci.sst.regrid.auxiliary.LutForSynopticAreas;

/**
 * Eta is the estimated effective number of synoptic areas in the new grid box.
 * According to the Regridding Tool Specification equation 1.5 for synoptically correlated uncertainties.
 * <p/>
 * {@author Bettina Scholze}
 * Date: 13.09.12 10:08
 */
public abstract class SynopticAreaCountEstimator {

    private LutForSynopticAreas lutForSynopticAreas;

    public SynopticAreaCountEstimator(LutForSynopticAreas lutForSynopticAreas) {
        this.lutForSynopticAreas = lutForSynopticAreas;
    }

    /**
     * Calculates eta, the parameter for the aggregation of synoptically correlated uncertainties.
     *
     * @param x           cell index x
     * @param y           cell index y
     * @param sampleCount valid input boxes
     * @return eta
     */
    public double calculateEta(int x, int y, long sampleCount) {

        //average time separation between each pair of input grid boxes
        double dt = lutForSynopticAreas.getDt();
        double dxy = getDxy(x, y);

        double lxy = 100.0; //km
        double lt = 1.0; //day

        double e = -0.5 * (dxy / lxy + dt / lt);
        double r = Math.exp(e);

        return sampleCount / (1 + r * (sampleCount - 1));
    }

    /**
     * dxy average distance in space between each pair of input grid boxes
     *
     * @param x cell index x
     * @param y cell index y
     * @return in km
     */
    public abstract double getDxy(int x, int y);
}
