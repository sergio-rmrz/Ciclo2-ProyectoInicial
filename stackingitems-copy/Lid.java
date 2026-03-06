import java.awt.*;
/**
 * Write a description of class Lid here.
 * 
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 2.6
 */
public class Lid
{
    /** Número lógico asociado a la taza correspondiente */
    private int number;
    /** Altura (grosor) de la tapa en píxeles */
    private int height;
    /** Ancho de la tapa en píxeles */
    private int width;
    /** Posición horizontal */
    private int xPosition;
    /** Posición vertical de referencia */
    private int yPosition;
    /** Color de la tapa */
    private String color;
    /** Indica si la tapa está visible en pantalla */
    private boolean isVisible;
    /** nuevo indicativo*/ 
    
    
    private boolean inside = false;
    
    private static final int PIXEL_POR_CM = 5;
    
    private Rectangle shape;
    /**
     * Constructor for objects of class Lid
     */
    public Lid(int number, String color) {
        this.number = number;
        this.color = color;
        this.width = ((2 * number) - 1) * PIXEL_POR_CM;

        this.height = PIXEL_POR_CM; 
        
        this.xPosition = 130; 
        this.yPosition = 0;
        this.isVisible = false;
        
    }
    
    /**
     * Hace visible la tapa en pantalla.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    
    /**
     * Hace invisible la tapa en pantalla.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Indica si la tapa está actualmente visible.
     * 
     * @return true si está visible
     */
    public boolean isVisible() {
        return isVisible;
    }
    
    public void setCanvasPosition(int x, int canvasY) {
        this.xPosition = x;
        this.yPosition = canvasY;
    }
    
    
    /**
     * Mueve la tapa verticalmente
     * 
     * @param distancia distancia en píxeles
     */
    public void moveVertical(int distancia) {
        erase();
        yPosition += distancia;
        draw();
    }
    
    /**
     * Mueve la tapa a una posición absoluta específica
     * 
     * @param x Nueva posición horizontal
     * @param y Nueva posición vertical
     */
    public void moveTo(int x, int y) {
        erase();
        xPosition = x;
        yPosition = y;
        draw();
    }
    
    /**
     * Devuelve el número 
     * 
     * @return Número identificador
     */
    public int getNumber() {
        return number;
    }
    
    public int getSize() {
        return number;
    }
    
    /**
     * Devuelve el color de la lid
     * 
     * @return color de lid
     */
    public String getColor() {
    return color;
    }
    
    public int getYPosition() {
    return yPosition;
    }

    /**
     * Establece una nueva posición sin redibujar de manera inmediata
     * @param x Nueva posición horizontal
     * @param y Nueva posición vertical
     */
    public void setPosition(int x, int y) {
        xPosition = x;
        yPosition = y;
    }
    public boolean isInside() {
        return inside; }
    public void setInside(boolean inside) {
        this.inside = inside; }
    /**
     * Dibuja la tapa en pantalla utilizando un rectángulo.   
    */
    private void draw() { // Cambiar tamaño y color en vez de crear uno nuevo :D
        if (isVisible) {
            if (shape != null) shape.makeInvisible();
            int esquinaX = xPosition - width / 2;
            // Dibuja la tapa en su posición
            
            
            shape = new Rectangle();
            shape.changeSize(height, width);
            shape.changeColor(color);
            shape.moveHorizontal(esquinaX - 70);
            shape.moveVertical(yPosition);
            shape.makeVisible();
        }
    }
    
    /**
     * Elimina la tapa en pantalla
     */
    public void erase() {
        if (shape != null) shape.makeInvisible();
    }
}