import {module, bootstrap} from 'angular';
import Login from "./login/login";
import Comment from "./comment/comment";
import Home from "./home";
import "../style/app.css";
import "jquery";


import router from './router/router';
import 'angular-ui-router';

import "@fortawesome/fontawesome-free/css/all.min.css";

import {default as userServiceName, UserService} from "./service/UserService";
import AuthInterceptor from "./service/AuthInterceptor";
// Declare app level module which depends on views, and core components
module('app', ['ui.router'])
  .component(Home.name, Home.component)
  .component(Login.name, Login.component)
  .component(Comment.name, Comment.component)
  .service(userServiceName, UserService)
  .factory('authInterceptor', AuthInterceptor)
  .config(['$stateProvider', '$urlRouterProvider', router])
  .config(['$httpProvider', ($httpProvider) => {
    $httpProvider.interceptors.push('authInterceptor');
  }]);

bootstrap(document.body, ['app']);
