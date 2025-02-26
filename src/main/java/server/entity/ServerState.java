package server.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerState implements Serializable {
    private static volatile ServerState instance = null;
    @Serial
    private static final long serialVersionUID = 1L;

    private final List<Topic> topics = new ArrayList<>();
    private final Map<String, String> activeUsers = new ConcurrentHashMap<>();
    private final List<String> users = new CopyOnWriteArrayList<>();

    public static ServerState getInstance() {
        if (instance == null) {
            synchronized (ServerState.class) {
                if (instance == null)
                    return instance = new ServerState();
            }
        }
        return instance;
    }

    public boolean login(String username, String id) {
        if (activeUsers.containsValue(username))
            return false;
        else if (!users.contains(username))
            users.add(username);
        activeUsers.put(id, username);
        return true;
    }

    public boolean createTopic(String nameTopic, String id) {
        if (activeUsers.containsKey(id))
            return false;
        if (topics.stream().anyMatch(topic -> topic.getName().equalsIgnoreCase(nameTopic)))
            return false;
        return topics.add(new Topic(nameTopic, id));
    }

    public void createVote(String nameTopic, String id, Vote vote) {
        var topic = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(nameTopic))
                .findFirst().orElseThrow();
        vote.setUsername(activeUsers.get(id));
        if (topic.getVoteStream().anyMatch(vote1 -> vote1.getName().equalsIgnoreCase(vote.getName())))
            throw new RuntimeException();
        topic.addVote(vote);
    }

    public String view() {
        StringBuilder stringBuilder = new StringBuilder();
        topics.forEach(topic -> stringBuilder
                .append(String.format("%s (votes in topic %d)", topic.getName(), topic.getVoteStream().count()))
                .append("/n"));
        return stringBuilder.toString();
    }

    public String view(String topic) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("List of votes in ").append(topic).append("/n");
        topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(topic))
                .findFirst()
                .orElseThrow()
                .getVoteStream()
                .forEach(lamb -> stringBuilder.append(lamb.getName()).append("/n"));
        return stringBuilder.toString();
    }

    public String view(String topic, String vote) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Information about vote: ").append(vote).append("/n");
        var currVote = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(topic))
                .findFirst()
                .orElseThrow()
                .getVoteStream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(vote))
                .findFirst()
                .orElseThrow();
        stringBuilder.append(currVote.getDescription()).append("/n");
        var answerOptions = currVote.getAnswerOptions();
        for (int i = 0; i < answerOptions.getOptions().size(); i++) {
            stringBuilder.append(String.format("%d. %s - %d", i + 1, answerOptions.getOptions().get(i),
                    answerOptions.getAnswers().get(i))).append("/n");
        }
        return stringBuilder.toString();
    }

    public boolean vote(String id, String topic, String vote, String choice) {
        var answerOptions = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(topic))
                .findFirst()
                .orElseThrow()
                .getVoteStream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(vote))
                .findFirst()
                .orElseThrow().getAnswerOptions();
        if (answerOptions.isUserVote(activeUsers.get(id)))
            return false;
        return answerOptions.vote(activeUsers.get(id), Integer.parseInt(choice));
    }

    public boolean delete(String id, String topic, String vote) {
        var tmpTopic = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(topic))
                .findFirst()
                .orElseThrow();
        if (tmpTopic.getVoteStream().anyMatch(lamb -> !(lamb.getName().equalsIgnoreCase(vote)
                        && lamb.getUsername().equalsIgnoreCase(activeUsers.get(id)))))
            return tmpTopic.removeVote(vote);
        return false;
    }

    public void restoreState(ServerState loadedState) {
        this.topics.clear();
        this.topics.addAll(loadedState.topics);
        this.activeUsers.clear();
        this.activeUsers.putAll(loadedState.activeUsers);
        this.users.clear();
        this.users.addAll(loadedState.users);
    }
}
