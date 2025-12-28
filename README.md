# I2CS_Ex2

I2CS_Ex2 is an assignment given by the Ariel University.
It mainly focused on BFS algorithm and StdDraw GUI.

## Description
This Project will get you started on using OOP and StdDraw. It creates new GUI window, 
represented in the code as Map - a 2D matrix of Pixels. And Solves various problems using
the BFS algorithm, It can fill Areas, find all path from a certain point
and finds the shortest Path from p1 to p2.

## Getting started
## Interfaces
- Map2D
- Pixel2D

## Map.java
### Description
This file is responsible for creating new Map object
### Main functions 

- _fill(Pixel2D xy, int new_v, boolean cyclic)_ - Fill all connected Pixels with xy with same color as xy, Returns Number of filled Pixels.
- _shortestPath(Pixel2D p1,Pixel2D p1, int obsColor, boolean cyclic)_ - Computes the Shortest Path from p1 to p2 using BFS returns Pixels2D array of the path.
- _allDistances(Pixel2D start, int obsColor, boolean cyclic)_ - Computes all possible Paths from start Pixel, returns new Map containing all paths.

## Index2D.java
### Description
This file is responsible for creating new Pixel.
Each pixel holds an x,y position.

## Ex2_GUI
### Description
This file is responsible for creating demo running of the project, it will be able to Create a simple Maze, Display
the shortest path between two points, and find all paths from single point.
#### Additional functions:
- _saveMap(Map2D map, String mapFileName)_ - Saves map as a txt file
- _loadMap(String mapFileName)_ - loads txt file and creates a new Map2D object
## Usage

```java
//Create new Pixel

import assignments.Ex2.Index2D;
import assignments.Ex2.Map;
import assignments.Ex2.Map2D;
import assignments.Ex2.Pixel2D;

Index2D p = new Index2D(0, 0);

//Create new map
Map map = new Map(10,10,0);// creates new matrix 10X10 each cell holds 0. 0 represents a certain color
//Create Shapes
map.drawCircle(p, rad, newColor);

int i = map.fill(p,newColor, true);// cyclic = true - Makes the board Circler so the end of the matrix is connected to the start.
Pixel2D[] pArr = map.shortestPath(p, new Index2D(8,9), obsColor ,true)
Map newM = map.allDistance(new Index2D(2,3), obsColor, true)

```
## Result
By running Ex2_GUI, you'll be able to perform the following actions:
- Create maze using 'M' key as shown in figure 1. 
- By using 'S' key it will randomize 2 points on the map, and it will display it as shown in Figure 2.
- By using the 'A' key it will display all paths from a randomized point as shown in Figure 3.

![alt text](https://github.com/Achiaga10/I2CS_Ex2/blob/main/src/images/genMaze.png)
Figure 1

![alt text](https://github.com/Achiaga10/I2CS_Ex2/blob/main/src/images/shortMaze.png)
Figure 2

![alt text](https://github.com/Achiaga10/I2CS_Ex2/blob/main/src/images/alldisfalse.png)
Figure 3


## Author
Achia Gabbay
