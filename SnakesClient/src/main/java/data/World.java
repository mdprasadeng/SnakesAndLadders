package data;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import imgui.ImGui;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class World {
    public int rows;
    public int columns;
    public Map<Integer, Line> lines;
    public List<Player> players;
    public int currentTurnBy;
    public int lastRoll;
    public String[] playerIds;

    public World withSFS(ISFSObject params) {
        rows = params.getInt("rw");
        columns = params.getInt("cl");
        currentTurnBy = params.getInt("c");
        lastRoll = params.getInt("l");
        playerIds = params.getUtfStringArray("p").toArray(e -> new String[e]);

        List<Line> lines = new ArrayList<>();
        ISFSArray linesSFS = params.getSFSArray("lns");
        for (int i = 0; i < linesSFS.size(); i++) {
            ISFSObject lineSFS = linesSFS.getSFSObject(i);
            lines.add(new Line().withSFS(lineSFS));
        }
        this.lines = lines.stream().collect(Collectors.toMap(
            e -> e.start,
            Function.identity()
        ));

        List<Player> players = new ArrayList<>();
        ISFSArray plySFS = params.getSFSArray("plys");
        for (int i = 0; i < plySFS.size(); i++) {
            players.add(new Player().withSFS(plySFS.getSFSObject(i)));
        }
        this.players = players;

        return this;
    }

    public static class Player {
        public String id;
        public String name;
        public PlayerColor color;
        public int position;

        public Player withSFS(ISFSObject plySFS) {
            id = plySFS.getUtfString("id");
            name = plySFS.getUtfString("nm");
            color = PlayerColor.valueOf(plySFS.getUtfString("cr"));
            position = plySFS.getInt("p");
            return this;
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

        public Line withSFS(ISFSObject lineSFS) {
            start = lineSFS.getInt("s");
            end = lineSFS.getInt("e");
            type = SNLLineType.valueOf(lineSFS.getUtfString("t"));

            return this;
        }
    }

    public enum SNLLineType {
        SNAKE,
        LADDER;
    }

}
