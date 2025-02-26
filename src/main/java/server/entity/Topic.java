package server.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Topic {
    private String name;
    private String username;
    private List<Vote> votes = new ArrayList<>();

    public Topic(String name, String username) {
        this.name = name;
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public boolean removeVote(String vote) {
        votes = votes.stream()
                .filter(lamb -> !lamb.getName().equalsIgnoreCase(vote))
                .toList();
        return true;
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }

    public Stream<Vote> getVoteStream() {
        return votes.stream();
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Topic topic = (Topic) o;
        return Objects.equals(name, topic.name) && Objects.equals(username, topic.username)
                && Objects.equals(votes, topic.votes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, username, votes);
    }
}
