'use strict';

/* App Module */
(function() {
    var app = angular.module('cambrianApp',['ngRoute','cambrianAnimations','cambrianControllers']);

    app.config(['$routeProvider',function($routeProvider){
    	$routeProvider
			.when('/login',{
				templateUrl: 'partials/login.html',
				controller: 'registerController',
				controllerAs: 'regC'
			})
			.when('/profile/:userId',{
				templateUrl: 'partials/profile.html',
				controller: 'profileController',
				controllerAs: 'profile'
			})
			.when('/error404',{
				templateUrl: 'partials/error404.html'
			})
			.otherwise({
				redirectTo: '/login'
			});
			
		
    }]);

}) ();

/*function DeleteUsers() {
	var ini = new Date();
	var filer = new Filer();
	filer.init({persistent: true, size: 1024 * 1024}, function(fs) {
		filer.rm('/profiles', function() {
			var end = new Date();
			console.log("finished, " + (end.getMilliseconds() - ini.getMilliseconds() ) + "ms");
		});
	});
	console.log("return");
}
DeleteUsers();*/


$(document).ready(function() {     
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	Login.init();
});

$( 'base' ).attr( 'href', window.location.origin + window.location.pathname );
