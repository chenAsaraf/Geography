# Geography

General Orientation:

## GPS App:

Application that runs on GoogleEarth, Calculates azimuth and distance for any two coordinates, Converts csv files into KML file by turning them to a GIS collection (elements, layers and finally project), The KML file contains TimeStamp and Point Coordinates.

## Pacman Based GIS Game:
###### Multi-threaded app, based on the GIS infrastructure mentioned above.
The goal of the game: Eat all the fruits in the shortest way (competition between the students' algorithms in OOP course, Ariel University)
How It Works?
1. Load gameboard from the csv files from gameboardData folder, or choose for yourself where to place the players and fruits (which players should eat).

For example, a part of a CSV file that contains lines with coordinates for pecans (P) and rows with position coordinates for fruits (F):

![csv file for example](https://github.com/chenAsaraf/Geography/blob/master/csv%20gameboard-data%20example.JPG)


2. Next, by clicking "Play-> Run" the software algorithm will calculate for each Pacman its fruit's track so that all fruits will be eaten in the shortest time.
   This is a greedy algorithm, That is, checking at any given time which pacman is closest to the leftover fruits.
   When clicking "Run" The algorithm runs in a separate thread from the GUI, in order for the calculation to continue to be performed without delaying the game view.
   Because the algorithm and the display both use the list of pacmans and fruits, it is necessary to synchronize the trades with access to them (see the method "run()" in ShortestPathAlgo class, and "step()" in GameFrame class).
   
![gameboard for example](https://github.com/chenAsaraf/Geography/blob/master/image%20board%20example.JPG)
   
   
3. The game outputs a KML file which runs on GoogleEarth.


**Summary of the main classes:**

*Packmans* - robots with mobility, spatial orientations, speed characteristics and eating radius.

*Fruits* -  static objects in the field with different weights.

*ShortestParhAlgo* - The main algorithm class uses a greedy algorithm, and goes through the entire list of Packmans (sorted in ascending order according to the rate of their progress)
and search for the fruit that is closest to each packman.
The solution is represented by a list of Pathes.

*Path* – a list of Fruit which belongs to every Pacman in the game. (Can be empty - according to the algorithm).

*Map* - initializes a generic map file from google by String 'path' of the image and a maximum left and minimum right coordinate point of the map that defines the map. 
It has static functions that define the conversion of global coordinates and pixels on and off the image .

*GUI Package*- Initializes the game on a map board, allows placing individual Packmans or fruits by pressing a button and loading from an existing file. The center of the game is running animation  Packmans according to the algorithm in real time
