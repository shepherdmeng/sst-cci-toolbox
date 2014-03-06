package org.esa.cci.sst.tools;

import org.esa.cci.sst.tools.samplepoint.*;
import org.esa.cci.sst.util.SamplingPoint;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class SamplingPointGenerationTool extends BasicTool {

    private WorkflowContext workflowContext;

    public SamplingPointGenerationTool() {
        super("sampling-point-generator", "1.0");
    }

    public static void main(String[] args) {
        final SamplingPointGenerationTool tool = new SamplingPointGenerationTool();
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
        long startTime = config.getDateValue(Configuration.KEY_MMS_SAMPLING_START_TIME).getTime();
        long stopTime = config.getDateValue(Configuration.KEY_MMS_SAMPLING_STOP_TIME).getTime();
        int halfRevisitTime = config.getIntValue(Configuration.KEY_MMS_SAMPLING_HALF_REVISIT_TIME);
        int sampleCount = config.getIntValue(Configuration.KEY_MMS_SAMPLING_COUNT);
        int sampleSkip = config.getBigIntegerValue(Configuration.KEY_MMS_SAMPLING_SKIP, BigInteger.valueOf(0)).intValue();
        String sensorName = config.getStringValue(Configuration.KEY_MMS_SAMPLING_SENSOR);

        workflowContext = new WorkflowContext();
        workflowContext.setLogger(getLogger());
        workflowContext.setConfig(config);
        workflowContext.setPersistenceManager(getPersistenceManager());
        workflowContext.setStartTime(startTime);
        workflowContext.setStopTime(stopTime);
        workflowContext.setHalfRevisitTime(halfRevisitTime);
        workflowContext.setSensorName(sensorName);
        workflowContext.setSampleCount(sampleCount);
        workflowContext.setSampleSkip(sampleSkip);
    }

    private void run() throws IOException {
        final Workflow generatePointsWorkflow = new GenerateSobolPointsWorkflow(workflowContext);
        final List<SamplingPoint> samples = generatePointsWorkflow.execute();

        final Workflow findObservationsWorkflow = new FindObservationsWorkflow(workflowContext);
        findObservationsWorkflow.execute(samples);

        final Workflow exportSamplingPointsWorkflow = new ExportSamplingPointsWorkflow(workflowContext);
        exportSamplingPointsWorkflow.execute(samples);
    }
}