package steering;

import engine.Car;
import engine.Game;

public abstract class SteeringBehavior {
    public abstract Vector2 update(Game game, Car subject, double delta_t);
}
