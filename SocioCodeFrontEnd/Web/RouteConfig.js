myApp.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "Template/Home.html"
	}).when("/login", {
		templateUrl : "R_User/login.html",
		controller : "UserController",
	}).when("/Register", {
		templateUrl : "R_User/Register.html"
	}).when("/Blog", {
		templateUrl : "R_Blog/Blog.html",
		controller : "BlogController"
	}).when("/Forum", {
		templateUrl : "R_Forum/Forum.html",
		controller : "ForumController"
	}).when("/Job", {
		templateUrl : "R_Job/Job.html",
		controller : "JobController"
	}).when("/AdminHome", {
		templateUrl : "R_Admin/AdminHome.html",
		controller : "AdminController"
	}).when("/getAllBlogs", {
		templateUrl : "R_Blog/Admin_ManageBlogs.html",
		controller : "BlogController"
	}).when("/getAllForums", {
		templateUrl : "R_Forum/Admin_ManageForums.html",
		controller : "ForumController"
	}).when("getUsers",{
		templateUrl:"R_User/Admin_ManageUsers.html",
		controller:"UserController"
	})

});