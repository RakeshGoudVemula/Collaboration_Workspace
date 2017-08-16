var myApp = angular.module("myApp", [ "ngRoute", "ngCookies" ]);
myApp.run(function($rootScope, $http, $cookieStore) {

	$rootScope.loggedInUser = $cookieStore.get("loggedInUser")||{};
	if ($rootScope.loggedInUser) {
		$http.defaults.headers.common['Authorization'] = 'Basic'
				+ $rootScope.loggedInUser;
	}

})