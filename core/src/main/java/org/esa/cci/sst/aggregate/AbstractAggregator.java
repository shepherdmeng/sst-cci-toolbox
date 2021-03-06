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

import org.esa.cci.sst.auxiliary.Climatology;
import org.esa.cci.sst.cell.CellGrid;
import org.esa.cci.sst.common.SstDepth;
import org.esa.cci.sst.common.TemporalResolution;
import org.esa.cci.sst.common.TimeStep;
import org.esa.cci.sst.file.FileStore;
import org.esa.cci.sst.file.FileType;
import org.esa.cci.sst.grid.GridDef;
import org.esa.cci.sst.grid.RegionMask;
import org.esa.cci.sst.log.SstLogging;
import org.esa.cci.sst.util.StopWatch;
import ucar.nc2.NetcdfFile;

import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * The base class for the averaging and regridding aggregators.
 *
 * @author Bettina Scholze
 * @author Ralf Quast
 */
public abstract class AbstractAggregator {

    private final FileStore fileStore;
    private final Climatology climatology;
    private final SstDepth sstDepth;
    private final FileType fileType;

    protected final Logger logger;

    protected AbstractAggregator(FileStore fileStore, Climatology climatology, SstDepth sstDepth) {
        this.fileStore = fileStore;
        this.climatology = climatology;
        this.sstDepth = sstDepth;
        this.fileType = fileStore.getProductType().getFileType();

        logger = SstLogging.getLogger();
    }

    abstract public List<? extends TimeStep> aggregate(
            Date startDate, Date endDate, TemporalResolution temporalResolution) throws IOException;

    protected final void readSourceGrids(NetcdfFile dataFile, AggregationContext context) throws IOException {
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.fine("Reading source grid(s)...");
        fileType.readSourceGrids(dataFile, sstDepth, context);

        stopWatch.stop();
        logger.fine(String.format("Reading source grid(s) took %d ms", stopWatch.getElapsedMillis()));
    }

    protected static <C extends SpatialAggregationCell> void aggregateSourcePixels(AggregationContext context,
                                                                                   RegionMask regionMask,
                                                                                   CellGrid<C> targetGrid) {
        final GridDef sourceGridDef = context.getSourceGridDef();
        final GridDef targetGridDef = regionMask.getGridDef();

        final int w = regionMask.getWidth();
        final int h = regionMask.getHeight();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if (regionMask.getSampleBoolean(x, y)) {
                    final Rectangle sourceRectangle = sourceGridDef.getGridRectangle(x, y, targetGridDef);
                    C targetCell = targetGrid.getCell(x, y);
                    if (targetCell != null) {
                        targetCell.accumulate(context, sourceRectangle);
                    } else {
                        targetCell = targetGrid.createCell(x, y);
                        targetCell.accumulate(context, sourceRectangle);
                        if (!targetCell.isEmpty()) {
                            targetGrid.setCell(targetCell);
                        }
                    }
                }
            }
        }
    }

    protected final FileStore getFileStore() {
        return fileStore;
    }

    protected final Climatology getClimatology() {
        return climatology;
    }

    protected final FileType getFileType() {
        return fileType;
    }
}
