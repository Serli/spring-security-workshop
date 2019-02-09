export default class LoginCtrl {
  constructor($state, UserService, $stateParams) {
    this.userService = UserService;
    this.$state = $state;
    this.$stateParams = $stateParams;
  }

  $onInit() {
    this.logged();
  }

  login() {
    const data = {
      email: this.id,
      password: this.password
    };

    this.userService.login(data)
      .then(() => {
        this.logged();
      })
      .catch(e => {
        console.log(e);
        alert("Couple login/password incorrect")
      });

  }

  logged() {
    this.userService.getCurrentUser()
      .then((user) => {
        if (user) {
          this.logged = user;
          this.isLoading = false;

        }

      })
  }
}
