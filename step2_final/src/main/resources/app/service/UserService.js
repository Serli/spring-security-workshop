export default class UserService {

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
        return fetch(`/api/user/current`)
            .then(resp => {
                if (resp.status === 200) {
                    return resp.json();

                }
            })
            .then(user=>{
                this._user = user;
                return this._user;
            })
            .catch((e) => {
                alert(e);
            });
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

