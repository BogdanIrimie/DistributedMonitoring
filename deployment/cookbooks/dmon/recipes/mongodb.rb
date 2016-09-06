#
# Cookbook Name:: dmon
# Recipe:: mongodb
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.


# Install MongoDB
bash 'install_mongodb' do
  user "root"
  code <<-EOH
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
    echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
    sudo apt-get update
    sudo apt-get install -y --allow-unauthenticated mongodb-org
  EOH
  not_if do ::File.exists?("/var/run/mongodb") end
end

# Create config file.
template "/etc/systemd/system/mongod.service" do
  source 'start_mongo.service.erb'
end

# Register component as a service in systemd.
bash 'register_mongodb' do
  user "root"
  code <<-EOH
    if [ systemctl is-enabled /etc/systemd/system/mongod.service != "*enabled*" ]
    then
      systemctl enable /etc/systemd/system/mongodb.service
    fi
  EOH
end

# Start MongoDB.
service "mongodb-start" do
  not_if do ::File.exists?("/var/run/mongodb/mongod.pid") end
  provider Chef::Provider::Service::Systemd
  service_name "mongod"
  action :start
end
