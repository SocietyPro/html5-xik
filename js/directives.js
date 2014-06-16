(function(){
    var cambrianDirectives = angular.module('cambrianDirectives', []);

    cambrianDirectives.directive("aboutProfile", function() {
      return {
        restrict: "E",
        templateUrl: "partials/about-profile.html",
        controller: "editInfoController",
        controllerAs: "about"
      };
    });

  })();
