export default class LoginCtrl {
  constructor($state, UserService, $stateParams) {
    this.userService = UserService;
  }

  $onInit() {
    this.logged();
  }

  logged() {
    this.userService.getCurrentUser()
      .then((user) => {
        if (user) {
          this.logged = user;
        }

      })
  }
}
