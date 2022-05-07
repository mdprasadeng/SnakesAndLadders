package data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class World {
    public int rows;
    public int columns;
    public Map<Integer, Line> lines;
    public List<Player> players;

    public static class Player {
        public String name;
        public int position;
    }

    public static class Line {
        public int start;
        public int end;
        public SNLLineType type;
    }

    public enum SNLLineType {
        SNAKE,
        LADDER;
    }

    public static World createWorld() {
        World world = new World();
        world.rows = 10;
        world.columns = 10;
        world.players = Arrays.asList(
                createPlayer("Red"),
                createPlayer("Blue"),
                createPlayer("Yellow"),
                createPlayer("Green")
        );
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


        return world;
    }

    public static Player createPlayer(String name) {
        Player player = new Player();
        player.name = name;
        player.position = 1;
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
        line.type = SNLLineType.SNAKE;
        line.start = start;
        line.end = end;
        return line;
    }
}
