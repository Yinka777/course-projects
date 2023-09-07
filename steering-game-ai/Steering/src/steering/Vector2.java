package steering;

public class Vector2 {
    double x_val;
    double y_val;
    double length;

    public Vector2(double x, double y) {
        x_val = x;
        y_val = y;
        length = Math.sqrt(x_val*x_val + y_val*y_val);
    }

    public Vector2(double angle) {
        x_val = Math.cos(angle);
        y_val = Math.sin(angle);
        length = Math.sqrt(x_val*x_val + y_val*y_val);
    }

    public double dotProduct(Vector2 b) {
        return (x_val * b.x_val) + (y_val * b.y_val);
    }

    public Vector2 add(Vector2 b) {
        return new Vector2(x_val + b.x_val, y_val + b.y_val);
    }

    public Vector2 subtract(Vector2 b) {
        return new Vector2(x_val - b.x_val, y_val - b.y_val);
    }

    public Vector2 normalize() {
        Vector2 new_v = null;
        if (length != 0) {
            new_v = new Vector2(x_val / length, y_val / length);
        }
        return new_v;
    }

    public Vector2 multiply(double constant) {
        return new Vector2(x_val * constant, y_val * constant);
    }
}
