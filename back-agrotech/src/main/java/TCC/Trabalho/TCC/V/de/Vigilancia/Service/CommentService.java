package TCC.Trabalho.TCC.V.de.Vigilancia.Service;


import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Post;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.CommentRepository;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.PostRepository;
import org.springframework.stereotype.Service;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment;


@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Comment addComment(Long postId, Comment comment) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post não encontrado"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
