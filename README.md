# GuardiansOfAdelia

My personal spigot(https://www.spigotmc.org/) plugin project to create a MMORPG minecraft server.

## Dependencies
* HikariCP (https://github.com/brettwooldridge/HikariCP)
* Spigot (https://github.com/SpigotMC)
* ProtocolLib (https://github.com/dmulloy2/ProtocolLib)
* Citizens (https://github.com/CitizensDev/Citizens2)
* LibsDisguises (https://github.com/libraryaddict/LibsDisguises)
* LuckPerms (https://github.com/lucko/LuckPerms)
* WorldEdit (https://github.com/EngineHub/WorldEdit)
* WorldGuard (https://github.com/EngineHub/WorldGuard)

## Features

Please note that these features are for my personal server. It probably will not work in any other setup.
* Characters
  * You can create different characters with 1 minecraft account.
  * Create new character or select an existing one to play when you join server.
* Classes
  * You can create new classes via configs.
  * You can setup elements, gears class can use and skill set in configuration.
* Attributes
  * A character's attributes define his/her power in different areas. Some of the main attributes are: Max health and mana, physical damage and defense, magical damage and defense and critical chance.
  * Your base attributes increases as you level up. Equipments adds their attributes to your character when you equip them.
  * There are elements which increases your attributes as you gain more point in them;
  * Fire: increases physical damage
  * Water: increases max mana
  * Earth: increases max health
  * Lightning: increases magical damage
  * Wind: increases critical chance
  * You can gain more points on elements by investing element points you gain by leveling up or equiping items which has element bonuses on them.
* Skills
  * You can create skills in class and mob skill set configs.
  * Invest skill points to learn or upgrade your skills
  * Use 1-2-3-4 hot bar keys to cast skills via SkillBar
  * Combine SkillComponents to create skill mechanics. Component types are trigger, target, mechanic, condition.
* Quests
  * You can create quests via configs.
  * Icons: 
    Quest icons appear on NPCs and disguises themselfs to each player differently. 
    If player has a completed quest from this NPC, he/she will see a 3D Q icon.
    Else if player can accept a new quest from this NPC,  he/she will see a 3D ? icon.
    Else if player has a quest in progress from this NPC,  he/she will see a 3D ! icon.
    Else he/she will see nothing.
  * Gui: 
    Players can open a gui including quests of a NPC and accept/complete them.
  * Actions: 
    Perform specific actions when player accepts, completes and turns in a quest.
  * Tasks: 
    Add specific tasks for your players to complete. You perform can actions on task complete too.
  * Rewards: 
    Give experience, money and/or items to your players when they turn in a quest.
* GuiBook
  * Create gui with unlimited pages.
    Think of items as words. Make a line from items. Make a page from lines. Make a book from pages.
* Merchants
  * Open a gui on right click to NPC.
    Select specific actions from this gui like buying/selling items, managing your storages, enchanting items..
* Entities
  * You can create entities with skill sets via configs.
* Spawners
  * You can create spawners via configs.
  * Spawns X of a customized entity on a location every Y seconds. Can't have more than Z spawned entities at once.
* RPG-Inventory
  * Equip jewelries to gain bonus stats
* Bazaar
  * Set up a bazaar to sell your items to other players.
  * Each bazaar has a model, players can left click this model to buy items from it.
  * Item on sale and money earned will be delivered to seller's bazaar-storage.
* Trade
  * Invite a player to trade. If he/she accepts a Trade-Gui will open for both players.
  * Add/remove items you will give to other player. Also you can see which items you will receive from other player.
  * Lock the trade. Once locked players can't add/remove items.
  * Accept the trade. When both players accept the trade finishes and items on Trade-Gui gets traded.
* Party
  * Fight together with your friends
  * Share experience gained from monsters with party members
  * Progress at your quests' together
* Guild
  * Create guild to gather a strong army and fight for power
  * Manage your guild with commands such as guild members' ranks
* GuildWars
  * Guild leader or commanders can join your guild to a GuildWar
  * The goal is conquering the castles on map
  * Each castle will give your team +1 point per second
  * First team that reaches the goal point wins the GuildWar
* Minigames
  * This is abstract class to create minigames
  * Current minigames created from this abstract class: Dungeons, LastManStanding(pvp)
* Dungeons
  * Subclass of minigames
  * Join dungeons with up to 4 players
  * Kill dungeon boss before timeout to gain a prize chest
* Crafting
  * There are 8 type of crafting. 
  * Each crafting type has an unique block that open crafting gui when you right click like anvil, grindstone etc
  * On crafting gui, select the level of items you want to create, then craft by clicking on items at cost of ingredients
  * You can gain job experience by crafting and unlock higher level crafts
* Pets
  * Equip an egg to spawn pet. When spawned your pet will follow you
  * There are 2 types of pets. Companions and mounts
  * Companions have health and meleeDamage attributes. They attack your enemies to protect you
  * Mounts have health, speed and jump attributes. You can mount and ride them to travel faster
  * Your active pet gains experience when you kill monsters. Their attributes increases on level up
  * If your pet dies, you can't spawn another pet for 5 minutes. Your pet repsawns with half hp after 5 minutes
  * You can feed pets to restore their healths
* Revive
  * Revive at closest town
  * Then you can choose to revive here or become a soul and search for your tomb. If you can find your tomb in 2 minutes, left click to revive there
