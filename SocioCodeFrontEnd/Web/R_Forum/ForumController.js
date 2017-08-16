myApp.controller("ForumController", function($scope, ForumService, $location, $rootScope, $cookieStore, $http, $route) {

	console.log("Startng of Forum Controller");
	this.forum = {
		forumId : '',
		forumName : '',
		forumContent : '',
		userId : '',
		createDate : '',
		status : '',
		likes : '',
		remarks : ''
	};

	this.forums = [];// json array

	this.getAllForums = function() {
		console.log("Starting of getAllForumsFunction()");
		ForumService.getAllForums().then(function(forumServiceData) {
			$rootScope.forums = forumServiceData;
			console.log(forumServiceData);
			// $cookieStore.put('forumServiceData', this.forums);

		});
	}
	this.getAllForums();

	$scope.reloadRoute = function() {
		$route.reload();
	};

	this.approveForum = function(forum) {
		console.log("starting of approveForum controller");
		ForumService.approveForum(forum).then(function(forumServiceD) {
			this.forum = forumServiceD;
			alert("Forum Approved successfully");
			$scope.reloadRoute();
			$location.path("/getAllForums");

		}, function(errResponse) {
			console.log("error while approving the forum");

		})
	};

	this.rejectForum = function(forum) {
		console.log("starting of rejectForum controller");
		ForumService.rejectForum(forum).then(function(forumServiceD) {
			this.forum = forumServiceD;
			alert("Forum Approved successfully");
			$scope.reloadRoute();
			$location.path("/getAllForums");
		}, function(errResponse) {
			console.log("error while rejecting the forum");

		})
	};

});