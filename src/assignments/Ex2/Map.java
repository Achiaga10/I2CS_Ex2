package assignments.Ex2;
import java.io.Serializable;

/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{
    private int _w;
    private int _h;
    private int _v;

    private int [][] _mapArray;

    private static final int DEFAULT_VALUE = 0;
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v the init value of all the entries in the 2D array.
	 */
	public Map(int w, int h, int v) {
        init(w, h, v);
    }
	/**
	 * Constructs a square map (size*size).
	 * @param size
	 */
	public Map(int size) {
        this(size,size, 0);
    }

    public Map(){
        init(10,10,DEFAULT_VALUE);
    }
	
	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}
	@Override
	public void init(int w, int h, int v) {
        _w = w;
        _h = h;
        _v = v;
	}
	@Override
	public void init(int[][] arr) {
        if (arr == null){
            throw new NullPointerException("Array is null");
        }
        try {
            _w = arr.length;
            _h = arr[0].length;
            _v = DEFAULT_VALUE;
        } catch (Exception e) {
            //this = new Map(arr.length, arr[0].length, DEFAULT_VALUE);
            System.out.println("Error in init");
        }


	}
	@Override
	public int[][] getMap() {
        int[][] copy = new int[_mapArray.length][];

        for (int i = 0; i < _mapArray.length; i++) {
            copy[i] = new int[_mapArray[i].length];

            for (int j = 0; j < _mapArray[i].length; j++) {
                copy[i][j] = _mapArray[i][j];
            }
        }
        return copy;
	}
	@Override
	public int getWidth() {
        return _w;
    }
	@Override
	public int getHeight() {
        return _h;
    }
	@Override
	public int getPixel(int x, int y) {
        return _mapArray[x][y];
    }
	@Override
	public int getPixel(Pixel2D p) {
        return _mapArray[p.getX()][p.getY()];
	}
	@Override
	public void setPixel(int x, int y, int v) {
        _mapArray[x][y] = v;
    }
	@Override
	public void setPixel(Pixel2D p, int v) {
        _mapArray[p.getX()][p.getX()] = v;
	}

    @Override
    public boolean isInside(Pixel2D p) {
        try{
            int i = _mapArray[p.getX()][p.getY()];
            return true;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    @Override
    public boolean sameDimensions(Map2D p) {
        return _w == p.getWidth() && _h == p.getHeight();
    }

    @Override
    public void addMap2D(Map2D p) {
        if (sameDimensions(p)){
//            add all scales in p with this
//            for example i have a 4X4 with values of 1 and a 4x4 values 2
//            it sets the _mapArray to a 4x4 value of 3
            for (int i=0; i<_mapArray.length; i++){
                for (int j=0; j<_mapArray[i].length; j++){
                    _mapArray[i][j] = getPixel(i, j);
                }
            }
        }
    }

    @Override
    public void mul(double scalar) {
        for (int i=0; i<_mapArray.length; i++){
            for (int j=0; j<_mapArray[i].length; j++){
                _mapArray[i][j] = (int) (_mapArray[i][j] * scalar);
            }
        }
    }

    @Override
    public void rescale(double sx, double sy) {

    }

    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {

    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {

    }

    @Override
    public boolean equals(Object ob) {
        boolean ans = false;

        return ans;
    }
	@Override
	/** 
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = -1;

		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

		return ans;
	}
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////
}
