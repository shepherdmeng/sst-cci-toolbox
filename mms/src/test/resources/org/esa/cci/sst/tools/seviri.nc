CDF       
      n          nx        ny        len_id        len_filename   A         	satellite         
meteosat09     date      
2010/06/01        P   msr_type                	long_name         measure type   valid_range             
_FillValue        �      flag_values            flag_meanings         moored drifter ship         V�   msr_id                     	long_name         
measure id     comment       filled with trailing spaces         V�   msr_time                	long_name         measure time   standard_name         time   units         !seconds since 1981-01-01 00:00:00      comment                   W    msr_lat                 	long_name         latitude   units         degrees_north      scale_factor      ?�z�G�{   
add_offset                   	valid_min         ��     	valid_max         #(     
_FillValue        �      C_format      %.2f        W   msr_lon                 	long_name         	longitude      units         degrees_east   scale_factor      ?�z�G�{   
add_offset                   	valid_min         ��     	valid_max         FP     
_FillValue        �      C_format      %.2f        W   msr_sst                 	long_name         measured sst   standard_name         sea_surface_temperature    units         K      
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment                   W   	msr_depth                   	long_name         depth below sea/water surface      units         m      
add_offset                   scale_factor      ?�������   
_FillValue        �      C_format      %.1f   comment       from BUFR 0 07 062 (buoy only)          W   msr_quality                 	long_name         measurement quality    
add_offset                   scale_factor      ?�������   
_FillValue        �      C_format      %.1f   comment       from BUFR 0 33 214 (buoy only)          W   
msr_method                  	long_name         'method of water temperature measurement    valid_range             
_FillValue        �      flag_values        	
   flag_meanings         �ship_intake bucket hull_contact_sensor reversing_thermometer STD/CTD_sensor mechanical_BT expendable_BT digital_BT thermistor_chain infra-red_scanner micro_wave_scanner other     comment       from BUFR 0 02 038 (ship only)          W   msr_air_temp                	long_name         dry-bulb temperature at 2m     standard_name         air_temperature    units         K      
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       from BUFR 0 12 195          W    msr_dew_point_temp                  	long_name         dew-point temperature at 2m    standard_name         dew_point_temperature      units         K      
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       from BUFR 0 12 197          W$   msr_wind_speed                  	long_name         wind speed at 10m      standard_name         
wind_speed     units         m s-1      
add_offset                   scale_factor      ?�         
_FillValue        �      C_format      %.0f   comment       from BUFR 0 11 012          W(   msr_wind_direction               
   	long_name         wind direction at 10m      standard_name         wind_direction     units         degrees    
add_offset                   scale_factor      ?�         
_FillValue        �      validmin             validmax      h     C_format      %.0f   comment       from BUFR 0 11 011          W,   	box_cover                   	long_name         box cover rate     scale_factor      ?�z�G�{   
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      C_format      %.2f   comment        number sst / number water          W0   box_prd_filename                   	long_name         source prd filename    comment       basename of the source prd file       D  W4   box_sat_filename                   	long_name         source sat filename    comment       basename of the source sat file       D  Wx   box_center_y_coord                  	long_name         center pixel y coordinate      
_FillValue        �      	valid_min                	valid_max         �     comment                   W�   box_center_x_coord                  	long_name         center pixel x coordinate      
_FillValue        �      	valid_min                	valid_max         �     comment                   W�   time                	long_name         measure time   standard_name         time   units         !seconds since 1981-01-01 00:00:00      comment       reference time du sat           W�   dtime                         	long_name                units         seconds    
_FillValue        ��׃�      comment       time pixel sat = time + dtime         �  W�   lat                       	long_name         latitude   units         degrees_north      scale_factor      ?6��C-   
add_offset                   	valid_min         ��D`   	valid_max          ��   
_FillValue        �         d  X�   lon                       	long_name         	longitude      units         degrees_east   scale_factor      ?6��C-   
add_offset                   	valid_min         ���   	valid_max          w@   
_FillValue        �         d  X�   satzen                        	long_name         satellite zenithal angle   units         degrees    scale_factor      ?6��C-   
add_offset                   	valid_min                	valid_max          w@   
_FillValue        �         d  Y\   solzen                        	long_name         solar zenithal angle   units         degrees    scale_factor      ?6��C-   
add_offset                   	valid_min                	valid_max          w@   
_FillValue        �         d  Y�   relazi                        	long_name         relative azimuthal angle   units         degrees    scale_factor      ?6��C-   
add_offset                   	valid_min                	valid_max          w@   
_FillValue        �         d  Z$   VIS006                     	   lon_name      0.6 microns visible channel    units         1      scale_factor      ?6��C-   
add_offset                   	valid_min                	valid_max         '     
_FillValue        �      C_format      %.2f   comment                 4  Z�   VIS008                     	   lon_name      0.8 microns visible channel    units         1      scale_factor      ?6��C-   
add_offset                   	valid_min                	valid_max         '     
_FillValue        �      C_format      %.2f   comment                 4  Z�   IR_016                     	   	long_name         1.6 microns visible channel    units         1      scale_factor      ?6��C-   
add_offset                   	valid_min                	valid_max         '     
_FillValue        �      C_format      %.2f   comment                 4  Z�   IR_039                     	   lon_name      3.9 microns infra-red channel      units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  [$   WV_062                     	   lon_name       6.2 microns water vapour channel   units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  [X   WV_073                     	   lon_name      7.3 microns infra-red channel      units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  [�   IR_087                     	   lon_name      8.7 microns infra-red channel      units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  [�   IR_097                     	   lon_name      9.7 microns infra-red channel      units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  [�   IR_108                     	   lon_name      10.8 microns infra-red channel     units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  \(   IR_120                     	   lon_name      12.0 microns infra-red channel     units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  \\   IR_134                     	   lon_name      13.4 microns infra-red channel     units         K      scale_factor      ?�z�G�{   
add_offset        @qfffff   	valid_min         �x     	valid_max         p     
_FillValue        �      C_format      %.2f   comment                 4  \�   
p_landmask                        	long_name         primary landmask   
_FillValue        �      flag_values             flag_meanings         sea land_coast     comment       %derive des produits SAFNWC p1m et p2t           \�   p_illumination                        	long_name         primary illumination condition     	valid_min                	valid_max               
_FillValue        �      flag_values           flag_meanings         1nighttime twiligtht daytime daytime_with_sunglint      comment       *extrait du 1er champ du produit SAFNWC p1q          \�   p_mask                        	long_name         primary mask   	valid_min                	valid_max         d      
_FillValue        �      comment       deduit du produit SAFNWC p1m        \�   p_mask_quality                        	long_name         primary mask quality   	valid_min                	valid_max         d      
_FillValue        �      comment       deduit du produit SAFNWC p1q        ]   p_ice                         	long_name         primary ice    units         percent    scale_factor      ?�         
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      comment       deduit du produit SAFNWC p1m        ]4   p_dust                        	long_name         primary dust   units         percent    scale_factor      ?�         
add_offset                   	valid_min                	valid_max         d      
_FillValue        �      comment       deduit du produit SAFNWC p1q        ]P   
x_landmask                        	long_name         auxiliary land mask    valid_range             
_FillValue        �      flag_values            flag_meanings         sea land lake      comment       from GMT        ]l   x_upwelling_ind                       	long_name         upwelling indicator    valid_range        d     
_FillValue        �      C_format      %.1f   comment                   ]�   x_clim_mean_sst                       	long_name         climatological mean sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f      4  ]�   x_clim_mean_sst_stddev                        	long_name         -standard deviation of climatological mean sst      units         K      standard_name         sea_surface_temperature    
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f      4  ]�   x_clim_mini_sst                       	long_name         climatological minimum sst     units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f      4  ^   x_clim_maxi_sst_gradient                      	long_name         maximum frontal gradient   units         K/5km      
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       .from monthly climatology by DMI Soren Andersen        4  ^@   x_aod                         	long_name         aerosol optical depth      standard_name         aerosol_optical_depth      
add_offset                   scale_factor      ?PbM���   
_FillValue        �      C_format      %.3f   comment       8valeur recuperee lors du calcul du dust aersol indicator      4  ^t   x_aod_dtime                       	long_name         aerosol optical depth time     units         s      
add_offset                   scale_factor      @         
_FillValue        �      C_format      %.0f   comment       4ecart a la datation du pixel ; -27h + 27h au maximum      4  ^�   	x_aod_src                         	long_name         aerosol optical depth source   valid_range        1     
_FillValue        �      flag_values              flag_meanings         	AOD_NAAPS      comment                   ^�   x_sdi                         	long_name         saharan dust index     standard_name         saharan_dust_index     
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       8valeur recuperee lors du calcul du dust aersol indicator      4  ^�   x_sdi_dtime                       	long_name         dtime      units         s      
add_offset                   scale_factor      @         
_FillValue        �      C_format      %.0f   comment       4ecart a la datation du pixel ; -27h + 27h au maximum      4  _,   	x_sdi_src                         	long_name         saharian dust index source     valid_range       2c     
_FillValue        �      flag_values       2      flag_meanings         
SDI_IR_MSG     comment                   _`   x_ice_edge_flag                       	long_name         sea ice edge flag      valid_range       �	     
_FillValue        �      flag_values       � 	      flag_meanings         :no_data_or_unclassified no_ice certain_ice likely_ice land     comment       +SAFOSI product ice_edge + postprocessing HR         _|   w_distance_to_cloud                       	long_name         distance to cloud      
_FillValue        �      	valid_min         �      	valid_max               max_distance            flag_values       
���      flag_meanings         �blind_pixel blind_clear blind_cloudy cloudy 1-pixel_to_cloud 2-pixel_to_cloud 3-pixel_to_cloud 4-pixel_to_cloud 5-pixel_to_cloud far_from_cloud    comment                   _�   w_time_variability_ind                     
   	long_name         time variability indicator     	valid_min                	valid_max         d      
_FillValue        �      	delta_min         @9         	delta_max         @N         critical      ��         limit         ��         failed        d      comment       	Wu method           _�   w_sst_gradient                        	long_name          sea surface temperature gradient   units         K/km   
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      comment                 4  _�   w_gradient_ind                        	long_name         gradient indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment                   `   w_sst_diffrate                        	long_name         'sea surface temperature difference rate    units         K/km   
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      comment                 4  `    w_strato_aerosol_ind                      	long_name         stratospheric aerosol indicator    valid_range        d     
_FillValue        �      C_format      %.1f   comment                   `T   w_tvalue_ind                      	long_name         temperature value indicator    valid_range        d     
_FillValue        �      C_format      %.1f   comment                   `p   w_dust_aerosol_ind                        	long_name         dust aerosol indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment                   `�   w_dust_aerosol_src                        	long_name         dust aerosol source    valid_range        c     
_FillValue        �      flag_values        2     flag_meanings         AOD_NAAPS  SDI_IR_MSG      comment                   `�   w_sdi_correction                      	long_name         SDI correction     units         K      
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      comment       sst = sst + w_sdi_correction      4  `�   	w_ice_ind                         	long_name         ice indicator      valid_range        d     
_FillValue        �      C_format      %.1f   comment                   `�   w_sst_algo_daytime                        	long_name         sst daytime algorithm used     	valid_min                	valid_max               
_FillValue        �      flag_values             flag_meanings         DUMMY NL   comment                   a   w_sst_algo_nighttime                      	long_name         sst nighttime algorithm used   	valid_min                	valid_max               
_FillValue        �      flag_values            flag_meanings         DUMMY NL  T37_1    comment                   a0   w_sst_algo_factor                         	long_name         &sst daytime algorithm weighting factor     
add_offset                   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       =0=night 1=day 0<k<1 twilight  sst=k.sst_day + (1-k).sst_night           aL   w_algo_risk_ind                       	long_name         algorithm risk indicator   	valid_min                	valid_max         d      
_FillValue        �      comment                   ah   sst_mask_ind                      	long_name         sst mask indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment       note: ex cloudmask_ind          a�   sst_1st                       	long_name         sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      grid_mapping      geos   comment                 4  a�   sst                       	long_name         sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       bored in sat file         4  a�   sst_missing_reason                        	long_name         sst missing reason flag    	valid_min                	valid_max               
_FillValue        �      flag_values            flag_meanings         1space land satzen no_data bad_mask mask off_range      comment                   b   sst_confidence_level                      
_FillValue        �      valid_range             flag_values             flag_meanings         3unprocessed masked bad suspect acceptable excellent    comment                   b$   prd_time                	long_name         measure time   standard_name         time   units         !seconds since 1981-01-01 00:00:00      comment       reference time du prd           b@   	prd_dtime                         	long_name                units         seconds    
_FillValue        ��׃�      comment       %time pixel prd = prd_time + prd_dtime         �  bH   prd_sst                       	long_name         sst    units         K      standard_name         sea_surface_temperature    
add_offset        @qfffff   scale_factor      ?�z�G�{   
_FillValue        �      C_format      %.2f   comment       extrait du prd        4  c   prd_sst_confidence_level                      
_FillValue        �      valid_range             flag_values             flag_meanings         3unprocessed masked bad suspect acceptable excellent    comment                   cD   prd_sst_mask_ind                      	long_name         sst mask indicator     valid_range        d     
_FillValue        �      C_format      %.1f   comment                   c`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                