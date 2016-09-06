#
# Cookbook Name:: dmon
# Recipe:: start_frontend
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Create config file.
template node['start_frontend']['config_file'] do
  source 'component_home_config.erb'
  variables({
    :environmentVariable => 'FRONTEND_HOME',
    :home => node['start_frontend']['home']
  })
end

# Create systemd service file.
template node['start_frontend']['systemd_service'] do
  source 'start_component.service.erb'
  variables({
    :componentName => 'FrontEnd',
    :home => node['start_frontend']['home']
  })
end

# Register component as a service in systemd.
bash 'register_frontend' do
  user "root"
  code <<-EOH
    if [ systemctl is-enabled #{node['start_frontend']['systemd_service']} != "*enabled*" ]
    then
      systemctl enable #{node['start_frontend']['systemd_service']}
    fi
  EOH
end


# Start FrontEnd component using systemd.
service "frontend_start" do
  not_if do ::File.exists?("#{node['dmon']['frontend']['component_home_directory']}/var/specs_monitoring_nmap_frontend.pid") end
  provider Chef::Provider::Service::Systemd
  service_name "frontend"
  action :start
end
