
# RabbitMQ
default['dmon']['rabbitmq']['remote_location'] = 'http://www.rabbitmq.com/community-plugins/v3.6.x/rabbitmq_delayed_message_exchange-0.0.1.ez'
default['dmon']['rabbitmq']['plugin_path'] = '/usr/lib/rabbitmq/lib/rabbitmq_server-3.5.7/plugins/rabbitmq_delayed_message_exchange-0.0.1.ez'

# FrontEnd
default['dmon']['frontend']['remote_location'] = 'https://github.com/IrimieBogdan/DistributedMonitoring/releases/download/0.1.5/specs_monitoring_nmap_frontend.tar.gz'
default['dmon']['frontend']['archive_path'] = '/usr/local/specs_monitoring_nmap_frontend.tar.gz'
default['dmon']['frontend']['deployment_directory'] = '/usr/local'
default['dmon']['frontend']['component_home_directory'] = '/usr/local/specs_monitoring_nmap_frontend'
default['dmon']['frontend']['etc_directory'] = '/usr/local/specs_monitoring_nmap_frontend/etc'
default['dmon']['frontend']['conf_file'] = '/usr/local/specs_monitoring_nmap_frontend/etc/conf.properties'

# Start FrontEnd
default['start_frontend']['config_file'] = '/etc/default/frontend_config'
default['start_frontend']['home'] = '/usr/local/specs_monitoring_nmap_frontend'
default['start_frontend']['systemd_service'] = '/etc/systemd/system/frontend.service'

# Scheduler
default['dmon']['scheduler']['remote_location'] = 'https://github.com/IrimieBogdan/DistributedMonitoring/releases/download/0.1.5/specs_monitoring_nmap_scheduler.tar.gz'
default['dmon']['scheduler']['archive_path'] = '/usr/local/specs_monitoring_nmap_sceduler.tar.gz'
default['dmon']['scheduler']['deployment_directory'] = '/usr/local'
default['dmon']['scheduler']['component_home_directory'] = '/usr/local/specs_monitoring_nmap_scheduler'
default['dmon']['scheduler']['etc_directory'] = '/usr/local/specs_monitoring_nmap_scheduler/etc'
default['dmon']['scheduler']['conf_file'] = '/usr/local/specs_monitoring_nmap_scheduler/etc/conf.properties'

# Start Scheduler
default['start_scheduler']['config_file'] = '/etc/default/scheduler_config'
default['start_scheduler']['home'] = '/usr/local/specs_monitoring_nmap_scheduler'
default['start_scheduler']['systemd_service'] = '/etc/systemd/system/scheduler.service'

# Scanner
default['dmon']['scanner']['remote_location'] = 'https://github.com/IrimieBogdan/DistributedMonitoring/releases/download/0.1.5/specs_monitoring_nmap_scanner.tar.gz'
default['dmon']['scanner']['archive_path'] = '/usr/local/specs_monitoring_nmap_scanner.tar.gz'
default['dmon']['scanner']['deployment_directory'] = '/usr/local'
default['dmon']['scanner']['component_home_directory'] = '/usr/local/specs_monitoring_nmap_scanner'
default['dmon']['scanner']['etc_directory'] = '/usr/local/specs_monitoring_nmap_scanner/etc'
default['dmon']['scanner']['conf_file'] = '/usr/local/specs_monitoring_nmap_scanner/etc/conf.properties'

# Start Scanner
default['start_scanner']['config_file'] = '/etc/default/scanner_config'
default['start_scanner']['home'] = '/usr/local/specs_monitoring_nmap_scanner'
default['start_scanner']['systemd_service'] = '/etc/systemd/system/scanner.service'

# Converter
default['dmon']['converter']['remote_location'] = 'https://github.com/IrimieBogdan/DistributedMonitoring/releases/download/0.1.5/specs_monitoring_nmap_converter.tar.gz'
default['dmon']['converter']['archive_path'] = '/usr/local/specs_monitoring_nmap_converter.tar.gz'
default['dmon']['converter']['deployment_directory'] = '/usr/local'
default['dmon']['converter']['component_home_directory'] = '/usr/local/specs_monitoring_nmap_converter'
default['dmon']['converter']['etc_directory'] = '/usr/local/specs_monitoring_nmap_converter/etc'
default['dmon']['converter']['conf_file'] = '/usr/local/specs_monitoring_nmap_converter/etc/conf.properties'

# Start Converter
default['start_converter']['config_file'] = '/etc/default/converter_config'
default['start_converter']['home'] = '/usr/local/specs_monitoring_nmap_converter'
default['start_converter']['systemd_service'] = '/etc/systemd/system/converter.service'

# Presenter
default['dmon']['presenter']['remote_location'] = 'https://github.com/IrimieBogdan/DistributedMonitoring/releases/download/0.1.5/specs_monitoring_nmap_presenter.tar.gz'
default['dmon']['presenter']['archive_path'] = '/usr/local/specs_monitoring_nmap_presenter.tar.gz'
default['dmon']['presenter']['deployment_directory'] = '/usr/local'
default['dmon']['presenter']['component_home_directory'] = '/usr/local/specs_monitoring_nmap_presenter'
default['dmon']['presenter']['etc_directory'] = '/usr/local/specs_monitoring_nmap_presenter/etc'
default['dmon']['presenter']['conf_file'] = '/usr/local/specs_monitoring_nmap_presenter/etc/conf.properties'

# Start Presenter
default['start_presenter']['config_file'] = '/etc/default/presenter_config'
default['start_presenter']['home'] = '/usr/local/specs_monitoring_nmap_presenter'
default['start_presenter']['systemd_service'] = '/etc/systemd/system/presenter.service'
