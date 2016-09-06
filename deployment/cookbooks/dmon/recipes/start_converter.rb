#
# Cookbook Name:: dmon
# Recipe:: start_converter
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Create config file.
template node['start_converter']['config_file'] do
  source 'component_home_config.erb'
  variables({
    :environmentVariable => 'CONVERTER_HOME',
    :home => node['start_converter']['home']
  })
end

# Create systemd service file.
template node['start_converter']['systemd_service'] do
  source 'start_component.service.erb'
  variables({
    :componentName => 'Converter',
    :home => node['start_converter']['home']
  })
end

# Register component as a service in systemd.
bash 'register_converter' do
  user "root"
  code <<-EOH
    if [ systemctl is-enabled #{node['start_converter']['systemd_service']} != "*enabled*" ]
    then
      systemctl enable #{node['start_converter']['systemd_service']}
    fi
  EOH
end


# Start Converter component using systemd.
service "converter_start" do
  not_if do ::File.exists?("#{node['dmon']['converter']['component_home_directory']}/var/specs_monitoring_nmap_converter.pid") end
  provider Chef::Provider::Service::Systemd
  service_name "converter"
  action :start
end
