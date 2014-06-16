'use strict'

/* Controllers */

var cambrianControllers = angular.module('cambrianControllers',['ui.utils']);

	/**
	* Register controller for the page
	* @class registerController
	*
	*/
	cambrianControllers.controller('registerController', ['$scope','$location', function ($scope,$location) {
		var self = this;
		self.profiles = [];
		
		// model variables
		self.user = {}; //Current User
		self.maxlength = config.maxiLength; //Maxlength of username
		self.minlength = config.minLength; //Minlength of username
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
		*
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
					$location.path('/profile/'+profileObj.id).replace();
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


	/**
	* Handles the profile page
	* @class profileController
	*
	*/
	cambrianControllers.controller('profileController', ['$scope','$routeParams',function($scope,$routeParams){
		var self 		= this;
		this.userId 	= $routeParams.userId;
		this.user 		= null;

		Cambrian.Profile.GetInfo(this.userId, function(err,profile){
			if ( !err) {
				$scope.$apply(function() {
					self.user = profile; 
				});		
			}
		});
		
		this.tab 		= 0;
		this.aboutTab 	= 0;

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

		
	}]);

	cambrianControllers.controller('editInfoController',['$scope','$routeParams',function($scope,$routeParams){
	
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
		
		this.edit 		= false;
		this.success	= false;
		this.failed 	= false;
		
		this.isFieldNull = function(field) {
			return field === undefined || field === null || field === "";
		};


		/**
		* Updates the profile picture, property profile_picture
		*
		* @method changeProfilePic
		* @memberof profileController
 		* @instance
		*/ 
		this.changeProfilePic = function(evt) {
			var inputObj 	= evt.target["profilePicture"],
				file 		= null,
				reader 		= new FileReader();
			

			if ( inputObj.files.length == 0) {
				console.log("No image selected");
				return;
			}
			file = inputObj.files[0];
			

			if ( !file.type.match('image.*')) {
				console.log("File is not an image "+ file.type);
				return;
			}

			reader.onload = function(e) {
				Cambrian.Profile.GetInfo(self.userId, function(err, profile) {
					profile["profile_picture"] = e.target.result;
					Cambrian.Profile.Save(profile, function() {
						$scope.$apply(function() {
							self.user = profile;
						});		
					});
				});
			};

			reader.readAsDataURL(file);
		}

		this.setEdit = function(value) {
			this.edit = value;
		};

		this.isEdit = function() {
			return this.edit
		};

		this.addFields = function() {
			console.log("addFields");
			console.log(this.newInfo.skype_id);
			this.edit = false;
			this.success = true;
		};

	}]);
