myApp.controller("UserController", function($scope, UserService, $location, $rootScope, $cookieStore, $http) {
	console.log("UserController...");
	this.user = {
		userId : '',
		firstName : '',
		lastName : '',
		password : '',
		emailId : '',
		role : '',
		status : '',
		isOnline : '',
		remarks : '',
		errorCode : '',
		errorMessage : ''
	};
	this.isUserLoggedIn = false;
	this.loggedInUser = {
		userId : '',
		firstName : '',
		lastName : '',
		password : '',
		emailId : '',
		role : '',
		status : '',
		isOnline : '',
		remarks : '',
		errorCode : '',
		errorMessage : ''

	};
	this.users = []; // json array

	this.validate = function(user) {
		console.log("validate method");
		UserService.validate(user).then(function(serviceData) {
			this.user = serviceData;
			console.log("user.errorCode:" + this.user.errorCode)
			if (this.user.errorCode == "404") {
				alert(this.user.errorMessage)
				this.user.userId = "";
				this.user.password = "";
			} else {
				console.log("valid crendentials");
				$rootScope.isUserLoggedIn = true;
				$rootScope.loggedInUser = this.user;
				$cookieStore.put('loggedInUser', this.user);
				$http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.loggedInUser;
				$location.path('/');
			}
		}, function(errResponse) {

			console.error("Error while authenticate Users");

		});
	};

	this.logout = function() {
		console.log("logout")
		$rootScope.isUserLoggedIn = false;
		$rootScope.loggedInUser = {};
		$cookieStore.remove('loggedInUser');
		UserService.logout()
		$location.path('/');

	}

	this.createUser = function(user) {
		console.log("createUser...")
		UserService.createUser(user).then(function(serviceData) {
			this.user = serviceData;
			alert("Thank you for registration");
			$location.path("/login");
		}, function(errResponse) {
			console.error('Error while creating User.');
		});
	};

	this.getUsers = function() {
		console.log("Starting of getAllUsers controller");
		UserService.getUsers().then(function(userServiceData) {
			$rootScope.users = userServiceData;
			console.log(userServiceData);
			console.log("Ending of Get Users ")
			// $cookieStore.put('userServiceData', this.users);
		});
	}
	this.getUsers();

});