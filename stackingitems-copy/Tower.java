import java.util.Stack;
import javax.swing.JOptionPane; // Poner los returnssss, definir atributos tambien 
import java.util.ArrayList;
import java.util.Collections;

// Prueba repositiorio 2

public class Tower 
{   
    /** Estructura (optamos por hacerlo en Stacks)*/
    private Stack<Cup> cups;
    /** Muestra si la torres es o no visible*/
    private boolean isVisible;
    
    /** Indica si la uĺtima acción tuvo exito*/
    private boolean isOK;

    /** Coordenadas en las que quisimos que se dibuje la torre*/
    private int baseX = 130;
    private int baseY = 200;
    
    /** Limite de altura de la torre*/
    private int maxHeight;
    
    /** Esta es una paleta de colores que vamos a usar para el constructor que es automático (el de ciclo 2), btw, decicimos meterle más colores debido a que consideramos que los que nos daba el canvas eran muy pocos */
    private static final String[] COLORES = {
        "red", "blue", "green", "yellow", "magenta",
        "orange", "cyan", "pink", "gray", "darkGray"
    };
    
    
    /**
     * Constructor de la torre
     * @param maxHeight Altura máxima permitida 
     * CONSTRUCTOR DE CICLO 1 (DECIDIMOS ACLARAR ESTO YA QUE PARA CICLO 2 NOS PIDEN UN Tower(Cups), EL CUAL ES OTRO CONSTRUCTOR POR SEPARADO)
     */
    public Tower(int maxHeight)
    {
        cups = new Stack<Cup>();
        this.maxHeight = maxHeight;

        isVisible = false;
        isOK = true;
    }
    
    /**
     * Constructor del ciclo 2 
     * @param numCups Número de copas 
     */
    public Tower(int numCups, int ignorar){
        cups = new Stack<Cup>();
        isVisible = false;
        isOK = true;
        int alturaTotal = 0;
        for (int k = 1; k <= numCups; k++) {
            alturaTotal += (2 * k - 1);
        }
        this.maxHeight = alturaTotal;
        for (int k = 1; k <= numCups; k++) {
            int size  = (2 * k) - 1;
            String color = COLORES[(k - 1) % COLORES.length];
            cups.push(new Cup(size, color));
        }
        isOK = true;
        
    }
    

    // COPAS
    
    
    /**
     * Agrega un copa en la torre
     * Realiza validaciones para que no se repitan ni colores ni tamaños
     */
    public void pushCup(int number, String color)
    {
        Cup nueva = new Cup(number, color);
        
        // Revisa que no existan tamaños repetidos
        if (repSize(number)) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Error, ya existe una copa con este tamaño. Ingrese otro valor");
            return;
        }
        
