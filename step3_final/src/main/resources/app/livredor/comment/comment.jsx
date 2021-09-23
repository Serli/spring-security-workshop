import React, {Component} from "react";
import './comment.css';

import UserService from "../../service/UserService";

export default class extends Component {

    constructor(props) {
        super(props);
        this.state = {comments: []}
        this.userService = new UserService();
    }

    componentDidMount() {
        this.loadComments();
        this.userService.initUser();
    }


    loadComments() {
        console.log("Chargement des commentaires");

        fetch('/api/comments')
            .then(res => res.json())
            .then((response) => {
                console.log(response);

                this.setState({comments: response});
            });

    }

    valider() {
        if (this.state.newCommentText) {

            fetch('/api/comments',
                {
                    method: "POST",
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({text: this.state.newCommentText, user: this.userService.user})
                }).then(() => {
                this.closeNewComment();
            });
        } else {
            alert("Il faut saisir un texte.")
        }
    }

    deleteComment(comment) {
        fetch("/api/comments?id=" + comment.id, {method: "DELETE"})
            .then(() => this.loadComments());
    }

    closeNewComment() {
        this.setState({mode: undefined, newCommentText: ""});
        this.loadComments();
    }

    addComment() {
        this.setState({mode: "NEW-COMMENT"});
    }

    render() {
        let {comments, newCommentText, mode} = this.state;
        let {user} = this.userService;

        return (
            <div>
                <header>
                    {
                        user &&
                        <span
                            className="fas fa-user-alt fa-2x"
                            title="Connecté en tant que {{ctrl.userService.user.email}}"
                        > {user.name}</span>
                    }

                    <span className="title"><b>Livre d'or</b></span>
                    {
                        user &&
                        <span
                            title="Se déconnecter"
                            className="fas fa-sign-out-alt fa-2x"
                            onClick={() => this.userService.deconnecter()}></span>
                    }
                </header>

                <main className="App-intro">
                    <div className="comments-container">
                        {comments.map(comment =>
                            <div key={comment.id} className="comment">
                                {user.admin &&
                                <div className="delete-button"
                                    onClick={() => deleteComment(comment)}><span className="fa fa-times"></span></div>
                                }
                                <div className="comment-text">{comment.text}</div>
                                <div className="comment-writer">Ecris par {comment.user.name}</div>
                            </div>
                        )}
                    </div>

                </main>
                <footer className="new-comment">
                    <div>
                    <textarea
                        placeholder="Ajouter une commentaire"
                        onChange={(e) => this.setState({newCommentText: e.currentTarget.value})}
                        value={newCommentText}
                        rows="6"></textarea>
                        <button onClick={() => this.valider()}>
                            <span className="fas fa-check fa-3x"></span></button>
                    </div>
                </footer>
            </div>
        )
    }
}

