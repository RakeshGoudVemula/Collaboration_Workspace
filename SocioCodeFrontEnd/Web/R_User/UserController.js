myApp.controller("UserController", function($scope, $http) {

	$scope.user = {
		userId : 2023,
		firstName : "",
		lastName : ""
	}

	$http.get("http://localhost:4040/SocioCode/getUsers").then(
			function(response) {
				$scope.userData = response.data;
			});
	$scope.saveUserPost = function() {
		$http.post('http://localhost:4040/SocioCode/insertUser', $scope.user)
				.then(function(response) {
					$scope.message = "Successfully User Added";
				});
	}

});