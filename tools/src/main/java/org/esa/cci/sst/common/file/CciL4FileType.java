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

package org.esa.cci.sst.common.file;

import org.esa.cci.sst.common.AggregationContext;
import org.esa.cci.sst.common.AggregationFactory;
import org.esa.cci.sst.common.ProcessingLevel;
import org.esa.cci.sst.common.RegionalAggregation;
import org.esa.cci.sst.common.SstDepth;
import org.esa.cci.sst.common.calculator.ArithmeticMeanAccumulator;
import org.esa.cci.sst.common.calculator.NumberAccumulator;
import org.esa.cci.sst.common.calculator.RandomUncertaintyAccumulator;
import org.esa.cci.sst.common.cell.AbstractAggregationCell;
import org.esa.cci.sst.common.cell.AggregationCell;
import org.esa.cci.sst.common.cell.CellAggregationCell;
import org.esa.cci.sst.common.cell.CellFactory;
import org.esa.cci.sst.common.cell.DefaultSpatialAggregationCell;
import org.esa.cci.sst.common.cell.SpatialAggregationCell;
import org.esa.cci.sst.common.cellgrid.Grid;
import org.esa.cci.sst.regavg.MultiMonthAggregation;
import org.esa.cci.sst.regavg.SameMonthAggregation;
import org.esa.cci.sst.util.NcUtils;
import ucar.ma2.DataType;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriteable;
import ucar.nc2.Variable;

import java.awt.Rectangle;
import java.io.IOException;

/**
 * @author Norman Fomferra, Bettina Scholze
 */
public class CciL4FileType extends AbstractCciFileType {

    public final static CciL4FileType INSTANCE = new CciL4FileType();

    @Override
    public String getFilenameRegex() {
        return "\\d{14}-" + getRdac() + "-" + ProcessingLevel.L4 + "_GHRSST-SST[a-z]{3,7}-[A-Z1-2_]{3,10}-[DMLT]{2}-v\\d{1,2}\\.\\d{1}-fv\\d{1,2}\\.\\d{1}.nc";

    }

    @Override
    public AggregationContext readSourceGrids(NetcdfFile dataFile, SstDepth sstDepth, AggregationContext context) throws IOException {
        context.setSstGrid(NcUtils.readGrid(dataFile, "analysed_sst", getGridDef(), 0));
        context.setRandomUncertaintyGrid(NcUtils.readGrid(dataFile, "analysis_error", getGridDef(), 0));
        context.setSeaIceFractionGrid(NcUtils.readGrid(dataFile, "sea_ice_fraction", getGridDef(), 0));

        return context;
    }

    @Override
    public Variable[] createOutputVariables(NetcdfFileWriteable file, SstDepth sstDepth, boolean totalUncertainty,
                                            Dimension[] dims) {
        Variable sstVar = file.addVariable(String.format("sst_%s", sstDepth), DataType.FLOAT, dims);
        sstVar.addAttribute(new Attribute("units", "kelvin"));
        sstVar.addAttribute(new Attribute("long_name", String.format("mean of sst %s in kelvin", sstDepth)));
        sstVar.addAttribute(new Attribute("_FillValue", Float.NaN));

        Variable sstAnomalyVar = file.addVariable(String.format("sst_%s_anomaly", sstDepth), DataType.FLOAT, dims);
        sstAnomalyVar.addAttribute(new Attribute("units", "kelvin"));
        sstAnomalyVar.addAttribute(
                new Attribute("long_name", String.format("mean of sst %s anomaly in kelvin", sstDepth)));
        sstAnomalyVar.addAttribute(new Attribute("_FillValue", Float.NaN));

        Variable seaIceCoverageVar = file.addVariable("sea_ice_fraction", DataType.FLOAT, dims);
        seaIceCoverageVar.addAttribute(new Attribute("units", "1"));
        seaIceCoverageVar.addAttribute(new Attribute("long_name", "mean of sea ice fraction"));
        seaIceCoverageVar.addAttribute(new Attribute("_FillValue", Float.NaN));

        Variable coverageUncertaintyVar = file.addVariable("coverage_uncertainty", DataType.FLOAT, dims);
        coverageUncertaintyVar.addAttribute(new Attribute("units", "1"));
        coverageUncertaintyVar.addAttribute(new Attribute("long_name", "mean of sampling/coverage uncertainty"));
        coverageUncertaintyVar.addAttribute(new Attribute("_FillValue", Float.NaN));

        Variable analysisErrorVar = file.addVariable("analysis_error", DataType.FLOAT, dims);
        analysisErrorVar.addAttribute(new Attribute("units", "kelvin"));
        analysisErrorVar.addAttribute(new Attribute("long_name", "mean of analysis_error in kelvin"));
        analysisErrorVar.addAttribute(new Attribute("_FillValue", Float.NaN));

        return new Variable[]{
                sstVar,
                sstAnomalyVar,
                seaIceCoverageVar,
                coverageUncertaintyVar,
                analysisErrorVar,
        };
    }

