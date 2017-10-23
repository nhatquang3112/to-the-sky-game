package com.tom.gameobjects;

import com.tom.gameworld.GameWorld;
import static com.tom.gameobjects.ScrollHandler.SCROLL_SPEED;

import java.util.Random;

/**
 * Created by NewUser on 6/22/2017.
 */

public class Goal {
    public int green_gem_goal, red_gem_goal, blue_gem_goal;
    public float remaining_time;
    private Random r = new Random();
    private GameWorld world;

    //constructor
    public Goal(float extra_time, GameWorld world) {
        this.world = world;

        green_gem_goal = r.nextInt(2) + 3;
        red_gem_goal = r.nextInt(2) + 3;
        blue_gem_goal = r.nextInt(2) + 3;

        remaining_time = 30 + extra_time;
    }

    //method
    public void update(float delta) {
        if (green_gem_goal == 0 && red_gem_goal == 0 && blue_gem_goal == 0) {
            reset();
            return;
        }

        if (remaining_time <= 0) {
            world.getPlayer().die();
            remaining_time = 0;
            return;
        }

        remaining_time -= delta;
        //System.out.printf("%d %d %d %f\n", green_gem_goal, red_gem_goal, blue_gem_goal, remaining_time);
    }

    public void gemUpdate(int gem_type) {
        //1 is blue, 2 is red, 3 is green
        switch(gem_type) {
            case 1: {
                blue_gem_goal--; if (blue_gem_goal < 0) blue_gem_goal = 0; break;
            }
            case 2: {
                red_gem_goal--; if (red_gem_goal < 0) red_gem_goal = 0; break;
            }
            case 3: {
                green_gem_goal--; if (green_gem_goal < 0) green_gem_goal = 0; break;
            }
            default: break;
        }
    }

    public void onRestart() {
        remaining_time = 30;
        green_gem_goal = r.nextInt(2) + 3;
        red_gem_goal = r.nextInt(2) + 3;
        blue_gem_goal = r.nextInt(2) + 3;
    }

    public void reset() {
        //reset time
        remaining_time += 7 ;
        //add increment before resetting
        world.addScore(10);
        //speed up the game
        SCROLL_SPEED *= 1.2;
        //reset everything again
        green_gem_goal = r.nextInt(2) + 3;
        red_gem_goal = r.nextInt(2) + 3;
        blue_gem_goal = r.nextInt(2) + 3;
    }
}
