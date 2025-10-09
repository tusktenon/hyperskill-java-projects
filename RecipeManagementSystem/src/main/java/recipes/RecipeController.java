package recipes;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeRepository repository;

    public RecipeController(RecipeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public Recipe retrieve(@PathVariable long id) {
        return findByIdOrThrowNotFound(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void update(@PathVariable long id,
                       @Valid @RequestBody Recipe updated,
                       @AuthenticationPrincipal SecurityChef securityChef) {
        Recipe current = findByIdOrThrowNotFound(id);
        principalIsAuthorOrThrowForbidden(securityChef, current);
        updated.setId(id);
        updated.setAuthor(securityChef.getChef());
        repository.save(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable long id, @AuthenticationPrincipal SecurityChef securityChef) {
        Recipe recipe = findByIdOrThrowNotFound(id);
        principalIsAuthorOrThrowForbidden(securityChef, recipe);
        repository.delete(recipe);
    }

    @PostMapping("/new")
    public Map<String, Long> add(@Valid @RequestBody Recipe recipe,
                                 @AuthenticationPrincipal SecurityChef securityChef) {
        recipe.setAuthor(securityChef.getChef());
        Recipe added = repository.save(recipe);
        return Map.of("id", added.getId());
    }

    @GetMapping(path = "/search/", params = "category")
    public List<Recipe> searchByCategory(@RequestParam String category) {
        return repository.findByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    @GetMapping(path = "/search/", params = "name")
    public List<Recipe> searchByName(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCaseOrderByDateDesc(name);
    }

    private Recipe findByIdOrThrowNotFound(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    private void principalIsAuthorOrThrowForbidden(SecurityChef securityChef, Recipe recipe) {
        if (!Objects.equals(securityChef.getChef().getId(), recipe.getAuthor().getId())) {
            throw new ResponseStatusException(FORBIDDEN);
        }
    }
}
