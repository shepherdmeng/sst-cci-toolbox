#!/bin/bash
set -e

year=$1
month=$2
sensors=$3
mmdtype=$4
seltype=$5
usecase=$6

echo "`date -u +%Y%m%d-%H%M%S` selection ${year}/${month} sensors ${sensors} type ${mmdtype} ..."

${mms.home}/bin/miz-selection-tool.sh -c ${mms.home}/config/${usecase}-config.properties \
-Dmms.selection.source=${mms.archive.root}/${usecase}/${mmdtype}/${sensors}/${year}/${sensors}-${mmdtype}-${year}-${month}.nc \
-Dmms.selection.target=${mms.archive.root}/${usecase}/${mmdtype}/${sensors}/${year}/${sensors}-${seltype}-${year}-${month}.nc
