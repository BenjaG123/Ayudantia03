import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class JuegoMatrizTest {


    @Test
    public void testPosicionInicial() {
        String[][] mapa = new String[10][10];
        int[] personaje = {1,1,100,15};
        int[][] enemigos = {{3, 3, 45, 10}, {6, 6, 45, 10}, {8, 2, 45, 10}};
        int[][] cofres = {{2, 3}, {7, 5}};
        mapa = JuegoMatriz.inicializarMapa(mapa, personaje, enemigos, cofres);
        assertEquals("P", mapa[1][1]);
        assertEquals("X", mapa[8][8]);
        assertEquals("E", mapa[3][3]);
    }

    @Test
    public void testMoverPersonaje() {
        String[][] mapa = new String[10][10];
        int[] personaje = {1,1,100,15};
        int[][] enemigos = {{3, 3, 45, 10}, {6, 6, 45, 10}, {8, 2, 45, 10}};
        int[][] cofres = {{2, 3}, {7, 5}};
        JuegoMatriz.inicializarMapa(mapa, personaje, enemigos, cofres);
        JuegoMatriz.moverPersonaje('S',mapa, personaje);
        assertEquals(2, personaje[0]);
        assertEquals(1, personaje[1]);
        assertEquals("P", mapa[2][1]);
    }

    @Test
    public void testMovimientoValido() {
        String[][] mapa = new String[10][10];
        int[] personaje = {1,1,100,15};
        int[][] enemigos = {{3, 3, 45, 10}, {6, 6, 45, 10}, {8, 2, 45, 10}};
        int[][] cofres = {{2, 3}, {7, 5}};
        JuegoMatriz.inicializarMapa(mapa, personaje, enemigos, cofres);
        assertTrue(JuegoMatriz.esMovimientoValido(4,4,mapa));
        mapa[4][4] = "#";
        assertFalse(JuegoMatriz.esMovimientoValido(4,4,mapa));
    }

    @Test
    public void testCombate(){
        String[][] mapa = new String[10][10];
        int[] personaje = {1,1,100,15};
        int[][] enemigos = {{3, 3, 45, 10}, {6, 6, 45, 10}, {8, 2, 45, 10}};
        int[][] cofres = {{2, 3}, {7, 5}};
        JuegoMatriz.inicializarMapa(mapa, personaje, enemigos, cofres);
        JuegoMatriz.combate(personaje, enemigos, mapa);
        assertTrue(personaje[2] <= 100);

    }


}