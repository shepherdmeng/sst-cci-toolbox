CDF       
      n          nx        ny        len_id        len_filename   A         identification        sssssss yyyymmdd hhmnss nnnnn      	satellite         METOP02    date      
2010/06/01        F   msr_type                	long_name         measure type   valid_range             
_FillValue        �      flag_values            flag_meanings         moored drifter ship         P�   msr_id                     	long_name         
measure id     comment       filled with trailing spaces         P�   msr_time                	long_name         measure time   standard_name         time   units         !seconds since 1981-01-01 00:00:00      comment                   P�   msr_lat                 	long_name         latitude   units         degrees_north      scale_factor      ?�z�G�{   
add_offset                   	valid_min         ��     	valid_max         #(     
_FillValue        �      C_format      %.2f        P�   msr_lon                 	long_name         	longitude      units         degrees_east   scale_factor      ?�z�G�{   
add_offset                   	valid_min         ��     	valid_max         FP     
_FillValue        �      C_format      %.2f        P�   msr_sst                 	long_name         measured sst   standard_name         sea_surface_temperature    units         K      
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment                   P�   	msr_depth                   	long_name         depth below sea/water surface      units         m      
add_offset                   scale_factor      ?�������   
_FillValue        �      C_format      %.1f   comment       from BUFR 0 07 062 (buoy only)          P�   msr_quality                 	long_name         measurement quality    
add_offset                   scale_factor      ?�������   
_FillValue        �      C_format      %.1f   comment       from BUFR 0 33 214 (buoy only)          P�   
msr_method                  	long_name         'method of water temperature measurement    valid_range             
_FillValue        �      flag_values        	
   flag_meanings         �ship_intake bucket hull_contact_sensor reversing_thermometer STD/CTD_sensor mechanical_BT expendable_BT digital_BT thermistor_chain infra-red_scanner micro_wave_scanner other     comment       from BUFR 0 02 038 (ship only)          P�   msr_air_temp                	long_name         dry-bulb temperature at 2m     standard_name         air_temperature    units         K      
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       from BUFR 0 12 195          P�   msr_dew_point_temp                  	long_name         dew-point temperature at 2m    standard_name         dew_point_temperature      units         K      
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       from BUFR 0 12 197          P�   msr_wind_speed                  	long_name         wind speed at 10m      standard_name         
wind_speed     units         m s-1      
add_offset                   scale_factor      ?�         
_FillValue        �      C_format      %.0f   comment       from BUFR 0 11 012          Q    msr_wind_direction               
   	long_name         wind direction at 10m      standard_name         wind_direction     units         degrees    
add_offset                   scale_factor      ?�         
_FillValue        �      validmin             validmax      h     C_format      %.0f   comment       from BUFR 0 11 011          Q   	box_cover                   	long_name         box cover rate     scale_factor      ?�z�G�{   
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      C_format      %.2f   comment        number sst / number water          Q   box_filename                   	long_name         source (metagranule) filename      comment       'basename of the source metagranule file       D  Q   box_center_y_coord                  	long_name         center pixel y coordinate      
_FillValue        �      	valid_min                	valid_max         �     comment                   QP   box_center_x_coord                  	long_name         center pixel x coordinate      
_FillValue        �      	valid_min                	valid_max         �     comment                   QT   dtime                      	long_name                units         seconds    
_FillValue        ��׃�      comment       -time scan= msr_time + dtime ; arrondi a la ms         �  QX   lat                       	long_name         latitude   units         degrees_north      scale_factor      ?�z�G�{   
add_offset                   	valid_min         ��     	valid_max         #(     
_FillValue        �      C_format      %.2f     t  R    lon                       	long_name         	longitude      units         degrees_east   scale_factor      ?�z�G�{   
add_offset                   	valid_min         ��     	valid_max         FP     
_FillValue        �      C_format      %.2f     t  Ut   solzen                        	long_name         solar zenith angle     units         degrees    scale_factor      ?�z�G�{   
add_offset                   validmin             validmax      FP     
_FillValue        �      C_format      %.2f     t  X�   satzen                        	long_name         satellite zenith angle     units         degrees    scale_factor      ?�z�G�{   
add_offset                   validmin             validmax      #(     
_FillValue        �      C_format      %.2f     t  \\   relazi                        	long_name         relative azimuth angle     units         degrees    scale_factor      ?�z�G�{   
add_offset                   validmin             validmax      FP     
_FillValue        �      C_format      %.2f     t  _�   VIS006                     	   lon_name      0.6 microns visible channel    units         percent albedo     scale_factor      ?�z�G�{   
add_offset                   	valid_min                	valid_max         '     
_FillValue        �      C_format      %.2f   comment       AVHRR channel 1      t  cD   VIS009                     	   lon_name      0.9 microns visible channel    units         percent albedo     scale_factor      ?�z�G�{   
add_offset                   	valid_min                	valid_max         '     
_FillValue        �      C_format      %.2f   comment       AVHRR channel 2      t  f�   VIS012                     	   lon_name      1.2 microns visible channel    units         percent albedo     scale_factor      ?�z�G�{   
add_offset                   	valid_min                	valid_max         '     
_FillValue        �      C_format      %.2f   comment       AVHRR channel 3A     t  j,   IR037                      	   lon_name      3.7 microns infra-red channel      units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment       AVHRR channel 3B     t  m�   IR108                      	   lon_name      10.8 microns infra-red channel     units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment       AVHRR channel 4      t  q   IR120                      	   lon_name      10.8 microns infra-red channel     units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment       AVHRR channel 5      t  t�   p_illumination                        	long_name         primary illumination condition     valid_range             
_FillValue        �      flag_values           flag_meanings         1nighttime twiligtht daytime daytime_with_sunglint      comment       from avh1c_mask bits 15-14       �  w�   
p_landmask                        	long_name         primary land mask      valid_range             
_FillValue        �      flag_values             flag_meanings         sea land_coast     comment       from avh1c_mask bit 13       �  y�   p_cloudmask_strict                     	   	long_name         strict primary cloud mask      units         percent    scale_factor      ?�         
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      C_format      %.1f   comment       from avh1c_mask bit 12       �  {t   p_cloudmask_relaxed                    	   	long_name         relaxed primary cloud mask     units         percent    scale_factor      ?�         
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      C_format      %.1f   comment       from avh1c_mask bit 0        �  }0   p_cloudmask_quality                    	   	long_name         primary cloud mask quality flag    units         percent    scale_factor      ?�         
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      C_format      %.1f   comment       from avh1c_mask bits 2-1         �  ~�   p_ice                      	   	long_name         primary ice    units         percent    scale_factor      ?�         
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      C_format      %.1f   comment       !from avh1c_mask bits 6-10 value 4        �  ��   p_sst                         	long_name         primary sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       from avh1c_tsurf     t  �d   
x_landmask                        	long_name         auxiliary land mask    valid_range             
_FillValue        �      flag_values            flag_meanings         sea land lake      comment       from GMT     �  ��   x_upwelling_ind                       	long_name         upwelling indicator    valid_range        d     
_FillValue        �      C_format      %.1f   comment                �  ��   x_clim_mean_sst                       	long_name         climatological mean sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f     t  �P   x_clim_mean_sst_stddev                        	long_name         -standard deviation of climatological mean sst      units         K      standard_name         sea_surface_temperature    
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f     t  ��   x_clim_mini_sst                       	long_name         climatological minimum sst     units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f     t  �8   x_clim_maxi_sst_gradient                      	long_name         maximum frontal gradient   units         K/5km      
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       .from monthly climatology by DMI Soren Andersen       t  ��   x_aod                         	long_name         aerosol optical depth      standard_name         aerosol_optical_depth      
add_offset                   scale_factor      ?PbM���   
_FillValue        �      C_format      %.3f   comment       8valeur recuperee lors du calcul du dust aersol indicator     t  �    x_aod_dtime                       	long_name         aerosol optical depth time     units         s      
add_offset                   scale_factor      @         
_FillValue        �      C_format      %.0f   comment       -27h + 27h au maximum        t  ��   	x_aod_src                         	long_name         aerosol optical depth source   valid_range        1     
_FillValue        �      flag_values              flag_meanings         	AOD_NAAPS      comment                �  �   x_sdi                         	long_name         saharan dust index     standard_name         saharan_dust_index     
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       8valeur recuperee lors du calcul du dust aersol indicator     t  ��   x_sdi_dtime                       	long_name         dtime      units         s      
add_offset                   scale_factor      @         
_FillValue        �      C_format      %.0f   comment       9-27h + 27h au maximum; datation sdi = time + x_sdi_dtime         t  �8   	x_sdi_src                         	long_name         saharian dust index source     valid_range       2c     
_FillValue        �      flag_values       2      flag_meanings         
SDI_IR_MSG     comment                �  ��   x_strato_aod                      	long_name         #stratospheric aerosol optical depth    standard_name         #stratospheric_aerosol_optical_depth    
add_offset                   scale_factor      ?PbM���   
_FillValue        �      C_format      %.3f   comment                t  �h   x_strato_aod_dtime                        	long_name         (stratospheric aerosol optical depth time   units         s      
add_offset                   scale_factor      @>         
_FillValue        �      C_format      %.0f   comment       6+- 11j au maximum; datation= time + x_strato_aod_dtime       t  ��   x_ice_edge_flag                       	long_name         sea ice edge flag      valid_range       �	     
_FillValue        �      flag_values       � 	      flag_meanings         :no_data_or_unclassified no_ice certain_ice likely_ice land     comment       +SAFOSI product ice_edge + postprocessing HR      �  �P   
x_ice_conc                        	long_name         sea ice concentration      units         %      valid_range        d     
add_offset                   scale_factor      ?�         
_FillValue        �      C_format      %.1f   comment       =SAFOSI product over_land unclassified and no_data are missing        �  �   w_distance_to_cloud                       	long_name         distance to cloud      
_FillValue        �      	valid_min         �      	valid_max               max_distance            flag_values       
���      flag_meanings         �blind_pixel blind_clear blind_cloudy cloudy 1-pixel_to_cloud 2-pixel_to_cloud 3-pixel_to_cloud 4-pixel_to_cloud 5-pixel_to_cloud far_from_cloud    comment                �  ��   w_t11_gradient                        	long_name         temperature 11 microns gradient    units         K/km   
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       computed in a 3x3 pixels box     t  ��   w_clim_maxi_t11_gradient                      	long_name         'maximal temperature 11 microns gradient    units         K/km   
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       %derived from x_clim_maxi_sst_gradient        t  ��   w_gradient_ind                        	long_name         gradient indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment                �  �l   w_strato_aerosol_ind                      	long_name         stratospheric aerosol indicator    valid_range        d     
_FillValue        �      C_format      %.1f   comment                �  �(   w_tvalue_ind                      	long_name         temperature value indicator    valid_range        d     
_FillValue        �      C_format      %.1f   comment                �  ��   w_dust_aerosol_ind                        	long_name         dust aerosol indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment                �  ��   w_dust_aerosol_src                        	long_name         dust aerosol source    valid_range        c     
_FillValue        �      flag_values        2     flag_meanings         AOD_NAAPS  SDI_IR_MSG      comment                �  �\   
w_ice_prob                        	long_name         ice probability    units         %      
add_offset                   scale_factor      ?�         valid_range        d     
_FillValue        �      C_format      %.1f   comment       from Steinar Eastwood (DNMI)     �  �   	w_ice_ind                         	long_name         ice indicator      valid_range        d     
_FillValue        �      C_format      %.1f   comment                �  ��   w_sst_algo_daytime                        	long_name         sst daytime algorithm used     valid_range             
_FillValue        �      flag_values             flag_meanings         
NL   G_DAY     comment                �  ǐ   w_sst_algo_nighttime                      	long_name         sst nighttime algorithm used   valid_range             
_FillValue        �      flag_values           flag_meanings         NL  T37_1 G_DAY G_NIG      comment                �  �L   w_sst_algo_factor                         	long_name         &sst daytime algorithm weighting factor     
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       =0=night 1=day 0<k<1 twilight  sst=k.sst_day + (1-k).sst_night        �  �   
w_sunglint                        	long_name         sunglint flag      	valid_min                	valid_max               
_FillValue        �      flag_values             flag_meanings         no_sunglint sunglint   comment       from p_illumination      �  ��   sst_mask_ind                      	long_name         sst mask indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment       note: ex cloudmask_ind       �  ΀   sst                    	   	long_name         sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      ancillary_variables       sst_missing_reason     C_format      %.2f   comment                t  �<   sst_missing_reason                        	long_name         sst missing reason flag    valid_range             
_FillValue        �      flag_values        	
     flag_meanings         �margin land no_data no_cloudmask absolutely_cloudy gradient gradient_backup saharian strato tvalue ice ice_or_cloud mask_indicator quality_control     comment                �  Ӱ   sst_confidence_level                      
_FillValue        �      valid_range             flag_values             flag_meanings         3unprocessed masked bad suspect acceptable excellent    comment                �  �l                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    