package assignments.Ex2;
import classes.week4.StdDraw;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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
    public static void drawMap(Map2D map) {
        StdDraw.setXscale(0, 1);
        StdDraw.clear();

        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                int v = map.getMap()[y][x];
                if (v == 0) {StdDraw.setPenColor(StdDraw.BLACK);}
                else if (v == 2) {StdDraw.setPenColor(StdDraw.BLUE);}
                else if (v == 1) {StdDraw.setPenColor(StdDraw.RED);}
                double d = Math.max(map.getHeight(), map.getWidth());
                double r = 1/(d*3);
                double x1 = 0.1+2.28*r*x;
                double y1 = 1-(0.1+2.28*r*y);

                StdDraw.filledSquare(x1, y1, r);
            }
        }
        StdDraw.show();
        StdDraw.pause(2);
    }

    /**
     * @param mapFileName
     * @return
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
        System.out.println(Arrays.deepToString(mapArray));

        return new Map(mapArray);
    }

    /**
     *
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
            System.out.println("✅ Successfully saved 2D array to: " + mapFileName);
        } catch (IOException e) {
            System.err.println("❌ An error occurred while writing to the file: " + e.getMessage());
        }
    }

    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        drawMap(map);

    }
    /// ///////////// Private functions ///////////////
}
