myApp.service("UserService", function($http, $q) {
	console.log("Starting of UserService...");
	var BASE_URL = 'http://localhost:4040/SocioCode';
	return {
		validate : function(user) {
			console.log("Calling the method validate with the user :" + user);
			return $http.post(BASE_URL + '/login', user).then(function(response) {
				return response.data;
			}, null);
		},
		logout : function() {
			console.log('logout....')
			return $http.get(BASE_URL + '/logout').then(function(response) {
				return response.data;
			}, null);
		},
		createUser : function(user) {
			console.log("calling create user")
			return $http.post(BASE_URL + '/insertUser', user).then(function(response) {
				return response.data;
			}, function(errResponse) {
				console.error('Error while creating user');
				return $q.reject(errResponse);
			});
		},
		getUsers : function() {
			console.log("Get All users in user Service");
			return $http.get(BASE_URL + '/getUsers').then(function(response) {
				return response.data;
			}, null);
		},
		
		
	};
});