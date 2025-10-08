package recipes;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable long id, @Valid @RequestBody Recipe updated) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        updated.setId(id);
        updated.setDate(Instant.now());
        repository.save(updated);
    }

    @PostMapping("/new")
    public Map<String, Long> add(@Valid @RequestBody Recipe recipe) {
        recipe.setDate(Instant.now());
        Recipe added = repository.save(recipe);
        return Map.of("id", added.getId());
    }

    @GetMapping("/search/")
    public List<Recipe> search(@RequestParam(required = false) String category,
                               @RequestParam(required = false) String name) {
        if (category != null && name == null)
            return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
        if (category == null && name != null)
            return repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    private Recipe findByIdOrThrowNotFound(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
