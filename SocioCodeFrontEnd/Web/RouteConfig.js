myApp.config(function($routeProvider) {
	$routeProvider.when("/", {
		templateUrl : "Template/Home.html"
	}).when("/Login", {
		templateUrl : "Template/Login.html",
	}).when("/Register", {
		templateUrl : "Template/Register.html"
	}).when("/Blog", {
		templateUrl : "R_Blog/Blog.html",
		controller: "BlogController"
	})

});