package Ejercicio2;

public class Main {
    public static void main(String[] args){
        // Agrega código necesario para poder ejecutar los siguiente:
        IdentifyMyParts a = new IdentifyMyParts();
        IdentifyMyParts b = new IdentifyMyParts();
        a.y = 5;
        b.y = 6;
        a.x = 1;
        b.x = 2;
        /* Se le asigna 2 a la propiedad estática,
        * por lo tanto, las propiedades a.x e IdentifyMyParts.x
        * también tendrán valor 2*/
        System.out.println("a.y = " + a.y);
        System.out.println("b.y = " + b.y);
        System.out.println("a.x = " + a.x);
        System.out.println("b.x = " + b.x);
        System.out.println("IdentifyMyParts.x = " + IdentifyMyParts.x);
    }
}
/* El resultado de las varibles es el sig.:
 a.y = 5
 b.y = 6
 a.x = 2
 b.x = 2
 IdentifyMyParts.x = 2
*/

/*
* Este código pareciera que tiene algo raro al imprimir
* la propiedad x de los objetos a y b, ya que al imprimir
* esa propiedad siempre sale el valor 2.
* Esto es así debido a que x se definió como una propiedad estática
* en la clase IdentiyMyParts, lo cual significa que el valor que se le
* asigne a la propiedad será compartido con todas las demás instancias.
* */


