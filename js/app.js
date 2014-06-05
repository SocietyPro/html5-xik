'use strict';

/* App Module */
(function() {
	var app = angular.module('cambrianRegisterApp',[]);

	app.controller('registerController', ['$scope', function ($scope) {
		var self = this;
		self.profiles = [];
		
		// model variables
		self.user = {};
		self.maxlength=config.maxiLength;
		self.minlength=config.minLength;
		self.page = 0;
		

		// private methods

		var appPageSwitch = function(value) {
			$scope.$apply(function() {
				self.page = value;
			});
		};


		// public methods

		this.getPage = function() {
			return this.page;
		};

		this.firstLogin = function() {
			return self.profiles.length === 0;
		};

		this.gotError = function() {
			return (this.user.name === undefined);
		};

		this.invalid = function() {
			if (this.user.name === undefined) return false;
			else {
				this.user.name=this.user.name.trim();
                return /[^\w\s]/.test(this.user.name);
            }
		};

		this.selectProfile = function(profile) {
			Cambrian.Profile.Switch(profile.id, function(err, profileObj) {
				self.user = profileObj;
				appPageSwitch(2);
			});
		}

		this.addProfile = function() {
			var self = this;
			
			if (this.user.name === "") this.user.name = undefined;
			Cambrian.Profile.Create(this.user, function(err, profile) {

				//console.log("Create" + self.user + " == " + profile);
				if ( !err ) {
					Cambrian.Profile.Switch(self.user.id, function(err, profile) {
						//console.log("Switch" + self.user + " == " + profile);
						if ( !err) {
							self.user = profile; 
							appPageSwitch(2);
						} else {
							// show error msg	
						}
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

}) ();