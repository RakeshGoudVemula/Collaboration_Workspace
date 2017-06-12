myApp.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "Template/Home.html"
	}).when("/Login", {
		templateUrl : "Template/Login.html",
	}).when("/Register", {
		templateUrl : "Template/Register.html"
	}).when("/Blog", {
		templateUrl : "R_Blog/Blog.html",
		controller : "BlogController"
	}).when("/Forum", {
		templateUrl : "R_Forum/Forum.html",
		controller : "ForumController"
	}).when("/Job", {
		templateUrl : "R_Job/Job.html",
		controller : "JobController"
	})

});