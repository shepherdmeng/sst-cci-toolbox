from workflow import Workflow

usecase = 'mms1a'
mmdtype = 'mmd1'

w = Workflow(usecase)
w.add_primary_sensor('atsr.2', '1995-06-01', '1996-01-01')
w.add_primary_sensor('atsr.2', '1996-07-01', '2003-06-23')
w.add_secondary_sensor('atsr.1', '1991-08-01', '1996-09-01')
w.add_secondary_sensor('atsr.1', '1996-10-01', '1996-11-01')
w.add_secondary_sensor('atsr.1', '1996-12-30', '1997-02-01')
w.add_secondary_sensor('atsr.1', '1997-03-01', '1997-04-01')
w.add_secondary_sensor('atsr.1', '1997-05-01', '1997-06-01')
w.add_secondary_sensor('atsr.1', '1997-07-01', '1997-09-01')
w.add_secondary_sensor('atsr.1', '1997-10-01', '1997-11-01')
w.add_secondary_sensor('atsr.1', '1997-12-01', '1997-12-18')
w.set_samples_per_month(15000000)
w.run(mmdtype, hosts=[('localhost', 48)])
