public class Rectangle implements Polygon{
    public float length = 0;
    public float width = 0;

    public Rectangle(float length, float width){
        this.length = length;
        this.width = width;
    }

    public float getArea() {
        return length * width;
    }

    public int getSides() {
        return 4;
    }
}
