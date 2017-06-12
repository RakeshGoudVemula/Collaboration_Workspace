myApp.controller("JobController", function($scope, $http) {

	$scope.job = {
		jobId : 2023,
		jobProfile : "",
		jobDescription : ""
	}

	$http.get("http://localhost:4040/SocioCode/getJobs").then(
			function(response) {
				$scope.jobData = response.data;
			});
	$scope.saveJobPost = function() {
		$http.post('http://localhost:4040/SocioCode/insertJob', $scope.job)
				.then(function(response) {
					$scope.message = "Successfully Job Added";
				});
	}

});