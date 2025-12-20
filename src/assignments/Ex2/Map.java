package assignments.Ex2;
import java.io.Serializable;
import java.util.*;

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
    private static final int BLOCK_VALUE = -1;
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
        init(100,100,DEFAULT_VALUE);
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
        _mapArray = new int[h][w];
        for(int i = 0; i < h; i++){
            for(int j = 0; j < w; j++){
                _mapArray[j][i] = v;
            }
        }
	}
	@Override
	public void init(int[][] arr) {
        if (arr == null){
            throw new NullPointerException("Array is null");
        }
        try {
            _w = arr[0].length;
            _h = arr.length;
            _v = DEFAULT_VALUE;

            _mapArray = new int[_h][_w];
            for(int i = 0; i < _h; i++){
                for(int j = 0; j < _w; j++){
                    _mapArray[i][j] = arr[i][j];
                }
            }
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
        _mapArray[p.getX()][p.getY()] = v;
	}

    @Override
    public boolean isInside(Pixel2D p) {
        try{
            int _ = _mapArray[p.getY()][p.getX()];
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
                    _mapArray[i][j] = p.getPixel(i, j);
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
        //Define the Bounding Box
        int minX = (int) Math.max(0, Math.floor(center.getX() - rad));
        int maxX = (int) Math.min(_mapArray[0].length - 1, Math.ceil(center.getX() + rad));
        int minY = (int) Math.max(0, Math.floor(center.getY() - rad));
        int maxY = (int) Math.min(_mapArray.length - 1, Math.ceil(center.getY() + rad));

        for (int i = minY; i <= maxY; i++) {
            for (int j = minX; j <= maxX; j++) {
                double dx = center.getX() - (j + 0.5);
                double dy = center.getY() - (i + 0.5);
                // 3. Distance Check
                if ((dx * dx + dy * dy) <= (rad * rad)) {
                    _mapArray[i][j] = color;
                }
            }
        }
    }

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        int m;
        if ((p2.getX()-p1.getX()) == 0){m=0;}
        else {
            m = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        }
        int b = p1.getY() - (m * p1.getX());
        for (int x = p1.getX(); x <= p2.getY(); x++) {
            int y = m*x + b;
            this._mapArray[y][x] = color;
        }
    }

    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        for  (int i=0; i<_mapArray.length; i++){
            for (int j=0; j<_mapArray[i].length; j++){
                if (j >= p1.getX() && j <= p2.getX() && i >= p1.getY() && i <= p2.getY()){
                    _mapArray[i][j] = color;
                }
            }
        }
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) {return true;}
        if (!(ob instanceof Map)) {return false;}

        Map m = (Map)ob;
        int [][] mArray = m.getMap();

        for (int i=0; i<_mapArray.length; i++){
            for (int j=0; j<_mapArray[i].length; j++){
                if (_mapArray[i][j] != mArray[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
		int ans = 1;
        int x = xy.getX();
        int y = xy.getY();
        int cur_v = _mapArray[y][x];
        if (new_v != BLOCK_VALUE){
            setPixel(xy, new_v);
        }
        boolean [][] visited = new boolean[getWidth()][getHeight()];
        for (boolean [] row: visited){
            Arrays.fill(row, false);
        }
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(x, y,null));
        visited[y][y] = true;

        int [][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!q.isEmpty()) {
            Node curr = q.poll();

            for (int [] dir: dirs){
                int new_x = curr.x + dir[0];
                int new_y = curr.y + dir[1];

                if(new_x >= 0 && new_y >= 0 && new_y < getWidth() && new_x < getHeight() &&
                        !visited[new_x][new_y] && _mapArray[new_x][new_y] == cur_v &&
                        _mapArray[new_x][new_y] != BLOCK_VALUE){
                    _mapArray[new_x][new_y] = new_v;
                    visited[new_x][new_y] = true;
                    ans++;
                    q.add(new Node(new_x, new_y,null));
                }
            }
        }
		return ans;
	}

	@Override
	/**
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
		Pixel2D[] ans = null;  // the result.

        boolean [][] visited = new boolean[getWidth()][getHeight()];
        for (boolean [] row: visited){
            Arrays.fill(row, false);
        }
        Queue<Node> q = new LinkedList<>();
        q.add(new Node(p1.getX(), p1.getY(),null));
        visited[p1.getX()][p1.getY()] = true;

        int [][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
        while (!q.isEmpty()) {
            Node curr = q.poll();

            if(curr.x ==  p2.getX() && curr.y == p2.getY()){
                return createPath(curr);
            }

            for(int[] d: dirs){
                int nx = curr.x + d[0];
                int ny = curr.y + d[1];

                if (nx >= 0 && ny >= 0 && ny < getWidth() && nx < getHeight() && !visited[nx][ny] &&
                        _mapArray[nx][ny] != obsColor) {
                    visited[nx][ny] = true;
                    q.add(new Node(nx,ny,curr));
                }
            }
        }
		return ans;
	}

    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
        Map2D ans = null;  // the result.

        return ans;
    }
	////////////////////// Private Methods ///////////////////////

    static class Node{
        int x, y;
        Node parent;
        Node(int x, int y, Node parent){
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    private static Pixel2D[] createPath(Node node){
        ArrayList<Pixel2D> ans = new ArrayList<>();
        while(node != null){
            ans.add(new Index2D(node.x, node.y));
            node = node.parent;
        }
        Collections.reverse(ans);

        return ans.toArray(new Pixel2D[0]);
    }
}
