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

package org.esa.cci.sst.product;

import org.esa.cci.sst.common.ProcessingLevel;
import org.esa.cci.sst.file.FileType;
import org.esa.cci.sst.grid.GridDef;

import java.io.File;
import java.text.ParseException;
import java.util.Date;

/**
 * Represents the product types handled by the SST tools.
 *
 * @author Norman Fomferra
 * @author Bettina Scholze
 * @author Ralf Quast
 */
public enum ProductType {
    ARC_L3U(ArcL3FileType.INSTANCE, ProcessingLevel.L3U),
    CCI_L2P(CciL2FileType.INSTANCE, ProcessingLevel.L2P),
    CCI_L3U(CciL3FileType.INSTANCE, ProcessingLevel.L3U),
    CCI_L3C(CciL3FileType.INSTANCE, ProcessingLevel.L3C),
    CCI_L4(CciL4FileType.INSTANCE, ProcessingLevel.L4);

    private final FileType fileType;
    private final ProcessingLevel processingLevel;

    private ProductType(FileType fileType, ProcessingLevel processingLevel) {
        this.fileType = fileType;
        this.processingLevel = processingLevel;
    }

    public FileType getFileType() {
        return fileType;
    }

    public Date parseDate(File file) throws ParseException {
        return getFileType().parseDate(file.getName());
    }

    public String getDefaultFilenameRegex() {
        return getFileType().getFilenameRegex();
    }

    public GridDef getGridDef() {
        return getFileType().getGridDef();
    }

    public ProcessingLevel getProcessingLevel() {
        return processingLevel;
    }
}
