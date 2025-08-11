package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.CommentService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/comments")
@CrossOrigin (origins = "*")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post/{postId}")
    public Comment addComment(@PathVariable Long postId, @RequestBody Comment comment) {
        // Agora o tipo 'Comment' aqui corresponde ao esperado pelo seu serviço
        return commentService.addComment(postId, comment);
    }
}
