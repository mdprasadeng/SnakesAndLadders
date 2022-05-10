package com.supg.snakes.data;

import com.supg.snakes.data.World.Line;
import com.supg.snakes.data.World.Player;
import com.supg.snakes.data.World.PlayerColor;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.commons.lang.math.RandomUtils;

public class WorldManager {

    private World world;

    public WorldManager(World world) {
        this.world = world;
    }

    public void playerJoin(String playerId) {
        if(world.players.size() < 4) {
            PlayerColor playerColor;
            switch (world.players.size()) {
                case 0:
                    playerColor = PlayerColor.RED;
                    break;
                case 1:
                    playerColor = PlayerColor.GREEN;
                    break;
                case 2:
                    playerColor = PlayerColor.BLUE;
                    break;
                case 3:
                    playerColor = PlayerColor.YELLOW;
                    break;
                default:
                    playerColor = PlayerColor.YELLOW;
                    break;
            }
            world.players.add(World.createPlayer(playerId, playerColor));
            if (world.players.size() == 4) {
                world.currentTurnBy = 0;
                String[] playerIds = new String[4];
                int i = 0;
                for(Player player : world.players) {
                    playerIds[i] = player.id;
                    i++;
                }
                world.playerIds = playerIds;
            }
            // Send entire world information for new player
            // DiffWorld to exiting players
        }
    }

    public void doADiceRoll(String inputPlayerId) {
        String playerId = world.playerIds[world.currentTurnBy];
        if (!inputPlayerId.equals(playerId)) {
            return;
        }
        Player player = world.players.stream().filter(e -> e.id.equals(playerId)).findFirst()
            .get();
        int value = RandomUtils.nextInt(6) + 1;
        player.position += value;
        world.lastRoll = value;
        world.currentTurnBy +=1;
        if (world.currentTurnBy == 4) {
            world.currentTurnBy = 0;
        }

        Line line = world.lines.get(player.position);
        if (line != null) {
            player.position = line.end;
        }
    }

    public World getWorld() {
        return world;
    }
}
