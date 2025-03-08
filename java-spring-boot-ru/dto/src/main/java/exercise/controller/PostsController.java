package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RequestMapping(path = "/posts")
@RestController
public class PostsController {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    @GetMapping(path = "/{id}")
    public PostDTO get(@PathVariable Long id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with id "+id+" not found"));
        var comments = commentRepository.findByPostId(id);

        return postToDTO(post, comments);
    }

    @GetMapping
    public List<PostDTO> getAll() {
        List<PostDTO> result = new ArrayList<>();
        postRepository.findAll().forEach(post -> {
            var comments = commentRepository.findByPostId(post.getId());
            result.add(postToDTO(post, comments));
        });
        return result;
    }

    private PostDTO postToDTO(Post post, List<Comment> comments) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(comments.stream().map(this::commentToDTO).toList());
        return dto;
    }
    private CommentDTO commentToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }
}
// END
