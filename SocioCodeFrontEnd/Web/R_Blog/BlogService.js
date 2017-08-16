myApp.service('BlogService', function($http) {
	console.log("Starting of BlogService...");
	var BASE_URL = 'http://localhost:4040/SocioCode';

	return {
		getAllBlogs : function() {
			console.log("Get All Blogs in Blog Service");
			return $http.get(BASE_URL + '/getAllBlogs').then(function(response) {
				return response.data;
			}, null);
		},
		approveBlog : function(blog) {
			console.log("Starting of accept blog service");
			return $http.put(BASE_URL + '/approveBlog/' + blog.blogId, blog).then(function(response) {
				return response.data;
			}, null);
		},
		rejectBlog : function(blog) {
			console.log("Starting of accept blog service");
			return $http.put(BASE_URL + '/rejectBlog/' + blog.blogId, blog).then(function(response) {
				return response.data;
			}, null);
		}
	}

})