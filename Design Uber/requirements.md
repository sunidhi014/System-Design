## Functional requirements
There will be two types of users: Customer and Driver.

### Customer
Customers should be able to see all the cabs in the vicinity with an ETA and pricing information.</br>
Customers should be able to book a cab to a destination.</br>
Customers should be able to see the location of the driver.</br>

### Drivers
Drivers should be able to accept or deny the customer requested ride.</br>
Once a driver accepts the ride, they should see the pickup location of the customer.</br>
Drivers should be able to mark the trip as complete on reaching the destination.</br>

## Non-Functional requirements
High reliability.</br>
High availability with minimal latency.</br>
The system should be scalable and efficient.</br>

## Extended requirements
Customers can rate the trip after it's completed.</br>
Payment processing.</br>
Metrics and analytics.</br>

## Architecture

#### 1. Customer Service
This service handles customer-related concerns such as authentication and customer information.

#### 2. Driver Service
This service handles driver-related concerns such as authentication and driver information.

#### 3. Ride Service
This service will be responsible for ride matching and quadtree aggregation. It will be discussed in detail separately.

#### 4. Trip Service
This service handles trip-related functionality in our system.

#### 5. Payment Service
This service will be responsible for handling payments in our system.

#### 6. Notification Service
This service will simply send push notifications to the users. It will be discussed in detail separately.

#### 7. Analytics Service
This service will be used for metrics and analytics use cases.

### Data Model Design
![image](https://github.com/sunidhi014/System-Design/assets/96500508/dad93b46-fa27-489c-a463-307f4bed4b4b)

### WorkFlow
![image](https://github.com/sunidhi014/System-Design/assets/96500508/e8f77dc7-f46c-4988-8673-07fecdd108a6)

### Location Service: Quadtree vs. Geohash
A critical component of Uber’s system is its location service, which manages spatial data. Two popular methods for this are quadtrees and geohashes, each with its own advantages and trade-offs.

#### Quadtree: 
A quadtree is a tree data structure, which is particularly effective in managing two-dimensional spatial data. It works by recursively subdividing the space into four quadrants or nodes. 
In the context of Uber, this allows the system to quickly search and retrieve the locations of drivers and riders within any given area. The primary advantage of a quadtree is its 
efficiency in spatial queries, as it can quickly narrow down the search area. However, a significant trade-off is the complexity of implementation, especially 
in a dynamic environment where driver and rider locations constantly change.

#### Geohash: 
Geohash is an alternative system that converts a two-dimensional geographical location into a short string of letters and numbers. This method is particularly useful for its simplicity 
and ease of implementation. It works well for applications like Uber as it provides a quick way to encode and compare locations, facilitating the matching of riders and drivers. 
However, one of the trade-offs of using geohash is its less efficient spatial querying compared to quadtrees. Geohashes can also lead to edge-case issues at the boundaries of geohash areas,
where two nearby points might end up having significantly different geohash codes.
