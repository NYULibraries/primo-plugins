# Call with cap -S branch="<branch-name>" [staging|production] deploy
require 'capistrano/ext/multistage'
set :application, "primo-custom"
set :user, "primo"

set :scm, :git
set :repository,  "git@github.com:NYULibraries/primo-plugins.git"
set :deploy_via, :copy
set :deploy_to, "/exlibris/primo/p3_1/ng/primo/home/profile/publish/publish/production/conf/plugins/enrichment"

set :use_sudo, false
set :stages, ["staging", "production"]
set :default_stage, "staging"

set :build_dir, "./target"

namespace :deploy do
  desc <<-DESC
    Package the jar with maven.
  DESC
  task :package do
    run_locally "git checkout #{branch} && mvn clean && mvn package"
  end
  
  desc <<-DESC
    Push javadocs to GitHub pages.
  DESC
  task :javadocs do
    run_locally "mvn javadoc:javadoc && git checkout gh-pages && rm -r apidocs && mv #{build_dir}/site/apidocs apidocs && git add apidocs && git commit -am 'Add javadocs.' && git push && git checkout #{branch}"
  end
  
   
  before "deploy", "deploy:package", "deploy:javadocs"
  
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
  DESC
  task :update_code do
    top.upload "/#{build_dir}/primo-plugin.jar", "#{deploy_to}"
  end

  desc <<-DESC
    
  DESC
  task :finalize_update, :except => { :no_release => true } do
  end
end