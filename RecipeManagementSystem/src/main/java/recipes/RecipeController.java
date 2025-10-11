package recipes;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/recipe")
@AllArgsConstructor
public class RecipeController {

    private final RecipeRepository repository;

    @GetMapping("/{id}")
    public Recipe retrieve(@PathVariable long id) {
        return findByIdOrThrowNotFound(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("@recipeAuthorIsPrincipal.test(#id, principal)")
    public void update(@PathVariable long id,
                       @Valid @RequestBody Recipe updated,
                       @AuthenticationPrincipal SecurityChef securityChef) {
        updated.setId(id);
        updated.setAuthor(securityChef.getChef());
        repository.save(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @PreAuthorize("@recipeAuthorIsPrincipal.test(#id, principal)")
    public void delete(@PathVariable long id) {
        repository.delete(findByIdOrThrowNotFound(id));
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
}
