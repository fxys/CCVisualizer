package fxys;

public class Main {
    public static Window w;

    public static void main(String[] args) {
        w = new Window(10000);
    }

    public static void exit(int i) {
        System.out.printf("Generation time: %.4fms %n", Window.gt);
        System.out.printf("Average rendering time: %.4fms %n", Visualizer.getAverageRenderTime());
        System.exit(i);
    }
}
