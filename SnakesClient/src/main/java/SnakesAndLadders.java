import data.World;
import data.World.Line;
import data.World.Player;
import data.World.SNLLineType;
import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImGui;
import imgui.app.Application;
import java.util.Random;

public class SnakesAndLadders extends Application {

    public static float _A = 0.8f;
    public static float _B = 0.3f;
    public static float _C = 0.6f;

    public static int GRID = 60;
    public static int OFF_X = 150;
    public static int OFF_Y = 60;
    private World world;
    private int lastRoll;
    private SFSClient sfsClient;
    private String myId;


    public SnakesAndLadders(String myId) {
        this.myId = myId;
    }

    @Override
    protected void preRun() {
        super.preRun();
        this.world = World.createWorld();
        this.sfsClient = new SFSClient(myId);
        sfsClient.connect();
        sfsClient.setOnDiceRoll(diceRoll -> {
            this.onCurrentPlayerTurn(diceRoll);
        });
    }

    private float getPosX(int i) {
        i -= 1;
        int x = i % this.world.columns;
        int y = i / this.world.rows;
        boolean leftToRight = y % 2 == 0;
        if (leftToRight) {
            return OFF_X + x * GRID + GRID/2f;
        } else {
            return OFF_X + this.world.columns * GRID - x * GRID - GRID/2f;
        }

    }

    private float getPosY(int i) {
        i -= 1;
        int x = i % this.world.columns;
        int y = i / this.world.rows;
        boolean leftToRight = y % 2 == 0;
        return OFF_Y + this.world.rows*GRID - y * GRID - GRID/2f;
    }



    @Override
    public void process() {

        ImGui.showDemoWindow();
        int COLOR_A = ImGui.getColorU32(_A, _A, _A, 1);
        int COLOR_B = ImGui.getColorU32(_B, _B, _B, 1);
        int COLOR_C = ImGui.getColorU32(_C, _C, _C, 1);
        int COLOR_S = ImGui.getColorU32(1f, 0f, 0f, 1);
        int COLOR_L = ImGui.getColorU32(0f, 1f, 0f, 1);

        ImGui.text("MyId is" + myId);
        ImGui.text("Last Roll was " + lastRoll);
        String playerId = world.playerIds[world.currentTurnBy];
        if (playerId.equals(myId)) {
            if (ImGui.button("Play As " + playerId)) {
                int min = 1;
                int max = 6;
                Random random = new Random();
                int value = random.nextInt(max + min) + min;
                this.sfsClient.sendDiceRoll(value);
            }
        } else {
            ImGui.text("Turn by" + playerId);
        }

        ImDrawList drawList = ImGui.getWindowDrawList();

        for (int i = 0; i < this.world.rows; i++) {
            for (int j = 0; j < this.world.columns; j++) {
                int color = (i + j) % 2 == 0 ? COLOR_A : COLOR_B;
                drawList.addRectFilled(OFF_X + i * GRID, OFF_Y + j * GRID, OFF_X + i * GRID + GRID, OFF_Y + j * GRID + GRID, color);
            }
        }


        for(int i = 1; i <= this.world.columns * this.world.rows; i++) {
            drawList.addText(getPosX(i), getPosY(i), COLOR_C, i + "");
        }

        for (Line value : this.world.lines.values()) {
            int color = value.type == SNLLineType.SNAKE ? COLOR_S : COLOR_L;
            drawList.addLine(getPosX(value.start), getPosY(value.start), getPosX(value.end), getPosY(value.end), color, 5);
        }

        for (Player player : this.world.players) {
            drawList.addCircleFilled(getPosX(player.position), getPosY(player.position), GRID/4f, player.color );
            drawList.addText(getPosX(player.position), getPosY(player.position) + GRID /5f, player.color,  player.name);
        }


    }

    public void onCurrentPlayerTurn(int value) {
        String playerId = world.playerIds[world.currentTurnBy];
        Player player = world.players.stream().filter(e -> e.id.equals(playerId)).findFirst()
            .get();
        player.position += value;
        lastRoll = value;
        world.currentTurnBy +=1;
        if (world.currentTurnBy == 4) {
            world.currentTurnBy = 0;
        }

        Line line = world.lines.get(player.position);
        if (line != null) {
            player.position = line.end;
        }
    }

    public static void main(String[] args) {
        SnakesAndLadders app = new SnakesAndLadders(args[0]);
        launch(app);
    }


}
