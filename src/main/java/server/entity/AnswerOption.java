package server.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class AnswerOption implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private CopyOnWriteArrayList<String> options;
    private Map<Integer, Integer> answers = new ConcurrentHashMap<>();
    private Map<Integer, List<String>> votedUsers = new ConcurrentHashMap<>();

    public AnswerOption(CopyOnWriteArrayList<String> options) {
        this.options = options;

        for (int i = 0; i < options.size(); i++) {
            answers.putIfAbsent(i, 0);
            votedUsers.putIfAbsent(i, Collections.synchronizedList(new ArrayList<>()));
        }
    }

    public boolean isUserVote(String username) {
        for (int i = 0; i < options.size(); i++) {
            if (votedUsers.get(i).contains(username))
                return true;
        }
        return false;
    }
    public boolean vote(String username, int choice) {
        if (choice >= options.size())
            return false;

        answers.compute(choice, (k, v) -> (v == null) ? 1 : v + 1);
        return votedUsers.get(choice).add(username);
    }

    public List<String> getOptions() {
        return options;
    }

    public Map<Integer, Integer> getAnswers() {
        return answers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AnswerOption that = (AnswerOption) o;
        return Objects.equals(options, that.options) && Objects.equals(answers, that.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(options, answers);
    }
}
