import java.util.Random;
import java.util.Scanner;

public class JuegoMatriz {

    public static void main(String[] args) {
        String[][] mapa = new String[10][10];
        int[] personaje = {1, 1, 100, 15};
        int[][] enemigos = {{3, 3, 45, 10}, {6, 6, 45, 10}, {8,2,45,10}};
        int[][] cofres = {{2,3},{7,5}};
        inicializarMapa(mapa, personaje, enemigos,cofres);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            mostrarMapa(mapa, personaje);
            if (verificarVictoria(personaje)) {
                System.out.println("¡Has ganado!");
                break;

            }
            if (personaje[2] <= 0) {
                System.out.println("¡Has perdido!");
                break;
            }

            System.out.print("Ingresa tu movimiento (W, A, S, D): ");
            char movimiento = scanner.next().toUpperCase().charAt(0);
            moverPersonaje(movimiento, mapa, personaje);

            if (mapa[personaje[0]][personaje[1]].equals(mapa[enemigos[0][0]][enemigos[0][1]]) || mapa[personaje[0]][personaje[1]].equals(mapa[enemigos[1][0]][enemigos[1][1]]) || mapa[personaje[0]][personaje[1]].equals(mapa[enemigos[2][0]][enemigos[2][1]])) {
                combate(personaje, enemigos, mapa);
            } else if (mapa[personaje[0]][personaje[1]].equals(mapa[cofres[0][0]][cofres[0][1]])|| mapa[personaje[0]][personaje[1]].equals(mapa[cofres[1][0]][cofres[1][1]])) {
                manejarCofre(personaje, mapa, cofres);
            }
        }

        scanner.close();
    }

    public static String[][] inicializarMapa(String[][] mapa, int[] personaje, int[][] enemigos, int[][] cofres) {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[0].length; j++) {
                if (i == 0 || i == mapa.length - 1 || j == 0 || j == mapa[0].length - 1) {
                    mapa[i][j] = "#";
                } else {
                    mapa[i][j] = ".";
                }
            }
        }

        mapa[personaje[0]][personaje[1]] = "P";
        mapa[mapa.length - 2][mapa[0].length - 2] = "X";

        for (int[] enemigo : enemigos) {
            mapa[enemigo[0]][enemigo[1]] = "E";
        }

        for (int[] cofre : cofres) {
            mapa[cofre[0]][cofre[1]] = "C";
        }
        colocarObstaculos(mapa);


        return mapa;
    }


    public static void colocarObstaculos(String[][] mapa) {
        Random rand = new Random();
        int obstaculos = 0;
        while (obstaculos < 10) {
            int x = rand.nextInt(mapa.length - 2) + 1;
            int y = rand.nextInt(mapa[0].length - 2) + 1;
            if (mapa[x][y].equals(".")) {
                mapa[x][y] = "#";
                obstaculos++;
            }
        }
    }

    public static void mostrarMapa(String[][] mapa, int[] personaje) {
        for (String[] fila : mapa) {
            for (String celda : fila) {
                System.out.print(celda + " ");
            }
            System.out.println();
        }
        System.out.println("Vida del jugador: " + personaje[2]);
    }

    public static String[][] moverPersonaje(char movimiento, String[][] mapa, int[] personaje) {
        int nuevaX = personaje[0];
        int nuevaY = personaje[1];

        switch (movimiento) {
            case 'W': nuevaX--; break;
            case 'S': nuevaX++; break;
            case 'A': nuevaY--; break;
            case 'D': nuevaY++; break;
            default: return mapa;
        }

        if (esMovimientoValido(nuevaX, nuevaY, mapa)) {
            mapa[personaje[0]][personaje[1]] = ".";
            personaje[0] = nuevaX;
            personaje[1] = nuevaY;
            mapa[nuevaX][nuevaY] = "P";
        }

        return mapa;
    }

    public static boolean esMovimientoValido(int x, int y, String[][] mapa) {
        return x >= 1 && x < mapa.length - 1 && y >= 1 && y < mapa[0].length - 1 && !mapa[x][y].equals("#");
    }

    public static String[][] combate(int[] personaje, int[][] enemigos, String[][] mapa) {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < enemigos.length; i++) {
            if (personaje[0] == enemigos[i][0] && personaje[1] == enemigos[i][1]) {
                System.out.println("¡Encuentras un enemigo!");
                while (personaje[2] > 0 && enemigos[i][2] > 0) {
                    System.out.println("Elige acción: 1) Atacar 2) Huir");

                    int accion = 0;
                    boolean entradaValida = false;

                    while (!entradaValida) {
                        try {
                            accion = Integer.parseInt(scanner.nextLine());
                            if (accion == 1 || accion == 2) {
                                entradaValida = true;
                            } else {
                                System.out.println("Opción inválida. Ingresa 1 para Atacar o 2 para Huir.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Por favor ingresa un número.");
                        }
                    }

                    if (accion == 1) {
                        enemigos[i][2] -= personaje[3];
                        System.out.println("Le hiciste daño al enemigo!");
                        System.out.println("Al enemigo le quedan " + enemigos[i][2] + " puntos de vida.");
                        if (enemigos[i][2] <= 0) {
                            mapa[enemigos[i][0]][enemigos[i][1]] = ".";
                            System.out.println("Has derrotado al enemigo!");
                            break;
                        }
                        personaje[2] -= enemigos[i][3];
                        if (personaje[2] <= 0) {
                            System.out.println("Has sido derrotado por el enemigo.");
                            return mapa;
                        }
                    } else if (accion == 2) {
                        System.out.println("Has huido del combate.");
                        break;
                    }
                }
            }
        }
        return mapa;
    }


    public static String[][] manejarCofre(int[] personaje, String[][] mapa, int[][]cofres) {
        Random rand = new Random();
        int evento = rand.nextInt(2);

        if (evento == 0) {
            personaje[2] += 20;
            System.out.println("¡Encontraste un cofre! Has ganado 20 puntos de vida.");
        } else {
            personaje[2] -= 20;
            System.out.println("¡Encontraste una trampa! Has perdido 20 puntos de vida.");
        }

        mapa[personaje[0]][personaje[1]] = ".";
        return mapa;
    }


    public static boolean verificarVictoria(int[] personaje) {
        return personaje[0] == 8 && personaje[1] == 8;
    }
}
