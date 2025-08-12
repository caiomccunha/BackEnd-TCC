package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


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
        return commentService.addComment(postId, comment);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByPost(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponse> response = comments.stream().map(CommentResponse::fromComment).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    public static class CommentResponse {
        public Long id;
        public String content;
        public String nomeUsuario;
        public byte[] fotoUsuario;

        public static CommentResponse fromComment(Comment comment) {
            CommentResponse resp = new CommentResponse();
            resp.id = comment.getId();
            resp.content = comment.getContent();
            if (comment.getUsuario() != null) {
                resp.nomeUsuario = comment.getUsuario().getNome();
                resp.fotoUsuario = comment.getUsuario().getFoto_perfil();
            }
            return resp;
        }
    }
    }
