export default class CommentCtrl {

  constructor($http, UserService, $sce) {
    this.$http = $http;
    this.$sce = $sce;
    this.userService=UserService;
  }

  $onInit() {
    this.loadComments();
    this.userService.getCurrentUser();
  }

  loadComments() {
    console.log("Chargement des commentaires");
    this.isLoading = true;
    this.$http.get('/api/comments',{
      headers:{
        'Authorization': `Bearer ${this.userService.token}`
      }
    })
      .then((response) => {
        this.comments = response.data;
        this.isLoading = false;
      });
  }

  valider() {
    if(this.newCommentText){

    this.$http.post('/api/comment', {text: this.newCommentText},
      {
        method: "POST",
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${this.userService.token}`
        },
      }).then(() => {
      this.closeNewComment();
    });
    }else{
      alert("Il faut saisir un texte.")
    }

  }

  closeNewComment() {
    this.mode=undefined;
    this.newCommentText = undefined;
    this.loadComments();
  }

  addComment() {
    this.mode =  "NEW-COMMENT";
  }
}
