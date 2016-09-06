#
# Cookbook Name:: dmon
# Recipe:: scheduler
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Install the required packages
package "openjdk-8-jdk"

# Download remote archive if there is no deployment.
remote_file node['dmon']['scheduler']['archive_path'] do
  source "#{node['dmon']['scheduler']['remote_location']}"
  owner 'root'
  group 'root'
  mode '0755'
  action :create
  not_if {::File.exists?("#{node['dmon']['scheduler']['component_home_directory']}")}
end

# Extract the archive if an archive exists.
execute 'extract_scheduler' do
  command "tar -xvzf #{node['dmon']['scheduler']['archive_path']}"
  cwd "#{node['dmon']['scheduler']['deployment_directory']}"
  only_if {::File.exists?("#{node['dmon']['scheduler']['archive_path']}")}
end

# Remove the archive if it exists.
file node['dmon']['scheduler']['archive_path'] do
  action :delete
  only_if {::File.exists?("#{node['dmon']['scheduler']['archive_path']}")}
end

# Create specs_monitoring_nmap_scheduler/etc directory if it does not exit
directory node['dmon']['scheduler']['etc_directory'] do
  action :create
  not_if {::File.exists?("#{node['dmon']['scheduler']['etc_directory']}")}
end

# Create/update config file.
template node['dmon']['scheduler']['conf_file'] do
  action :create
  source 'scheduler.conf.properties.erb'
end
