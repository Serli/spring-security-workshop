export class UserService {

    constructor($http) {
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
        //TODO
    }

}

export default "UserService"
