# UECS2453---Food-Delivery-Job-Sequencing-Problem
2026 March @ UTAR 2453 Problem Solving with Data Structures and Algorithms (Assignment Task: Job Sequencing Problem)

## Primary Task 
To find a sequence of jobs, which will be completed within their deadline constraints and provides maximum profit

## Scenario Overview
A food delivery company (similar to GrabFood or FoodPanda) receives multiple delivery orders every day. Each order must be delivered within a specfic deadline from the moment it is placed by the customer. The company operates with a single delivery resource (One Delivery Driver); thus, only one order can be fulfilled per time unit (1 time unit = 1 hour). Each order carries a profit value. The objective is to filter out and sequence orders so that total profit is maximized while every accepted order is specifically delivered on time.

## Formal Definition
Given a set of n delivery orders, each with a deadline,d (hours) and profit, p (RM), find a subset, S and a scheduling sequence such that:
- Every order i in S is completed within its specified deadline
- Total profit for i in S is maximized
- At most one order occupies each time slot
  {Orders not selected are rejected. Partial completion are not allowed}

## Assumptions and Constraints
1. Each delivery takes exactly 1 time unit (1 hour) to complete, regardless of the order.
2. Deadlines are measured in integer hours from the order placement time.
3. A delivery is either fully completed or rejected, there is no partial completion.
4. All deadlines and profits are positive integers greater than zero.
5. Single resource constraint: Only one order per time slot for one driver.
6. An order placed cannot be paused or swapped.
7. All orders are expected to be independent.


## Data Models 
| Parameter | Description |
|-----------|-------------|
| Job ID    | Unique Identifier for each job |
| Deadlines | Time stated for delivery (Hours) |
| Profit    | Total profit earned from each specific job (delivery) |


## Features Included
| Feature | Description |
|---------|-------------|
| File Input | Loads delivery orders from a file path. FileHandler parses, validates, and constructs Jobs objects |
| Random Generation | Generate n random jobs with configurable maxDeadline and maxProfit. |
| 4 Algorithms | Each independently callable from Main |
| Selected Jobs Display | Tabular output of scheduled orders with structured fields |
| Unselected Jobs Display | Show rejected orders with necessary reasoning |
| Error Handling | IOExeception, NumberFormatException, input validation, and empty dataset guard |


## Algorithm Implemented
- Greedy Method (Profit-based)
- Dynamic Programming
- Genetic Algorithm
- Backtracking Algorithm


## System Architecture
| Layer | Classes | Functionality |
|-------|---------|---------------|
| Interface | JobScheduler | Declares the contract between schedule() and getAlgorithm(). Enables polymorphism|
| Abstract | AbstractScheduler | Holds the shared jobs list, provides resuable methods. Prevents code duplication across all algorithms |
| Algorithms | GreedyStrategy, GeneticAlgo, Backtracking, DynamicProgramming | Each concrete class implements one scheduling strategy. Returns Result<Jobs> wrapping selected jobs, rejected jobs, and totalProfit|
| Model | Jobs, Result<T> | Jobs holds all order data. Result is a generic container for all algorithms|
| Utility | FileHandler | Static methods: loadFromFile(), generateRandomData() |
| Entry Point | MainApp | For user input (data input + algorithm selection), and output display |


## Project Structure 
```bash
Food_Delivery/
├── src/
│   ├── algoStrategies/
│   │   ├── JobSeqStrategy.java      
│   │   ├── Result.java               
│   │   ├── GreedyStrategy.java      
│   │   ├── GeneticAlgo.java          
│   │   ├── Backtracking.java        
│   │   └── DynamicProgramming.java   
│   ├── jobModel/
│   │   └── Jobs.java                
│   ├── service/
│   │   ├── JobScheduler.java        
│   │   └── AbstractScheduler.java    
│   ├── util/
│   │   └── FileHandler.java          
│   └── main/
│       └── MainApp.java    
├── jobs_data.txt                    
└── README.md                        
```

## Tech Stack
|Tool|Description|
|---|---|
|Eclipse|IDE For Java Implementation|
|Java (JDK22)|Programming language used for the project|

## Contributors
- [@IcarusFlew-SE]
- [@lilyvx]|[Natalie]
- [@bryanmartyn-software-engineer] [Bryan]
- [@kzz300]|[KZ]

