myApp.controller("ForumController", function($scope, $http) {

	$scope.forum = {
		forumId : 2023,
		forumName : "",
		forumContent : ""
	}

	$http.get("http://localhost:4040/SocioCode/getForums").then(
			function(response) {
				$scope.forumData = response.data;
			});
	$scope.saveForumPost = function() {
		$http.post('http://localhost:4040/SocioCode/insertForum', $scope.forum)
				.then(function(response) {
					$scope.message = "Successfully Forum Added";
				});
	}

});