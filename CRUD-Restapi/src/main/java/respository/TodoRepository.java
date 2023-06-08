package respository;
import models.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface TodoRepository extends MongoRepository<Todo, Integer> {

}
