import CommentsComponent from '../comment/comment';

export default function router($stateProvider, $urlRouterProvider, $locationProvider) {

    const comments = {
        name: "comments",
        state: {
            url: "/livredor",
            views: {
                'main@': {
                    component: CommentsComponent.name
                }
            }
        }
    };

    $urlRouterProvider.otherwise("/livredor");
    $locationProvider.html5Mode(true);
    $stateProvider
        .state(comments.name, comments.state)

}