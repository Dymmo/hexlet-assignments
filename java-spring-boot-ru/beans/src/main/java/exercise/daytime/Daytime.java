package exercise.daytime;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

// Интерфейс содержит метод, возвращающий название времени суток
// Реализация методов представлена в классах Morning, Day, Evening, Night,
// которые реализуют этот интерфейс
@Component
public interface Daytime {

    String getName();
}
