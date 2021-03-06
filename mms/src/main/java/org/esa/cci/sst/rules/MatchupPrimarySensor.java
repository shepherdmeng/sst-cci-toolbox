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
import org.esa.cci.sst.data.Item;
import org.esa.cci.sst.tools.Constants;
import ucar.ma2.Array;
import ucar.ma2.DataType;

import java.text.MessageFormat;

/**
 * Setting the matchup's primary sensor.
 *
 * @author Thomas Storm
 */
@SuppressWarnings({"ClassTooDeepInInheritanceTree", "UnusedDeclaration"})
final class MatchupPrimarySensor extends AbstractImplicitRule {

    @Override
    protected void configureTargetColumn(ColumnBuilder targetColumnBuilder, Item sourceColumn) throws RuleException {
        targetColumnBuilder
                .flagMeanings("ATSR_MD METOP_MD SEVIRI_MD AVHRR_MD dummy")
                .flagValues(new byte[]{0, 1, 2, 3});
    }

    @SuppressWarnings({"IfStatementWithTooManyBranches"})
    @Override
    public Array apply(Array sourceArray, Item sourceColumn) throws RuleException {
        final Array array = Array.factory(DataType.BYTE, new int[]{1});
        final String sensor = getContext().getMatchup().getRefObs().getSensor();
        byte flag;
        if (Constants.SENSOR_NAME_ATSR_MD.equalsIgnoreCase(sensor)) {
            flag = 0;
        } else if (Constants.SENSOR_NAME_METOP_MD.equalsIgnoreCase(sensor)) {
            flag = 1;
        } else if (Constants.SENSOR_NAME_SEVIRI_MD.equalsIgnoreCase(sensor)) {
            flag = 2;
        } else if (Constants.SENSOR_NAME_AVHRR_MD.equalsIgnoreCase(sensor)) {
            flag = 3;
        } else if (sensor != null && sensor.startsWith(Constants.SENSOR_NAME_DUMMY.substring(0, 3) + "_")) {
            flag = 4;
        } else {
            throw new RuleException(MessageFormat.format("Unknown primary sensor ''{0}''.", sensor));
        }
        array.setByte(0, flag);
        return array;
    }
}
