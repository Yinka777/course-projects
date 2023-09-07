package steering;

import engine.Car;
import engine.Game;
import engine.GameObject;

public class Seek extends SteeringBehavior {
    GameObject E = null;
    double maxAcceleration;

    public Seek(GameObject a_E, double a_maxAcceleration) {
        E = a_E;
        maxAcceleration = a_maxAcceleration;
    }

    public Vector2 update(Game game, Car subject, double delta_t) {
        Vector2 D = new Vector2(E.getX(), E.getY());
        Vector2 car = new Vector2(subject.getX(), subject.getY());
        D = D.subtract(car);
        D = D.normalize();

        Vector2 A = D;
        A = A.multiply(maxAcceleration);
        return A;
    }
}