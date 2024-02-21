import java.util.ArrayList;
import java.util.List;

// Represents a 2D point with latitude and longitude
class Point {
    double latitude;
    double longitude;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

// Represents a Quad Tree node
class QuadTreeNode {
    double x, y;  // Coordinates of the node
    QuadTreeNode[] children;  // NW, NE, SW, SE quadrants
    List<User> users;  // Users in this node

    public QuadTreeNode(double x, double y) {
        this.x = x;
        this.y = y;
        this.children = new QuadTreeNode[4];
        this.users = new ArrayList<>();
    }
}

// Represents a user with a location
class User {
    String userId;
    Point location;

    public User(String userId, Point location) {
        this.userId = userId;
        this.location = location;
    }
}

// Represents a Quad Tree for spatial organization
class QuadTree {
    QuadTreeNode root;
    int capacity;  // Maximum number of users a node can hold

    public QuadTree(double x, double y, int capacity) {
        this.root = new QuadTreeNode(x, y);
        this.capacity = capacity;
    }

    // Insert a user into the Quad Tree
    public void insert(User user) {
        insertHelper(root, user);
    }

    private void insertHelper(QuadTreeNode node, User user) {
        if (node.users.size() < capacity) {
            // If the node has space, add the user to it
            node.users.add(user);
        } else {
            // If the node is full, subdivide and insert into the appropriate quadrant
            if (node.children[0] == null) {
                subdivide(node);
            }

            for (int i = 0; i < 4; i++) {
                if (isPointInQuadrant(user.location, node.children[i])) {
                    insertHelper(node.children[i], user);
                    break;
                }
            }
        }
    }

    // Subdivide a node into four quadrants
    private void subdivide(QuadTreeNode node) {
        double x = node.x;
        double y = node.y;
        double newWidth = x / 2;
        double newHeight = y / 2;

        node.children[0] = new QuadTreeNode(x + newWidth, y - newHeight);  // NW
        node.children[1] = new QuadTreeNode(x + newWidth, y + newHeight);  // NE
        node.children[2] = new QuadTreeNode(x - newWidth, y - newHeight);  // SW
        node.children[3] = new QuadTreeNode(x - newWidth, y + newHeight);  // SE
    }

    // Check if a point is in a quadrant
    private boolean isPointInQuadrant(Point point, QuadTreeNode node) {
        return point.latitude <= node.x && point.longitude <= node.y;
    }

    // Query nearby users within a specified range
    public List<User> query(Point center, double range) {
        List<User> result = new ArrayList<>();
        queryHelper(root, center, range, result);
        return result;
    }

    private void queryHelper(QuadTreeNode node, Point center, double range, List<User> result) {
        if (node == null) {
            return;
        }

        // Check if the node's quadrant is within the range
        double distance = calculateDistance(center, new Point(node.x, node.y));
        if (distance <= range) {
            // Add users in this node to the result
            result.addAll(node.users);
        } else {
            // Recursively query the quadrants
            for (QuadTreeNode child : node.children) {
                queryHelper(child, center, range, result);
            }
        }
    }

    // Calculate distance between two points using Euclidean distance
    private double calculateDistance(Point p1, Point p2) {
        double xDiff = p1.latitude - p2.latitude;
        double yDiff = p1.longitude - p2.longitude;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }
}

public class Main {
    public static void main(String[] args) {
        // Initialize Quad Tree with a center point and capacity
        QuadTree quadTree = new QuadTree(0, 0, 2);

        // Register users with their initial locations
        User driver1 = new User("Driver1", new Point(1, 1));
        User driver2 = new User("Driver2", new Point(2, 2));
        User rider1 = new User("Rider1", new Point(0.5, 0.5));

        // Insert users into the Quad Tree
        quadTree.insert(driver1);
        quadTree.insert(driver2);
        quadTree.insert(rider1);

        // Query nearby users for a rider within a specified range
        Point riderLocation = new Point(0.5, 0.5);
        double range = 1.0;
        List<User> nearbyUsers = quadTree.query(riderLocation, range);

        // Display nearby users
        System.out.println("Nearby Users for Rider1:");
        for (User nearbyUser : nearbyUsers) {
            System.out.println("User ID: " + nearbyUser.userId + ", Location: (" +
                    nearbyUser.location.latitude + ", " + nearbyUser.location.longitude + ")");
        }
    }
}
