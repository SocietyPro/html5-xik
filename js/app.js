'use strict';

/* App Module */
(function() {
    var app = angular.module('cambrianApp',['ngRoute','cambrianAnimations','cambrianControllers','cambrianDirectives']);

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

    if ( typeof toastr != 'undefined' ) {
	    toastr.options = {
		  "closeButton": true,
		  "debug": false,
		  "positionClass": "toast-top-right",
		  "onclick": null,
		  "showDuration": "200",
		  "hideDuration": "200",
		  "timeOut": "1000",
		  "extendedTimeOut": "200",
		  "showEasing": "swing",
		  "hideEasing": "linear",
		  "showMethod": "fadeIn",
		  "hideMethod": "fadeOut"
		}
	}

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
