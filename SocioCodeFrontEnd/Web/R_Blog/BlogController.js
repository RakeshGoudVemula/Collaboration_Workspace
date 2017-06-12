myApp.controller("BlogController", function($scope, $http) {

	$scope.blog = {
		blogId : 2023,
		blogName : "",
		blogContent : ""
	}

	$http.get("http://localhost:4040/SocioCode/getBlogs").then(
			function(response) {
				$scope.blogData = response.data;
			});
	$scope.saveBlogPost = function() {
		$http.post('http://localhost:4040/SocioCode/insertBlog', $scope.blog)
				.then(function(response) {
					$scope.message = "Successfully Blog Added";
				});
	}

});