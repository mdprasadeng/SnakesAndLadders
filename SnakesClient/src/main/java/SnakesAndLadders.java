import data.World;
import data.World.Line;
import data.World.Player;
import data.World.SNLLineType;
import imgui.ImDrawList;
import imgui.ImFont;
import imgui.ImGui;
import imgui.app.Application;

public class SnakesAndLadders extends Application {

    public static float _A = 0.8f;
    public static float _B = 0.3f;
    public static float _C = 0.6f;

    public static int GRID = 60;
    public static int OFF_X = 150;
    public static int OFF_Y = 60;
    private World world;

    @Override
    protected void preRun() {
        super.preRun();
        this.world = World.createWorld();
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

        ImGui.text("Hello");
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


    }

    public static void main(String[] args) {
        SnakesAndLadders app = new SnakesAndLadders();
        launch(app);
    }


}
