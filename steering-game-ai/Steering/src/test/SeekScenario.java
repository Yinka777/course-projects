package test;

import controllers.KeyboardController;
import controllers.SteeringController;
import engine.Car;
import engine.Game;
import engine.GameObject;
import engine.GameWindow;
import engine.Obstacle;
import steering.Seek;

import java.awt.Color;

/**
 *
 * @author santi
 */
public class SeekScenario {
    /*
        Goal of this exercise:
        - Write a controller for "car2" that uses the "Seek" steering behavior to always run
          after car1 (that is controlled using the arrow keys).
    */
    
    public static void main(String args[]) throws Exception {
        Game game = new Game(800,600, 25);
        // set up the outside walls:
        game.add(new Obstacle(0,0,800,25,Color.GRAY));
        game.add(new Obstacle(0,575,800,25,Color.GRAY));
        game.add(new Obstacle(0,0,25,600,Color.GRAY));
        game.add(new Obstacle(775,0,25,600,Color.GRAY));
        // set up the cars and markers:
        GameObject car1 = new Car("Steering/graphics/redcar.png",200,300,-Math.PI/2, new KeyboardController());
        GameObject car2 = new Car("Steering/graphics/bluecar.png",600,300,-Math.PI/2, new SteeringController(new Seek(car1,1)));
        game.add(car1);
        game.add(car2);
        GameWindow.newWindow(game);
    }
}