    @Override
    public AggregationFactory<SameMonthAggregation> getSameMonthAggregationFactory() {
        return new AggregationFactory<SameMonthAggregation>() {
            @Override
            public SameMonthAggregation createAggregation() {
                return new L4SameMonthAggregation();
            }
        };
    }

    @Override
    public AggregationFactory<MultiMonthAggregation> getMultiMonthAggregationFactory() {
        return new AggregationFactory<MultiMonthAggregation>() {
            @Override
            public MultiMonthAggregation createAggregation() {
                return new L4MultiMonthAggregation();
            }
        };
    }

    @Override
    public CellFactory<CellAggregationCell<AggregationCell>> getTemporalAggregationCellFactory() {
        return new CellFactory<CellAggregationCell<AggregationCell>>() {
            @Override
            public L4TemporalCell createCell(int cellX, int cellY) {
                return new L4TemporalCell(cellX, cellY);
            }
        };
    }

    @Override
    public CellFactory getCellFactory(final AggregationContext context, final CellTypes cellType) {
        switch (cellType) {
            case CELL_90: {
                return new CellFactory<CellAggregationCell>() {
                    @Override
                    public L4Cell90 createCell(int cellX, int cellY) {
                        return new L4Cell90(context, cellX, cellY);
                    }
                };
            }
            case SPATIAL_CELL_5: {
                return new CellFactory<SpatialAggregationCell>() {
                    @Override
                    public L4Cell5 createCell(int cellX, int cellY) {
                        return new L4Cell5(context, cellX, cellY);
                    }
                };
            }
            default:
                throw new IllegalStateException("never come here.");
        }
    }

    private static abstract class AbstractL4Cell extends AbstractAggregationCell {

        protected final NumberAccumulator sstAccu = new ArithmeticMeanAccumulator();
        protected final NumberAccumulator sstAnomalyAccu = new ArithmeticMeanAccumulator();
        protected final NumberAccumulator analysisErrorAccu = new RandomUncertaintyAccumulator();
        protected final NumberAccumulator seaIceFractionAccu = new ArithmeticMeanAccumulator();

        private AbstractL4Cell(AggregationContext context, int x, int y) {
            super(context, x, y);
        }

        @Override
        public long getSampleCount() {
            return sstAnomalyAccu.getSampleCount();
        }

        public double computeSstAverage() {
            return sstAccu.combine();
        }

        public double computeSstAnomalyAverage() {
            return sstAnomalyAccu.combine();
        }

        public double computeAnalysisErrorAverage() {
            return analysisErrorAccu.combine();
        }

        public double computeSeaIceFractionAverage() {
            return seaIceFractionAccu.combine();
        }

        public abstract double computeCoverageUncertainty();

