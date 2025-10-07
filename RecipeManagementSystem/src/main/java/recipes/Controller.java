package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/recipe")
public class Controller {

    private final List<Recipe> recipes = new ArrayList<>();

    @GetMapping("/{id}")
    public Recipe retrieve(@PathVariable int id) {
        try {
            return recipes.get(id - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new")
    public Map<String, Integer> add(@RequestBody Recipe recipe) {
        recipes.add(recipe);
        return Map.of("id", recipes.size());
    }
}
