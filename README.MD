# Board game GUI

Play board games like regular TicTacToe and Numerical TicTacToe in a Graphical User Interface

## Description

This software is built with Java's swing library. User can play the game TicTacToe and Numerical TicTacToe

#### TicTacToe
* One player will be responsible for placing X on the 3x3 board, the other player will be responsible for placing O on the board
* The player with X will be going first
* Players can't overwrite other player's moves
* First player to place 3 of their respecting symbols either horizontally, vertically, or diagonally wins
* This game can also be played with command line arguements

#### Numerical TicTacToe
* One Player will be responsible for placing odd numbers from 1 to 9 on the grid while the other will place even numbers from 2-8
* Each numberss can only be inputed once
* The player with the odd numbers will go first
* Whoever place the third number in a horizontal, vertical, or diagonal line that adds exactly to 15 wins



## Getting Started

### Dependencies

* Java 1.11
* Gradle 7.5.1
* Kotlin 1.6.21
* Groovy 3.0.10
* Ant 1.10.11
* JVM 11.0.16

### Installing

You can download the project from the git repository

### Executing program

* Run the following command to build program: 
```
gradle build
```
* Run the following command to run the program:
```
java -jar build/libs/A3.jar
```

## Load save files

#### Player file format: 
* <playerName>
* <gameWon> (only enter integers)
* <gamePlayed> (only enter integers)
* <gameLost> (only enter integers)

#### TicTacToe file format:
* <X or O> determine which player will go next
* replace (a) with either "X", "O" or nothing. X and O must be capitalized
* <(a),(a),(a)>
* <(a),(a),(a)>
* <(a),(a),(a)> 


## Authors

* Author Name: GuangLi (Jay) Lin
* Author Git username: theonej
* Author Email: guangli@uoguelph.ca


## Acknowledgments

Inspiration, code snippets, etc.
* [awesome-readme](https://github.com/matiassingers/awesome-readme)
* [PurpleBooth](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2)
* [dbader](https://github.com/dbader/readme-template)
* [zenorocha](https://gist.github.com/zenorocha/4526327)
* [fvcproductions](https://gist.github.com/fvcproductions/1bfc2d4aecb01a834b46)