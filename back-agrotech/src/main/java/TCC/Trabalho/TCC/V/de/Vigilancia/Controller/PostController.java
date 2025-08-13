package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Post;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.PostService;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.UsuarioService;
import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Usuario.UsuarioModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin (origins = "*")
public class PostController {
    private final PostService postService;
    private final UsuarioService usuarioService;
    private final ObjectMapper objectMapper;

    public PostController(PostService postService, UsuarioService usuarioService, ObjectMapper objectMapper) {
        this.postService = postService;
        this.usuarioService = usuarioService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<?> createPost(@RequestPart("post") String postJson,
                                        @RequestPart(value = "file", required = false) MultipartFile file,
                                        @RequestParam("autorId") Long autorId) throws IOException {
        Post post = objectMapper.readValue(postJson, Post.class);
        if (post.getMessage() == null || post.getMessage().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("O campo 'message' do post é obrigatório.");
        }
        Post savedPost = postService.createPost(post, file, autorId);
        return ResponseEntity.ok(savedPost);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getPostImage(@PathVariable Long id) {
        Post post = postService.getPostById(id).orElseThrow(() -> new RuntimeException("Post não encontrado"));

        if (post.getFotoPost() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, post.getTipoMimePost());

        return new ResponseEntity<>(post.getFotoPost(), headers, HttpStatus.OK);
    }

    @GetMapping
        public ResponseEntity<?> getAllPosts(@RequestParam Long usuarioId) {
            if (usuarioId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Parâmetro 'usuarioId' é obrigatório para esta operação.");
            }
            List<Post> posts = postService.getAllPost();
            List<PostResponse> response = posts.stream().map(post -> toResponse(post, usuarioId)).toList();
            return ResponseEntity.ok(response);
    }

    private PostResponse toResponse(Post post, Long usuarioId) {
        boolean likedByCurrentUser = false;
        if (usuarioId != null && post.getLikedBy() != null) {
            likedByCurrentUser = post.getLikedBy().stream().anyMatch(u -> u.getId().equals(usuarioId));
        }
        UsuarioModel autor = post.getAutor();
        return new PostResponse(post.getId(), post.getMessage(), post.getLikes(), likedByCurrentUser, autor.getId(), autor.getNome());
    }

    public static class PostResponse {
        public long id;
        public String message;
        public int likes;
        public boolean likedByCurrentUser;
        public Long autor;
        public String autorNome;
        public PostResponse(long id, String message, int likes, boolean likedByCurrentUser, Long autor, String autorNome) {
            this.id = id;
            this.message = message;
            this.likes = likes;
            this.likedByCurrentUser = likedByCurrentUser;
            this.autor = autor;
            this.autorNome = autorNome;
        }
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postService.getPostById(id).orElseThrow();
    }

    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        return postService.updatePost(id, post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @GetMapping("/{id1}/liked/{id2}")
    public boolean verificarConexao(@PathVariable Long postId, @PathVariable Long usuarioId) {
        Post post = postService.getPostById(postId)
            .orElseThrow(() -> new RuntimeException("Post não encontrado"));
        UsuarioModel usuario = usuarioService.buscarUserPorID(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário que curtiu não encontrado"));
        return post.getLikedBy().contains(usuario);
    }

    @PutMapping("/{id1}/like/{id2}")
    public void conectar(@PathVariable Long postId, @PathVariable Long usuarioId) {
        postService.curtirPost(postId, usuarioId);
    }
}
