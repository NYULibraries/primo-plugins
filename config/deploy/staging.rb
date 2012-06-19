server "primodev3.bobst.nyu.edu", :app, :web
set(:branch, 'master') unless exists?(:branch)
