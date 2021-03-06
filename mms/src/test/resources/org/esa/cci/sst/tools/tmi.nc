CDF       
      ni    �   nj     h   time             Conventions       CF-1.0     title         LSea Surface Temperature from TMI onboard TRMM, 25 km resolution, tropicsDSD_   DSD_entry_id      REMSS-L2P-TMI      
references        MW SSES Report version 3.0     institution       Remote Sensing Systems     contact       support@remss.com      GDS_version_id        GDS-v1.0-rev1.6    netcdf_version_id         3.5.0      creation_date         
2011-02-13     product_version       v4     history       none   platform      TRMM   sensor        TMI    spatial_resolution        25 km      
start_date        
2010-06-01     
start_time        03:04:32 UTC   	stop_date         
2010-06-01     	stop_time         04:40:03 UTC   southernmost_latitude         �p�   northernmost_latitude         B(�   westernmost_longitude         �3�q   easternmost_longitude         C4     file_quality_index              comment       none         lat                       	long_name         latitude   units         degrees_north      
_FillValue        �       )�  |   lon                       	long_name         	longitude      units         degrees_east   
_FillValue        �       )� <\   time               	long_name         reference time of sst file     units         !seconds since 1981-01-01 00:00:00          &f<   sea_surface_temperature                    	   	long_name         sea surface temperature    units         kelvin     
_FillValue        �      
add_offset        C��3   scale_factor      <#�
   	valid_min         �x     	valid_max         �     coordinates       lon lat    source        REMSS       	�� &f@   	sst_dtime                         	long_name         #time difference from reference time    units         second     
_FillValue        �      
add_offset               scale_factor      A       	�� /�0   SSES_bias_error                       	long_name         SSES bias error    units         kelvin     
_FillValue        �      
add_offset               scale_factor      <#�
   	valid_min         �      	valid_max               coordinates       lon lat     �x 9�    SSES_standard_deviation_error                         	long_name         SSES standard deviation    units         kelvin     
_FillValue        �      
add_offset        ?@     scale_factor      <#�
   	valid_min         �      	valid_max               coordinates       lon lat     �x >Z�   
wind_speed                     
   	long_name         
wind speed     units         m s-1      
_FillValue        �      
add_offset        A�     scale_factor      >L��   	valid_min         �      	valid_max               coordinates       lon lat    source        native_TMI_wind    dtime_from_sst_in_minutes         0       �x C%   rejection_flag                        	long_name         rejection flag     comment       �b0:1=SST out of range;b1:1=Sunglint;b2:1=quality of data bad;b3:1=rain detected;b4:1=ice;b5:1=wind > 20 m/s;b6:1=land;b7:1=edge of swath           coordinates       lon lat     �x G�   confidence_flag                       	long_name         confidence flag    comment      4b0:1=within 50km rain, 0.6 dif mwoisst;b1:1=within 100km rain, 0.8 dif mwoisst;b2:1=within 150km ice, 0.6 dif mwoisst;b3:1=more than 5deg dif mwoisst;b4:1=3-sigma test;b5:1=(tmi only) within 150 km of land and 0.6 warmer than mwoisst;b6:1=diurnal estimate > 0.6 warming;b7:1=diurnal estimate > 0.3 warming      coordinates       lon lat     �x L�    proximity_confidence                      	long_name         proximity confidence       comment       �1=Bad, data rejected;2=Suspected Bad, data that has any confidence flags bit 0-5 thrown;3=Unprocessed proximity confidence flag, should be Good data;4=Good data       coordinates       lon lat     �x Q�x   diurnal_amplitude                      	   	long_name         Diurnal warming amplitude      units         kelvin     
_FillValue        �      
add_offset        ?�     scale_factor      <��
   	valid_min         �      	valid_max               coordinates       lon lat    comment       non L2P core field      �x VN�   	cool_skin                      	   	long_name         	cool skin      units         kelvin     
_FillValue        �      
add_offset        ��     scale_factor      <#�
   	valid_min         �      	valid_max               coordinates       lon lat    comment       non L2P core field      �x [h