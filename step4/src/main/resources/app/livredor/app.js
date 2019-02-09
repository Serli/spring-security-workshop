import {bootstrap, module} from 'angular';
import Comment from "./comment/comment";
import "../style/app.css";


import router from './router/router';
import 'angular-ui-router';

import "@fortawesome/fontawesome-free/css/all.min.css";

import {default as userServiceName, UserService} from "../service/UserService";
import {CommentService, default as commentServiceName} from "../service/CommentService";

module('app', ['ui.router'])
    .component(Comment.name, Comment.component)
    .service(userServiceName, UserService)
    .service(commentServiceName, CommentService)
    .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', router])

bootstrap(document.body, ['app']);
