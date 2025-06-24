package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Quiz(String title, String text, String[] options, @JsonIgnore int answer) {}
