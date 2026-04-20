package com.lms.domain.lesson;

import java.util.Objects;

public class Lesson {
    private final String id;
    private final String title;
    private final String content;

    public Lesson(String id, String title, String content) {
        this.id = Objects.requireNonNull(id, "id cannot be null");
        this.title = Objects.requireNonNull(title, "title cannot be null");
        this.content = Objects.requireNonNull(content, "content cannot be null");
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
