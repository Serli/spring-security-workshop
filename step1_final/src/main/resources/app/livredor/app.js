import * as angular from 'angular';
import Comment from "./comment/comment";
import "../style/app.css";
import "jquery";


import router from '../livredor/router/router';
import 'angular-ui-router';

import "@fortawesome/fontawesome-free/css/all.min.css";

import {default as userServiceName, UserService} from "../service/UserService";
// Declare livredor level module which depends on views, and core components
angular.module('app', ['ui.router'])
  .component(Comment.name, Comment.component)
  .service(userServiceName, UserService)
  .config(['$stateProvider', '$urlRouterProvider', router])


angular.bootstrap(document.body, ['app']);