        //  Revisa que no existan colores repetidos 
        if (repColor(color)) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Error, ya existe una copa con este color. Ingrese otro color");
            return;
        }
        
        // Revisa que no se pase el limite de altura de la torre
        if (getHeight() + nueva.getHeight() <= maxHeight) {

            cups.push(nueva);

            redraw();
            isOK = true;
        }
        else {
            isOK = false;
            JOptionPane.showMessageDialog(null, "Esta copa sobrepasa el límite máximo de la torre");
        }
    }

    
    /**
     * Elimina la última copa que se colocó
     */
    public Cup popCup()
    {
        if (!cups.isEmpty()) {

            Cup removida = cups.pop();
            removida.makeInvisible();
            redraw();
            isOK = true;
            return removida;
        }

        isOK = false;
        return null;
    }

    
    /**
     * Se remuveve la copa ingresada según su número, en caso de que no sea posible, se manda una notificación de error
     */
    public void removeCup(int number) {

        if (cups.isEmpty()) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "La torre está vacía");
            return;
        }
    
        for (int i = 0; i < cups.size(); i++) {
            if (cups.get(i).getNumber() == number) {
                cups.get(i).makeInvisible();
                cups.remove(i);
                redraw();
                isOK = true;
                return;
            }
        }
        isOK = false;
        JOptionPane.showMessageDialog(null, "No hay una cup con este tamaño en esta torre :(");
    }
    
    
    // TAPAS
    
    
    /**
     * Agrega tapas a la torre sobre una copa
     */
    public void pushLid()
    {
        if (cups.isEmpty()) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "No hay tazas en esta torre");
            return;
        }
        Cup top = cups.peek();
        if (top.hasLidOn()) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "La taza ya tiene su tapa :c");
            return;
        }
        top.activateLid();
        if (isVisible){
            top.showLid();
        }
        isOK = true;
    }

    /**
     * Elimina la última tapa que se colocó
     */        
    public Cup popLid()
    {
        if (cups.isEmpty()) {
            isOK = false;
            return null;
        }
        Cup top = cups.peek();
        if (!top.hasLidOn()) {
            isOK = false;
            JOptionPane.showMessageDialog(null, "La taza no tiene su tapa activa");
            return null;
        }
        top.hideLid();
        isOK = true;
        return top;
    }
    
    /**
     * Se remuveve la tapa ingresada según su número, en caso de que no sea posible, se manda una notificación de error
     */
    public void removeLid(String color)
    {
        for (Cup c : cups) {
            if (c.getColor().equals(color) && c.hasLidOn()) {
                c.hideLid();
                redraw();
                isOK = true;
                return;
            }
        }
        isOK = false;
        JOptionPane.showMessageDialog(null,"No existe una tapa con ese color");
    }
    
    
    /**
     * Calcula el top (o el más alto) en la torre
     */
    private int getTop(Cup c) {
        return c.getYPosition() - c.getHeight() * 5;
    }
    
    /**
     * Calcula entre dos copas quien es la más alta
     */
    private Cup getHighestCup(Cup actualMasAlta, Cup candidata) {

        if (actualMasAlta == null) {
            return candidata;
        }
    
        if (getTop(candidata) < getTop(actualMasAlta)) {
            return candidata;
        }
    
        return actualMasAlta;
    }
    
    /**
     * Verifica si ya existe una copa con el tamaño ingresado
     */
    private boolean repSize(int number) {

        for (Cup c : cups) {
            if (c.getNumber() == number) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Verifica si ya existe una copa con el color ingresado
     */
    private boolean repColor(String color) {

        for (Cup c : cups) {
            if (c.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Realiza una verificacion de donde van las copas, si van por dentro o por fuera según sus tamaños, también las "coloca" en la torre
     */
    private void redraw() {
        int yActual = baseY;
        int yTope = baseY; // tope visual real de la torre
        Cup anterior = null;
        Cup ultimaExterna = null;
    
        for (Cup c : cups) {
            c.erase();
            c.eraseLid();
    
            if (anterior == null) {
                c.setPosition(baseX, yActual);
                c.setInside(false);
                ultimaExterna = c;
                yTope = getTop(c);
            } else {
                boolean cabeAdentro = c.getNumber() <= ultimaExterna.getNumber() && !ultimaExterna.hasLidOn();
    
                if (c.getNumber() > ultimaExterna.getNumber()) {
                    yActual = yTope;
                    c.setInside(false);
                    ultimaExterna = c;
                    yTope = getTop(c);
                } else if (cabeAdentro) {
                    if (anterior.isInside() && c.getNumber() < anterior.getNumber()) {
                        yActual = anterior.getYPosition() - 7;
                    } else {
                        yActual = anterior.isInside()
                            ? anterior.getYPosition() - anterior.getHeight() * 5
                            : ultimaExterna.getYPosition() - 7;
                    }
                    c.setInside(true);
                    // actualizar yTope si esta copa sobresale
                    int topC = c.getYPosition() - c.getHeight() * 5;
                    if (topC < yTope) yTope = topC;
                } else {
                    yActual = yTope;
                    c.setInside(false);
                    ultimaExterna = c;
                    yTope = getTop(c);
                }
                c.setPosition(baseX, yActual);
            }
    
            if (isVisible) {
                c.makeVisible();
                if (c.hasLidOn()) c.showLid();
            }
            anterior = c;
        }
    
        if (isVisible) {
            for (Cup c : cups) {
                if (c.hasLidOn()) c.showLid();
            }
        }
    }

    /**
     * Dibuja la regla guía de la tore
     */
    public void drawRule()
    {
        int escala = 5;

        for (int i = 0; i <= maxHeight; i++) {
            Rectangle r = new Rectangle();
            if (i % 5 == 0) {
                r.changeSize(2, 20);
            } else {
                r.changeSize(2, 10);
            }
            r.moveHorizontal(baseX - 200);
            r.moveVertical(baseY - (i * escala));
            r.changeColor("black");
            r.makeVisible();
        }
    }
    
    private Cup getHighestAmong(Cup limite) {
        Cup masAlta = null;
        for (Cup c : cups) {
            if (c == limite) break;
            if (c.isInside()) continue; // ignorar copas internas
            if (masAlta == null || getTop(c) < getTop(masAlta)) masAlta = c;
        }
        return masAlta;
    }
    
    
    
    /**
     * Obtiene la altura de la torre
     */
    public int getHeight() {
        int total = 0;
        for (Cup c : cups){
            if (!c.isInside()) {
                total += c.getHeight();
                if (c.hasLidOn()) total += 1;
            }
        }
        return total;
    }
    
    
    /**
     * Retorna los números de las tazas tapadas, ordenados de menor a mayor.
     * @return Arreglo de números de tazas tapadas
     */
    public int[] lidedCups()
    {
        ArrayList<Integer> tapadas = new ArrayList<>();
        for (Cup c : cups) {
            if (c.hasLidOn()) tapadas.add(c.getNumber());
        }
        Collections.sort(tapadas);
        return tapadas.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Retorna la información de todos los elementos apilados de base a cima.
     * Formato: {{"cup","4"},{"lid","4"}, ...}
     * @return Matriz con tipo y número de cada elemento
     */
    public String[][] stackingItems()
    {
        ArrayList<String[]> items = new ArrayList<>();
        for (Cup c : cups) {
            items.add(new String[]{"cup", String.valueOf(c.getNumber())});
            if (c.hasLidOn()) {
                items.add(new String[]{"lid", String.valueOf(c.getNumber())});
            }
        }
        return items.toArray(new String[0][]);
    }
    
    /**
     * Oderna la torre de menor a mayor (de arriba a abajo en este caso)
     */
   public void orderTower()
    {
        ArrayList<Cup> lista = new ArrayList<>(cups);
        Collections.sort(lista, (c1, c2) -> c2.getNumber() - c1.getNumber());
        cups.clear();
        int altura = 0;
        for (Cup c : lista) {
            int alturaConTapa = c.getHeight() + (c.hasLidOn() ? 1 : 0);
            if (altura + alturaConTapa <= maxHeight) {
                cups.push(c);
                altura += alturaConTapa;
            }
        }
        redraw();
        isOK = true;
    }
    
    /**
     * Coloca la torre al revés de como se encuentra
     */
    public void reverseTower()
    {
        ArrayList<Cup> lista = new ArrayList<>(cups);
        Collections.reverse(lista);
        cups.clear();
        int altura = 0;
        for (Cup c : lista) {
            int alturaConTapa = c.getHeight() + (c.hasLidOn() ? 1 : 0);
            if (altura + alturaConTapa <= maxHeight) {
                cups.push(c);
                altura += alturaConTapa;
            }
        }
        redraw();
        isOK = true;
    }
    
    
    
    public void swap(String[] o1, String[] o2)
    {
        int num1 = Integer.parseInt(o1[1]);
        int num2 = Integer.parseInt(o2[1]);

        ArrayList<Cup> lista = new ArrayList<>(cups);
        int idx1 = -1, idx2 = -1;

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getNumber() == num1) idx1 = i;
            if (lista.get(i).getNumber() == num2) idx2 = i;
        }

        if (idx1 == -1 || idx2 == -1) {
            isOK = false;
            JOptionPane.showMessageDialog(null,"Uno o ambos objetos no existen en la torre");
            return;
        }
        if (idx1 == idx2) {
            isOK = false;
            JOptionPane.showMessageDialog(null,"No se puede intercambiar un objeto consigo mismo");
            return;
        }

        Cup temp = lista.get(idx1);
        lista.set(idx1, lista.get(idx2));
        lista.set(idx2, temp);

        cups.clear();
        for (Cup c : lista) cups.push(c);

        redraw();
        isOK = true;
    }
    
    
    /**
     * Tapa todas las tazas de la torre que aún no tienen su tapa "activa" (Requisito 12, parte del Ciclo 2)
     */
    public void cover()
    {
        for (Cup c : cups) {
            if (!c.hasLidOn()) {
                c.activateLid();
                if (isVisible) 
                    c.showLid();
            }
        }
        isOK = true;
    }
    
    
    public String[][] swapToReduce() {
        int alturaActual = getHeight();
        for (int i = 0; i < cups.size(); i++) {
            for (int j = i + 1; j < cups.size(); j++) {
                Cup ci = cups.get(i);
                Cup cj = cups.get(j);
                cups.set(i, cj);
                cups.set(j, ci);
                redraw();
    
                if (getHeight() < alturaActual) {
                    // Revertir antes de retornar
                    cups.set(i, ci);
                    cups.set(j, cj);
                    redraw();
                    isOK = true;
                    return new String[][]{
                        {cj.hasLidOn() ? "lid" : "cup", String.valueOf(cj.getNumber())},
                        {ci.hasLidOn() ? "lid" : "cup", String.valueOf(ci.getNumber())}
                    };
                }
                // Revertir
                cups.set(i, ci);
                cups.set(j, cj);
                redraw();
            }
        }
        isOK = true;
        return null;
    }
    
    /**
     * Hace visible la torre
     */
    public void makeVisible()
    {
        isVisible = true;
        redraw();
        drawRule();
    }
    
    /**
     * Hace invisible la torre
     */
    public void makeInvisible()
    {
        for (Cup c : cups) {
            c.makeInvisible();
        }
        isVisible = false;
    }

    /**
     * Se comprueba si la última acción fue correcta
     */
    public boolean isOk()
    {
        return isOK;
    }

    /**
     * Se hace un "reset" de la torre
     */
    public void exit()
    {
        makeInvisible();
        cups.clear();
        isOK = true;
    }
}