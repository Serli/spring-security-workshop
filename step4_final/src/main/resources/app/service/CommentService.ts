import * as angular from "angular";

export class CommentService {


    constructor(private $http) {
    }

    loadComments() {
        // return fetch('/api/comments', {
        //   credentials: 'include',
        // }).then(r=>r.json());
        return this.$http.get('/api/comments', {
            credentials: 'include',
        });

    }

    delete(comment) {
        return this.$http.delete("/api/comments?id=" + comment.id,
            {
                credentials: 'include',
            })
    }

    addComment(comment) {
        return this.$http.post('/api/comments', {text: comment},
            {
                credentials: 'include',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
            })
    }

}

export default "CommentService";
