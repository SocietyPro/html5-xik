'use strict';

/* App Module */
(function() {
    var app = angular.module('cambrianRegisterApp',['ngRoute','ui.utils']);

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
		
		//
		// private methods
		//


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
		* @method keypressCallback
		* @memberof registerController
		* @instance
		*/
		this.keypressCallback = function() {
			if (!this.invalid() && !this.gotError()) {
				this.addProfile();
			}
		};


		/**
		* Gets the current page index
		* @method getPage
		* @memberof registerController
		* @instance
		*/
		this.getPage = function() {
			return this.page;
		};

		/**
		* checks if is the firt time you log in
		* @method firstLogin
		* @memberof registerController
		* @instance
		*/
		this.firstLogin = function() {
			return self.profiles.length === 0;
		};
		

		/**
		* returns the current tab
		*
		* @method currentTab
		* @memberof registerController
		* @instance
		*/
		this.currentTab = function() {
			return (self.profiles.length !== 0) && this.tab;
		};
		

		/**
		* set the current tab (false for create, true for list)
		* @method setTab
		* @memberof registerController
		* @instance
		*/
		this.setTab = function(value) {
			this.tab = value;
		};

		/**
		* Returns true if the username is empty
		* @method gotError
		* @memberof registerController
		* @instance
		*/
		this.gotError = function() {
			if (this.user.name === "") this.user.name = undefined;
			return (this.user.name === undefined);
		};

		/**
		* Checks for invalid characters
		* @method invalid
		* @memberof registerController
		* @instance
		*/
		this.invalid = function() {
			if (this.user.name === undefined) return false;
			else {
				this.user.name=this.user.name.trim();
                return /[^\w\s]/.test(this.user.name);
            }
		};

		/**
		* change between profiles
		* @method selectProfile
		* @memberof registerController
		* @instance
		*/
		this.selectProfile = function(profile) {
			Cambrian.Profile.Switch(profile.id, function(err, profileObj) {
				//console.log("switched to profile step1");
				$scope.$apply(function() {
					self.user = profileObj;
					//console.log("switched to profile step2");
					$location.path('/profile/'+profileObj.id).replace();
				});
				//appPageSwitch(2);
			});

		}

		/**
		* create profile
		* @method addProfile
		* @memberof registerController
		* @instance
		*/
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
								$location.path('/profile/'+profile.id).replace();
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

	app.controller('profileController', ['$scope','$routeParams',function($scope,$routeParams){
		var self = this;
		this.userId = $routeParams.userId;
		Cambrian.Profile.GetInfo(this.userId, function(err,profile){
			if ( !err) {
				$scope.$apply(function() {
					self.user = profile; 
				});		
			} else {
				// show error msg	
			}
		});
		this.tab = 0;
		this.aboutTab = 0;

		this.isSet = function(checkTab) {
			return this.tab === checkTab;
		};

		this.setTab = function(activeTab) {
			this.tab = activeTab;
		};

		this.isSetAboutTab = function(checkTab) {
			return this.aboutTab === checkTab;
		};

		this.setAboutTab = function(activeTab) {
			this.aboutTab = activeTab;
		};

		this.isFieldNull = function(field) {
			return field === undefined || field === null || field === "";
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


$(document).ready(function() {     
	Metronic.init(); // init metronic core components
	Layout.init(); // init current layout
	Login.init();
});

$( 'base' ).attr( 'href', window.location.origin + window.location.pathname );