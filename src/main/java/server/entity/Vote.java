package server.entity;

import java.util.Objects;

public class Vote {
    private String name;
    private String username;
    private String description;
    private AnswerOption answerOptions;

    public Vote(String name, String username, String description, AnswerOption answerOptions) {
        this.name = name;
        this.username = username;
        this.description = description;
        this.answerOptions = answerOptions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AnswerOption getAnswerOptions() {
        return answerOptions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(name, vote.name)
                && Objects.equals(username, vote.username) && Objects.equals(description, vote.description)
                && Objects.equals(answerOptions, vote.answerOptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, description, answerOptions);
    }
}
