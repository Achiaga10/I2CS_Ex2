package assignments.Ex2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Index2DTest {

    final double EPS = 0.0001;

    Index2D index1 = new Index2D(10,15);
    Index2D index2 = new Index2D(24,33);
    Index2D index3 = new Index2D(index2);

    @Test
    public void getXTest(){
        assertEquals(10 ,index1.getX());
    }
    @Test
    public void getYTest(){
        assertEquals(15 ,index1.getY());
    }
    @Test
    public void distance2DTest(){
        assertEquals(index1.distance2D(index2), 22.8035, EPS);
    }
    @Test
    public void toStringTest(){
        assertEquals(index1.toString(),"x: "+index1.getX()+", y: "+index1.getY());
    }
    @Test
    public void equalsTest(){
        assertFalse(index1.equals(null));
        assertFalse(index1.equals(index2));
        assertTrue(index2.equals(index3));
    }
}
