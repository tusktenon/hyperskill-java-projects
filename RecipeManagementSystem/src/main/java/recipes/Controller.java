package recipes;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/recipe")
public class Controller {

    private final RecipeRepository repository;

    public Controller(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Recipe retrieve(@PathVariable long id) {
        return findByIdOrThrowNotFound(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        repository.delete(findByIdOrThrowNotFound(id));
    }

    @PostMapping("/new")
    public Map<String, Long> add(@Valid @RequestBody Recipe recipe) {
        Recipe added = repository.save(recipe);
        return Map.of("id", added.getId());
    }

    private Recipe findByIdOrThrowNotFound(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
