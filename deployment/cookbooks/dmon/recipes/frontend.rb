#
# Cookbook Name:: dmon
# Recipe:: frontend
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Install the required packages.
package "openjdk-8-jdk"

# Download remote archive if there is no deployment.
remote_file node['dmon']['frontend']['archive_path'] do
  source "#{node['dmon']['frontend']['remote_location']}"
  owner 'root'
  group 'root'
  mode '0755'
  action :create
  not_if {::File.exists?("#{node['dmon']['frontend']['component_home_directory']}")}
end

# Extract the archive if an archive exists.
execute 'extract_frontend' do
  command "tar -xvzf #{node['dmon']['frontend']['archive_path']}"
  cwd "#{node['dmon']['frontend']['deployment_directory']}"
  only_if {::File.exists?("#{node['dmon']['frontend']['archive_path']}")}
end

# Remove the archive if it exists.
file node['dmon']['frontend']['archive_path'] do
  action :delete
  only_if {::File.exists?("#{node['dmon']['frontend']['archive_path']}")}
end

# Create specs_monitoring_nmap_frontend/etc directory if it does not exit.
directory node['dmon']['frontend']['etc_directory'] do
  action :create
  not_if {::File.exists?("#{node['dmon']['frontend']['etc_directory']}")}
end

# Create/update config file.
template node['dmon']['frontend']['conf_file'] do
  action :create
  source 'frontend.conf.properties.erb'
end
