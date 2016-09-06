#
# Cookbook Name:: dmon
# Recipe:: start_presenter
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Create config file.
template node['start_presenter']['config_file'] do
  source 'component_home_config.erb'
  variables({
    :environmentVariable => 'PRESENTER_HOME',
    :home => node['start_presenter']['home']
  })
end

# Create systemd service file.
template node['start_presenter']['systemd_service'] do
  source 'start_component.service.erb'
  variables({
    :componentName => 'Presenter',
    :home => node['start_presenter']['home']
  })
end

# Register component as a service in systemd.
bash 'register_presenter' do
  user "root"
  code <<-EOH
    if [ systemctl is-enabled #{node['start_presenter']['systemd_service']} != "*enabled*" ]
    then
      systemctl enable #{node['start_presenter']['systemd_service']}
    fi
  EOH
end


# Start Presenter component using systemd.
service "presenter_start" do
  not_if do ::File.exists?("#{node['dmon']['presenter']['component_home_directory']}/var/specs_monitoring_nmap_presenter.pid") end
  provider Chef::Provider::Service::Systemd
  service_name "presenter"
  action :start
end
