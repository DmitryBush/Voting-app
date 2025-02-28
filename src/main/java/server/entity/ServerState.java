package server.entity;

import handlers.exceptions.IncorrectCommand;
import server.entity.exceptions.NotLoggedIn;
import server.entity.exceptions.NotUniqueName;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
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

    private void isUserLoggedIn(String id) {
        if (!activeUsers.containsKey(id))
            throw new NotLoggedIn("You're not logged in");
    }

    public boolean login(String username, String id) {
        if (username == null)
            throw new IncorrectCommand("Entered empty username");
        if (activeUsers.containsKey(id))
            return false;
        else if (!users.contains(username))
            users.add(username);
        activeUsers.put(id, username);
        return true;
    }

    public boolean createTopic(String nameTopic, String id) {
        isUserLoggedIn(id);
        if (nameTopic == null)
            throw new IncorrectCommand("Entered empty nameTopic");
        if (topics.stream().anyMatch(topic -> topic.getName().equalsIgnoreCase(nameTopic)))
            throw new NotUniqueName("You're entered not unique name for topic");
        return topics.add(new Topic(nameTopic, id));
    }

    public String createVote(String nameTopic, String id, Vote vote) {
        isUserLoggedIn(id);
        if (nameTopic == null)
            throw new IncorrectCommand("Entered empty nameTopic");
        var topic = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(nameTopic))
                .findFirst().orElseThrow(NoSuchElementException::new);
        vote.setUsername(activeUsers.get(id));
        if (topic.getVoteStream()
                .anyMatch(vote1 -> vote1.getName().equalsIgnoreCase(vote.getName())))
            throw new NotUniqueName("You're entered not unique name for vote");
        topic.addVote(vote);
        return "Vote created";
    }

    public String view(String id) {
        isUserLoggedIn(id);
        StringBuilder stringBuilder = new StringBuilder();
        if (topics.isEmpty())
            stringBuilder.append("There are no topics");
        else
            topics.forEach(topic -> stringBuilder
                    .append(String.format("%s (votes in topic - %d)", topic.getName(), topic.getVoteStream().count()))
                    .append("/n"));
        return stringBuilder.toString();
    }

    public String view(String id, String nameTopic) {
        isUserLoggedIn(id);
        StringBuilder stringBuilder = new StringBuilder();
        if (nameTopic == null)
            throw new IncorrectCommand("Entered empty nameTopic");

        var topic = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(nameTopic))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        if (topic.getVoteStream().findAny().isEmpty())
            return stringBuilder.append(String.format("There are no votes in the %s", nameTopic)).toString();
        else {
            stringBuilder.append("List of votes in ").append(nameTopic).append("/n");
            topic.getVoteStream().forEach(lamb -> stringBuilder.append(lamb.getName()).append("/n"));
        }
        return stringBuilder.toString();
    }

    public String view(String id, String nameTopic, String nameVote) {
        isUserLoggedIn(id);
        StringBuilder stringBuilder = new StringBuilder();
        if (nameTopic == null || nameVote == null)
            throw new IncorrectCommand("Entered empty parameters");

        stringBuilder.append("Information about vote: ").append(nameVote).append("/n");
        var vote = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(nameTopic))
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getVoteStream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(nameVote))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        stringBuilder.append(vote.getDescription()).append("/n");
        var answerOptions = vote.getAnswerOptions();
        for (int i = 0; i < answerOptions.getOptions().size(); i++) {
            stringBuilder.append(String.format("%d. %s - %d", i + 1, answerOptions.getOptions().get(i),
                    answerOptions.getAnswers().get(i))).append("/n");
        }
        return stringBuilder.toString();
    }

    public boolean vote(String id, String topic, String vote, String choice) {
        isUserLoggedIn(id);
        if (topic == null || vote == null || choice == null)
            throw new IncorrectCommand("Occurred error near parameter");
        var answerOptions = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(topic))
                .findFirst()
                .orElseThrow(NoSuchElementException::new)
                .getVoteStream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(vote))
                .findFirst()
                .orElseThrow(NoSuchElementException::new).getAnswerOptions();
        if (answerOptions.isUserVote(activeUsers.get(id)))
            return false;
        return answerOptions.vote(activeUsers.get(id), Integer.parseInt(choice));
    }

    public boolean delete(String id, String topic, String vote) {
        isUserLoggedIn(id);
        var tmpTopic = topics.stream()
                .filter(lamb -> lamb.getName().equalsIgnoreCase(topic))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        if (tmpTopic.getVoteStream()
                .anyMatch(lamb -> lamb.getName().equalsIgnoreCase(vote)
                        && lamb.getUsername().equalsIgnoreCase(activeUsers.get(id))))
            return tmpTopic.removeVote(vote);
        return false;
    }

    public void restoreState(ServerState loadedState) {
        this.topics.clear();
        this.topics.addAll(loadedState.topics);
//        this.activeUsers.clear();
//        this.activeUsers.putAll(loadedState.activeUsers);
        this.users.clear();
        this.users.addAll(loadedState.users);
    }
}
