import angular from 'angular';
import Comment from "./comment/comment";
import "../style/app.css";import "jquery";

import CommentsComponent from './comment/comment';
import uirouter from '@uirouter/angularjs';

import "@fortawesome/fontawesome-free/css/all.min.css";
import AuthInterceptor from "../service/AuthInterceptor";
import {default as userServiceName, UserService} from "../service/UserService";
// Declare livredor level module which depends on views, and core components
angular.module('app', [uirouter])
    .component(Comment.name, Comment.component)
    .service(userServiceName, UserService)
    .factory('authInterceptor', AuthInterceptor)
    .config(['$httpProvider', ($httpProvider) => {
        $httpProvider.interceptors.push('authInterceptor');
    }])
    .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {


        const comments = {
            name: "comments",
            state: {
                url: "/comments",
                views: {
                    'main@': {
                        component: CommentsComponent.name
                    }
                }
            }
        };

        $urlRouterProvider.otherwise("/comments");

        $stateProvider
            .state(comments.name, comments.state)

    }])


angular.bootstrap(document.body, ['app']);
