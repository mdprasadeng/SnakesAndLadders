import imgui.ImDrawList;
import imgui.ImGui;
import imgui.app.Application;

public class SnakesAndLadders extends Application {
    @Override
    public void process() {
        ImGui.text("Hello");
        ImDrawList drawList = ImGui.getWindowDrawList();
        drawList.addRectFilled(0, 0, 100, 100, ImGui.getColorU32(255, 0.1f, 0.1f, 1));

    }

    public static void main(String[] args) {
        SnakesAndLadders app = new SnakesAndLadders();
        launch(app);
    }


}
