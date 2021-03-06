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

import org.esa.beam.framework.datamodel.GeoCoding;
import org.esa.beam.framework.datamodel.GeoPos;
import org.esa.cci.sst.data.Item;
import org.esa.cci.sst.data.ReferenceObservation;
import org.esa.cci.sst.reader.Reader;
import org.esa.cci.sst.util.TimeUtil;
import org.postgis.Point;
import ucar.ma2.Array;
import ucar.ma2.DataType;

import java.io.IOException;

/**
 * Sets the time of the reference observation.
 *
 * @author Thomas Storm
 */
@SuppressWarnings({"ClassTooDeepInInheritanceTree", "UnusedDeclaration"})
final class ObservationTime extends AbstractImplicitRule {

    private static final DataType DATA_TYPE = DataType.INT;

    @Override
    public final Array apply(Array sourceArray, Item sourceColumn) throws RuleException {
        final Array array = Array.factory(DATA_TYPE, new int[]{1});
        final Long time = getTime();
        if (time != null) {
            array.setInt(0, TimeUtil.intMillisToSecondsSinceEpoch(time));
        } else {
            array.setInt(0, Integer.MIN_VALUE);
        }
        return array;
    }

    private Long getTime() throws RuleException {
        final Context context = getContext();
        final Reader reader = context.getObservationReader();
        if (reader == null) {
            return null;
        }
        final ReferenceObservation refObs = context.getMatchup().getRefObs();
        final int recordNo = context.getObservation().getRecordNo();
        final Point point = refObs.getPoint().getGeometry().getFirstPoint();
        final double lon = point.getX();
        final double lat = point.getY();
        final GeoPos geoPos = new GeoPos((float) lat, (float) lon);
        final long time;
        try {
            final GeoCoding geoCoding = reader.getGeoCoding(recordNo);
            final int scanLine = (int) geoCoding.getPixelPos(geoPos, null).getY();
            time = reader.getTime(recordNo, scanLine);
        } catch (IOException e) {
            throw new RuleException("Cannot read time.", e);
        }
        return time;
    }
}
