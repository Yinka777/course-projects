package steering;

import engine.Car;
import engine.Game;
import engine.Marker;

public class Arrive extends SteeringBehavior {
    Marker E = null;
    double maxAcceleration;

    public Arrive(Marker a_E, double a_maxAcceleration) {
        E = a_E;
        maxAcceleration = a_maxAcceleration;
    }

    public void setE(Marker a_E) {
        E = a_E;
    }

    public Vector2 update(Game game, Car subject, double delta_t) {
        Vector2 D = new Vector2(E.getX(), E.getY());
        Vector2 car_position = new Vector2(subject.getX(), subject.getY());
        D = D.subtract(car_position);
        double distance = D.length;

        double car_vx = (subject.getSpeed()/distance)*(E.getX()-subject.getX());
        double car_vy = (subject.getSpeed()/distance)*(E.getY()-subject.getY());
        Vector2 car_velocity = new Vector2(car_vx, car_vy);

        double slowRadius = E.getRadius()+80;
        double targetSpeed;

        if (distance < 1) {
            return new Vector2(0,0);
        }
        if (distance > slowRadius) {
            targetSpeed = subject.m_max_velocity;
        } else {
            targetSpeed = subject.m_max_velocity * (distance/slowRadius);
        }

        Vector2 targetVelocity = (D.normalize()).multiply(targetSpeed);
        Vector2 A = (targetVelocity.subtract(car_velocity)).multiply(1/delta_t);

        if (A.length>maxAcceleration) {
            A = (A.normalize()).multiply(maxAcceleration);
        }
        return A;
    }
}
