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

package org.esa.cci.sst.file;

import org.esa.cci.sst.log.SstLogging;
import org.esa.cci.sst.product.ProductType;
import org.esa.cci.sst.util.TimeUtil;

import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * A product store.
 *
 * @author Norman Fomferra
 */
public class FileStore {

    private ProductType productType;
    private String[] inputPaths;
    private FileTree fileTree;

    public static FileStore create(ProductType productType, String filenameRegex, String... inputPaths) {
        return new FileStore(productType, inputPaths, scanFiles(productType, new InputFileFilter(filenameRegex), inputPaths));
    }

    private FileStore(ProductType productType, String[] inputPaths, FileTree fileTree) {
        this.productType = productType;
        this.inputPaths = inputPaths;
        this.fileTree = fileTree;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String[] getInputPaths() {
        return inputPaths;
    }

    /**
     * Returns a partitioned list of files starting with date1 and ending 1 day before date2.
     *
     * @param date1 inclusive
     * @param date2 exclusive
     * @return a partitioned list of files starting with date1 and ending 1 day before date2
     */
    public List<FileList> getFiles(Date date1, Date date2) {
        final Calendar calendar = TimeUtil.createUtcCalendar(date1);
        final List<FileList> files = new ArrayList<FileList>();

        while (calendar.getTime().before(date2)) {
            final List<File> filesForOneDay = fileTree.get(calendar.getTime());
            if (!filesForOneDay.isEmpty()) {
                files.add(new FileList(calendar.getTime(), filesForOneDay));
            }
            calendar.add(Calendar.DATE, 1);
        }

        return files;
    }

    private static FileTree scanFiles(ProductType productType, FileFilter fileFilter, String... inputPaths) {
        final FileTree fileTree = new FileTree();
        for (String inputPath : inputPaths) {
            scanFiles(productType, fileFilter, new File(inputPath), fileTree);
        }
        return fileTree;
    }

    private static void scanFiles(ProductType productType, FileFilter fileFilter, File entry, FileTree fileTree) {
        if (entry.isDirectory()) {
            File[] files = entry.listFiles(fileFilter);
            if (files != null) {
                for (File file : files) {
                    scanFiles(productType, fileFilter, file, fileTree);
                }
            }
        } else if (entry.isFile()) {
            try {
                Date date = productType.parseDate(entry);
                if (date != null) {
                    fileTree.add(date, entry);
                } else {
                    SstLogging.getLogger().warning("Ignoring input file with unknown naming convention: " + entry.getPath());
                }
            } catch (ParseException e) {
                SstLogging.getLogger().warning("Ignoring input file because date can't be parsed from filename: " + entry.getPath());
            }
        }
    }

    private static class InputFileFilter implements FileFilter {
        private final Pattern filenamePattern;

        private InputFileFilter(String filenameRegex) {
            this.filenamePattern = Pattern.compile(filenameRegex);
        }

        @Override
        public boolean accept(File file) {
            return file.isDirectory() || file.isFile() &&
                    filenamePattern.matcher(file.getName()).matches();
        }
    }

}
