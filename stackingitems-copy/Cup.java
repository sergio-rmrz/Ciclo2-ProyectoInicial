
/**
 * Write a description of class Cup here.
 * 
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 1.0
 */

public class Cup
{
    /** Número lógico que identifica el tamaño de la taza */
    private int number;
    /** Altura de la taza en píxeles */
    private int height;
    /** Ancho de la taza en píxeles */
    private int width;
    /** Posición horizontal */
    private int xPosition;
    /** Posición vertical que representa la base de la taza */
    private int yPosition;
    /** Color de la taza */
    private String color;
    /** Indica si la taza está visible en pantalla */
    private boolean isVisible;
    /** Indica si la taza tiene tapa */
    private boolean hasLid;
    /** Indica si está adentro */ 
    private boolean inside;
    private boolean lidActive;
    private Lid lid;
    
    private boolean lidOn;
    
    private static final int PIXEL_POR_CM = 5;
    
    private Rectangle shape1;
    private Rectangle shape2;
    
    

    /**
     * Constructor for objects of class Cup
     */
    public Cup(int number, String color) {
        this.number = number;
        this.height = calcularHeight(number);
        this.color = color;
        this.xPosition = 130; 
        this.yPosition = 200;
        this.width = calcularHeight(number);
        this.isVisible = false;
        this.inside = false;
        this.lidActive = false;
        this.lid = new Lid(number, color);
        
    }
    
    public int calcularHeight(int number) {
        return ((2 * number) - 1) * PIXEL_POR_CM;
    }
    
    /**
     * Hace visible la taza en pantalla
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }
    /**
     * Hace invisible la taza en pantalla
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
        lid.makeInvisible();
    }
    
    
    // Parte de la tapa (correción debido a que la profe comentó que así podeia ser mejor) 
    
    public void activateLid() {
        lidActive = true;
    }
    
    /**
     * Muestra la tapa de esta taza
     * La tapa se posiciona automáticamente sobre la taza antes de mostrarse
     */
    public void showLid() {
        lid.setPosition(xPosition, yPosition - height);
        lid.makeVisible();
    }
    
    public void eraseLid() {
        lid.erase();
    }
    
    /**
     * Oculta la tapa de esta taza sin eliminarse
     */
    public void hideLid() {
        lidActive = false;
        lid.makeInvisible();
    }
    
    
    /**
     * Indica si la tapa de esta taza está actualmente visible (activa).
     * 
     * @return true si la tapa está visible
     */
    public boolean hasLidOn() {
        return lidActive;
    }   
    
    
    /**
     * Mueve la taza verticalmente una distancia determinada
     * 
     * @param distancia distancia en píxeles
     */

    public void moverVertical(int distancia) {
        erase();
        yPosition += distancia;
        if (lid.isVisible()){
            lid.setPosition(xPosition, yPosition - height + PIXEL_POR_CM);
        }
        draw();
    }
    
    
    /**
     * Devuelve la tapa integrada (para posicionarla externamente si es necesario).
     * 
     * @return La tapa de esta taza
     */
    public Lid getLid() {
        return lid;
    }
    
    
     /**
     * Establece una nueva posición sin redibujar inmediatamente
     * @param x Nueva posición horizontal
     * @param y Nueva posición vertical
     */
    public void setPosition(int x, int y) {
        xPosition = x;
        yPosition = y;
        // esto en caso de la que exista una tapa "activa"
        if (lid.isVisible()){
            lid.setPosition(x, y - height);
        }
    }
    
    /**
     * Mueve la taza a una posición
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
     * Devuelve el número lógico de la taza.
     * 
     * @return Número identificador
     */
    public int getNumber() {
        return number;
    }
    
    /**
     * Devuelve la altura de la taza en píxeles
     * 
     * @return Altura en píxeles
     */
    public int getHeight() {
        return height/5;
    }
    
    /**
     * Devuelve el color de la cup
     * 
     * @return color de cup
     */
    public String getColor() {
    return color;
    }
    
    public boolean isInside() {
    return inside;
    }
    
    public void setInside(boolean value) {
    inside = value;
    }
    
    
    /**
     * Marca la taza como cubierta con una tapa.
     */
    public void gotALid() {
        hasLid = true;
    }
    
    
    /**
     * Dibuja la taza en pantalla utilizando dos rectángulos
     */
    public void draw() {
        if (isVisible) {  // Cambiar tamaño y color en vez de crear uno nuevo :D

            int grosor = 7;

            int esquinaX = xPosition - width / 2;
            int esquinaY = yPosition - height;

            shape1 = new Rectangle();
            shape1.changeSize(height, width);
            shape1.changeColor(color);
            shape1.moveHorizontal(esquinaX - 70);
            shape1.moveVertical(esquinaY);
            shape1.makeVisible();
            
            shape2 = new Rectangle();
            shape2.changeSize(height - grosor, width - 2 * grosor);
            shape2.changeColor("white");
            shape2.moveHorizontal((esquinaX + grosor) - 70);
            shape2.moveVertical(esquinaY);
            shape2.makeVisible();
        }
    }
    
    /**
     * Elimina la representación gráfica de la taza en pantalla
     */
    public void erase() {
        if (shape1 != null) shape1.makeInvisible();
        if (shape2 != null) shape2.makeInvisible();
    }

    public int getYPosition() {
    return yPosition;
    }
}