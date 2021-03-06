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

package org.esa.cci.sst.tools.regrid;

import org.esa.cci.sst.grid.*;
import org.esa.cci.sst.log.SstLogging;
import org.esa.cci.sst.netcdf.NcTools;
import org.esa.cci.sst.util.StopWatch;
import ucar.nc2.NetcdfFile;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Lookup table for standard deviation (LUT1) used for calculating coverage uncertainties.
 *
 * @author Bettina Scholze
 * @author Ralf Quast
 */
final class RegriddingLUT1 implements LUT {

    private static final Logger logger = SstLogging.getLogger();
    private static final GridDef SOURCE_GRID_DEF = GridDef.createGlobal(0.05);

    private final Grid grid;

    private RegriddingLUT1(Grid grid) {
        this.grid = grid;
    }

    public static LUT create(File file, GridDef gridDef) throws IOException {
        Grid grid = readGrid(file);
        if (SOURCE_GRID_DEF.getResolution() != gridDef.getResolution()) {
            logger.fine("Downscaling regridding LUT1");
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            grid = Downscaling.create(grid, gridDef);

            stopWatch.stop();
            logger.fine(String.format("Downscaling regridding LUT1 took %d ms", stopWatch.getElapsedMillis()));
        }
        return new RegriddingLUT1(YFlip.create(grid));
    }

    @Override
    public Grid getGrid() {
        return grid;
    }

    static Grid readGrid(File file) throws IOException {
        logger.info(String.format("Opening file for regridding LUT1 '%s'", file.getPath()));

        final NetcdfFile gridFile = NetcdfFile.open("file:" + file.getPath());
        try {
            logger.fine("Reading 'analysed_sst_anomaly'...");
            final StopWatch stopWatch = new StopWatch();
            stopWatch.start();

            final Grid grid = NcTools.readGrid(gridFile, "analysed_sst_anomaly", SOURCE_GRID_DEF, 0);

            stopWatch.stop();
            logger.fine(String.format("Reading 'analysed_sst_anomaly' took %d ms", stopWatch.getElapsedMillis()));
            return grid;
        } finally {
            try {
                logger.fine("Closing file for regridding LUT1");
                gridFile.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
