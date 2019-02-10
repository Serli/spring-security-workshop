export default class CommentCtrl {

    constructor($http, UserService, $sce) {
        this.$http = $http;
        this.$sce = $sce;
        this.userService = UserService;
    }

    $onInit() {
        this.loadComments();
        this.userService.getCurrentUser();
    }


    loadComments() {
        console.log("Chargement des commentaires");


        this.$http.get('/api/comments')
            .then((response) => {
                this.comments = response.data;
            });
    }

    valider() {
        if (this.newCommentText) {

            this.$http.post('/api/comments', {text: this.newCommentText, user:this.userService.user},
                {
                    method: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                }).then(() => {
                this.closeNewComment();
            });
        } else {
            alert("Il faut saisir un texte.")
        }

    }

    deleteComment(comment){
        this.$http.delete("/api/comments?id="+comment.id)
            .then(()=>this.loadComments());
    }

    closeNewComment() {
        this.mode = undefined;
        this.newCommentText = undefined;
        this.loadComments();
    }

    addComment() {
        this.mode = "NEW-COMMENT";
    }
}
