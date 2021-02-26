package Ejercicio1;

public class Rectangle {
    public int width;
    public int height;

    public Rectangle(int width, int height){
        this.width = width;
        this.height = height;
    }

    public Rectangle(){

    }

    public float area(){
        return width * height;
    }
}

/*
* Esta es la clase que se debe crear, las propiedades y su métodos
* deben de ser públicos para que puedan ser llamados.
* También debe de existir un constructor que no reciba parámetros
* para que al momento de ser llamado no pida argumentos para crear la instancia.
* */
