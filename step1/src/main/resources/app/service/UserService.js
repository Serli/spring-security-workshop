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
    }

    initUser() {
    }



}

export default "UserService"
