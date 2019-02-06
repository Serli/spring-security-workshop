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
    const data = new FormData();
    data.append("username", this.id);
    data.append("password", this.password);

    this.$http.post('/api/user/login', data, {
      transformRequest: angular.identity,
      headers: {'Content-Type': undefined}
    }).then(response => {
      this.logged(response.data);
    }).catch(e => {

      console.log(e);
      alert("Couple login/password incorrect")
    });
  }

  logged(user) {
      if (user) {
        this.logged = user;
        this.userService.user=user;
        this.isLoading = false;
        this.$state.go('home.comments');
      }

  }
}