        @Override
        public Number[] getResults() {
            // Note: Result types must match those defined in FileType.createOutputVariables().
            return new Number[]{
                    (float) computeSstAverage(),
                    (float) computeSstAnomalyAverage(),
                    (float) computeSeaIceFractionAverage(),
                    (float) computeCoverageUncertainty(),
                    (float) computeAnalysisErrorAverage(),
            };
        }
    }

    private static class L4Cell5 extends AbstractL4Cell implements SpatialAggregationCell {

        private L4Cell5(AggregationContext coverageUncertaintyProvider, int x, int y) {
            super(coverageUncertaintyProvider, x, y);
        }

        @Override
        public double computeCoverageUncertainty() {
            return getAggregationContext().getCoverageUncertaintyProvider().calculate(this, 5.0);
        }

        @Override
        public void accumulate(AggregationContext aggregationContext, Rectangle rectangle) {

            final Grid sstGrid = aggregationContext.getSourceGrids()[0];
            final Grid analysisErrorGrid = aggregationContext.getSourceGrids()[1];
            final Grid seaIceFractionGrid = aggregationContext.getSourceGrids()[2];
            final Grid analysedSstGrid = aggregationContext.getClimatologySstGrid();
            final Grid seaCoverageGrid = aggregationContext.getSeaCoverageGrid();

            final int x0 = rectangle.x;
            final int y0 = rectangle.y;
            final int x1 = x0 + rectangle.width - 1;
            final int y1 = y0 + rectangle.height - 1;
            for (int y = y0; y <= y1; y++) {
                for (int x = x0; x <= x1; x++) {
                    final double seaCoverage = seaCoverageGrid.getSampleDouble(x, y);
                    if (seaCoverage > 0.0) {
                        sstAccu.accumulate(sstGrid.getSampleDouble(x, y), seaCoverage);
                        sstAnomalyAccu.accumulate(sstGrid.getSampleDouble(x, y) - analysedSstGrid.getSampleDouble(x, y),
                                                  seaCoverage);
                        analysisErrorAccu.accumulate(analysisErrorGrid.getSampleDouble(x, y), seaCoverage);
                    }
                    seaIceFractionAccu.accumulate(seaIceFractionGrid.getSampleDouble(x, y), 1);
                }
            }
        }
    }

    private static class L4TemporalCell extends AbstractL4Cell implements CellAggregationCell<AggregationCell> {

        private final NumberAccumulator coverageUncertaintyAccu = new RandomUncertaintyAccumulator();

        private L4TemporalCell(int x, int y) {
            super(null, x, y);
        }

        @Override
        public double computeCoverageUncertainty() {
            return coverageUncertaintyAccu.combine();
        }

        @Override
        public void accumulate(AggregationCell cell, double weight) {
            Number[] values = cell.getResults();
            //Note: know the ordering from AbstractL4Cell#getResults
            sstAccu.accumulate(values[0].floatValue(), 1);
            sstAnomalyAccu.accumulate(values[1].floatValue(), 1);
            seaIceFractionAccu.accumulate(values[2].floatValue(), 1);
            coverageUncertaintyAccu.accumulate(values[3].floatValue(), 1);
            analysisErrorAccu.accumulate(values[4].floatValue(), 1);
        }
    }

    private static class L4Cell90 extends AbstractL4Cell implements CellAggregationCell<L4Cell5> {

        // New 5-to-90 deg coverage uncertainty aggregation
        protected final NumberAccumulator coverageUncertainty5Accu = new RandomUncertaintyAccumulator();

        private L4Cell90(AggregationContext coverageUncertaintyProvider, int x, int y) {
            super(coverageUncertaintyProvider, x, y);
        }

        public double computeCoverageUncertainty5Average() {
            return coverageUncertainty5Accu.combine();
        }

        @Override
        public double computeCoverageUncertainty() {
            final double uncertainty5 = computeCoverageUncertainty5Average();
            final double uncertainty90 = getAggregationContext().getCoverageUncertaintyProvider().calculate(this, 90.0);
            return Math.sqrt(uncertainty5 * uncertainty5 + uncertainty90 * uncertainty90);
        }

