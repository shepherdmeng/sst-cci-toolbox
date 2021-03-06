/*
 * Copyright (C) 2010 Brockmann Consult GmbH (info@brockmann-consult.de)
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

package org.esa.cci.sst.reader;

import com.bc.ceres.core.Assert;
import org.esa.cci.sst.data.ColumnBuilder;
import org.esa.cci.sst.data.DataFile;
import org.esa.cci.sst.data.Item;
import org.esa.cci.sst.data.Observation;
import org.esa.cci.sst.data.RelatedObservation;
import org.esa.cci.sst.data.Sensor;
import org.esa.cci.sst.tools.Constants;
import org.esa.cci.sst.util.IoUtil;
import org.postgis.PGgeometry;
import org.postgis.Point;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for observation readers reading from mmd files.
 *
 * @author Thomas Storm
 */
abstract class AbstractMmdReader implements ObservationReader {

    private final DataFile datafile;
    private final NetcdfFile mmd;
    private final String sensorName;

    AbstractMmdReader(DataFile datafile, final NetcdfFile mmd, final String sensorName) {
        this.datafile = datafile;
        this.mmd = mmd;
        this.sensorName = sensorName;
    }

    @Override
    public int getNumRecords() {
        Variable variable = mmd.findVariable(NetcdfFile.makeValidPathName(Constants.VARIABLE_NAME_MATCHUP_ID));
        // allow for matchup_id instead of matchup.id to support ARC2 output
        if (variable == null) {
            variable = mmd.findVariable(NetcdfFile.makeValidPathName(Constants.VARIABLE_NAME_ARC2_MATCHUP_ID));
        }
        return variable.getDimensions().get(0).getLength();
    }

    @Override
    public Item getColumn(String role) {
        final Variable variable = mmd.findVariable(NetcdfFile.makeValidPathName(role));
        if (variable != null) {
            return createColumn(variable);
        }
        return null;
    }

    @Override
    public Item[] getColumns() {
        final List<Item> items = new ArrayList<Item>();
        final List<Variable> variables = mmd.getVariables();
        for (Variable variable : variables) {
            final Item item = createColumn(variable);
            items.add(item);
        }
        return items.toArray(new Item[items.size()]);
    }

    void setupObservation(Observation observation) throws IOException {
        observation.setDatafile(datafile);
        observation.setSensor(sensorName);
    }

    void validateRecordNumber(final int recordNo) {
        if (getNumRecords() < recordNo) {
            throw new IllegalArgumentException(MessageFormat.format("Invalid record number: ''{0}''.", recordNo));
        }
    }

    void setObservationLocation(final RelatedObservation observation, int recordNo) throws IOException {
        final Variable latitudeVariable = findVariable("latitude", "lat");
        final Variable longitudeVariable = findVariable("longitude", "lon");
        Assert.state(latitudeVariable != null, "No latitude variable found.");
        Assert.state(longitudeVariable != null, "No longitude variable found.");

        final float centerLatitude = (Float) readCenterValue(recordNo, latitudeVariable);
        final float centerLongitude = (Float) readCenterValue(recordNo, longitudeVariable);

        final Point centerPoint = new Point(centerLongitude, centerLatitude);
        final PGgeometry geometry = new PGgeometry(centerPoint);
        observation.setLocation(geometry);
    }

    protected Object readCenterValue(int recordNo, Variable variable) throws IOException {
        final int dimCount = variable.getDimensions().size();
        final int[] origin = new int[dimCount];
        final int[] shape = new int[dimCount];

        origin[0] = recordNo;
        shape[0] = 1;
        for (int i = 1; i < dimCount; i++) {
            origin[i] = variable.getDimension(i).getLength() / 2;
            shape[i] = 1;
        }

        final Object centerValue;
        try {
            centerValue = variable.read(origin, shape).getObject(0);
        } catch (InvalidRangeException e) {
            throw new IOException(e);
        }
        return centerValue;
    }

    protected Variable findVariable(String... variableNames) {
        for (Variable variable : mmd.getVariables()) {
            for (String name : variableNames) {
                if (variable.getShortName().endsWith(name)) {
                    return variable;
                }
            }
        }
        return null;
    }

    private Item createColumn(final Variable variable) {
        final Sensor dataFileSensor = datafile.getSensor();
        final ColumnBuilder columnBuilder = IoUtil.createColumnBuilder(variable, sensorName);
        columnBuilder.sensor(dataFileSensor);
        return columnBuilder.build();
    }
}
