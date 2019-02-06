export class UserService {

  constructor($state, $http, $q) {
    this.$state = $state;
    this.$http = $http;
    this.$q = $q;
  }

  set user(user) {
    this._user = user;
  }

  get user() {
    return this._user;
  }

  set token(token) {
    token ? sessionStorage.setItem("x-auth-token", token)
    : sessionStorage.clear();
    this._token = token;
  }

  get token() {
    return this._token || sessionStorage.getItem("x-auth-token");
  }

  deconnecter() {
    this.user = undefined;
    this.token = undefined;
    this.$state.go("home.login");
  }

  getCurrentUser() {
    let response = this.token && (
      this.$http.get("/api/user/current", {
        headers: {
          'Authorization': `Bearer ${this.token}`
        }
      })
        .then(resp => {
          if (resp.status === 200) {
            this._user = resp.data;
            return this._user;
          }
        }).catch((e) => {
        alert(e);
      })
    );
    return response || this.$q.resolve();
  }

}

export default "UserService"
