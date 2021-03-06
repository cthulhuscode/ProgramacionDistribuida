import static java.lang.Math.round;

public class Circle implements Polygon{
    public float radio = 0;

    public Circle(float radio){
        this.radio = radio;
    }

    public float getArea() {
        return (float) (3.1416 * (radio * radio));
    }

    public int getSides() {
        return 0;
    }
}
