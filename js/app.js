'use strict';

/* App Module */
(function() {
    var app = angular.module('cambrianRegisterApp',['ngRoute','ui.utils']);

    app.config(['$routeProvider',function($routeProvider){
    	$routeProvider.
    	when('/login',{
    		templateUrl: 'partials/login.html',
    		controller: 'registerController',
    		controllerAs: 'regC'
    	}).
    	when('/profile',{
    		templateUrl: 'partials/profile.html',
    		controller: 'profileController',
    		controllerAs: 'profile'
    	}).
    	when('/error404',{
    		templateUrl: 'partials/error404.html'
    	});
    	/*.otherwise({
    		redirectTo: '/error404'
    	});*/
    }]);
	/**
	* Register controller for the page
	* @class registerController
	*
	*/
	app.controller('registerController', ['$scope','$location', function ($scope,$location) {
		var self = this;
		self.profiles = [];
		
		// model variables
		self.user = {}; //Current User
		self.maxlength=config.maxiLength; //Maxlength of username
		self.minlength=config.minLength; //Minlength of username
		self.page = 0; // 0 to create/list page, 1 for welcome page
		this.tab = true; //true for list, false for create
		

		// private methods
		// appPageSwith: switch between pages
		var appPageSwitch = function(value) { 
			$scope.$apply(function() {
				self.page = value;
			});
		};

		//
		// public methods
		//

		/**
		* Handles the enter key press event
		*/
		this.keypressCallback = function() {
			if (!this.invalid() && !this.gotError()) {
				this.addProfile();
			}
		};


		/**
		* Gets the current page index
		* @method getPage
		*
		*/
		this.getPage = function() {
			return this.page;
		};

		/**
		* checks if is the firt time you log in
		* @method firstLogin
		*/
		this.firstLogin = function() {
			return self.profiles.length === 0;
		};
		

		/**
		* returns the current tab
		*
		* @method currentTab
		*/
		this.currentTab = function() {
			return (self.profiles.length !== 0) && this.tab;
		};
		

		/**
		* set the current tab (false for create, true for list)
		* @method setTab
		*/
		this.setTab = function(value) {
			this.tab = value;
		};
		// gotError: returns true if the username is empty
		this.gotError = function() {
			if (this.user.name === "") this.user.name = undefined;
			return (this.user.name === undefined);
		};
		// invalid: checcks for invalid characters
		this.invalid = function() {
			if (this.user.name === undefined) return false;
			else {
				this.user.name=this.user.name.trim();
                return /[^\w\s]/.test(this.user.name);
            }
		};
		// selectProfile: change between profiles
		this.selectProfile = function(profile) {
			Cambrian.Profile.Switch(profile.id, function(err, profileObj) {
				//console.log("switched to profile step1");
				$scope.$apply(function() {
					self.user = profileObj;
					//console.log("switched to profile step2");
					$location.path('/profile').replace();
				});
				//appPageSwitch(2);
			});

		}
		// addProfile: create profile
		this.addProfile = function() {
			var self = this;
			Cambrian.Profile.Create(this.user, function(err, profile) {

				//console.log("Create" + self.user + " == " + profile);
				if ( !err ) {
					Cambrian.Profile.Switch(self.user.id, function(err, profile) {
						//console.log("Switch" + self.user + " == " + profile);
						if ( !err) {
							$scope.$apply(function() {
								self.user = profile; 
								//console.log("switched to profile step2");
								$location.path('/profile').replace();
							});
							//appPageSwitch(2);
							
						} else {
							// show error msg	
						}
						//$location.path('/profile').replace();
					});
				} else {
					//show error msg
				}
			});
		};


		// initializatin
		var self = this;
		Cambrian.Profile.List(function(err,data){
			if (!err) {
				self.profiles = data;
				appPageSwitch(1);
			}
		});
		

	}]);

	app.controller('profileController', ['$scope',function($scope){
		this.user = Cambrian.Profile.GetCurrentProfile();
		this.tab = 0;

		 this.isSet = function(checkTab) {
            return this.tab === checkTab;
          };

          this.setTab = function(activeTab) {
            this.tab = activeTab;
          };
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
