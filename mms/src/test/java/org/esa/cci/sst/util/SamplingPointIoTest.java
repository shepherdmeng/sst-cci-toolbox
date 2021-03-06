package org.esa.cci.sst.util;

import org.esa.cci.sst.common.InsituDatasetId;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SamplingPointIoTest {

    private static final String EMPTY_LIST_STRING = "{\"samplingPoints\":[]}";
    private static final String SINGLE_POINT_LIST_STRING = "{\"samplingPoints\":[{\"random\":0.5,\"lon\":1.0,\"lat\":2.0,\"time\":1000,\"reference\":67,\"index\":1,\"reference2\":71,\"x\":17,\"y\":11,\"insituReference\":19,\"referenceTime\":20,\"referenceLat\":21.0,\"referenceLon\":22.0,\"insituDatasetId\":\"animal\",\"datasetName\":\"jippieh\",\"reference2Time\":23}]}";

    @Test
    public void testWriteEmptyList() throws IOException {
        final ByteArrayOutputStream outputStream = createEmptyStream();
        final List<SamplingPoint> emptyList = new ArrayList<>();

        SamplingPointIO.write(emptyList, outputStream);

        assertEquals(EMPTY_LIST_STRING, outputStream.toString());
    }

    @Test
    public void testReadEmptyStream() throws IOException {
        final ByteArrayInputStream inputStream = createEmptyListStream();

        final List<SamplingPoint> emptyList = SamplingPointIO.read(inputStream);
        assertNotNull(emptyList);
        assertEquals(0, emptyList.size());
    }

    @Test
    public void testWriteSinglePointList() throws IOException {
        final ByteArrayOutputStream outputStream = createEmptyStream();
        final ArrayList<SamplingPoint> list = new ArrayList<>();

        createSinglePointList(list);

        SamplingPointIO.write(list, outputStream);
        assertEquals(SINGLE_POINT_LIST_STRING, outputStream.toString());
    }

    @Test
    public void testReadSinglePointStream() throws IOException {
        final ByteArrayInputStream inputStream = createSinglePointStream();

        final List<SamplingPoint> list = SamplingPointIO.read(inputStream);
        assertNotNull(list);
        assertEquals(1, list.size());
        final SamplingPoint point = list.get(0);
        assertEquals(0.5, point.getRandom(), 0.0);
        assertEquals(1.0, point.getLon(), 0.0);
        assertEquals(2.0, point.getLat(), 0.0);
        assertEquals(1000, point.getTime());
        assertEquals(67, point.getReference());
        assertEquals(1, point.getIndex());
        assertEquals(71, point.getReference2());
        assertEquals(17, point.getX());
        assertEquals(11, point.getY());
        assertEquals(19, point.getInsituReference());
        assertEquals(20, point.getReferenceTime());
        assertEquals(21.0, point.getReferenceLat(), 1e-8);
        assertEquals(22.0, point.getReferenceLon(), 1e-8);
    }

    @Test
    public void testWriteReadMultiPointList() throws IOException {
        final ByteArrayOutputStream outputStream = createEmptyStream();
        final List<SamplingPoint> list1 = new ArrayList<>();
        list1.add(new SamplingPoint(1.0, 1.0, 1, 0.1));
        list1.add(new SamplingPoint(2.0, 2.0, 2, 0.2));
        list1.add(new SamplingPoint(3.0, 3.0, 3, 0.3));

        SamplingPointIO.write(list1, outputStream);

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        final List<SamplingPoint> list2 = SamplingPointIO.read(inputStream);

        assertNotNull(list2);
        assertEquals(list1.size(), list2.size());

        final SamplingPoint referencePoint = list1.get(1);
        final SamplingPoint testedPoint = list2.get(1);

        assertEquals(referencePoint.getTime(), testedPoint.getTime());
        assertEquals(referencePoint.getLat(), testedPoint.getLat(), 1e-8);
        assertEquals(referencePoint.getLon(), testedPoint.getLon(), 1e-8);
        assertEquals(referencePoint.getRandom(), testedPoint.getRandom(), 1e-8);
        assertEquals(referencePoint.getReference(), testedPoint.getReference());
        assertEquals(referencePoint.getReference2(), testedPoint.getReference2());
        assertEquals(referencePoint.getX(), testedPoint.getX());
        assertEquals(referencePoint.getY(), testedPoint.getY());
    }

    private ByteArrayOutputStream createEmptyStream() {
        return new ByteArrayOutputStream();
    }

    private ByteArrayInputStream createEmptyListStream() {
        return new ByteArrayInputStream(EMPTY_LIST_STRING.getBytes());
    }

    private ByteArrayInputStream createSinglePointStream() {
        return new ByteArrayInputStream(SINGLE_POINT_LIST_STRING.getBytes());
    }

    private void createSinglePointList(List<SamplingPoint> list) {
        final SamplingPoint point = new SamplingPoint(1.0, 2.0, 1000, 0.5);
        point.setIndex(1);
        point.setReference(67);
        point.setX(17);
        point.setY(11);
        point.setReference2(71);
        point.setInsituReference(19);
        point.setReferenceTime(20);
        point.setReferenceLat(21.0);
        point.setReferenceLon(22.0);
        point.setInsituDatasetId(InsituDatasetId.animal);
        point.setDatasetName("jippieh");
        point.setReference2Time(23);
        list.add(point);
    }
}
