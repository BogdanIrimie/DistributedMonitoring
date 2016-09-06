#
# Cookbook Name:: dmon
# Recipe:: scanner
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Install the required packages.
package "openjdk-8-jdk"
package "nmap"

# Download remote archive if there is no deployment.
remote_file node['dmon']['scanner']['archive_path'] do
  source "#{node['dmon']['scanner']['remote_location']}"
  owner 'root'
  group 'root'
  mode '0755'
  action :create
  not_if {::File.exists?("#{node['dmon']['scanner']['component_home_directory']}")}
end

# Extract the archive if an archive exists.
execute 'extract_scanner' do
  command "tar -xvzf #{node['dmon']['scanner']['archive_path']}"
  cwd "#{node['dmon']['scanner']['deployment_directory']}"
  only_if {::File.exists?("#{node['dmon']['scanner']['archive_path']}")}
end

# Remove the archive if it exists.
file node['dmon']['scanner']['archive_path'] do
  action :delete
  only_if {::File.exists?("#{node['dmon']['scanner']['archive_path']}")}
end

# Create specs_monitoring_nmap_scanner/etc directory if it does not exit.
directory node['dmon']['scanner']['etc_directory'] do
  action :create
  not_if {::File.exists?("#{node['dmon']['scanner']['etc_directory']}")}
end

# Create/update config file.
template node['dmon']['scanner']['conf_file'] do
  action :create
  source 'scanner.conf.properties.erb'
end
