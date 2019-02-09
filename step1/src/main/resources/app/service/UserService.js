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
        document.location.href = "/";
    }

    initUser() {
        let user = this.getCookie("USER");
        if (user) {
            this.user = JSON.parse(user);
        }else{
            this.deconnecter();
        }
    }

    getCookie(cname) {
        var name = cname + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

}

export default "UserService"
