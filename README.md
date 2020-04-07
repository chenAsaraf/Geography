# Geography

General Orientation:

## GPS App:

Application that runs on GoogleEarth, Calculates azimuth and distance for any two coordinates, Converts csv files into KML file by turning them to a GIS collection (elements, layers and finally project), The KML file contains TimeStamp and Point Coordinates.

## Pacman Based GIS Game:

Multi-threaded app, based on the GIS infrastructure mentioned above.
How It Works?
1. Load gameboard from the csv files from gameboardData folder, or choose for yourself where to place the players and fruits (which players should eat).

![csv file for example](csv gameboard-data example.JPG)


calculates the shortest path to each fruit for each Packman.
built as a game, can push an existing csv file of Packman or draw Packmans and fruits by ourselves and then click RUN which will run the game with the most effective path (using the algorithm described earlier).
the game outputs a KML file which runs on GoogleEarth.


Introduction and Summary:
The game can be loaded using a CSV file, and you can also save the game to such a file.
The goal of the game: Eat all fruits in the quickest way.

Packmans - robots with mobility, spatial orientations, speed characteristics and eating radius.

Fruits -  static objects in the field with different weights.

ShortestParhAlgo - The main algorithm class uses a greedy algorithm, and goes through the entire list of Packmans (sorted in ascending order according to the rate of their progress)
and search for the fruit that is closest to each packman.
The solution is represented by a list of Pathes.

Path – a list of Fruit which belongs to every Pacman in the game. (Can be empty - according to the algorithm).

Map Class - initializes a generic map file from google by String 'path' of the image and a maximum left and minimum right coordinate point of the map that defines the map. 
It has static functions that define the conversion of global coordinates and pixels on and off the image .

GUI Package- Initializes the game on a map board, allows placing individual Packmans or fruits by pressing a button and loading from an existing file. The center of the game is running animation  Packmans according to the algorithm in real time
