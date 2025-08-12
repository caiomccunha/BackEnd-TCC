package TCC.Trabalho.TCC.V.de.Vigilancia.Service;


import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Post;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.CommentRepository;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.PostRepository;
import org.springframework.stereotype.Service;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment;
import java.util.List;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post não encontrado"));
        comment.setPost(post);
        // O usuário deve ser setado no comment antes de salvar (no front-end ou aqui)
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findAll().stream()
                .filter(c -> c.getPost() != null && c.getPost().getId() == postId)
                .toList();
    }
    }

