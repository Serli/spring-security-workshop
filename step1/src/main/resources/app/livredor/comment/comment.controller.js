export default class CommentCtrl {

    constructor($http, UserService, $sce) {
        this.$http = $http;
        this.$sce = $sce;
        this.userService = UserService;
    }

    $onInit() {
        this.loadComments();
        this.userService.initUser();
    }


    loadComments() {

    }

    valider() {


    }

    deleteComment(comment){

    }
}
