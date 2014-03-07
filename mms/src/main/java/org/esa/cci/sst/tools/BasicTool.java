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

package org.esa.cci.sst.tools;

import org.apache.commons.cli.*;
import org.esa.beam.framework.gpf.GPF;
import org.esa.beam.util.logging.BeamLogManager;
import org.esa.cci.sst.orm.PersistenceManager;
import org.esa.cci.sst.orm.Storage;
import org.esa.cci.sst.util.TimeUtil;

import javax.media.jai.JAI;
import javax.persistence.Query;
import java.io.*;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.logging.*;

/**
 * The base class for all MMS command line tools.
 *
 * @author Martin Boettcher
 * @author Norman Fomferra
 * @author Ralf Quast
 */
public abstract class BasicTool {

    private Storage toolStorage;

    static class SstLogFormatter extends Formatter {

        private static final String LINE_SEPARATOR = System.getProperty("line.separator");

        @Override
        public String format(LogRecord record) {
            final String time = TimeUtil.formatCcsdsUtcMillisFormat(new Date(record.getMillis()));
            final StringBuilder sb = new StringBuilder(time);
            sb.append(" ")
                    .append(record.getLevel().getLocalizedName())
                    .append(": ")
                    .append(formatMessage(record))
                    .append(LINE_SEPARATOR);

            final Throwable thrown = record.getThrown();
            if (thrown != null) {
                try {
                    final StringWriter sw = new StringWriter();
                    final PrintWriter pw = new PrintWriter(sw);
                    thrown.printStackTrace(pw);
                    pw.close();
                    sb.append(sw.toString());
                } catch (Exception ignored) {
                    // ignore
                }
            }

            return sb.toString();
        }
    }


    public static final String CONFIG_FILE_OPTION_NAME = "c";
    public static final String DEFAULT_CONFIGURATION_FILE_NAME = "mms-config.properties";

    private final String name;
    private final String version;
    private final Configuration config;
    private final Options options;

    private Logger logger;
    private ErrorHandler errorHandler;

    private boolean initialised;
    private PersistenceManager persistenceManager;

    private Date sourceStartTime;
    private Date sourceStopTime;

    static {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        Locale.setDefault(Locale.ENGLISH);

        System.setProperty("EPSG-HSQL.directory", System.getProperty("user.home", ".") + File.separator + "tmp");
        System.setProperty("beam.imageManager.enableSourceTileCaching", "true");

        JAI.enableDefaultTileCache();
        JAI.getDefaultInstance().getTileCache().setMemoryCapacity(1024 * 1024 * 128);

        GPF.getDefaultInstance().getOperatorSpiRegistry().loadOperatorSpis();

        for (Handler handler : BeamLogManager.getSystemLogger().getHandlers()) {
            BeamLogManager.getSystemLogger().removeHandler(handler);
        }
    }

    protected BasicTool(String name, String version) {
        this.name = name;
        this.version = version;

        options = createCommandLineOptions();

        config = new Configuration();
        config.add(System.getProperties(), "mms.");
    }

    public final String getName() {
        return name;
    }

    public Logger getLogger() {
        if (logger == null) {
            synchronized (this) {
                if (logger == null) {
                    logger = Logger.getLogger("org.esa.cci.sst");
                    logger.setLevel(Level.INFO);
                    final ConsoleHandler consoleHandler = new ConsoleHandler();
                    final Formatter formatter = new SstLogFormatter();
                    consoleHandler.setFormatter(formatter);
                    consoleHandler.setLevel(Level.INFO);
                    logger.addHandler(consoleHandler);
                }
            }
        }
        return logger;
    }

    public final ErrorHandler getErrorHandler() {
        if (errorHandler == null) {
            synchronized (this) {
                if (errorHandler == null) {
                    errorHandler = new ErrorHandler() {
                        @Override
                        public void terminate(ToolException e) {
                            getLogger().log(Level.SEVERE, e.getMessage(), e);
                            if (getLogger().isLoggable(Level.FINEST)) {
                                for (final StackTraceElement element : e.getCause().getStackTrace()) {
                                    getLogger().log(Level.FINEST, element.toString());
                                }
                            }
                            e.getCause().printStackTrace(System.err);
                            System.exit(e.getExitCode());
                        }

                        @Override
                        public void warn(Throwable t, String message) {
                            Logger.getLogger("org.esa.cci.sst").log(Level.WARNING, message, t);
                            if (getLogger().isLoggable(Level.FINEST)) {
                                for (final StackTraceElement element : t.getStackTrace()) {
                                    getLogger().log(Level.FINEST, element.toString());
                                }
                            }
                        }
                    };
                }
            }
        }
        return errorHandler;
    }

    public Configuration getConfig() {
        return config;
    }

    private void setDebug(boolean debug) {
        if (debug) {
            getLogger().setLevel(Level.ALL);
        }
    }

