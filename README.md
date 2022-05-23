#SKC_Aster

Gameplay:
Players must survive as many waves of Asteroids and Enemy ships as possible in order to obtain a higher score. Destroying Asteroids and Enemies can be done by firing your ship’s autocannon and particle cannon at them or by simply ramming them. Ammunition is limited, but replenishable. Powerups may be obtained in the process of destroying enemies.

Start-up:
This implementation of Asteroids does not feature a menu screen and instead players will automatically enter a new game being spawned in the lower center of the map, facing upwards. 

Weapons:
The ship is equipped with 2 weapons:
the autocannon – fires rapidly with relatively low damaging but fast bullets, has a big magazine, relatively fast ammo replenishment rate
the particle cannon – fires slowly with a slow moving, large and durable projectile, has a small magazine with a very slow ammo replenishment rate
Bullets do not damage the parent ship.

Movement:
The player may move forward and steer left and right. Turning in place and firing while moving is permitted. Firing weapons can only be done in the direction the ship is facing.
The movement of Asteroids is constant from the moment of their construction.
Enemy ships change their direction and speed every 3 seconds. They may fire in any direction.

HUD:
The HUD is comprised of an HP and Shield, an Ammo and a Score section.
The HP and Shield section displays the ship’s current health and shields, additionally displaying the maximum number of shields. It is situated in the upper left corner of the screen.
The Ammo section displays the ship’s current ammo for both the autocannon and the particle cannon. It is situated in the bottom left corner of the screen.
The Score section displays the current score cast to an integer. It is situated in the bottom right corner.


Key-bindings:
Turn Right – Right Arrow Key / D
Turn Left – Left Arrow Key / A
Thrusters – Up Arrow Key / W
Autocannon fire – Space Key
Proton cannon – Shift Key
Restart game – Escape Key

Power-ups:
Powerups can be obtained by the player by damaging Asteroids and Enemy Ships with either weapon, by ramming into them or via a NUKE triggered by the player.
Drop rate depends on how and what is being hit. Asteroids have a lower drop rate than Enemy Ships and damaging via bullets rather than ramming has a lower rate than the reverse.
All powerup type have the same chance to spawn and are represented by colored circles.
There are 4 kinds of power-ups available in the game, color coded as following:
YELLOW – multiplies the current score by 110%
GREEN – adds 1 hp to the player
RED – Detonates a nuke which does not hurt the player
WHITE – Adds to the autocannon maximum ammo count and decreases ammo replenishment rate for the autocannon

Special Thanks to Bojor Barbu for contributing with the cosmic art assets:
https://gitlab.com/bbojor
