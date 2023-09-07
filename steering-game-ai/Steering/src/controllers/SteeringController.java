package controllers;

import engine.Car;
import engine.Game;
import steering.SteeringBehavior;
import steering.Vector2;

public class SteeringController extends Controller {
    SteeringBehavior sb = null;

    public SteeringController(SteeringBehavior a_sb) {
        sb = a_sb;
    }

    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        Vector2 A = sb.update(game, subject, delta_t);
        Vector2 Direction = new Vector2(Math.cos(subject.getAngle()),
                                        Math.sin(subject.getAngle()));
        Vector2 Right = new Vector2(Math.cos(subject.getAngle()+Math.PI/2),
                                    Math.sin(subject.getAngle()+Math.PI/2));
        double parallel = A.dotProduct(Direction);
        double perpendicular = A.dotProduct(Right);

        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;
        controlVariables[VARIABLE_STEERING] = perpendicular;
        if (parallel>=0) controlVariables[VARIABLE_THROTTLE] = Math.max(parallel,0.1);
        if (parallel<0) controlVariables[VARIABLE_BRAKE] = Math.max(-parallel,-0.1);
        if (parallel==0 && perpendicular!=0) controlVariables[VARIABLE_THROTTLE] = 1;
    }
}
