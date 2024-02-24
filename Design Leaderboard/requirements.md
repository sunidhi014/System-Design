### Functional Requirements
The client (player) can view the top 10 players on the leaderboard in real-time (absolute leaderboard)<br>
The client can view a specific player’s rank and score<br>
The client can view the surrounding ranked players to a particular player (relative leaderboard)<br>

### Non-Functional Requirements
High availability<br>
Low latency<br>
Scalability<br>
Reliability<br>
Minimal operational overhead<br>

### Leaderboard data storage
![image](https://github.com/sunidhi014/System-Design/assets/96500508/e9623438-249e-4aaf-8f41-11dfb174bf24)
![image](https://github.com/sunidhi014/System-Design/assets/96500508/bfd0856f-b34d-4730-a38f-057a9939210c)

### Leaderboard high-level design
![image](https://github.com/sunidhi014/System-Design/assets/96500508/3efe631e-ffb1-4dd8-9049-cff2e73ae148)
![image](https://github.com/sunidhi014/System-Design/assets/96500508/7a830747-b5dd-4753-a28f-e25613dbe38a)

#### The following operations are performed when a player updates the score:
The client creates a WebSocket connection to the load balancer for real-time communication<br>
The load balancer delegates the client’s request to the closest data center<br>
The server updates the score on the sorted set data type in Redis<br>
The serverless function updates the records on the relational database using the write-behind cache pattern<br>

#### The following operations are performed when a player wants to view the leaderboard:
The client creates a WebSocket connection to the load balancer for real-time communication<br>
The load balancer delegates the client’s request to the closest data center<br>
The serverless function invokes the sorted set data type in Redis<br>
The serverless function queries the relational database on a cache miss using the read-through cache pattern
