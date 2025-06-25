package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record Quiz(int id, String title, String text, String[] options, @JsonIgnore int answer) {}
