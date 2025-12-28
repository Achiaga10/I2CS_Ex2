package assignments.Ex2;
import classes.week4.StdDraw;

import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import java.awt.Color;

/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 * project: https://docs.google.com/document/d/1BtSldHciAGqjccYC3d7BKvWqIljfxKeJ/edit
 *
 */

public class Ex2_GUI {

    public final static String TOP_LABEL = "S: Shortest Path | A: All Distances | M: Generate Maze | R: Reset Map";
    public static boolean cyclic = false;
    public static int size = 20;

    public static void drawMap(Map2D map, String label) {
        int w = map.getWidth();
        int h = map.getHeight();
        int labelSpace = 3; // space reserved at top for labels

        StdDraw.setXscale(-0.5, w - 0.5);
        StdDraw.setYscale(h + labelSpace - 0.5, -0.5); // still flip Y

        StdDraw.clear();

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(w / 2.1, 0.5, label); // top label

        // Second label (cyclic)
        StdDraw.text(w / 2.0, 1.5, "Press Q: Save map| L: load map| Cyclic: " + cyclic + " - Press C to toggle");

        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int v = map.getMap()[y][x];

                StdDraw.setPenColor(colorForValue(v));
                StdDraw.filledSquare(x, y + labelSpace, 0.5); // shift Y by labelSpace

                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.text(x, y + labelSpace, String.valueOf(v));
            }
        }

        StdDraw.show();
    }



    /**
     * Load a .txt file
     * @param mapFileName
     * @return new Map with the loaded matrix.
     */
    public static Map2D loadMap(String mapFileName) {
        int lineCount = 0;
        Path path = Paths.get(mapFileName);
        try (Stream<String> lines = Files.lines(path)) {
            lineCount = (int)lines.count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int [][] mapArray = new int[lineCount][];
        int counter = 0;
        try(BufferedReader reader = new BufferedReader(new FileReader(mapFileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int[] intArray = Arrays.stream(line.split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
                mapArray[counter] = intArray;
                counter++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Map(mapArray);
    }

    /**
     * Save the matrix map as a txt file
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        int [][] array = map.getMap();
        String delimiter = ",";
        try (
                PrintWriter writer = new PrintWriter(new FileWriter(mapFileName));
        ) {
            for (int i = 0; i < array.length; i++) {
                StringBuilder rowString = new StringBuilder();

                for (int j = 0; j < array[i].length; j++) {
                    rowString.append(array[i][j]);

                    if (j < array[i].length - 1) {
                        rowString.append(delimiter);
                    }
                }
                writer.println(rowString.toString());
            }
            System.out.println("Successfully saved 2D array to: " + mapFileName);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void main(String[] a) {

        Map2D map = new Map(size); // create your map
        StdDraw.enableDoubleBuffering();


        while (true) {
            StdDraw.clear();
            drawMap(map, TOP_LABEL);

            if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                Pixel2D p1 = randomValidPixel(map.getMap());
                Pixel2D p2 = randomValidPixel(map.getMap());
                Pixel2D[] p = map.shortestPath(p1, p2, Map.BLOCK_VALUE, cyclic);
                printShortestPath(p, map);
                map.setPixel(p1, 1);
                map.setPixel(p2, 1);
                drawMap(map, TOP_LABEL);

                while (StdDraw.isKeyPressed(KeyEvent.VK_S)) StdDraw.pause(20);
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_A)) {
                Pixel2D p = randomValidPixel(map.getMap());
                map.setPixel(p,0);
                System.out.println(map.getPixel(p));
                map = map.allDistance(p, -1, cyclic);

                drawMap(map, TOP_LABEL);
                while (StdDraw.isKeyPressed(KeyEvent.VK_A)) StdDraw.pause(20);
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_M)) {
                map.mul(0);
                mazeGenerator(map);

                StdDraw.pause(20);
                drawMap(map, TOP_LABEL);

                while (StdDraw.isKeyPressed(KeyEvent.VK_M)) StdDraw.pause(20);
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_R)) {
                map.mul(0);

                StdDraw.pause(20);
                drawMap(map, TOP_LABEL);

                while (StdDraw.isKeyPressed(KeyEvent.VK_R)) StdDraw.pause(20);
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_C)) {
                cyclic = !cyclic; // toggle
                drawMap(map, TOP_LABEL);

                while (StdDraw.isKeyPressed(KeyEvent.VK_C)) {
                    StdDraw.pause(20);
                }
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_L)) {
                cyclic = !cyclic; // toggle
                try{
                    map = loadMap("mapfile.txt");
                    drawMap(map , TOP_LABEL);
                }catch(Exception e){
                    System.out.println("An error occurred while loading the map: " + e.getMessage());
                }

                while (StdDraw.isKeyPressed(KeyEvent.VK_L)) {
                    StdDraw.pause(20);
                }
            }
            else if (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                cyclic = !cyclic; // toggle
                try{
                    saveMap(map,"mapfile.txt");
                }catch(Exception e){
                    System.out.println("An error occurred while Saving the map: " + e.getMessage());
                }

                while (StdDraw.isKeyPressed(KeyEvent.VK_Q)) {
                    StdDraw.pause(20);
                }
            }

            StdDraw.show();
            StdDraw.pause(20);
        }

    }




    /// ///////////// Private functions ///////////////
    /**
     * This method prints the Pixels values of the shortestPath
     * @param map Map2d object
     * @param p2d Pixel2d object array
     * */
    private static void printShortestPath(Pixel2D [] p2d, Map2D map) {
        try {
            for (Pixel2D p: p2d) {
//            System.out.println(p.toString());
//            System.out.print("new Index2D("+p.getX()+","+p.getY()+"), ");

                map.setPixel(p,2);
            }
        }catch (Exception e){
            System.out.println("No Path was found!");
        }

    }


    private static void mazeGenerator(Map2D m) {
        int size = m.getHeight();
        int couplesCount = size / 2;

        int index = 0;

        for (int i = 0; i < couplesCount; i++) {
            boolean sameX = ThreadLocalRandom.current().nextBoolean();

            int x1, y1, x2, y2;

            if (sameX) {
                x1 = x2 = ThreadLocalRandom.current().nextInt(size);
                y1 = ThreadLocalRandom.current().nextInt(size);
                do {
                    y2 = ThreadLocalRandom.current().nextInt(size);
                } while (y2 == y1);
            } else {
                y1 = y2 = ThreadLocalRandom.current().nextInt(size);
                x1 = ThreadLocalRandom.current().nextInt(size);
                do {
                    x2 = ThreadLocalRandom.current().nextInt(size);
                } while (x2 == x1);
            }

            m.drawLine(new Index2D(x1, y1), new Index2D(x2, y2), Map.BLOCK_VALUE);
        }
    }

    public static Pixel2D randomValidPixel(int[][] mat) {
        int h = mat.length;
        int w = mat[0].length;

        while (true) {
            int x = ThreadLocalRandom.current().nextInt(w);
            int y = ThreadLocalRandom.current().nextInt(h);

            if (mat[y][x] != -1) {
                System.out.println(y+","+x);
                return new Index2D(x, y);
            }
        }
    }

    private static Color colorForValue(int v) {
        if (v == -1) return Color.BLACK;
        else if (v == 0) return Color.GRAY;
        else if (v == 1) return Color.BLUE;

        // Map value to hue [0,1)
        float hue = (v * 0.61803398875f) % 1; // golden ratio for good spread
        float saturation = 0.6f;
        float brightness = 0.9f;

        return Color.getHSBColor(hue, saturation, brightness);
    }

}
