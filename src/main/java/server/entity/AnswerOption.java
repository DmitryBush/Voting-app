package server.entity;

import java.util.*;

public class AnswerOption {
    private List<String> options;
    private Map<Integer, Integer> answers = new HashMap<>();
    private Map<Integer, List<String>> votedUsers = new HashMap<>();

    public AnswerOption(List<String> options) {
        this.options = options;

        for (int i = 0; i < options.size(); i++) {
            answers.put(i, 0);
            votedUsers.put(i, new ArrayList<>());
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
        answers.put(choice, answers.get(choice) + 1);
        return votedUsers.get(choice).add(username);
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
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
