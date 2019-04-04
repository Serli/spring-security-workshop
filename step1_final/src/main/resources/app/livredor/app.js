import angular from 'angular';
import Comment from "./comment/comment";
import "../style/app.css";import "jquery";

import CommentsComponent from './comment/comment';
import uirouter from '@uirouter/angularjs';

import "@fortawesome/fontawesome-free/css/all.min.css";

import {default as userServiceName, UserService} from "../service/UserService";
// Declare livredor level module which depends on views, and core components
angular.module('app', [uirouter])
  .component(Comment.name, Comment.component)
  .service(userServiceName, UserService)
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
