public class Square implements Polygon{
    public float width;

    public Square(float width){
        this.width = width;
    }

    public float getArea(){
        return width * width;
    }

    public int getSides(){
        return 4;
    }
}
