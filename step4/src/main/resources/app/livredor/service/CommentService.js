export class CommentService {


  constructor($http) {
    this.$http = $http;
  }

  loadComments() {
    // return fetch('/api/comments', {
    //   credentials: 'include',
    // }).then(r=>r.json());
    return this.$http.get('/api/comments', {
      credentials: 'include',
    });

  }

  addComment(comment){
    return this.$http.post('/api/comment', {text: comment},
      {
        credentials:'include',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json',
        },
      })
  }

}

export default "CommentService";
