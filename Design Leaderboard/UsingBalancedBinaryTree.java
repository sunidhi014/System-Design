import java.util.*;

class Leaderboard {
    private TreeMap<Integer, Integer> scores;

    public Leaderboard() {
        // Use a TreeMap to maintain scores in ascending order
        scores = new TreeMap<>(Comparator.reverseOrder());
    }

    // Add or update a user's score
    public void addScore(int userId, int score) {
        scores.put(score, userId);
    }

    // Get the top k scores
    public List<Integer> getTopK(int k) {
        List<Integer> topK = new ArrayList<>();
        Iterator<Map.Entry<Integer, Integer>> iterator = scores.entrySet().iterator();

        for (int i = 0; i < k && iterator.hasNext(); i++) {
            topK.add(iterator.next().getValue());
        }

        return topK;
    }

    // Get the highest score
    public Map.Entry<Integer, Integer> getHighestScore() {
        return scores.firstEntry();
    }
}

public class Main {
    public static void main(String[] args) {
        Leaderboard leaderboard = new Leaderboard();

        // Add scores for users
        leaderboard.addScore(1, 100);
        leaderboard.addScore(2, 150);
        leaderboard.addScore(3, 120);
        leaderboard.addScore(4, 200);

        // Get the top 3 scores
        List<Integer> top3 = leaderboard.getTopK(3);
        System.out.println("Top 3 Scores: " + top3);

        // Get the highest score
        Map.Entry<Integer, Integer> highestScore = leaderboard.getHighestScore();
        System.out.println("Highest Score: " + highestScore.getKey() + " for User ID: " + highestScore.getValue());
    }
}
