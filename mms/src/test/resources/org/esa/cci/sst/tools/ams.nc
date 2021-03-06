CDF       
      ni    a   nj     �   time             Conventions       CF-1.0     title         JSea Surface Temperature from AMSR-E onboard AQUA, 25 km resolution, global     DSD_entry_id      USA-RSS-AMSRE-MW-L2-SST    
references        MW SSES Report version 2.0     institution       Remote Sensing Systems     contact       support@remss.com      GDS_version_id        GDS-v1.0-rev1.6    netcdf_version_id         3.5.0      creation_date         
2010-06-05     product_version       v5     history       none   platform      AQUA   sensor        AMSRE      spatial_resolution        25 km      
start_date        
2010-06-01     
start_time        00:30:56 UTC   	stop_date         
2010-06-01     	stop_time         02:15:40 UTC   southernmost_latitude         ²�)   northernmost_latitude         B�G�   westernmost_longitude         �3�q   easternmost_longitude         C4     file_quality_index              comment       none         lat                       	long_name         latitude   units         degrees_north      
_FillValue        �       >0L  �   lon                       	long_name         	longitude      units         degrees_east   
_FillValue        �       >0L >B�   time               	long_name         reference time of sst file     units         !seconds since 1981-01-01 00:00:00          |s$   sea_surface_temperature                    	   	long_name         sea surface temperature    units         kelvin     
_FillValue        �      
add_offset        C��3   scale_factor      <#�
   	valid_min         �x     	valid_max         �     coordinates       lon lat    source        REMSS       ( |s(   	sst_dtime                         	long_name         #time difference from reference time    units         second     
_FillValue        �      
add_offset               scale_factor      ?�      ( ��P   SSES_bias_error                       	long_name         SSES bias error    units         kelvin     
_FillValue        �      
add_offset               scale_factor      <#�
   	valid_min         �      	valid_max               coordinates       lon lat     � ��x   SSES_standard_deviation_error                         	long_name         SSES standard deviation    units         kelvin     
_FillValue        �      
add_offset        ?@     scale_factor      <#�
   	valid_min         �      	valid_max               coordinates       lon lat     � �/�   
wind_speed                     
   	long_name         
wind speed     units         m s-1      
_FillValue        �      
add_offset        A�     scale_factor      >L��   	valid_min         �      	valid_max               coordinates       lon lat    source        native_AMSRE_wind      dtime_from_sst_in_minutes         0       � ٻ�   rejection_flag                        	long_name         rejection flag     comment       �b0:1=SST out of range;b1:1=Sunglint;b2:1=quality of data bad;b3:1=rain detected;b4:1=ice;b5:1=wind > 20 m/s;b6:1=land;b7:1=edge of swath           coordinates       lon lat     � �G�   confidence_flag                       	long_name         confidence flag    comment      4b0:1=within 50km rain, 0.6 dif mwoisst;b1:1=within 100km rain, 0.8 dif mwoisst;b2:1=within 150km ice, 0.6 dif mwoisst;b3:1=more than 5deg dif mwoisst;b4:1=3-sigma test;b5:1=(tmi only) within 150 km of land and 0.6 warmer than mwoisst;b6:1=diurnal estimate > 0.6 warming;b7:1=diurnal estimate > 0.3 warming      coordinates       lon lat     � ���   proximity_confidence                      	long_name         proximity confidence       comment       �1=Bad, data rejected;2=Suspected Bad, data that has any confidence flags bit 0-5 thrown;3=Unprocessed proximity confidence flag, should be Good data;4=Good data       coordinates       lon lat     �_�   diurnal_amplitude                      	   	long_name         Diurnal warming amplitude      units         kelvin     
_FillValue        �      
add_offset        ?�     scale_factor      <��
   	valid_min         �      	valid_max               coordinates       lon lat    comment       non L2P core field      ���   	cool_skin                      	   	long_name         	cool skin      units         kelvin     
_FillValue        �      
add_offset        ��     scale_factor      <#�
   	valid_min         �      	valid_max               coordinates       lon lat    comment       non L2P core field      �'x