myApp.service('ForumService', function($http) {
	console.log("Starting of ForumService...");
	var BASE_URL = 'http://localhost:4040/SocioCode';

	return {
		getAllForums : function() {
			console.log("Get All Forums in Forum Service");
			return $http.get(BASE_URL + '/getAllForums').then(function(response) {
				return response.data;
			}, null);
		},
		approveForum : function(forum) {
			console.log("Starting of accept forum service");
			return $http.put(BASE_URL + '/approveForum/' + forum.forumId, forum).then(function(response) {
				return response.data;
			}, null);
		},
		rejectForum : function(forum) {
			console.log("Starting of accept forum service");
			return $http.put(BASE_URL + '/rejectForum/' + forum.forumId, forum).then(function(response) {
				return response.data;
			}, null);
		}
	}

})