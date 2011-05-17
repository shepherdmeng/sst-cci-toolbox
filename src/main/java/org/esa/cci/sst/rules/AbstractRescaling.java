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
import ucar.ma2.Array;
import ucar.ma2.DataType;

/**
 * Abstract rescaling rule.
 *
 * @author Ralf Quast
 */
abstract class AbstractRescaling implements Rule {

    private final double targetAddOffset;
    private final double targetScaleFactor;

    protected AbstractRescaling(double targetScaleFactor, double targetAddOffset) {
        this.targetScaleFactor = targetScaleFactor;
        this.targetAddOffset = targetAddOffset;
    }

    @Override
    public final Item apply(Item sourceColumn) throws RuleException {
        final ColumnBuilder builder = new ColumnBuilder(sourceColumn);
        builder.addOffset(targetAddOffset);
        builder.scaleFactor(targetScaleFactor);
        builder.type(DataType.SHORT);
        builder.fillValue(Short.MIN_VALUE);
        configureTargetColumn(builder);

        return builder.build();
    }

    @Override
    public final Array apply(Array sourceArray, Item sourceColumn) throws RuleException {
        final Array targetArray = Array.factory(DataType.SHORT, sourceArray.getShape());
        apply(sourceArray, targetArray,
              sourceColumn.getScaleFactor(),
              sourceColumn.getAddOffset(),
              sourceColumn.getFillValue());

        return targetArray;
    }

    protected abstract void configureTargetColumn(ColumnBuilder targetColumnBuilder);

    private void apply(Array sourceArray, Array targetArray,
                       Number sourceScaleFactor,
                       Number sourceAddOffset,
                       Number sourceFillValue) {
        final double a = getDouble(sourceScaleFactor, 0.0);
        final double b = getDouble(sourceAddOffset, 0.0);
        for (int i = 0; i < sourceArray.getSize(); i++) {
            final double number = sourceArray.getDouble(i);
            if (isInvalid(number, sourceFillValue)) {
                targetArray.setShort(i, Short.MIN_VALUE);
            }
            targetArray.setShort(i, rescale(number, a, b));
        }
    }

    private boolean isInvalid(double d, Number fillValue) {
        return fillValue != null && d == fillValue.doubleValue() || Double.isNaN(d) || Double.isInfinite(d);
    }

    private short rescale(double d, double sourceScaleFactor, double sourceAddOffset) {
        return (short) (((sourceScaleFactor * d + sourceAddOffset) - targetAddOffset) / targetScaleFactor);
    }

    private double getDouble(Number number, double defaultValue) {
        if (number == null) {
            return defaultValue;
        }
        return number.floatValue();
    }
}
