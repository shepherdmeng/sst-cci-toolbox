package org.esa.cci.sst.tools;

import org.esa.cci.sst.tools.samplepoint.*;
import org.esa.cci.sst.util.SamplingPoint;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class SamplingPointGenerator extends BasicTool {

    private long startTime;
    private long stopTime;
    private int halfRevisitTime;
    private int sampleCount;
    private int sampleSkip;
    private String sensorName;

    public SamplingPointGenerator() {
        super("sampling-point-generator", "1.0");
    }

    public static void main(String[] args) {
        final SamplingPointGenerator tool = new SamplingPointGenerator();
        try {
            final boolean ok = tool.setCommandLineArgs(args);
            if (!ok) {
                tool.printHelp();
                return;
            }
            tool.initialize();
            tool.run();
        } catch (ToolException e) {
            tool.getErrorHandler().terminate(e);
        } catch (Exception e) {
            tool.getErrorHandler().terminate(new ToolException(e.getMessage(), e, ToolException.UNKNOWN_ERROR));
        }
    }

    @Override
    public void initialize() {
        super.initialize();

        final Configuration config = getConfig();
        startTime = config.getDateValue(Configuration.KEY_MMS_SAMPLING_START_TIME).getTime();
        stopTime = config.getDateValue(Configuration.KEY_MMS_SAMPLING_STOP_TIME).getTime();
        halfRevisitTime = config.getIntValue(Configuration.KEY_MMS_SAMPLING_HALF_REVISIT_TIME);
        sampleCount = config.getIntValue(Configuration.KEY_MMS_SAMPLING_COUNT);
        sampleSkip = config.getIntValue(Configuration.KEY_MMS_SAMPLING_SKIP, 0);
        sensorName = config.getStringValue(Configuration.KEY_MMS_SAMPLING_SENSOR);
    }

    private void run() throws IOException {
        final SobolSamplePointGenerator generator = createSamplePointGenerator();
        final List<SamplingPoint> samples = generator.createSamples(sampleCount, sampleSkip, startTime, stopTime);

        final LandPointRemover landPointRemover = createLandPointRemover();
        landPointRemover.removeSamples(samples);

        final ClearSkyPointRemover clearSkyPointRemover = createClearSkyPointRemover();
        clearSkyPointRemover.removeSamples(samples);

        final ObservationFinder observationFinder = new ObservationFinder(getPersistenceManager());
        observationFinder.findPrimarySensorObservations(samples, sensorName, startTime, stopTime, halfRevisitTime);

        final TimeRange timeRange = new TimeRange(new Date(startTime), new Date(stopTime));
        final SamplePointExporter samplePointExporter = new SamplePointExporter(getConfig());
        samplePointExporter.setLogger(getLogger());
        samplePointExporter.export(samples, timeRange);
    }

    private SobolSamplePointGenerator createSamplePointGenerator() {
        return new SobolSamplePointGenerator();
    }

    private LandPointRemover createLandPointRemover() {
        return new LandPointRemover();
    }

    private ClearSkyPointRemover createClearSkyPointRemover() {
        return new ClearSkyPointRemover();
    }
}