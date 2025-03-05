package exercise;

import exercise.model.Post;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAll(@RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer page) {
        return ResponseEntity
                .ok()
                .headers(httpHeaders -> httpHeaders.add("X-Total-Count", String.valueOf(posts.size())))
                .body(posts.stream().skip((long) size * (page - 1)).limit(size).toList());
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Optional<Post>> get(@PathVariable String id) {
        var p = posts.stream().filter(post -> post.getId().equals(id)).findFirst();
        if (p.isPresent()) return ResponseEntity.ok(p);
        return ResponseEntity.status(404).body(Optional.empty());
    }

    @PostMapping("/posts")
    public ResponseEntity<Post> postResponseEntity(@RequestBody Post post) {
        posts.add(post);
        return ResponseEntity.status(201).body(post);
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Post> put(@RequestBody Post post, @PathVariable String id) {
        AtomicReference<ResponseEntity<Post>> response = new AtomicReference<>();
        posts.stream()
                .filter(pst -> pst.getId().equals(id))
                .findFirst()
                .ifPresentOrElse(
                        post1 -> {
                            post1.setTitle(post.getTitle());
                            post1.setBody(post.getBody());
                            response.set(ResponseEntity.ok(post1));
                        },
                        () -> response.set(ResponseEntity.status(204).body(null)));
        return response.get();
    }
    // END

    @DeleteMapping("/posts/{id}")
    public void destroy(@PathVariable String id) {
        posts.removeIf(p -> p.getId().equals(id));
    }
}
