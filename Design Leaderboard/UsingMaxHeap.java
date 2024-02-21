import java.util.*;

class Leaderboard {
    private PriorityQueue<UserScore> maxHeap;

    public Leaderboard() {
        // Use a max heap to maintain UserScore objects in descending order based on scores
        maxHeap = new PriorityQueue<>(Comparator.comparingInt(UserScore::getScore).reversed());
    }

    // UserScore class to represent a user along with their score
    public static class UserScore {
        private int userId;
        private int score;

        public UserScore(int userId, int score) {
            this.userId = userId;
            this.score = score;
        }

        public int getUserId() {
            return userId;
        }

        public int getScore() {
            return score;
        }
    }

    // Add or update a user's score
    public void addScore(int userId, int score) {
        // If the user already exists, remove their current score
        maxHeap.removeIf(userScore -> userScore.getUserId() == userId);

        // Add the new score
        UserScore newUserScore = new UserScore(userId, score);
        maxHeap.offer(newUserScore);
    }

    // Get the top k scores
    public List<UserScore> getTopK(int k) {
        List<UserScore> topK = new ArrayList<>();
        for (int i = 0; i < k && !maxHeap.isEmpty(); i++) {
            topK.add(maxHeap.poll());
        }
        // Restore the heap
        maxHeap.addAll(topK);
        return topK;
    }

    // Get the highest score
    public UserScore getHighestScore() {
        return maxHeap.peek();
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
        List<Leaderboard.UserScore> top3 = leaderboard.getTopK(3);
        System.out.println("Top 3 Scores: " + top3);

        // Get the highest score
        Leaderboard.UserScore highestScore = leaderboard.getHighestScore();
        System.out.println("Highest Score: " + highestScore.getScore() + " for User ID: " + highestScore.getUserId());
    }
}
