#
# Cookbook Name:: dmon
# Recipe:: presenter
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

# Install the required packages
package "openjdk-8-jdk"

# Download remote archive if there is no deployment.
remote_file node['dmon']['presenter']['archive_path'] do
  source "#{node['dmon']['presenter']['remote_location']}"
  owner 'root'
  group 'root'
  mode '0755'
  action :create
  not_if {::File.exists?("#{node['dmon']['presenter']['component_home_directory']}")}
end

# Extract the archive if an archive exists.
execute 'extract_presenter' do
  command "tar -xvzf #{node['dmon']['presenter']['archive_path']}"
  cwd "#{node['dmon']['presenter']['deployment_directory']}"
  only_if {::File.exists?("#{node['dmon']['presenter']['archive_path']}")}
end

# Remove the archive if it exists.
file node['dmon']['presenter']['archive_path'] do
  action :delete
  only_if {::File.exists?("#{node['dmon']['presenter']['archive_path']}")}
end

# Create specs_monitoring_nmap_presenter/etc directory if it does not exit
directory node['dmon']['presenter']['etc_directory'] do
  action :create
  not_if {::File.exists?("#{node['dmon']['presenter']['etc_directory']}")}
end

# Create/update config file.
template node['dmon']['presenter']['conf_file'] do
  action :create
  source 'presenter.conf.properties.erb'
end
