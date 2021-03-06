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

package org.esa.cci.sst.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class TimeUtil {

    /**
     * The Julian Date of 1978-01-01 00:00:00Z.
     */
    public static final double EPOCH_JD = 2440587.5;
    /**
     * The number of milliseconds per day.
     */
    public static final double MILLIS_PER_DAY = 86400000.0;
    /**
     * The number of milliseconds from 1970-01-01 00:00:00Z to 1978-01-01 00:00:00Z
     */
    public static final long MILLIS_1978 = createCalendar(1978, 0, 1, 0, 0, 0).getTimeInMillis();
    /**
     * The number of milliseconds from 1970-01-01 00:00:00Z to 1981-01-01 00:00:00Z
     */
    public static final long MILLIS_1981 = createCalendar(1981, 0, 1, 0, 0, 0).getTimeInMillis();
    /**
     * The number of seconds per day.
     */
    public static final double SECONDS_PER_DAY = 86400.0;

    private static final SimpleDateFormat CCSDS_UTC_MILLIS_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final SimpleDateFormat CCSDS_UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final SimpleDateFormat ISO_UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static final SimpleDateFormat SHORT_UTC_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat INSITU_FILE_NAME_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    static {
        final TimeZone utcTimeZone = TimeZone.getTimeZone("UTC");

        CCSDS_UTC_MILLIS_FORMAT.setTimeZone(utcTimeZone);
        CCSDS_UTC_FORMAT.setTimeZone(utcTimeZone);
        ISO_UTC_FORMAT.setTimeZone(utcTimeZone);
        SHORT_UTC_FORMAT.setTimeZone(utcTimeZone);
        INSITU_FILE_NAME_DATE_FORMAT.setTimeZone(utcTimeZone);
    }

    public static String formatCcsdsUtcFormat(Date time) {
        if (time == null) {
            return "";
        }
        return CCSDS_UTC_FORMAT.format(time);
    }

    public static String formatCcsdsUtcMillisFormat(Date time) {
        if (time == null) {
            return "";
        }
        return CCSDS_UTC_MILLIS_FORMAT.format(time);
    }

    public static Date parseCcsdsUtcFormat(String timeString) throws ParseException {
        if (timeString.length() == 20) {
            return CCSDS_UTC_FORMAT.parse(timeString);
        }
        return CCSDS_UTC_MILLIS_FORMAT.parse(timeString);
    }

    public static String formatIsoUtcFormat(Date time) {
        return ISO_UTC_FORMAT.format(time);
    }

    public static Date parseInsituFileNameDateFormat(String timeString) throws ParseException {
        return INSITU_FILE_NAME_DATE_FORMAT.parse(timeString);
    }

    public static String formatInsituFilenameFormat(Date time) {
        return INSITU_FILE_NAME_DATE_FORMAT.format(time);
    }

    public static Date parseShortUtcFormat(String timeString) throws ParseException {
        return SHORT_UTC_FORMAT.parse(timeString);
    }

    public static String formatShortUtcFormat(Date time) {
        return SHORT_UTC_FORMAT.format(time);
    }

    public static Date julianDateToDate(double julianDate) {
        return new Date((long) ((julianDate - EPOCH_JD) * MILLIS_PER_DAY));
    }

    public static double dateToSecondsSinceEpoch(Date date) {
        return (date.getTime() - MILLIS_1978) / 1000.0;
    }

    public static double julianDateToSecondsSinceEpoch(double julianDate) {
        return (julianDate - EPOCH_JD) * SECONDS_PER_DAY - MILLIS_1978 / 1000.0;
    }

    public static double toJulianDate(Date date) {
        return date.getTime() / MILLIS_PER_DAY + EPOCH_JD;
    }

    public static Date secondsSince1981ToDate(double secondsSince1981) {
        return new Date(MILLIS_1981 + (long) (secondsSince1981 * 1000.0));
    }

    public static long toSecondsSince1978(Date time) {
        return (time.getTime() - MILLIS_1978) / 1000;
    }

    public static Date secondsSince1978ToDate(double secondsSince1978) {
        return new Date(MILLIS_1978 + (long) (secondsSince1978 * 1000.0));
    }

    public static double secondsSince1981ToSecondsSinceEpoch(double secondsSince1981) {
        return secondsSince1981 + (MILLIS_1981 - MILLIS_1978) / 1000.0;
    }

    public static double millisToSecondsSinceEpoch(long millis) {
        return (millis - MILLIS_1978) / 1000.0;
    }

    public static int intMillisToSecondsSinceEpoch(long millis) {
        return (int) ((millis - MILLIS_1978) / 1000);
    }

    /**
     * Checks if an objective time falls inside a time interval, while taking into account
     * a certain tolerance.
     *
     * @param time      The objective time.
     * @param start     The start of the time interval.
     * @param end       The end of the time interval.
     * @param timeDelta The tolerance (seconds).
     * @return {@code true} if the objective time falls within the time interval (taking into
     * account the tolerance), {@code false} otherwise.
     */
    public static boolean checkTimeOverlap(Date time, Date start, Date end, double timeDelta) {
        final double deltaInMillis = timeDelta * 1000.0;
        return time.getTime() + deltaInMillis >= start.getTime() &&
                time.getTime() - deltaInMillis < end.getTime();
    }

    public static double getTimeDifferenceInSeconds(Date d, Date d2) {
        return Math.abs(d.getTime() - d2.getTime()) / 1000.0;
    }

    public static Date getCenterTime(Date startTime, Date endTime) {
        return new Date((startTime.getTime() + endTime.getTime()) / 2);
    }

    public static double getTimeRadius(Date startTime, Date endTime) {
        return Math.abs(endTime.getTime() - startTime.getTime()) / 2000.0;
    }

    private static Calendar createCalendar(int year, int month, int date, int hour, int minute, int second) {
        final Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
        c.clear();
        c.set(year, month, date, hour, minute, second);
        return c;
    }

    public static Calendar createCalendarAtBeginningOfYear(int year) {
        final GregorianCalendar calendar = createUtcCalendar();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar;
    }

    public static Date getBeginningOfDay(Date day) {
        return calendarDayOf(day).getTime();
    }

    public static Date getEndOfDay(Date day) {
        final GregorianCalendar calendar = createUtcCalendar(day);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar.getTime();
    }

    public static GregorianCalendar createUtcCalendar() {
        return new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
    }

    public static GregorianCalendar createUtcCalendar(Date date) {
        final GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.ENGLISH);
        calendar.setTime(date);
        return calendar;
    }

    public static int getYear(Date date) {
        final GregorianCalendar utcCalendar = createUtcCalendar(date);
        return utcCalendar.get(Calendar.YEAR);
    }

    public static int getDayOfYear(Date date) {
        final GregorianCalendar utcCalendar = createUtcCalendar(date);
        return utcCalendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getMonth(Date date) {
        final GregorianCalendar utcCalendar = createUtcCalendar(date);
        return utcCalendar.get(Calendar.MONTH) + 1;
    }

    public static Date getBeginOfMonth(Date date) {
        final GregorianCalendar utcCalendar = createUtcCalendar(date);
        utcCalendar.set(Calendar.DAY_OF_MONTH, 1);
        return getBeginningOfDay(utcCalendar.getTime());
    }

    public static Date getEndOfMonth(Date date) {
        final GregorianCalendar utcCalendar = createUtcCalendar(date);
        final int maxDay = utcCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        utcCalendar.set(Calendar.DAY_OF_MONTH, maxDay);
        return getEndOfDay(utcCalendar.getTime());
    }

    private TimeUtil() {
        // prevent instantiation
    }

    private static GregorianCalendar calendarDayOf(Date time) {
        final GregorianCalendar calendar = createUtcCalendar();
        calendar.setTime(time);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        return calendar;
    }
}
