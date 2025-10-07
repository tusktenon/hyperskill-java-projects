package recipes;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipe")
public class Controller {

    private Recipe recipe;

    @GetMapping
    public Recipe retrieve() {
        return recipe;
    }

    @PostMapping
    public void add(@RequestBody Recipe recipe) {
        this.recipe = recipe;
    }
}
