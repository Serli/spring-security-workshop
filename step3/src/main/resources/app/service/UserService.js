export class UserService {

    constructor($state, $http, $q) {
        this.$state = $state;
        this.$http = $http;
        this.$q = $q;
        this.api = "/api/user"
    }

    set user(user) {
        this._user = user;
    }

    get user() {
        return this._user;
    }

    login(user) {

    }

    deconnecter() {
        this.user = undefined;
        document.location.href = "/";
    }

    getCurrentUser() {
        return this.$http
            .get(`${this.api}/current`)
            .then(resp => {
                if (resp.status === 200) {
                    this._user = resp.data;
                    return this._user;
                }
                return this.$q.reject(resp);
            })
            .catch((e) => {
                return this.$q.reject(e);
            })

    }

}

export default "UserService"
