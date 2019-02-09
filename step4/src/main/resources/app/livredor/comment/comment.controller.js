export default class CommentCtrl {

  constructor(UserService, $sce, CommentService) {
    this.$sce = $sce;
    this.userService = UserService;
    this.commentService = CommentService;
  }

  $onInit() {
    this.loadComments();
    this.userService.getCurrentUser();
  }

  loadComments() {
    console.log("Chargement des commentaires");
    this.isLoading = true;
    this.commentService.loadComments()
      .then((response) => {
        this.comments = response.data;
        this.isLoading = false;
      });
  }

  valider() {
    if (this.newCommentText) {

      this.commentService.addComment(this.newCommentText)
        .then(() => {
          this.closeNewComment();
        })
        .catch(() => {
          alert('ajout du commentaire impossible.');
        });
    } else {
      alert("Il faut saisir un texte.")
    }

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
