import {UserService, default as userServiceName} from "../../service/UserService";
import {CommentService, default as commentServiceName} from "../../service/CommentService";

export default class CommentCtrl {

    private static readonly $inject=[
        userServiceName,
        "$sce",
        commentServiceName
    ]
    private comments: Array<any>;
    private newCommentText: string;

    constructor(private userService:UserService, private $sce, private commentService:CommentService) {
    }

    $onInit() {
        this.userService.getCurrentUser()
            .then((response) => {
                this.loadComments();
            });
    }

    loadComments() {
        return this.commentService.loadComments()
            .then((response) => {
                this.comments = response.data;
                return response;
            });
    }

    deleteComment(comment){
        this.commentService.delete(comment)
            .then(()=>this.loadComments());
    }

    async valider() {
        if (this.newCommentText) {
            await this.loadComments();
            let response = await this.commentService.addComment(this.newCommentText);
            if (response.status === 200) {
                this.closeNewComment();
            }
        } else {
            alert("Il faut saisir un texte.")
        }

    }

    closeNewComment() {
        this.newCommentText = undefined;
        this.loadComments();
    }

}
