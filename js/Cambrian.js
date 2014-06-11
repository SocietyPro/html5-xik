/**
* @namespace Cambrian
*/

var Cambrian = {

	/**
	* @class
	* @classdesc Cambrian.Profile
	*/
	Profile: {

		/**
		* Stores the current profile
		*
		* @private
		*/
		currentProfile: null,

		/**
		*	Creates a new profile
		* 	profileInfo {
		* 		name:
				id:
				first_name:
				last_name:
				mobile_number:
				email:
				FGP_fingerprint:
				bitcoin_address:
				skype_id:
		*	}
		*	
		*	@method Create
		*	@returns The same profile filled with the new id
		*/
		Create: function(profileInfo, callback) {
			Cambrian.Profile.List(function(err, profilesList) {
				var err = null,
					filer = new Filer();

				filer.init({persistent: true, size: 2014 * 1024}, function(fs) {
					if ( true ) {// TODO: validations
						// save
						profileInfo["id"] = profilesList.length + 1;
						var profileStr = JSON.stringify(profileInfo);
						
						filer.write("/profiles/profile" + profileInfo["id"], 
							{data: profileStr, type: 'text/plain'},
							function() {
								if ( typeof callback == 'function') {
									callback(err, profileInfo);
								}
						});
						
						// localStorage.setItem('profiles', JSON.stringify({'list': profilesList}));
					} else {
						err = "Invalid profile name";
						if ( typeof callback == 'function') {
							callback(err, profileInfo);
						}
					}
					
					
				});
			});
		},
		
		/**
		* Returns an array with the existing profiles
		*
		* @method List
		* @returns profiles array
		*/
		List: function(callback) {
			var filer = new Filer();
			
			filer.init({persistent: true, size: 2014 * 1024}, function(fs) {
				filer.mkdir('profiles', false, function(dirEntry) {
					filer.ls('/profiles', function(entries) {

						var profiles = [],
							filesCount = 0;

						for (var i = entries.length - 1; i >= 0; i--) {
							if ( entries[i].isFile ) {
								filesCount ++;

								filer.open( entries[i], function(file) {
									
									var reader = new FileReader();
									reader.onload = function(evt) {
										var textFile = evt.target;
										var textContent = textFile.result;
										var profile = JSON.parse(textContent);
										profiles.push(profile);

										if ( profiles.length == entries.length) {
											// when every file has been processed
											callback(null, profiles);
										}
									}
									reader.readAsText(file);
								});
							}
						}

						// in case there were no files
						if ( filesCount == 0 ) {
							callback(null, profiles);
						}
					});
				});
			});
		},

		/**
		* Switches to the given profile id
		*
		* 	@method Switch
		*	@returns the profile if it exists or null otherwise
		*/
		Switch: function(profileId, callback) {
			
			Cambrian.Profile.GetInfo(profileId, function(err, profileInfo) {

				if ( profileInfo != null ) {
					Cambrian.Profile.currentProfile = profileInfo;
				} else {
					err = "Profile does not exist";
				}

				//console.log("switched to profile " + ((profileInfo != null ) ? profileInfo.name : "[No profile]") );
				if ( typeof callback == 'function') {
					callback(err, profileInfo);
				}
			});

		},

		/**
		* Gets the profile info for a given id
		*
		* @method GetInfo
		*
		*/
		GetInfo: function(profileId, callback) {

			Cambrian.Profile.List(function(err, profilesList) {
				var profileInfo 	= null;

				for(var i=0; i < profilesList.length; i++) {
					if ( profilesList[i]["id"] == profileId) {
						profileInfo = profilesList[i];
						break;
					}
				}

				if ( profileInfo == null ) {
					err = "Profile does not exist";
				}

				if ( typeof callback == 'function') {
					callback(err, profileInfo);
				}
			});
		},

		/**
		* Returns the current profile
		*
		* @method GetCurrentProfile
		*/
		GetCurrentProfile: function() {
			return Cambrian.Profile.currentProfile;
		}
	}
};



