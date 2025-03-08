package exercise.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

// BEGIN
@Getter
@Setter
public class PostDTO {
    private long id;
    private String title;
    private String body;
    private List<CommentDTO> comments;
}
// END
