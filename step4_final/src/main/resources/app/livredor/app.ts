import angular from 'angular';
import Comment from "./comment/comment";
import User from "./comment/user";
import "../style/app.css";import "jquery";

import CommentsComponent from './comment/comment';
import UserComponent from './comment/user';
import uirouter from '@uirouter/angularjs';

import "@fortawesome/fontawesome-free/css/all.min.css";

import {default as userServiceName, UserService} from "../service/UserService";
import {default as commentServiceName, CommentService} from "../service/CommentService";
// Declare livredor level module which depends on views, and core components
angular.module('app', [uirouter])
    .component(Comment.name, Comment.component)
    .component(User.name, User.component)
    .service(userServiceName, UserService)
    .service(commentServiceName, CommentService)
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

        const commentsUser = {
            name: "user",
            state: {
                url: "/comments/user?id",
                views: {
                    'main@': {
                        component: UserComponent.name
                    }
                }
            }
        };



        $urlRouterProvider.otherwise("/comments");

        $stateProvider
            .state(comments.name, comments.state);

        $stateProvider
            .state(commentsUser.name, commentsUser.state)

    }])


angular.bootstrap(document.body, ['app']);
