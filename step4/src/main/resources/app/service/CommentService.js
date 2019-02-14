export class CommentService {


    constructor($http) {
        this.$http = $http;
    }

    loadComments() {
        // return fetch('/api/comments', {
        //   credentials: 'include',
        // }).then(r=>r.json());

    }

    delete(comment) {
    }

    addComment(comment) {
    }

}

export default "CommentService";
