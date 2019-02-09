import {module, bootstrap} from 'angular';
import Login from "./login/login";
import "jquery";

import router from './router/router';
import 'angular-ui-router';

import {default as userServiceName, UserService} from "../livredor/service/UserService";

module('login', ['ui.router'])
  .component(Login.name, Login.component)
  .service(userServiceName, UserService)
  .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', router])
;
bootstrap(document.body, ['login']);
