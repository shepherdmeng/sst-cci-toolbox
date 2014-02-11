#!/bin/bash
# nwp-run2.sh 2003 01 atsr.3 mms2
# mms/archive/ecmwf-era-interim/v01/2003/01/...
# mms/archive/mms2/sub/atsr.3/2003/atsr.3-sub-2003-01.nc
# mms/archive/mms2/nwp/atsr.3/2003/atsr.3-nwp-2003-01.nc

year=$1
month=$2
sensor=$3
usecase=$4

pattern=`cat $MMS_HOME/config/$usecase-config.xml | awk "/mms.pattern.$sensor/ { print \\$3 }"`

# TODO - adapt path of NWP archive
nwp-tool.sh -c $usecase-config.properties \
false \
$sensor \
$pattern \
$MMS_HOME/config/mmd-dimensions.properties \
$MMS_ARCHIVE/$usecase/sub/$sensor/$year/$sensor-sub-$year-$month.nc \
$MMS_ARCHIVE/ecmwf-era-interim/v01 \
$MMS_ARCHIVE/$usecase/nwp/$sensor/$year/$sensor-nwp-$year-$month.nc