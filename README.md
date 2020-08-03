# Morty's Run Game

## Aims

* Appreciate issues in user interface design
* Learn practical aspects of graphical user interface programming
* Learn more about the Java class libraries
* Learn the application of design patterns
* Make a wacky game in the process
---

## Overview
This project was done for a object oriented programming course in UNSW. Written by Leonard Lee and Zachary Tan.
Special thanks to Well son and Wei ze! 

## Setup

1. Download and set-up Visual Studio Code here :point_right:   [VSCode](https://code.visualstudio.com/)

2. set up java jdk  here :point_right:   [vscode for java](https://code.visualstudio.com/docs/java/java-tutorial)
    - download the installer for your Operating System (windows/mac)
    
3. Go to Extension tab in VSCode and in the search bar look for 'Java Extension Pack' 
    - this package includes the java code runner so we can launch the game.
    - details of the extension here  :point_right: [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

4. That should do it! :star: Go to src/unsw/dungeon/FrontendApplication.java :star: and click on 'Run' on top of the main function.


:heavy_exclamation_mark: **IMPORTANT** :heavy_exclamation_mark: The set-up is currently tested and working for macOS and Linux. 

:heavy_exclamation_mark: **IMPORTANT** :heavy_exclamation_mark: for windows, first download git [here](https://git-scm.com/), then checkout into the 'windows' branch and follow setup steps 1 - 4.



## The different levels


The client desires an application that lets the user move a player around a dungeon and try to overcome various challenges in order to "complete" the level by reaching some goal. 

Here is how the start menu looks like:

![Start][start]

Be sure to read the instructions for the game and go on to select the different level maps! 

![Rules][rules]

![Select][select]

Here's a glimpse as to how they look like!

![Maze][lv] 
![Maze2][lv1]


### Dungeon layout

To be specific, the layout of each dungeon is defined by a grid of squares, each of which may contain one or more entities. The different types of entities are as follows:

| Entity               | Example | Description                             |
| ------               | ------- | --------------------------------------- |
| Player               | ![Player][player] | Can be moved up, down, left, and right into adjacent squares, provided another entity doesn't stop them (e.g. a wall). |
| Wall                 | ![Wall][wall] | Blocks the movement of the player, enemies and boulders. |
| Exit                 | ![Exit][exit] | If the player goes through it the puzzle is complete.  |
| Treasure             | ![Treasure][treasure] | Can be collected by the player. |
| Door                 | ![Door][door_open] ![Door][door_closed] | Exists in conjunction with a single key that can open it. If the player holds the key, they can open the door by moving through it. Once open it remains so. The client will be satisfied if dungeons can be made with up to 3 doors. |
| Key                  | ![Key][key] | Can be picked up by the player when they move into the square containing it. The player can carry only one key at a time, and only one door has a lock that fits the key. It disappears once it is used to open its corresponding door. |
| Boulder              | ![Boulder][boulder] | Acts like a wall in most cases. The only difference being that it can be pushed by the player into adjacent squares. The player is only strong enough to push **one** boulder at a time. |
| Floor switch         | ![Floor switch][switch] | Switches behave like empty squares, so other entities can appear on top of them. When a boulder is pushed onto a floor switch, it is triggered. Pushing a boulder off the floor switch untriggers it. |
| Portal               | ![Portal][portal] | Teleports entities to a corresponding portal. |
| Enemy                | ![Enemy][enemy] | Constantly moves toward the player, stopping if it cannot move any closer. The player dies upon collision with an enemy. |
| Sword                | ![Sword][sword] | This can be picked up the player and used to kill enemies. Only one sword can be carried at once. Each sword is only capable of 5 hits and disappears after that. One hit of the sword is sufficient to destroy any enemy. |
| Invincibility potion | ![Invincibility][potion] | If the player picks this up they become invincible to enemies. Colliding with an enemy should result in their immediate destruction. Because of this, all enemies will run away from the player when they are invincible. The effect of the potion only lasts a limited time. |
| Invincible mode      | ![Inv][inv] | This is how morty looks like in invincible mode. (After picking up potion) 
| Armed mode           | ![Armed][armed] | This is how morty looks like in armed mode. (After picking up sword) 

### Goals

In addition to its layout, each dungeon also has a goal that defines what must be achieved by the player for the dungeon to be considered complete. Basic goals are:

* Getting to an exit.
* Destroying all enemies.
* Having a boulder on all floor switches.
* Collecting all treasure.

More complex goals are being stated in the side panel in the game Dungeon window. 


### Input

<details>
<summary> :memo: notes for setting goals (CLICK ME TO FIND OUT) </summary>
<p>
This application will read from a JSON file containing a complete specification of the dungeon (the initial position of entities, goal, etc.).

The dungeon files have the following format:

> { "width": *width in squares*, "height": *height in squares*, "entities": *list of entities*, "goal-condition": *goal condition* }

Each entity in the list of entities is structured as:

> { "type": *type*, "x": *x-position*, "y": *y-position* }

where *type* is one of

> ["player", "wall", "exit", "treasure", "door", "key", "boulder", "switch", "portal", "enemy", "sword", "invincibility"]

The `door`, `key`, and `portal` entities include an additional field `id` containing a number. Keys open the door with the same `id` (e.g. the key with `id` 0 opens the door with `id` 0). Portals will teleport entities to the **one** other portal with the same ID.

The goal condition is a JSON object representing the logical statement that defines the goal. Basic goals are:

> { "goal": *goal* }

where *goal* is one of

> ["exit", "enemies", "boulders", "treasure"]

In the case of a more complex goal, *goal* is the logical operator and the additional *subgoals* field is a JSON array containing subgoals, which themselves are goal conditions. For example,

```JSON
{ "goal": "AND", "subgoals":
  [ { "goal": "exit" },
    { "goal": "OR", "subgoals":
      [ {"goal": "enemies" },
        {"goal": "treasure" }
      ]
    }
  ]
}
```
</p>
</details>


Note that the same basic goal *can* appear more than once in a statement.

### User interface

The UI component of this project will be implemented in JavaFX. 

The client has given you free reign over the visual design of the program. Included in the starter code are some example assets, but you are free to use different ones. The examples above came from [here](http://opengameart.org).


```bash
have fun!
```




[player]:        images/morty.png
[wall]:          images/wall2.png
[exit]:          images/exit.png
[door_open]:     images/open_door.png
[door_closed]:   images/closed_door.png
[key]:           images/key.png
[boulder]:       images/boulder2.png
[switch]:        images/pressure_plate.png
[portal]:        images/portalgreen.png
[enemy]:         images/rick.png
[sword]:         images/greatsword_1_new.png
[treasure]:      images/gem.png
[potion]:        images/red.png
[inv]:           images/firemorty.png
[armed]:         images/mortysword.png

[lv]:            examples/lv.png
[lv1]:           examples/lv1.png
[rules]:         examples/rules.png
[select]:        examples/select.png
[start]:         examples/start.png