    private void setSilent(boolean silent) {
        if (silent) {
            getLogger().setLevel(Level.WARNING);
        }
    }

    private Options getOptions() {
        return options;
    }

    public final PersistenceManager getPersistenceManager() {
        return persistenceManager;
    }

    public final Date getSourceStartTime() {
        return sourceStartTime;
    }

    public final Date getSourceStopTime() {
        return sourceStopTime;
    }

    public final boolean setCommandLineArgs(String[] args) {
        final CommandLineParser parser = new PosixParser();
        try {
            final CommandLine commandLine = parser.parse(getOptions(), args);
            setSilent(commandLine.hasOption("silent"));
            setDebug(commandLine.hasOption("debug"));
            if (commandLine.hasOption("version")) {
                printVersion();
                return false;
            }
            if (commandLine.hasOption("help")) {
                printHelp();
                return false;
            }
            File configurationFile = (File) commandLine.getParsedOptionValue(CONFIG_FILE_OPTION_NAME);
            if (configurationFile == null) {
                configurationFile = new File(DEFAULT_CONFIGURATION_FILE_NAME);
                if (configurationFile.exists()) {
                    addConfigurationProperties(configurationFile);
                    config.put(Configuration.KEY_CONFIGURATION, configurationFile.getPath());
                }
            } else {
                addConfigurationProperties(configurationFile);
                config.put(Configuration.KEY_CONFIGURATION, configurationFile.getPath());
            }

            final Properties optionProperties = commandLine.getOptionProperties("D");
            config.add(optionProperties);
        } catch (ParseException e) {
            throw new ToolException(e.getMessage(), e, ToolException.COMMAND_LINE_ARGUMENTS_PARSE_ERROR);
        }

        return true;
    }

    public Storage getStorage() {
        return toolStorage;
    }

    public void initialize() {
        if (initialised) {
            return;
        }
        getLogger().info("connecting to database " + config.getStringValue("openjpa.ConnectionURL"));
        try {
            persistenceManager = PersistenceManager.create(Constants.PERSISTENCE_UNIT_NAME,
                    Constants.PERSISTENCE_RETRY_COUNT,
                    config.getAsProperties());
        } catch (Exception e) {
            throw new ToolException("Unable to establish database connection.", e, ToolException.TOOL_DB_ERROR);
        }
        if (config.getBooleanValue("mms.db.useindex", false)) {
            try {
                persistenceManager.transaction();
                final Query setSeqScanOff = persistenceManager.createNativeQuery("set enable_seqscan to off");
                setSeqScanOff.executeUpdate();
                persistenceManager.commit();
            } catch (Exception e) {
                getLogger().warning("failed setting seqscan to off: " + e.getMessage());
            }
        }
        toolStorage = persistenceManager.getToolStorage();
        sourceStartTime = config.getDateValue(Configuration.KEY_SOURCE_START_TIME, "1978-01-01T00:00:00Z");
        sourceStopTime = config.getDateValue(Configuration.KEY_SOURCE_STOP_TIME, "2100-01-01T00:00:00Z");

        initialised = true;
    }

    protected String getCommandLineSyntax() {
        return getName();
    }

    protected void printHelp() {
        new HelpFormatter().printHelp(getCommandLineSyntax(), "Valid options are", getOptions(), "", true);
    }

    private void addConfigurationProperties(File configurationFile) {
        FileReader reader = null;
        try {
            reader = new FileReader(configurationFile);
            config.load(reader);
        } catch (FileNotFoundException e) {
            throw new ToolException(MessageFormat.format("File not found: {0}", configurationFile), e,
                    ToolException.CONFIGURATION_FILE_NOT_FOUND_ERROR);
        } catch (IOException e) {
            throw new ToolException(MessageFormat.format("Failed to read from {0}.", configurationFile), e,
                    ToolException.CONFIGURATION_FILE_IO_ERROR);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    private void printVersion() {
        System.out.println(MessageFormat.format("Version {0}", version));
    }

    private static Options createCommandLineOptions() {
        final Option helpOpt = new Option("help", "print this message");
        final Option versionOpt = new Option("version", "print the version information and exit");
        final Option verboseOpt = new Option("verbose", "be extra verbose");
        final Option debugOpt = new Option("debug", "print debugging information");

        final Option confFileOpt = new Option(CONFIG_FILE_OPTION_NAME, "alternative configuration file");
        confFileOpt.setArgs(1);
        confFileOpt.setArgName("file");
        confFileOpt.setType(File.class);

        final Option propertyOpt = new Option("D", "use value for given property");
        propertyOpt.setValueSeparator('=');
        propertyOpt.setArgName("property=value");
        propertyOpt.setArgs(2);

        Options options = new Options();
        options.addOption(helpOpt);
        options.addOption(versionOpt);
        options.addOption(verboseOpt);
        options.addOption(debugOpt);
        options.addOption(confFileOpt);
        options.addOption(propertyOpt);

        return options;
    }
}
