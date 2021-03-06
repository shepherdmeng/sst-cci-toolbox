package org.esa.cci.sst.tools.samplepoint;

import org.esa.cci.sst.tools.overlap.PolarOrbitingPolygon;
import org.esa.cci.sst.util.SamplingPoint;
import org.esa.cci.sst.util.TimeUtil;
import org.junit.Test;
import org.postgis.Geometry;
import org.postgis.LinearRing;
import org.postgis.Point;
import org.postgis.Polygon;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ObservationFinderTest {

    @Test
    public void testFindDualSensorMatchups() throws ParseException {
        final long startTime = TimeUtil.parseCcsdsUtcFormat("2003-01-01T00:00:00Z").getTime();
        final long stopTime = TimeUtil.parseCcsdsUtcFormat("2003-01-02T00:00:00Z").getTime();
        final List<SamplingPoint> samples = new SobolSamplePointGenerator().createSamples(10000, 0, startTime,
                                                                                          stopTime);

        assertEquals(10000, samples.size());

        final PolarOrbitingPolygon orbitPolygonAatsr = createAtsrPolygon(1);
        final int seventeenDaysTwelveHoursInMillisHalf = 86400 * 175 / 20 * 1000;
        ObservationFinder.findObservations(samples, seventeenDaysTwelveHoursInMillisHalf,
                                           seventeenDaysTwelveHoursInMillisHalf, true, orbitPolygonAatsr);

        assertEquals(518, samples.size());

        final PolarOrbitingPolygon orbitPolygonAtsr2 = createAtsr2Polygon();

        final int twelfAndAHalfHour = 90000 * 500;
        ObservationFinder.findObservations(samples, twelfAndAHalfHour, twelfAndAHalfHour, false, orbitPolygonAtsr2);

        assertEquals(471, samples.size());
    }


    @Test
    public void testFindObservations_withNoPolygon() throws Exception {
        final List<SamplingPoint> samples = new ArrayList<>();
        samples.add(new SamplingPoint());

        final int seventeenDaysTwelveHoursInMillisHalf = 86400 * 175 / 10 * 1000;
        ObservationFinder.findObservations(samples, seventeenDaysTwelveHoursInMillisHalf,
                                           seventeenDaysTwelveHoursInMillisHalf, true);

        assertTrue(samples.isEmpty());
    }

    @Test
    public void testAssignToSamplingPoint_primary() throws ParseException {
        final PolarOrbitingPolygon polygon = createAtsrPolygon(12);
        final SamplingPoint samplingPoint = new SamplingPoint();

        ObservationFinder.assignToSamplingPoint(true, samplingPoint, polygon);

        assertEquals(12, samplingPoint.getReference());
        assertEquals(1041384411000L, samplingPoint.getReferenceTime());
    }

    @Test
    public void testAssignToSamplingPoint_secondary() throws ParseException {
        final PolarOrbitingPolygon polygon = createAtsrPolygon(14);
        final SamplingPoint samplingPoint = new SamplingPoint();

        ObservationFinder.assignToSamplingPoint(false, samplingPoint, polygon);

        assertEquals(14, samplingPoint.getReference2());
        assertEquals(1041384411000L, samplingPoint.getReference2Time());
    }

    @Test
    public void testSetGetSensorName_parameterObject() {
        final ObservationFinder.Parameter parameter = new ObservationFinder.Parameter();
        final String sensorName_1 = "schnuckelchen";
        final String sensorName_2 = "bratz";

        parameter.setSensorName(sensorName_1);
        assertEquals(sensorName_1, parameter.getSensorName());

        parameter.setSensorName(sensorName_2);
        assertEquals(sensorName_2, parameter.getSensorName());
    }

    @Test
    public void testSetGetStartTime_parameterObject() {
        final ObservationFinder.Parameter parameter = new ObservationFinder.Parameter();
        final long start_1 = 78;
        final long start_2 = 554;

        parameter.setStartTime(start_1);
        assertEquals(start_1, parameter.getStartTime());

        parameter.setStartTime(start_2);
        assertEquals(start_2, parameter.getStartTime());
    }

    @Test
    public void testSetGetStopTime_parameterObject() {
        final ObservationFinder.Parameter parameter = new ObservationFinder.Parameter();
        final long stop_1 = 86;
        final long stop_2 = 754;

        parameter.setStopTime(stop_1);
        assertEquals(stop_1, parameter.getStopTime());

        parameter.setStopTime(stop_2);
        assertEquals(stop_2, parameter.getStopTime());
    }

    @Test
    public void testBinarySearch() throws Exception {
        final long[] values = {2, 3, 4, 6, 9, 11, 12};

        // value in array
        assertEquals(0, ObservationFinder.binarySearch(values, 2));
        assertEquals(1, ObservationFinder.binarySearch(values, 3));
        assertEquals(2, ObservationFinder.binarySearch(values, 4));
        assertEquals(3, ObservationFinder.binarySearch(values, 6));
        assertEquals(4, ObservationFinder.binarySearch(values, 9));
        assertEquals(5, ObservationFinder.binarySearch(values, 11));
        assertEquals(6, ObservationFinder.binarySearch(values, 12));

        // value in range of array values
        assertEquals(2, ObservationFinder.binarySearch(values, 5));
        assertEquals(3, ObservationFinder.binarySearch(values, 7));
        assertEquals(3, ObservationFinder.binarySearch(values, 8));
        assertEquals(4, ObservationFinder.binarySearch(values, 10));

        // values greater than the greatest value in array
        assertEquals(6, ObservationFinder.binarySearch(values, 13));
        assertEquals(6, ObservationFinder.binarySearch(values, 14));

        // values less than the least value in array
        assertEquals(-1, ObservationFinder.binarySearch(values, 0));
        assertEquals(-1, ObservationFinder.binarySearch(values, 1));
    }

    @Test
    public void testSetGetSearchTimeFuture_parameterObject() {
        final ObservationFinder.Parameter parameter = new ObservationFinder.Parameter();
        final int future_1 = 83;
        final int future_2 = 713;

        parameter.setSearchTimeFuture(future_1);
        assertEquals(future_1, parameter.getSearchTimeFuture());

        parameter.setSearchTimeFuture(future_2);
        assertEquals(future_2, parameter.getSearchTimeFuture());
    }

    @Test
    public void testSetGetSearchTimePast_parameterObject() {
        final ObservationFinder.Parameter parameter = new ObservationFinder.Parameter();
        final int past_1 = 72;
        final int past_2 = 513;

        parameter.setSearchTimePast(past_1);
        assertEquals(past_1, parameter.getSearchTimePast());

        parameter.setSearchTimePast(past_2);
        assertEquals(past_2, parameter.getSearchTimePast());
    }

    @Test
    public void testGetPointTime_primarySensor() {
        final SamplingPoint samplingPoint = new SamplingPoint();
        samplingPoint.setTime(8756);
        samplingPoint.setReferenceTime(6654);

        assertEquals(8756, ObservationFinder.getPointTime(samplingPoint, true));
        assertEquals(6654, ObservationFinder.getPointTime(samplingPoint, false));
    }

    private static PolarOrbitingPolygon createPolarOrbitingPolygon(String time, Point[] points, int id) throws
                                                                                                        ParseException {
        final Geometry orbitGeometryAatsr = new Polygon(new LinearRing[]{new LinearRing(points)});
        final long orbitTimeAatsr = TimeUtil.parseCcsdsUtcFormat(time).getTime();

        return new PolarOrbitingPolygon(id, orbitTimeAatsr, orbitGeometryAatsr);
    }

    private static PolarOrbitingPolygon createAtsr2Polygon() throws ParseException {
        return createPolarOrbitingPolygon(
                "2003-01-01T01:57:44Z", new Point[]{
                        new Point(-54.5425491333008, -0.572052896022797),
                        new Point(-54.4460601806641, -0.550578653812408),
                        new Point(-53.5689125061035, -0.355294853448868),
                        new Point(-52.6917915344238, -0.159927800297737),
                        new Point(-51.8146934509277, 0.0354748256504536),
                        new Point(-50.9375953674316, 0.23087166249752),
                        new Point(-50.0604705810547, 0.426214307546616),
                        new Point(-50.0702362060547, 0.4706851541996),
                        new Point(-53.1910018920898, 14.7926692962646),
                        new Point(-56.4491729736328, 29.0929718017578),
                        new Point(-60.2450637817383, 43.3312950134277),
                        new Point(-65.5074462890625, 57.4486045837402),
                        new Point(-75.7172698974609, 71.2531127929688),
                        new Point(-119.207054138184, 82.8917922973633),
                        new Point(149.968399047852, 77.5372314453125),
                        new Point(132.138977050781, 64.185546875),
                        new Point(125.307220458984, 50.1730575561523),
                        new Point(120.986190795898, 35.9814834594727),
                        new Point(117.535377502441, 21.7041492462158),
                        new Point(114.385704040527, 7.38590812683105),
                        new Point(111.226890563965, -6.93340396881104),
                        new Point(107.778221130371, -21.2095718383789),
                        new Point(103.628204345703, -35.3865623474121),
                        new Point(97.9136199951172, -49.3706359863281),
                        new Point(88.1723327636719, -62.9155731201172),
                        new Point(64.3462295532227, -74.9117050170898),
                        new Point(-0.719147861003876, -78.5744247436523),
                        new Point(-42.3153495788574, -68.9833602905273),
                        new Point(-56.4116744995117, -55.9015579223633),
                        new Point(-63.5322647094727, -42.0764083862305),
                        new Point(-68.2671737670898, -27.9763069152832),
                        new Point(-71.9818801879883, -13.7406215667725),
                        new Point(-75.2396697998047, 0.562504291534424),
                        new Point(-75.3361587524414, 0.541033387184143),
                        new Point(-76.2133102416992, 0.345777601003647),
                        new Point(-77.0904388427734, 0.150440484285355),
                        new Point(-77.9675445556641, -0.0449291430413723),
                        new Point(-78.8446502685547, -0.240291088819504),
                        new Point(-79.7217788696289, -0.435596495866776),
                        new Point(-79.7120208740234, -0.48006871342659),
                        new Point(-76.592903137207, -14.7943773269653),
                        new Point(-73.3392181396484, -29.0711994171143),
                        new Point(-69.5538940429688, -43.272159576416),
                        new Point(-64.3218612670898, -57.3415679931641),
                        new Point(-54.2479057312012, -71.0957260131836),
                        new Point(-12.1913080215454, -82.7785720825195),
                        new Point(79.5451202392578, -77.7916793823242),
                        new Point(97.8643035888672, -64.5309295654297),
                        new Point(104.789825439453, -50.5793495178223),
                        new Point(109.141784667969, -36.4353103637695),
                        new Point(112.604019165039, -22.1912460327148),
                        new Point(115.755722045898, -7.89001798629761),
                        new Point(118.909690856934, 6.4298620223999),
                        new Point(122.345634460449, 20.7243576049805),
                        new Point(126.469863891602, 34.936595916748),
                        new Point(132.129837036133, 48.9719085693359),
                        new Point(141.73063659668, 62.5867958068848),
                        new Point(165.065765380859, 74.7028274536133),
                        new Point(-130.181671142578, 78.6472015380859),
                        new Point(-87.7167053222656, 69.1272735595703),
                        new Point(-73.4382705688477, 56.0059623718262),
                        new Point(-66.2723083496094, 42.1353034973145),
                        new Point(-61.5223236083984, 27.99831199646),
                        new Point(-57.8020133972168, 13.7390441894531),
                        new Point(-54.5425491333008, -0.572052896022797)
                },
                1
        );
    }

    private PolarOrbitingPolygon createAtsrPolygon(int id) throws ParseException {
        return createPolarOrbitingPolygon(
                "2003-01-01T01:26:51Z", new Point[]{
                        new Point(-52.6307830810547, -9.38224411010742),
                        new Point(-52.5328979492188, -9.36125659942627),
                        new Point(-51.6435508728027, -9.16922187805176),
                        new Point(-50.7551651000977, -8.97500228881836),
                        new Point(-49.8677253723145, -8.77864646911621),
                        new Point(-48.9812316894531, -8.58020401000977),
                        new Point(-48.0956535339355, -8.37972354888916),
                        new Point(-48.134349822998, -8.21097278594971),
                        new Point(-51.5582008361816, 7.26080989837646),
                        new Point(-54.9689636230469, 22.7320823669434),
                        new Point(-58.7682762145996, 38.1525917053223),
                        new Point(-63.7668418884277, 53.4622344970703),
                        new Point(-72.7942047119141, 68.5031814575195),
                        new Point(-108.159439086914, 81.9055633544922),
                        new Point(151.44416809082, 78.0440902709961),
                        new Point(131.763916015625, 63.6392631530762),
                        new Point(124.699699401855, 48.4737930297852),
                        new Point(120.240562438965, 33.1171760559082),
                        new Point(116.625686645508, 17.6722431182861),
                        new Point(113.248237609863, 2.19256854057312),
                        new Point(109.745918273926, -13.2703638076782),
                        new Point(105.721504211426, -28.656120300293),
                        new Point(100.447036743164, -43.8770751953125),
                        new Point(91.9739379882812, -58.7354774475098),
                        new Point(72.5303955078125, -72.4156875610352),
                        new Point(10.4324769973755, -79.0926818847656),
                        new Point(-40.6953125, -69.8626251220703),
                        new Point(-56.4975738525391, -55.786262512207),
                        new Point(-64.0283355712891, -40.8267250061035),
                        new Point(-68.9568481445312, -25.5644588470459),
                        new Point(-72.8340148925781, -10.159649848938),
                        new Point(-76.2830581665039, 5.30880546569824),
                        new Point(-79.675895690918, 20.7832908630371),
                        new Point(-79.7792358398438, 20.7626857757568),
                        new Point(-80.7174301147461, 20.572509765625),
                        new Point(-81.6532745361328, 20.3772563934326),
                        new Point(-82.5867309570312, 20.1769981384277),
                        new Point(-83.5177764892578, 19.9717922210693),
                        new Point(-84.4463882446289, 19.7617034912109),
                        new Point(-84.4034118652344, 19.5933151245117),
                        new Point(-80.7428207397461, 4.14061832427979),
                        new Point(-77.3561782836914, -11.3327980041504),
                        new Point(-73.8936462402344, -26.7733364105225),
                        new Point(-69.9009094238281, -42.1326675415039),
                        new Point(-64.3277053833008, -57.3445358276367),
                        new Point(-52.893238067627, -72.1763687133789),
                        new Point(6.14448070526123, -83.629264831543),
                        new Point(86.5661315917969, -74.732780456543),
                        new Point(100.602157592773, -60.0559158325195),
                        new Point(106.710418701172, -44.882640838623),
                        new Point(110.877311706543, -29.5417423248291),
                        new Point(114.394432067871, -14.1100854873657),
                        new Point(117.76961517334, 1.36336731910706),
                        new Point(121.353149414062, 16.8264331817627),
                        new Point(125.5869140625, 32.2136306762695),
                        new Point(131.365814208984, 47.4206657409668),
                        new Point(141.313705444336, 62.1804046630859),
                        new Point(167.103713989258, 75.2029571533203),
                        new Point(-121.469955444336, 77.9336624145508),
                        new Point(-83.5459747314453, 66.4741058349609),
                        new Point(-70.943603515625, 51.9940490722656),
                        new Point(-64.3290557861328, 36.8811950683594),
                        new Point(-59.757080078125, 21.5366725921631),
                        new Point(-56.0327072143555, 6.09133005142212),
                        new Point(-52.6307830810547, -9.38224411010742)
                },
                id
        );
    }
}
