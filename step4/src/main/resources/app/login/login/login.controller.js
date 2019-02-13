export default class LoginCtrl {
    constructor($state, UserService, $stateParams) {
        this.userService = UserService;
    }

    $onInit() {
        this.logged();
    }

    async login() {
        let data = new FormData();
        data.append("username", this.id);
        data.append("password", this.password);
        await this.userService.getCurrentUser();
        this.userService.login(data)
            .then((response) => {
                if (response.status === 200)
                    document.location.href = "/livredor";
            })
    }

    logged() {
        this.userService.getCurrentUser()
            .then((user) => {
                if (user) {
                    this.logged = user;
                    document.location.href="/livredor";
                }

            })
    }
}
