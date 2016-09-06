#
# Cookbook Name:: dmon
# Recipe:: start_scanner
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Create config file.
template node['start_scanner']['config_file'] do
  source 'component_home_config.erb'
  variables({
    :environmentVariable => 'SCANNER_HOME',
    :home => node['start_scanner']['home']
  })
end

# Create systemd service file.
template node['start_scanner']['systemd_service'] do
  source 'start_component.service.erb'
  variables({
    :componentName => 'Scanner',
    :home => node['start_scanner']['home']
  })
end

# Register component as a service in systemd.
bash 'register_scanner' do
  user "root"
  code <<-EOH
    if [ systemctl is-enabled #{node['start_scanner']['systemd_service']} != "*enabled*" ]
    then
      systemctl enable #{node['start_scanner']['systemd_service']}
    fi
  EOH
end


# Start Scanner component using systemd.
service "scanner_start" do
  not_if do ::File.exists?("#{node['dmon']['scanner']['component_home_directory']}/var/specs_monitoring_nmap_scanner.pid") end
  provider Chef::Provider::Service::Systemd
  service_name "scanner"
  action :start
end
