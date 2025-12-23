package assignments.Ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}, {0,1,0}};
    private int[][] _map_3_4 = {{0,0,0}, {0,0,0}, {0,0,0}, {0,0,0}};
    private Map2D _m0, _m1, _m3_3, _m4;
    private Map m = new Map(_map_3_3);
    @BeforeEach
    public void setup() {
        _m3_3 = new Map(_map_3_3);
        _m0 = new Map();
        _m1 = new Map();
        _m4 = new Map(_map_3_4);
    }

    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }

    @Test
    void testGetMap() {
        Map m1212 = new Map(_map_3_3);
        assertArrayEquals(_map_3_3, m1212.getMap(), "The 2D arrays are equal");
    }

    @Test
    void testGetWidth() {
        assertTrue (m.getWidth() == _map_3_3[0].length);
    }

    @Test
    void testGetHeight() {
        assertTrue (m.getHeight() == _map_3_3.length);
    }

    @Test
    void testGetPixel() {
        Index2D p1 = new Index2D(3,2);
        Index2D p2 = new Index2D(p1);
        m.setPixel(p1, 1);
        _m1.setPixel(p1, 1);
        assertTrue (m.getPixel(p1) == _m1.getPixel(p1));
        assertTrue (m.getPixel(p1.getX(),p1.getY()) == _m1.getPixel(p1));
    }

    @Test
    void setPixel() {
        Index2D p1 = new Index2D(3,2);
        Index2D p2 = new Index2D(p1);
        m.setPixel(p1, 1);
        _m1.setPixel(p2, 1);
        assertTrue (m.getPixel(p1) == _m1.getPixel(p2));
    }

    @Test
    void testSetPixel() {
        m.setPixel(3,2, 1);
        _m1.setPixel(3,2, 1);
        assertTrue (m.getPixel(3,2) == _m1.getPixel(3,2));
    }

    @Test
    void testIsInside() {
        assertFalse(m.isInside(new Index2D(m.getWidth()+1,m.getHeight()+1)));
        assertTrue(m.isInside(new Index2D(m.getWidth()-1,m.getHeight()-1)));
    }

    @Test
    void testSameDimensions() {
        assertTrue(m.sameDimensions(_m3_3));
        assertFalse(m.sameDimensions(_m0));
    }

    @Test
    void testAddMap2D() {
        _m4.addMap2D(m);
        assertArrayEquals(m.getMap(), _m4.getMap(), "The 2D arrays are equal");
        assertNotEquals(_map_3_4, _m4.getMap());
    }

    @Test
    void testMul() {
        m.mul(0);
        assertArrayEquals(m.getMap(), _map_3_4, "The 2D arrays are equal");
        assertNotEquals(m.getMap(), _map_3_3, "The 2D arrays are NOT equal");
    }

    @Test
    void testRescale() {
        Map2D rescaled = new Map(new int[][]{{1,1},{0,0}});
        Map2D result = new Map(new int[][]{{1,1,1,1},{1,1,1,1},{0,0,0,0},{0,0,0,0}});
        rescaled.rescale(2.0,2.0);
        assertTrue(rescaled.equals(result));

    }

    @Test
    void testDrawCircle() {
        Pixel2D p1 = new Index2D(20,20);
        int rad = 3;
        int color = 2;
        boolean ans = true;
        _m0.drawCircle(p1, rad, color);
        for (int i = 0; i < _m0.getMap().length; i++) {
            for (int j = 0; j < _m0.getMap()[0].length; j++) {
                double dx = p1.getX() - (j + 0.5);
                double dy = p1.getY() - (i + 0.5);
                if ((dx * dx + dy * dy) <= (rad * rad) && _m0.getMap()[i][j] != color) {
                    ans = false;
                }
            }
        }
        assertTrue(ans);

    }

    @Test
    void testDrawLine() {
        Map2D line = new Map(10);
        line.drawLine(new Index2D(2,2),new Index2D(9,9), 4);
        int [][] result = new int[][]{
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,4,0,0,0,0,0,0,0},
                {0,0,0,4,0,0,0,0,0,0},
                {0,0,0,0,4,0,0,0,0,0},
                {0,0,0,0,0,4,0,0,0,0},
                {0,0,0,0,0,0,4,0,0,0},
                {0,0,0,0,0,0,0,4,0,0},
                {0,0,0,0,0,0,0,0,4,0},
                {0,0,0,0,0,0,0,0,0,4},
        };
        assertArrayEquals(line.getMap(),result, "The 2D arrays are equal");
    }

    @Test
    void testDrawRect() {
        Map2D line = new Map(10);
        line.drawRect(new Index2D(2,2),new Index2D(9,9), 4);
        int [][] result = new int[][]{
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
                {0,0,4,4,4,4,4,4,4,4},
        };
        assertArrayEquals(line.getMap(),result, "The 2D arrays are equal");
    }

    @Test
    void testEquals1() {
        assertTrue(_m0.equals(_m1));
        assertFalse(_m0.equals(_m3_3));

    }

    @Test
    void testFill() {
        assertTrue(false);

    }

    @Test
    void testShortestPath() {
        assertTrue(false);

    }

    @Test
    void testAllDistance() {
        assertTrue(false);

    }
}