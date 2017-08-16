myApp.controller("BlogController", function($scope, BlogService, $location, $rootScope, $cookieStore, $http, $route) {

	console.log("Startng of Blog Controller");
	this.blog = {
		blogId : '',
		blogName : '',
		blogContent : '',
		userId : '',
		createDate : '',
		status : '',
		likes : '',
		remarks : ''
	};

	this.blogs = [];// json array

	this.getAllBlogs = function() {
		console.log("Starting of getAllBlogsFunction()");
		BlogService.getAllBlogs().then(function(blogServiceData) {
			$rootScope.blogs = blogServiceData;
			console.log(blogServiceData);
			// $cookieStore.put('blogServiceData', this.blogs);

		});
	}
	this.getAllBlogs();

	$scope.reloadRoute = function() {
		$route.reload();
	};

	this.approveBlog = function(blog) {
		console.log("starting of approveBlog controller");
		BlogService.approveBlog(blog).then(function(blogServiceD) {
			this.blog = blogServiceD;
			alert("Blog Approved successfully");
			$scope.reloadRoute();
			$location.path("/getAllBlogs");

		}, function(errResponse) {
			console.log("error while approving the blog");

		})
	};

	this.rejectBlog = function(blog) {
		console.log("starting of rejectBlog controller");
		BlogService.rejectBlog(blog).then(function(blogServiceD) {
			this.blog = blogServiceD;
			alert("Blog Approved successfully");
			$scope.reloadRoute();
			$location.path("/getAllBlogs");
		}, function(errResponse) {
			console.log("error while rejecting the blog");

		})
	};

});