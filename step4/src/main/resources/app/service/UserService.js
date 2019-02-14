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
    }

    login(data) {
    }

    getCurrentUser() {

    }

}

export default "UserService";
