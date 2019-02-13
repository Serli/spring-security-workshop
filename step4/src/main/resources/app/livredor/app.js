import {bootstrap, module} from 'angular';
import Comment from "./comment/comment";
import "../style/app.css";


import router from './router/router';
import 'angular-ui-router';

import "@fortawesome/fontawesome-free/css/all.min.css";
import 'angular-cookies';

import {default as userServiceName, UserService} from "../service/UserService";
import {CommentService, default as commentServiceName} from "../service/CommentService";

import {default as csrfInterceptorName, csrfInterceptor} from "../service/Interceptor";

module('app', ['ui.router'])
    .component(Comment.name, Comment.component)
    .service(userServiceName, UserService)
    .service(commentServiceName, CommentService)
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', router])
    .factory(csrfInterceptorName, csrfInterceptor)
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push(csrfInterceptorName);
    }])

bootstrap(document.body, ['app']);
