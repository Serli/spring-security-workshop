import {bootstrap, module} from 'angular';
import "@babel/polyfill";
import Login from "./login/login";
import "jquery";

import router from './router/router';
import 'angular-ui-router';

import {default as userServiceName, UserService} from "../service/UserService";
import {default as csrfInterceptorName, csrfInterceptor} from "../service/Interceptor";

module('login', ['ui.router'])
    .component(Login.name, Login.component)
    .service(userServiceName, UserService)
    .factory(csrfInterceptorName, csrfInterceptor)
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push(csrfInterceptorName)
    }])
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', router])
;
bootstrap(document.body, ['login']);
