package com.supg.snakes.data;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSArray;
import com.smartfoxserver.v2.entities.data.SFSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class World {
    public int rows;
    public int columns;
    public Map<Integer, Line> lines;
    public List<Player> players;
    public int currentTurnBy;
    public String[] playerIds;
    public int lastRoll;

    public static class Player {
        public String id;
        public String name;
        public PlayerColor color;
        public int position;

        public SFSObject toSFS() {
            SFSObject sfsObject = new SFSObject();
            sfsObject.putUtfString("id", id);
            sfsObject.putUtfString("nm", name);
            sfsObject.putUtfString("cr", color.name());
            sfsObject.putInt("p", position);
            return sfsObject;
        }
    }

    public enum PlayerColor {
        RED,
        GREEN,
        BLUE,
        YELLOW
    }

    public static class Line {
        public int start;
        public int end;
        public SNLLineType type;

        public SFSObject toSFS() {
            SFSObject lineSFS = new SFSObject();
            lineSFS.putInt("s", this.start);
            lineSFS.putInt("e", this.end);
            lineSFS.putUtfString("t", this.type.name());
            return lineSFS;
        }
    }

    public enum SNLLineType {
        SNAKE,
        LADDER;
    }

    public ISFSObject toSFS() {
        SFSObject sfsObject = new SFSObject();
        sfsObject.putInt("rw", rows);
        sfsObject.putInt("cl", columns);
        sfsObject.putInt("c", currentTurnBy);
        sfsObject.putInt("l", lastRoll);
        sfsObject.putUtfStringArray("p", Arrays.stream(playerIds).collect(Collectors.toList()));

        SFSArray sfsArrayLines = new SFSArray();
        for (Line line : lines.values()) {
            SFSObject lineSFS = line.toSFS();
            sfsArrayLines.addSFSObject(lineSFS);
        }
        sfsObject.putSFSArray("lns", sfsArrayLines);

        SFSArray sfsArrayPlayers = new SFSArray();
        for (Player player : players) {
            SFSObject playerSFS = player.toSFS();
            sfsArrayPlayers.addSFSObject(playerSFS);
        }
        sfsObject.putSFSArray("plys", sfsArrayPlayers);

        return sfsObject;

    }

    public static World createWorld() {
        World world = new World();
        world.rows = 10;
        world.columns = 10;
        world.players = new ArrayList<>();
        List<Line> lines = Arrays.asList(
                createSnakes(17, 7),
                createSnakes(62, 19),
                createSnakes(54, 34),
                createSnakes(64, 60),
                createSnakes(87, 36),
                createSnakes(93, 73),
                createSnakes(94, 75),
                createSnakes(98, 79),
                createLadder(1, 38),
                createLadder(4, 14),
                createLadder(9, 31),
                createLadder(21, 42),
                createLadder(28, 84),
                createLadder(51, 67),
                createLadder(72, 91),
                createLadder(80, 99)
                );
        world.lines = new HashMap<>();
        for (Line line : lines) {
            world.lines.put(line.start, line);
        }
        world.currentTurnBy = -1;
        world.playerIds = new String[] {};


        return world;
    }

    public static Player createPlayer(String name, PlayerColor color) {
        Player player = new Player();
        player.id = name;
        player.name = name;
        player.position = 1;
        player.color = color;
        return player;
    }

    public static Line createSnakes(int start, int end) {
        Line line = new Line();
        line.type = SNLLineType.SNAKE;
        line.start = start;
        line.end = end;
        return line;
    }

    public static Line createLadder(int start, int end) {
        Line line = new Line();
        line.type = SNLLineType.LADDER;
        line.start = start;
        line.end = end;
        return line;
    }
}
