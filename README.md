# SnakesAndLadders
* Lets build a Snakes And Ladders Game
* All of us can play together 
* Getting Hello World working via IDE
* What is the IDE doing to make it work
* Java History
* JVM JDK JRE
* Typical Development of a Java Application
* Libraries
* How does Smartfox Work?
* How to write our own extensions?
* Hello world inside smartfox
* Hello <name>, meet <name>,<name> when a user logs in
* A sample client to show the information
* GUI for client (if we have more than half hour left)
* Summary

# Session 1 Notes
* This repo : https://github.com/mdprasadeng/SnakesAndLadders
* Smartfox Server: https://www.smartfoxserver.com/
* Intellij: https://www.jetbrains.com/idea/download
 
 Video : https://drive.google.com/file/d/14NqWqplOf5idY3UhAUGQTqhFmbdj3j3X/view?usp=sharing


# Session 2
* Snakes And Ladders
* Lets finish building the client
* talk about what and how to add multiplayer support
* How to think when building multiplayer games

* Compare approaches of Authoritative vs Relay Servers
* build a relay based multiplayer first
* build a authoritative server next



In the middle of game
* its turn of PlayerA - now ClientA has clicked a button
* Client 
  * rolling a dice 
  * computing new position
    * dice roll ++
    * check for snakes and ladders
  * compute next turn  

* Authoritative - 
 * Client -> Server
 * Client sends rollADice -> Server
 * Server -> to all clients {dice roll, new position of this player, update next turn}
 * Input -> Process -> Send
 

* Relay Server
 * Client -> Server
 * Client sends data -> Server
 * Server sends data {to all clients} 
 * data -> {dice roll}

* Server to react on input from user
* Server to send data to all user 
 
 Videos:
* Part 1 : https://drive.google.com/file/d/10ZtfhRIwwf1cumfqbdw5hpgDgUzsjeUl/view?usp=sharing
* Part 2 : https://drive.google.com/file/d/1vU_5Wycn5Kk8k_O0zMMvkGFLK6lGFn3J/view?usp=sharing
* Part 3: https://drive.google.com/file/d/1yBFiUve7gbsGK1e0BB-mk6oZWDtIBA59/view?usp=sharing

 ## Session 3 
 Slides : https://docs.google.com/presentation/d/1ow91hosYEJNCtq-vrxkVv-G5V9NRa_oxrQ_ZR1jkYas/edit#slide=id.p
 
 Videos:
 * Part 1 : https://drive.google.com/file/d/16pVvKpRdNUV1BGSiDcEK0mtAqIxVWial/view?usp=sharing
 * Part 2 : https://drive.google.com/file/d/1BZkd8pZRPzHKs40BkG9OJeu0lRXcGcaI/view?usp=sharing 
 
