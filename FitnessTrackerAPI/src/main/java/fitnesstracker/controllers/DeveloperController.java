package fitnesstracker.controllers;

import fitnesstracker.models.*;
import fitnesstracker.repositories.DeveloperRepository;
import fitnesstracker.security.SecurityDeveloper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/developers")
@AllArgsConstructor
public class DeveloperController {

    private final DeveloperRepository repository;
    private final DeveloperMapper mapper;

    @GetMapping("/{id}")
    @PreAuthorize("#securityDeveloper.developer.id == #id")
    @Transactional
    public Developer getProfile(
            @PathVariable long id, @AuthenticationPrincipal SecurityDeveloper securityDeveloper) {
        return repository.findById(id).orElseThrow();
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@Valid @RequestBody DeveloperRegistration request) {
        try {
            Developer developer = repository.save(mapper.convert(request));
            URI location = URI.create("/api/developers/" + developer.getId());
            return ResponseEntity.created(location).build();
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
