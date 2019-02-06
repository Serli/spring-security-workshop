export class UserService {

  constructor($state, $http) {
    this.$state = $state;
    this.$http = $http;
  }

  set user(user) {
    this._user = user;
  }

  get user() {
    return this._user;
  }

  deconnecter() {
    this.user = undefined;
    this.$state.go("home.login");
  }

}

export default "UserService"
