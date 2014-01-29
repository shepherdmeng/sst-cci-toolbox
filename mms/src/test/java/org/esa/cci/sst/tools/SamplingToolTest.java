package org.esa.cci.sst.tools;/*
 * Copyright (C) 2012 Brockmann Consult GmbH (info@brockmann-consult.de)
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

import org.esa.cci.sst.DatabaseTestRunner;
import org.esa.cci.sst.util.SamplingPoint;
import org.esa.cci.sst.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(DatabaseTestRunner.class)
public class SamplingToolTest {

    private SamplingTool tool;

    @Before
    public void setUp() throws Exception {
        tool = new SamplingTool();

        tool.setCommandLineArgs(new String[]{"-Dmms.sampling.count=10000"});
        tool.initialize();
    }

    @Test
    public void testCreateSamples() throws Exception {
        final List<SamplingPoint> sampleList = tool.createSamples();

        assertEquals(10000, sampleList.size());
    }

    @Test
    public void testRemoveLandSamples() throws Exception {
        final List<SamplingPoint> sampleList = tool.createSamples();
        tool.removeLandSamples(sampleList);

        assertEquals(6612, sampleList.size());
    }

    @Test
    public void testReduceClearSamples() throws Exception {
        final List<SamplingPoint> sampleList = tool.createSamples();
        tool.removeLandSamples(sampleList);
        tool.reduceClearSamples(sampleList);

        assertEquals(2973, sampleList.size());
    }

    @Test
    public void testFindSatelliteSubscenes() throws Exception {
        final List<SamplingPoint> sampleList = tool.createSamples();
        tool.removeLandSamples(sampleList);
        tool.reduceClearSamples(sampleList);
        tool.findObservations(sampleList);
        tool.findSatelliteSubscenes(sampleList);

        assertEquals(77, sampleList.size());
    }

    @Test
    public void testRemoveOverlappingAreas() throws Exception {
        final List<SamplingPoint> sampleList = tool.createSamples();
        tool.removeLandSamples(sampleList);
        tool.reduceClearSamples(sampleList);
        tool.findObservations2(sampleList);
        tool.findSatelliteSubscenes(sampleList);
        tool.removeOverlappingSamples(sampleList);

        assertEquals(77, sampleList.size());
    }

    @Test
    public void testTempSamples() throws Exception {
        final String startTimeString = "2004-06-12T00:00:00Z";
        final String stopTimeString = "2004-06-13T00:00:00Z";
        final int sampleCount = 1000;

        final SamplingTool tool = new SamplingTool();
        tool.setCommandLineArgs(new String[]{
                "-Dmms.sampling.count=" + sampleCount,
                "-Dmms.sampling.startTime=" + startTimeString,
                "-Dmms.sampling.stopTime=" + stopTimeString,
                "-Dmms.sampling.cleanup=" + false
        });
        tool.initialize();
//        if (Boolean.parseBoolean(tool.getConfiguration().getProperty("mms.sampling.cleanup"))) {
//            tool.cleanup();
//        } else if  (Boolean.parseBoolean(tool.getConfiguration().getProperty("mms.sampling.cleanupinterval"))) {
//            tool.cleanupInterval();
//        }
        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Creating samples...");
        final List<SamplingPoint> sampleList = tool.createSamples();
        System.out.println(
                TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Creating samples... " + sampleList.size());
        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Removing land samples...");
        tool.removeLandSamples(sampleList);
        System.out.println(
                TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Removing land samples..." + sampleList.size());
        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Reducing clear samples...");
        tool.reduceClearSamples(sampleList);
        System.out.println(
                TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Reducing clear samples..." + sampleList.size());
//        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Store samples to temp...");
//        tool.insertSamplesIntoTemp(sampleList);
//        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Store samples to temp..." + sampleList.size());
//        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Match with atsr.3...");
//        tool.determineOrbits();
//        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Match with atsr.3..." + sampleList.size());
        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(new Date()) + " Finding reference observations...");
        tool.findObservations2(sampleList);
        System.out.println(TimeUtil.formatCcsdsUtcMillisFormat(
                new Date()) + " Finding reference observations..." + sampleList.size());
    }

    public static void main(String[] args) throws IOException, ParseException {
        final String startTimeString = "2004-06-17T00:00:00Z";
        final String stopTimeString = "2004-06-18T00:00:00Z";
        final int sampleCount = 10000000;

        final SamplingTool tool = new SamplingTool();
        tool.setCommandLineArgs(new String[]{
                "-Dmms.sampling.count=" + sampleCount,
                "-Dmms.sampling.startTime=" + startTimeString,
                "-Dmms.sampling.stopTime=" + stopTimeString,
                "-Dmms.sampling.cleanup=" + true
        });
        tool.initialize();
        if (Boolean.parseBoolean(tool.getConfiguration().getProperty("mms.sampling.cleanup"))) {
            tool.cleanup();
        } else if (Boolean.parseBoolean(tool.getConfiguration().getProperty("mms.sampling.cleanupinterval"))) {
            tool.cleanupInterval();
        }

        System.out.println("Creating samples...");
        final List<SamplingPoint> sampleList = tool.createSamples();
        System.out.println("Creating samples... " + sampleList.size());
        System.out.println("Removing land samples...");
        tool.removeLandSamples(sampleList);
        System.out.println("Removing land samples..." + sampleList.size());
        System.out.println("Reducing clear samples...");
        tool.reduceClearSamples(sampleList);
        System.out.println("Reducing clear samples..." + sampleList.size());
        System.out.println("Finding reference observations...");
        //tool.findObservations2(sampleList);
        System.out.println("Finding reference observations..." + sampleList.size());
        System.out.println("Finding satellite sub-scenes...");
        //tool.findSatelliteSubscenes(sampleList);
        System.out.println("Finding satellite sub-scenes..." + sampleList.size());
        System.out.println("Removing overlapping areas...");
        //tool.removeOverlappingSamples(sampleList);
        System.out.println("Removing overlapping areas..." + sampleList.size());
        System.out.println("Creating matchups...");
        //tool.createMatchups(sampleList);
        System.out.println("Creating matchups..." + sampleList.size());

        //plotSamples(sampleList, "n days with (nearly) global revisit of AATSR", "sampling.png");

        /*
        final Date startTime = TimeUtil.parseCcsdsUtcFormat(startTimeString);
        final Date stopTime = TimeUtil.parseCcsdsUtcFormat(stopTimeString);

        final List<ReferenceObservation> orbits = tool.findOrbits(startTimeString, stopTimeString);
        int noOrbitsToPlot = 14;
        for (ReferenceObservation orbit : orbits) {
            final int orbitId = orbit.getId();
            List<SamplingPoint> orbitSamples = filter(sampleList, new Predicate<SamplingPoint>() {
                @Override
                public boolean apply(SamplingPoint s) {
                    return s.getReference() == orbitId;
                }
            });
            plotSamples(orbitSamples, "orbit " + orbit.getDatafile().getPath(), null);
            if (--noOrbitsToPlot <= 0) {
                break;
            }
        }
        */
    }

    public interface Predicate<T> {

        boolean apply(T type);
    }

    public static <T> List<T> filter(Collection<T> target, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T element : target) {
            if (predicate.apply(element)) {
                result.add(element);
            }
        }
        return result;
    }

    private static void plotSamples(List<SamplingPoint> sampleList, String title, String imageFilePathname) throws
                                                                                                            IOException {
        final int w = 800;
        final int h = 400;
        final BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        final JLabel label = new JLabel(new ImageIcon(image));

        final JFrame frame = new JFrame();
        frame.setTitle(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(label);
        frame.setSize(w, h);
        frame.setVisible(true);

        final Graphics2D graphics = image.createGraphics();

        for (SamplingPoint p : sampleList) {
            final double x = (p.getLon() + 180.0) / 360.0;
            final double y = (90.0 - p.getLat()) / 180.0;
            final int i = (int) (y * h);
            final int k = (int) (x * w);
            graphics.fill(new Rectangle(k, i, 1, 1));
            label.repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException ignored) {
                // ignore
            }
        }

        if (imageFilePathname != null) {
            ImageIO.write(image, "png", new File(imageFilePathname));
        }
    }
}