export default class LoginCtrl {
  constructor($http, $state, UserService) {
    this.$http = $http;
    this.userService = UserService;
    this.$state = $state;
  }

  $onInit() {
    this.logged();
  }

  login() {
    const data = {
      email: this.id,
      password: this.password
    };

    this.$http.post('/api/user/login', data).then(response => {
      if (response.status === 200 && response.data) {
        this.userService.token = response.data.token;
        this.logged();
      }
    }).catch(e => {

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
          this.$state.go('home.comments');
        }

      })
  }
}
