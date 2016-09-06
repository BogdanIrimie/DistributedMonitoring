#
# Cookbook Name:: dmon
# Recipe:: default
#
# Copyright (c) 2016 Bogdan-Constantin Irimie, All Rights Reserved.

include_recipe 'dmon::rabbitmq'
include_recipe 'dmon::mongodb'

include_recipe 'dmon::frontend'
include_recipe 'dmon::scheduler'
include_recipe 'dmon::scanner'
include_recipe 'dmon::converter'
include_recipe 'dmon::presenter'

include_recipe 'dmon::start_frontend'
include_recipe 'dmon::start_scheduler'
include_recipe 'dmon::start_scanner'
include_recipe 'dmon::start_converter'
include_recipe 'dmon::start_presenter'
