package org.esa.cci.sst.rules;


import org.esa.cci.sst.tools.Constants;
import org.esa.cci.sst.tools.mmdgeneration.ContextBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

public class InsituTimeTest {

    @Test
    public void testSetContext_historyShapeIsSetFromDimensionMap() {
        final Map<String, Integer> dimensionConfiguration = new HashMap<>();
        dimensionConfiguration.put(Constants.DIMENSION_NAME_INSITU_NTIME, 53);

        final Context context = new ContextBuilder().dimensionConfiguration(dimensionConfiguration).build();

        final InsituTime insituTime = new InsituTime();
        insituTime.setContext(context);

        assertNotNull(insituTime.historyShape);
        assertArrayEquals(new int[]{1, 53}, insituTime.historyShape);
    }
}
