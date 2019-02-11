export class UserService {

    constructor($state, $http) {
        this.$state = $state;
        this.$http = $http;
        this.api = "/api/user";
    }

    set user(user) {
        this._user = user;
    }

    get user() {
        return this._user;
    }

    deconnecter() {
        document.location.href = `${this.api}/logout`;
    }

    getCurrentUser() {
        return this.$http.get(`${this.api}/current`)
            .then(resp => {
                if (resp.status === 200) {
                    this._user = resp.data;
                    return this._user;
                }
            }).catch((e) => {
                alert(e);
            });
    }

}

export default "UserService"
