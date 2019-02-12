export default class LoginCtrl {
    constructor(UserService, $http, $state) {
        this.userService = UserService;
        this.$http = $http;
        this.$state = $state;
    }

    $onInit() {
        this.logged();
    }

    login() {
        const data = {
            username: this.id,
            password: this.password
        };
        this.userService.login(data).then(() => {
            this.logged();
        });

    }

    logged() {
        return this.userService.getCurrentUser()
            .then((user) => {
                if (user) {
                    this.logged = user;
                    document.location.href = '/livredor';
                } else {
                    this.init = true;
                }

            })
    }
}
