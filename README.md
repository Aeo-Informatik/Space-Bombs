
# DESCRIPTION
Space Bombs is a Bomberman inspired PVP game written in Java with the libgdx library.
It is available on Mac OSX, Linux, Windows and Android 4.4+. It can be played across all devices
but it is encouraged to play it on a computer as it was primarily developed for it.

![Space Bombs](https://github.com/Aeo-Informatik/Space-Bombs/blob/master/android/assets/screenshots/server-menu.png "Server Menu")

## Gameplay
With at least two human players one hosts the game session and the other connects to it.
The game instantly starts and follows the standard last man standing pattern BUT you can choose between
9 different bomb types giving possibilities of combination thus creating different tactics. Each player
has an amount X of gold that gets replenished over time and can be increased by killing other players or picking up items
and coins. Every special bomb you place costs you gold. This forces the player to tactically use special bombs in order to achive victory instead of spamming them.
Speaking of items there are item spawner on each map which will periodically spawn upgrades like
range upgrades, coin bags, speed upgrade ecc. These are the "hot points" where players gather and fight.
But these item spawners can also spawn some bombs so that players have to move away and give enemies a chance to grab some items.

## [Download Here](http://aeo-informatik.github.io/Space-Bombs/ "Download Page")

![Space Bombs](https://github.com/Aeo-Informatik/Space-Bombs/blob/master/android/assets/screenshots/X3-bomb.png "X3 Bomb")
![Space Bombs](https://github.com/Aeo-Informatik/Space-Bombs/blob/master/android/assets/screenshots/black-hole.png "Black Hole")


## Bomb Types
| Bomb        | Description           | Cost in Coins   |
| ------------- |:-------------:| -----:|
| Normal      | Explodes in a cross shaped way after 2s | 0 |
| Dynamite      |  Explodes in a cubic radius after 2s      |   30 |
| Frag Grenade | Explodes and reproduces itself at the end of some explosion tiles | 150 |
| X3 | Explodes multiple times in a row each time increasing the range     | 70 |
| Mine | Explodes after detecting a player     |    50 |
| Black Hole | Creates a black hole pulling every player inside and exploding afterwards | 200 |
| Remote      | Explodes after receiving a detonation signal      | 70 |
| Barrel | Explodes with a huge cubic radius after getting hit by a bomb | 50 |
| Turret | Explodes with double range spinning around | 50 |


## Items
| Item        | Description           |
| ------------- |:-------------:|
| Normal Range Upgrade | Increases range of cross shaped explosions |
| Cubic Range Upgrade | Increases range of cubic explosions |
| Coin Bag  | Gives the player 100 coins |
| Life Up | Increases player life by 1 |
| Speed Up | Increases player speed by 10% |
| Tombstone      | Spawns after player death and holds all coins from dead player |
| Coin | Adds 10 coins to players balance|

## Developers
Dominik Plonis:
- Illustrator
- Map Design

Christian Bugge:
- Developer

Luis Hebendanz:
- Main Developer
- Team-Management

Pascal Langer:
- Developer

Leon Jemain Jäger:
- Testing