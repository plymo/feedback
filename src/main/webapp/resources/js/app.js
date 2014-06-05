'use strict';

var feedbackApp = angular.module('feedbackApp', []);

feedbackApp.controller('FeedbackController', ['$rootScope', '$scope', '$http',
    function ($rootScope, $scope, $http) {
        $scope.feedback = {name: "", email: "", message: "", agreedWithSpam: true};

        $scope.createFeedback = function () {
            console.log('create feedback');

            $http.post('feedback', {
                "name": $scope.feedback.name,
                "email": $scope.feedback.email,
                "message": $scope.feedback.message,
                "agreedWithSpam": $scope.feedback.agreedWithSpam
            })
                .success(function (data) {
                    console.log('feedback data = ', data);
                    $scope.feedback = {name: "", email: "", message: "", agreedWithSpam: true};

                    $rootScope.$broadcast("listChanged", { newComment: data });
                })
                .error(function (data) {
                    console.log('error: comments data = ', data);
                });
        };
    }
]);

feedbackApp.controller('FeedbackCommentsController', ['$rootScope', '$scope', '$http',
    function ($rootScope, $scope, $http) {

        $scope.getFeedbackMessages = function () {
            $http.get('feedback')
                .success(function (data) {
                    console.log('data = ', data);
                    $scope.comments = data;
                })
                .error(function (data) {
                    console.log('error: data = ', data);
                });
        };

        // listen to feedback comment added and reload immediately
        $rootScope.$on('listChanged', function (event, args) {
            console.log('new feedback = ', args);
            $scope.getFeedbackMessages();
        });

        $scope.getFeedbackMessages();
    }
]);
