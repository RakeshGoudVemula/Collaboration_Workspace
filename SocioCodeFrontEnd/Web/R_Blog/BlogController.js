myApp.controller("BlogController",function($scope,$http){
	
	$http.get("http://localhost:4040/SocioCode/getBlogs")
	.then(function(response){
		
		$scope.blogData=response.data;
	});
});