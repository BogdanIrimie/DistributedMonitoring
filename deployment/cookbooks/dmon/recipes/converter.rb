#
# Cookbook Name:: dmon
# Recipe:: converter
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Install the required packages
package "openjdk-8-jdk"

# Download remote archive if there is no deployment.
remote_file node['dmon']['converter']['archive_path'] do
  source "#{node['dmon']['converter']['remote_location']}"
  owner 'root'
  group 'root'
  mode '0755'
  action :create
  not_if {::File.exists?("#{node['dmon']['converter']['component_home_directory']}")}
end

# Extract the archive if an archive exists.
execute 'extract_converter' do
  command "tar -xvzf #{node['dmon']['converter']['archive_path']}"
  cwd "#{node['dmon']['converter']['deployment_directory']}"
  only_if {::File.exists?("#{node['dmon']['converter']['archive_path']}")}
end

# Remove the archive if it exists.
file node['dmon']['converter']['archive_path'] do
  action :delete
  only_if {::File.exists?("#{node['dmon']['converter']['archive_path']}")}
end

# Create specs_monitoring_nmap_converter/etc directory if it does not exit
directory node['dmon']['converter']['etc_directory'] do
  action :create
  not_if {::File.exists?("#{node['dmon']['converter']['etc_directory']}")}
end

# Create/update config file.
template node['dmon']['converter']['conf_file'] do
  action :create
  source 'converter.conf.properties.erb'
end
