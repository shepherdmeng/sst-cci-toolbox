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

package org.esa.beam.dataio.cci.sst;

import org.esa.beam.framework.dataio.DecodeQualification;
import org.esa.beam.framework.dataio.ProductReaderPlugIn;
import org.esa.beam.util.io.BeamFileFilter;

import java.io.File;
import java.util.Locale;

public class NcAaiProductReaderPlugIn implements ProductReaderPlugIn {

    public static final String FORMAT_NAME = "AAI-NC";

    @Override
    public NcAaiProductReader createReaderInstance() {
        return new NcAaiProductReader(this);
    }

    @Override
    public DecodeQualification getDecodeQualification(Object input) {
        final File file = new File(input.toString());
        if (file.getName().matches("aai_[0-9]{8}.*\\.nc")) {
            return DecodeQualification.INTENDED;
        }
        return DecodeQualification.UNABLE;
    }

    @Override
    public Class[] getInputTypes() {
        return new Class[]{File.class, String.class};
    }

    @Override
    public String[] getDefaultFileExtensions() {
        return new String[]{".nc"};
    }

    @Override
    public String getDescription(Locale locale) {
        return "Metop-A/GOME-2 Absorbing Aerosol Index";
    }

    @Override
    public String[] getFormatNames() {
        return new String[]{FORMAT_NAME};
    }

    @Override
    public BeamFileFilter getProductFileFilter() {
        return new BeamFileFilter(getFormatNames()[0], getDefaultFileExtensions(), getDescription(null));
    }
}
