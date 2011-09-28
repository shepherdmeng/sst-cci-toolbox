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

package org.esa.cci.sst;

import org.esa.cci.sst.orm.PersistenceManager;
import org.esa.cci.sst.tools.Constants;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * A test runner used for unit tests that require JPA persistence.
 *
 * @author Ralf Quast
 */
public class QueriesTestRunner extends BlockJUnit4ClassRunner {

    private static boolean canPersist;

    public QueriesTestRunner(Class<?> klass) throws InitializationError {
        super(klass);
        checkIfPersistenceIsAvailable(klass);
    }


    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        if (canPersist) {
            super.runChild(method, notifier);
        } else {
            notifier.fireTestIgnored(describeChild(method));
        }
    }

    private static void checkIfPersistenceIsAvailable(Class<?> klass) {
        final Properties configuration = new Properties();

        InputStream is = null;
        try {
            is = new FileInputStream("mms-config.properties");
            configuration.load(is);
        } catch (IOException e) {
            System.out.println("Test" + klass.getName() + " suppressed - no configuration file found.");
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {
                }
            }
        }
        try {
            @SuppressWarnings({"UnusedDeclaration"})
            final PersistenceManager pm = new PersistenceManager(Constants.PERSISTENCE_UNIT_NAME, configuration);
            canPersist = true;
        } catch (Throwable t) {
            System.out.println("Test" + klass.getName() + " suppressed - JPA persistence not available.");
            t.printStackTrace();
        }
    }
}