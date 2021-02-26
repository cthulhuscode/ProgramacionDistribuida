package Ejercicio1;

public class SomethingIsWrong {
    public static void main(String[] args){
        // Error
        // Rectangle myRect;
        /* El error de esta sentencia es que se declara
           la variable myRect pero no se inicializa creando
           una instancia u objeto de la clase Rectangle.
         */

        Rectangle myRect = new Rectangle();
        myRect.width = 40;
        myRect.height = 50;
        System.out.println("myRect's area is " + myRect.area());
    }
}
/*
* Otro error es que no existe la clase Rectangle
* así que debe crearse.
*/

/*
* El resultado devuelto es:
* myRect's area is 2000.0
* */