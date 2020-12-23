package geneticProgramming;

public class Point {
    private double x;
    private double y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
