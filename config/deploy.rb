# Call with cap -S branch="<branch-name>" [staging|production] deploy
require 'capistrano/ext/multistage'
set :application, "primo-custom"
set :user, "primo"

set :scm, :git
set :repository,  "git@github.com:NYULibraries/primo-plugins.git"
set :deploy_via, :remote_cache
set :deploy_to, "/exlibris/primo/p3_1/ng/primo/home/profile/publish/publish/production/conf/plugins/enrichment"

set :use_sudo, false
set :stages, ["staging", "production"]
set :default_stage, "staging"

set :build_dir, "target"

namespace :deploy do
  desc <<-DESC
    Package the jar with maven.
  DESC
  task :package do
    run "mvn clean && mvn package"
  end
  
  desc <<-DESC
  DESC
  task :javadocs do
    run "git"
  end
  
  desc <<-DESC
    No restart necessary for Primo.
  DESC
  task :restart do
    puts "Skipping restart."
  end

  desc <<-DESC
    No symlink creation necessary for Primo.
  DESC
  task :create_symlink do
    puts "Skipping symlink creation."
  end

  desc <<-DESC
    Touches up the released code. This is called by update_code after the basic \
    deploy finishes. Overrides internal implementation since the internal \
    implementation assumes rails.

    This task will make the release group-writable (if the :group_writable \
    variable is set to true, which is the default). It will copy the latest \
    release to the custom directory and cleanup the releases.
  DESC
  task :finalize_update, :except => { :no_release => true } do
  end
end