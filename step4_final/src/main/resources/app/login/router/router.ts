import LoginComponent from '../login/login'

export default function router($stateProvider, $urlRouterProvider, $locationProvider) {


    const login = {
        name: "login",
        state: {
            url: "/login",
            views: {
                'main@': {
                    component: LoginComponent.name
                }
            }
        }
    };

    const error = {
        name: "login.error",
        state: {
            url: "/error",
            views: {
                'error@login': {
                    template: `<div class="error">Couple login/mot de passe incorrect</div>`,
                }
            }
        }
    };


    $urlRouterProvider.otherwise("/login");

    $locationProvider.html5Mode(true);
    $stateProvider
        .state(login.name, login.state)
        .state(error.name, error.state)

}