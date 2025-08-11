package TCC.Trabalho.TCC.V.de.Vigilancia.Service;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Post;
import TCC.Trabalho.TCC.V.de.Vigilancia.Repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Long id){
        return postRepository.findById(id);
    }

    // MÉTODO CREATE ATUALIZADO PARA SALVAR NO BANCO
    public Post createPost(Post post, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            post.setNomeArquivoPost(file.getOriginalFilename());
            post.setTipoMimePost(file.getContentType());
            post.setFotoPost(file.getBytes());
        }
        return postRepository.save(post);
    }

    public Post updatePost(Long id, Post postDetails){
        Post post = postRepository.findById(id).orElseThrow();
        post.setMessage(postDetails.getMessage());
        // Aqui você poderia adicionar lógica para atualizar a foto também, se necessário
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public void likePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow();
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }
}

