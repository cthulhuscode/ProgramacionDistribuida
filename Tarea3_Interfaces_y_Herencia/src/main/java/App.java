public class App {
    public static void main(String[] args){
        Polygon square1 = new Square(5);
        Polygon square2 = new Square(6);
        Polygon circle1 = new Circle(4);
        Polygon circle2 = new Circle(3);
        Polygon rectangle1 = new Rectangle(8,5);
        Polygon rectangle2 = new Rectangle(4,10);

        System.out.println("---- Squares ----");
        System.out.println("square1's area: " + square1.getArea());
        System.out.println("square1's sides: " + square1.getSides());
        System.out.println("square2's area: " + square2.getArea());
        System.out.println("square2's sides: " + square2.getSides());

        System.out.println("\n---- Circles ----");
        System.out.println("circle1's area: " + circle1.getArea());
        System.out.println("circle1's sides: " + circle1.getSides());
        System.out.println("circle2's area: " + circle2.getArea());
        System.out.println("circle2's sides: " + circle2.getSides());

        System.out.println("\n---- Rectangles ----");
        System.out.println("rectangle1's area: " + rectangle1.getArea());
        System.out.println("rectangle1's sides: " + rectangle1.getSides());
        System.out.println("rectangle2's area: " + rectangle2.getArea());
        System.out.println("rectangle2's sides: " + rectangle2.getSides());
    }
}
