package TCC.Trabalho.TCC.V.de.Vigilancia.Controller;

import TCC.Trabalho.TCC.V.de.Vigilancia.Model.Postagens.Post;
import TCC.Trabalho.TCC.V.de.Vigilancia.Service.PostService;
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
    private final ObjectMapper objectMapper;

    public PostController(PostService postService, ObjectMapper objectMapper) {
        this.postService = postService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public Post createPost(@RequestParam("post") String postJson,
                           @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        Post post = objectMapper.readValue(postJson, Post.class);
        return postService.createPost(post, file);
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
    public List<Post> getAllPosts() {
        return postService.getAllPost();
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

    @PostMapping("/{id}/like")
    public void likePost(@PathVariable Long id) {
        postService.likePost(id);
    }

}
