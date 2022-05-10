import data.World;
import data.World.Line;
import data.World.Player;
import data.World.PlayerColor;
import data.World.SNLLineType;
import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImGui;
import imgui.app.Application;
import imgui.type.ImString;
import java.util.Random;

public class SnakesAndLadders extends Application {

    public static float _A = 0.8f;
    public static float _B = 0.3f;
    public static float _C = 0.6f;

    public static int GRID = 60;
    public static int OFF_X = 150;
    public static int OFF_Y = 60;
    private World world;
    private SFSClient sfsClient;
    private ImString name;


    public SnakesAndLadders() {
    }

    @Override
    protected void preRun() {
        super.preRun();
        this.sfsClient = new SFSClient();
        name = new ImString(30);
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

        if (world == null) {
            ImGui.inputText("Name", this.name);
            if (ImGui.button("Connect")) {
                this.sfsClient.setOnWorldUpdate(world -> {
                    this.updateWorld(world);
                });
                this.sfsClient.connect(this.name.get());
            }
            return;
        }

        ImGui.text("MyId is" + this.name.get());
        ImGui.text("Last Roll was " + world.lastRoll);
        if (world.currentTurnBy >= 0) {
            String playerId = world.playerIds[world.currentTurnBy];
            if (playerId.equals(this.name.get())) {
                if (ImGui.button("Play As " + playerId)) {
                    this.sfsClient.sendDiceRoll();
                }
            } else {
                ImGui.text("Turn by" + playerId);
            }
        } else {
            ImGui.text("Waiting for more players");
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
            drawList.addCircleFilled(getPosX(player.position), getPosY(player.position), GRID/4f, getColor(player.color) );
            drawList.addText(getPosX(player.position), getPosY(player.position) + GRID /5f, getColor(player.color),  player.name);
        }


    }

    private void updateWorld(World diffWorld) {
        if (this.world == null) {
            this.world = new World();
        }
        this.world = diffWorld;
    }

    private int getColor(PlayerColor color) {
        switch (color) {
            case RED -> {
                return ImGui.getColorU32(1f, 0f, 0f, 1);
            }
            case GREEN -> {
                return ImGui.getColorU32(0f, 1f, 0f, 1);
            }
            case BLUE -> {
                return ImGui.getColorU32(0f, 0f, 1f, 1);
            }
            case YELLOW -> {
                return ImGui.getColorU32(1f, 1f, 0f, 1);
            }
        }
        return ImGui.getColorU32(1f, 1f, 0f, 1);
    }


    public static void main(String[] args) {
        SnakesAndLadders app = new SnakesAndLadders();
        launch(app);
    }



}
