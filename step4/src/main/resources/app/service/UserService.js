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

  deconnecter() {
    this.user = undefined;
    this.$http.post('/api/user/logout', undefined, {credentials: 'include'})
      .then(() => {
        document.location.href="/login";
      })
  }

  getCurrentUser() {

    return this.$http.get("/api/user/current", {
      credentials: 'include',
    })
      .then(resp => {
        if (resp.status === 200) {
          this._user = resp.data;
          return this._user;
        }
      })
      .catch((e) => {
        alert(e);
      });
  }

}

export default "UserService";
