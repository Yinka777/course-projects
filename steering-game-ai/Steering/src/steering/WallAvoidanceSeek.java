package steering;

import engine.*;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.min;

public class WallAvoidanceSeek extends SteeringBehavior{
    GameObject E;
    double maxAcceleration;

    public WallAvoidanceSeek(GameObject a_E, double a_maxAcceleration) {
        E = a_E;
        maxAcceleration = a_maxAcceleration;
    }

    public Vector2 update(Game game, Car subject, double delta_t) {
        Vector2 D = new Vector2(E.getX(), E.getY());
        Vector2 car = new Vector2(subject.getX(), subject.getY());
        D = D.subtract(car);
        D = D.normalize();

        Vector2 seekA = D;
        //seekA = seekA.multiply(maxAcceleration);

        double car_vx = (subject.getSpeed()/D.length)*(E.getX()-subject.getX());
        double car_vy = (subject.getSpeed()/D.length)*(E.getY()-subject.getY());
        Vector2 car_velocity = new Vector2(car_vx, car_vy);

        Vector2 A = new Vector2(0,0);
        Vector2 collisionV = rayCast(game, subject,car_velocity);
        if(collisionV!=null) {
            A = collisionV.subtract(car);
            A = A.normalize().multiply(-1);
            A = A.multiply(maxAcceleration);
        }

        A = A.add(seekA);
        //A = A.normalize();
        //A = A.multiply(maxAcceleration);
        //System.out.println(A.x_val+", "+A.y_val);
        return A;
    }

    private Vector2 rayCast(Game game, Car subject, Vector2 velocity) {
        RotatedRectangle rayRect;
        Vector2 rayV;
        GameObject col_obj = null;
        boolean collision =false;
        List<Integer> col_distances = new ArrayList<>();
        double car_x = subject.getX();
        double car_y = subject.getY();
        double car_angle = subject.getAngle();

        rayRect = new RotatedRectangle(
                car_x,
                car_y,
                subject.getCollisionBox().S.x,
                subject.getCollisionBox().S.y,
                car_angle
        );

        for (double i = -Math.PI/4; i <= Math.PI/4; i += Math.PI/4) {
            int ctr;

            rayRect.C.rotate(i);
            rayV = new Vector2(velocity.length*Math.cos(i),velocity.length*Math.sin(i));

            for (ctr = 1; ctr < 100; ctr+=5) {
                RotatedRectangle._Vector2D inc = new RotatedRectangle._Vector2D();
                inc.x = rayV.x_val*ctr;
                inc.y = rayV.y_val*ctr;
                rayRect.S.add(inc);

                col_obj = game.collision(rayRect);
                if(col_obj != null && !(col_obj instanceof Car)) {
                    col_distances.add(ctr);
                    collision = true;
                    break;
                }
            }
            col_distances.add(ctr);
        }

        if (!collision) return null;

        int col_dir = min(col_distances);
        if (col_distances.get(0) == col_dir) {
            return new Vector2(car_angle - Math.PI / 4).multiply(subject.getSpeed());
        }
        if (col_distances.get(1) == col_dir) {
            return new Vector2(car_angle).multiply(subject.getSpeed());
        }
        if (col_distances.get(2) == col_dir) {
            return new Vector2(car_angle + Math.PI / 4).multiply(subject.getSpeed());
        }
        return null;
    }


}
