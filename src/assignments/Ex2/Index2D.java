package assignments.Ex2;

public class Index2D implements Pixel2D {
    private int _x;
    private int _y;

    public Index2D(int w, int h) {
        _x=w;
        _y=h;
    }
    public Index2D(Pixel2D other) {
        this._x = other.getX();
        this._y = other.getY();
    }
    @Override
    public int getX() {
        return this._x;
    }

    @Override
    public int getY() {
        return this._y;
    }

    @Override
    public double distance2D(Pixel2D p2) {
        if (p2 == null) {
            throw new RuntimeException("Cannot calculate distance to a null Pixel2D object.");
        }

        double dx = this._x - p2.getX();
        double dy = this._y - p2.getY();

        return Math.hypot(dx, dy);
    }

    @Override
    public String toString() {
        String ans = "Pixel: (" + this._x + ", " + this._y + ")";
        return ans;
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (!(p instanceof Pixel2D)) {
            return false;
        }
        Pixel2D p1 = (Pixel2D) p;
        if  (this._x == p1.getX() && this._y == p1.getY()) {
            return true;
        }
        return false;
    }
}
