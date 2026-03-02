import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas de unidad para Tower - Ciclo 2.
 * Todas las pruebas corren en modo invisible.
 *
 * @author Yazid Sánchez - Sergio Ramírez
 * @version 2.0
 */
public class TowerC2Test
{
    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(50);
    }

    // -----------------------------------------------------------------------
    // Requisito 10: Tower(numCups, ignored)
    // -----------------------------------------------------------------------

    @Test
    public void accordingRSShouldCreateTowerWithCorrectNumberOfCups() {
        Tower t = new Tower(4, 0);
        assertEquals(28, t.getHeight());
        assertTrue(t.isOk());
    }

    @Test
    public void accordingRSShouldCreateTowerWithOddSizes() {
        Tower t = new Tower(3, 0);
        String[][] items = t.stackingItems();
        assertEquals("1", items[0][1]);
        assertEquals("3", items[1][1]);
        assertEquals("5", items[2][1]);
    }

    @Test
    public void accordingRSShouldCreateTowerWithNoLids() {
        Tower t = new Tower(3, 0);
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void accordingRSShouldNotCreateTowerWithZeroCups() {
        Tower t = new Tower(0, 0);
        assertEquals(0, t.getHeight());
    }

    @Test
    public void accordingRSShouldCreateTowerWithOneCup() {
        Tower t = new Tower(1, 0);
        String[][] items = t.stackingItems();
        assertEquals(1, items.length);
        assertEquals("1", items[0][1]);
    }

    @Test
    public void accordingRSShouldCreateCorrectNumberOfStackingItems() {
        Tower t = new Tower(4, 0);
        // 4 copas, sin tapas → 4 items
        assertEquals(4, t.stackingItems().length);
    }

    // -----------------------------------------------------------------------
    // Requisito 11: swap
    // -----------------------------------------------------------------------

    @Test
    public void accordingRSShouldSwapTwoCups() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        assertTrue(tower.isOk());
        String[][] items = tower.stackingItems();
        assertEquals("5", items[0][1]);
        assertEquals("3", items[1][1]);
    }

    @Test
    public void accordingRSShouldSwapReducesHeight() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        int antes = tower.getHeight();
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        assertTrue(tower.getHeight() < antes);
    }

    @Test
    public void accordingRSShouldSwapTwiceReturnsToOriginalOrder() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        String[][] original = tower.stackingItems();
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        String[][] actual = tower.stackingItems();
        assertEquals(original[0][1], actual[0][1]);
        assertEquals(original[1][1], actual[1][1]);
    }

    @Test
    public void accordingRSShouldSwapPreservesAllCups() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.pushCup(7, "green");
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","7"});
        assertEquals(3, tower.stackingItems().length);
    }

    @Test
    public void accordingRSShouldSwapPreservesLidState() {
        tower.pushCup(3, "red");
        tower.pushLid();
        tower.pushCup(5, "blue");
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        int[] tapadas = tower.lidedCups();
        assertEquals(1, tapadas.length);
        assertEquals(3, tapadas[0]);
    }

    @Test
    public void accordingRSShouldSwapCupAndLid() {
        tower.pushCup(3, "red");
        tower.pushLid();
        tower.pushCup(5, "blue");
        tower.swap(new String[]{"cup","5"}, new String[]{"lid","3"});
        assertTrue(tower.isOk());
    }

    @Test
    public void accordingRSShouldNotSwapNonExistentCup() {
        tower.pushCup(3, "red");
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","99"});
        assertFalse(tower.isOk());
    }

    @Test
    public void accordingRSShouldNotSwapCupWithItself() {
        tower.pushCup(3, "red");
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","3"});
        assertFalse(tower.isOk());
    }

    @Test
    public void accordingRSShouldNotSwapOnEmptyTower() {
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        assertFalse(tower.isOk());
    }

    @Test
    public void accordingRSShouldSwapThreeCupsAndVerifyOrder() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.pushCup(7, "green");
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","7"});
        String[][] items = tower.stackingItems();
        assertEquals("7", items[0][1]);
        assertEquals("5", items[1][1]);
        assertEquals("3", items[2][1]);
    }

    // -----------------------------------------------------------------------
    // Requisito 12: cover
    // -----------------------------------------------------------------------

    @Test
    public void accordingRSShouldCoverAllCupsWithoutLid() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        assertTrue(tower.isOk());
        assertEquals(2, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldCoverOnlyUncoveredCups() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.pushLid();
        tower.cover();
        assertEquals(2, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldNotDuplicateLidsIfAlreadyCovered() {
        tower.pushCup(3, "red");
        tower.pushLid();
        tower.cover();
        assertEquals(1, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldCoverEmptyTowerGracefully() {
        tower.cover();
        assertTrue(tower.isOk());
        assertEquals(0, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldCoverMaintainsCorrectLidNumbers() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.pushCup(7, "green");
        tower.cover();
        int[] tapadas = tower.lidedCups();
        assertEquals(3, tapadas[0]);
        assertEquals(5, tapadas[1]);
        assertEquals(7, tapadas[2]);
    }

    @Test
    public void accordingRSShouldCoverThenPopLidReducesCount() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        tower.popLid();
        assertEquals(1, tower.lidedCups().length);
        assertEquals(3, tower.lidedCups()[0]);
    }

    @Test
    public void accordingRSShouldCoverAfterRemovingAllLids() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        tower.removeLid("red");
        tower.removeLid("blue");
        tower.cover();
        assertEquals(2, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldCoverStackingItemsIncludeLids() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        String[][] items = tower.stackingItems();
        // 2 copas + 2 tapas = 4 items
        assertEquals(4, items.length);
    }

    // -----------------------------------------------------------------------
    // Requisito 13: swapToReduce
    // -----------------------------------------------------------------------

    @Test
    public void accordingRSShouldReturnSwapThatReducesHeight() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        String[][] resultado = tower.swapToReduce();
        assertTrue(tower.isOk());
        assertNotNull(resultado);
        assertEquals(2, resultado.length);
    }

    @Test
    public void accordingRSShouldReturnNullWhenNoSwapReducesHeight() {
        tower.pushCup(5, "red");
        tower.pushCup(3, "blue");
        assertNull(tower.swapToReduce());
    }

    @Test
    public void accordingRSShouldReturnNullForSingleCupTower() {
        tower.pushCup(3, "red");
        assertNull(tower.swapToReduce());
    }

    @Test
    public void accordingRSShouldReturnNullForEmptyTower() {
        assertNull(tower.swapToReduce());
    }

    @Test
    public void accordingRSShouldSwapToReduceNotModifyTower() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        int alturaBefore = tower.getHeight();
        tower.swapToReduce();
        assertEquals(alturaBefore, tower.getHeight());
    }

    @Test
    public void accordingRSShouldSwapToReduceReturnValidObjectTypes() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        String[][] resultado = tower.swapToReduce();
        assertNotNull(resultado);
        assertTrue(resultado[0][0].equals("cup") || resultado[0][0].equals("lid"));
        assertTrue(resultado[1][0].equals("cup") || resultado[1][0].equals("lid"));
    }

    @Test
    public void accordingRSShouldApplySwapToReduceAndVerifyHeightDecreases() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        int antes = tower.getHeight();
        String[][] resultado = tower.swapToReduce();
        assertNotNull(resultado);
        tower.swap(resultado[0], resultado[1]);
        assertTrue(tower.getHeight() < antes);
    }

    @Test
    public void accordingRSShouldReturnNullWhenAllCupsAreNested() {
        tower.pushCup(7, "red");
        tower.pushCup(5, "blue");
        tower.pushCup(3, "green");
        // Ya están ordenadas de mayor a menor → todo anidado → ningún swap mejora
        assertNull(tower.swapToReduce());
    }

    // -----------------------------------------------------------------------
    // Integración
    // -----------------------------------------------------------------------

    @Test
    public void accordingRSShouldPreserveCoverAfterOrderTower() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        tower.orderTower();
        assertEquals(2, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldPreserveCoverAfterReverseTower() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        tower.reverseTower();
        assertEquals(2, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldPreserveCoverAfterSwap() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        tower.cover();
        tower.swap(new String[]{"cup","3"}, new String[]{"cup","5"});
        assertEquals(2, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldCoverThenOrderMaintainsAllLids() {
        tower.pushCup(3, "red");
        tower.pushCup(7, "blue");
        tower.pushCup(5, "green");
        tower.cover();
        tower.orderTower();
        assertEquals(3, tower.lidedCups().length);
    }

    @Test
    public void accordingRSShouldConstructorTowerCoverAndVerify() {
        Tower t = new Tower(3, 0);
        t.cover();
        assertEquals(3, t.lidedCups().length);
    }

    @Test
    public void accordingRSShouldConstructorTowerSwapAndVerifyOrder() {
        Tower t = new Tower(3, 0);
        t.swap(new String[]{"cup","1"}, new String[]{"cup","5"});
        assertTrue(t.isOk());
        String[][] items = t.stackingItems();
        assertEquals("5", items[0][1]);
    }

    @Test
    public void accordingRSShouldSwapToReduceThenCoverAndVerify() {
        tower.pushCup(3, "red");
        tower.pushCup(5, "blue");
        String[][] resultado = tower.swapToReduce();
        assertNotNull(resultado);
        tower.swap(resultado[0], resultado[1]);
        tower.cover();
        assertEquals(2, tower.lidedCups().length);
    }
}






