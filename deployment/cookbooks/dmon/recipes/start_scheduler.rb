#
# Cookbook Name:: dmon
# Recipe:: start_scheduler
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Create config file.
template node['start_scheduler']['config_file'] do
  source 'component_home_config.erb'
  variables({
    :environmentVariable => 'SCHEDULER_HOME',
    :home => node['start_scheduler']['home']
  })
end

# Create systemd service file.
template node['start_scheduler']['systemd_service'] do
  source 'start_component.service.erb'
  variables({
    :componentName => 'Scheduler',
    :home => node['start_scheduler']['home']
  })
end

# Register component as a service in systemd.
bash 'register_scheduler' do
  user "root"
  code <<-EOH
    if [ systemctl is-enabled #{node['start_scheduler']['systemd_service']} != "*enabled*" ]
    then
      systemctl enable #{node['start_scheduler']['systemd_service']}
    fi
  EOH
end


# Start Scheduler component using systemd.
service "scheduler_start" do
  not_if do ::File.exists?("#{node['dmon']['scheduler']['component_home_directory']}/var/specs_monitoring_nmap_scheduler.pid") end
  provider Chef::Provider::Service::Systemd
  service_name "scheduler"
  action :start
end