        @Override
        public void accumulate(L4Cell5 cell, double seaCoverage90) {
            sstAccu.accumulate(cell.computeSstAverage(), seaCoverage90);
            sstAnomalyAccu.accumulate(cell.computeSstAnomalyAverage(), seaCoverage90);
            // New 5-to-90 deg coverage uncertainty aggregation
            coverageUncertainty5Accu.accumulate(cell.computeCoverageUncertainty(), seaCoverage90);
            analysisErrorAccu.accumulate(cell.computeAnalysisErrorAverage(), seaCoverage90);
            seaIceFractionAccu.accumulate(cell.computeSeaIceFractionAverage(), seaCoverage90);
        }
    }

    private static class L4Aggregation implements RegionalAggregation {

        protected final NumberAccumulator sstAccu = new ArithmeticMeanAccumulator();
        protected final NumberAccumulator sstAnomalyAccu = new ArithmeticMeanAccumulator();
        protected final NumberAccumulator coverageUncertaintyAccu = new RandomUncertaintyAccumulator();
        protected final NumberAccumulator analysisErrorAccu = new RandomUncertaintyAccumulator();
        protected final NumberAccumulator seaIceFractionAccu = new ArithmeticMeanAccumulator();

        @Override
        public long getSampleCount() {
            return sstAccu.getSampleCount();
        }

        public double computeSstAverage() {
            return sstAccu.combine();
        }

        public double computeSstAnomalyAverage() {
            return sstAnomalyAccu.combine();
        }

        public double computeAnalysisErrorAverage() {
            return analysisErrorAccu.combine();
        }

        public double computeCoverageUncertaintyAverage() {
            return coverageUncertaintyAccu.combine();
        }

        public double computeSeaIceFractionAverage() {
            return seaIceFractionAccu.combine();
        }

        @Override
        public Number[] getResults() {
            // Note: Result types must match those defined in FileType.createOutputVariables().
            return new Number[]{
                    (float) computeSstAverage(),
                    (float) computeSstAnomalyAverage(),
                    (float) computeSeaIceFractionAverage(),
                    (float) computeCoverageUncertaintyAverage(),
                    (float) computeAnalysisErrorAverage(),
            };
        }
    }

    private static class L4SameMonthAggregation extends L4Aggregation implements SameMonthAggregation<AbstractL4Cell> {

        @Override
        public void accumulate(AbstractL4Cell cell, double seaCoverage) {
            sstAccu.accumulate(cell.computeSstAverage(), seaCoverage);
            sstAnomalyAccu.accumulate(cell.computeSstAnomalyAverage(), seaCoverage);
            coverageUncertaintyAccu.accumulate(cell.computeCoverageUncertainty(), seaCoverage);
            analysisErrorAccu.accumulate(cell.computeAnalysisErrorAverage(), seaCoverage);
            seaIceFractionAccu.accumulate(cell.computeSeaIceFractionAverage(), 1.0);
        }
    }

    private static class L4MultiMonthAggregation extends L4Aggregation implements MultiMonthAggregation<L4Aggregation> {

        @Override
        public void accumulate(L4Aggregation aggregation) {
            sstAccu.accumulate(aggregation.computeSstAverage(), 1.0);
            sstAnomalyAccu.accumulate(aggregation.computeSstAnomalyAverage(), 1.0);
            coverageUncertaintyAccu.accumulate(aggregation.computeCoverageUncertaintyAverage(), 1.0);
            analysisErrorAccu.accumulate(aggregation.computeAnalysisErrorAverage(), 1.0);
            seaIceFractionAccu.accumulate(aggregation.computeSeaIceFractionAverage(), 1.0);
        }
    }

    @Override
    public boolean hasSynopticUncertainties() {
        return false;
    }
}
