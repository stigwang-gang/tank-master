package CSYE6200.tank.scene;

import CSYE6200.tank.Director;
import CSYE6200.tank.sprite.*;
import CSYE6200.tank.util.Direction;
import CSYE6200.tank.util.Group;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GameScene {

    private Canvas canvas = new Canvas(Director.WIDTH, Director.HEIGHT);
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    private KeyProcess keyProcess = new KeyProcess();
    private Refresh refresh = new Refresh();
    private boolean running = false;

    private Background background = new Background();
    //玩家一
    private Tank self = null;

    //玩家二
    private Tank2 self2 = null;
    //子弹
    public List<Bullet> bullets = new ArrayList<>();
    //敌军坦克
    public List<Tank> tanks = new ArrayList<>();
    //爆炸
    public List<Explode> explodes = new ArrayList<>();
    public List<Crate>  crates = new ArrayList<>();
    public List<Rock> rocks = new ArrayList<>();
    public List<Tree> trees = new ArrayList<>();


    private void paint() {
        background.paint(graphicsContext);
        if (self != null && self.isAlive()) {
            self.paint(graphicsContext);
            self.impact(tanks);
            self.impact(crates);
            self.impact(rocks);
            if (self2 != null && self2.isAlive())
                self.impact(self2);
        }
        if (self2 != null && self2.isAlive()) {
            self2.paint(graphicsContext);
            self2.impact(tanks);
            self2.impact(crates);
            self2.impact(rocks);
            if (self != null && self.isAlive())
                self2.impact(self);
        }
        for (int i = 0; i < bullets.size(); i++) {
            Bullet b = bullets.get(i);
            b.paint(graphicsContext);
            b.impactCrates(crates);
            b.impactTank(tanks);
            b.impactRocks(rocks);
            if (self != null && self.isAlive())
                b.impactTank(self);
            if (self2 != null && self2.isAlive())
                b.impactTank(self2);
        }

        for (int i = 0; i < tanks.size(); i++) {
            Tank tank = tanks.get(i);
            tank.paint(graphicsContext);
            tank.impact(crates);
            if (self != null && self.isAlive())
            tank.impact(self);
            tank.impact(rocks);
            tank.impact(tanks);
            if (self2 != null && self2.isAlive())
                tank.impact(self2);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode e = explodes.get(i);
            e.paint(graphicsContext);
        }

        for (int i = 0; i < crates.size(); i++) {
            Crate crate = crates.get(i);
            crate.paint(graphicsContext);
        }

        for (int i = 0; i < rocks.size(); i++) {
            Rock rock = rocks.get(i);
            rock.paint(graphicsContext);
        }

        for (int i = 0; i < trees.size(); i++) {
            Tree tree = trees.get(i);
            tree.paint(graphicsContext);
        }

//        graphicsContext.setFill(Color.GREEN);
        graphicsContext.setFont(new Font(20));
        graphicsContext.fillText("敌军的数量：" + tanks.size(), 800, 60);
        graphicsContext.fillText("子弹的数量：" + bullets.size(), 800, 90);

        if(!self.isAlive()){
            Director.getInstance().gameOver(false);
        }
        if(!self2.isAlive()) {
            Director.getInstance().gameOver(false);
        } else if(tanks.isEmpty()) {
            Director.getInstance().gameOver(true);
        }
    }

    public void init(Stage stage,int level) {
        AnchorPane root = new AnchorPane(canvas);
        //first choice to change the level
//        Button nextLevelButton = new Button("Next Level");
//        nextLevelButton.setLayoutX(50); // Set X position
//        nextLevelButton.setLayoutY(50); // Set Y position
//        root.getChildren().add(nextLevelButton);
        stage.getScene().setRoot(root);
        stage.getScene().setOnKeyReleased(keyProcess);
        stage.getScene().setOnKeyPressed(keyProcess);
        running = true;
        self = new Tank(800, 530, Group.green, Direction.stop, Direction.up, this);
        self2 = new Tank2(100, 530, Group.green, Direction.stop, Direction.up, this);
        if(level==1)
            initSprite();
        else if(level==2)
            initSprite2();
        else if(level==3){
            self2 = new Tank2(100, 530, Group.red, Direction.stop, Direction.up, this);
            initSprite3();
        }
        refresh.start();
    }
//map3 draws
    private void initSprite3() {
        for (int i = 0; i < 1; i++) {
            Tank tank = new Tank(421, 280,Group.green, Direction.stop, Direction.down, this);
            tanks.add(tank);
        }
        for (int i = 0; i < 4; i++) {
            Rock rock = new Rock(350-65*i, 278);
            Rock rock2 = new Rock(632+65*i, 278);
            rocks.add(rock);
            rocks.add(rock2);
        }
        for (int i = 0; i < 5; i++) {
            //down
            Crate crate1 = new Crate(321 , 400+32*i );
            Crate crate2 = new Crate(321+31 , 400+32*i );
            Crate crate3 = new Crate(538 , 400+32*i );
            Crate crate4 = new Crate(538+31 , 400+32*i );
            Crate crate5 = new Crate(383+i*31 , 460 );
            Crate crate6 = new Crate(383+i*31 , 492 );
            crates.add(crate1);
            crates.add(crate2);
            crates.add(crate3);
            crates.add(crate4);
            crates.add(crate5);
            crates.add(crate6);
            //up
            Crate crate7 = new Crate(321 , 32+32*i );
            Crate crate8 = new Crate(321+31 , 32+32*i );
            Crate crate9 = new Crate(538 , 32+32*i );
            Crate crate10 = new Crate(538+31 , 32+32*i );
            Crate crate11 = new Crate(383+i*31 , 92 );
            Crate crate12 = new Crate(383+i*31 , 92 );
            crates.add(crate7);
            crates.add(crate8);
            crates.add(crate9);
            crates.add(crate10);
            crates.add(crate11);
            crates.add(crate12);
        }

        for (int i = 0; i < 8; i++) {
            Crate crate1 = new Crate(0 , 0+32*i );
            Crate crate2 = new Crate(31 , 0+32*i );
            Crate crate3 = new Crate(914, 0+32*i );
            Crate crate4 = new Crate(883 , 0+32*i );

            Crate crate5 = new Crate(0 , 352+32*i );
            Crate crate6 = new Crate(31 , 352+32*i );
            Crate crate7 = new Crate(914 , 352+32*i );
            Crate crate8 = new Crate(883 , 352+32*i );
            crates.add(crate1);
            crates.add(crate2);
            crates.add(crate3);
            crates.add(crate4);
            crates.add(crate5);
            crates.add(crate6);
            crates.add(crate7);
            crates.add(crate8);


        }

        for (int i = 0; i < 4; i++) {
            Rock rock = new Rock(350 , 278);
            Rock rock2 = new Rock(482 , 278);
            Rock rock3 = new Rock(420 , 218);
            Rock rock4 = new Rock(420 , 338);
            rocks.add(rock);
            rocks.add(rock2);
            rocks.add(rock3);
            rocks.add(rock4);
        }

        for (int i = 0; i < 2; i++) {
            Tree tree = new Tree(0 + i * 75, 270);
            trees.add(tree);
            Tree tree2 = new Tree(480 + i * 75, 270);
            trees.add(tree2);
        }
    }

    //map1 draw setting
    private void initSprite() {
        for (int i = 0; i < 6; i++) {
            Tank tank = new Tank(200 + i * 80, 30,Group.red, Direction.stop, Direction.down, this);
            tanks.add(tank);
        }

        for (int i = 0; i < 7; i++) {
            Crate crate1 = new Crate(200,300+i*32);
            Crate crate2 = new Crate(231,300+i*32);
            Crate crate3 = new Crate(708,300+i*32);
            Crate crate4 = new Crate(739,300+i*32);
            crates.add(crate1);
            crates.add(crate2);
            crates.add(crate3);
            crates.add(crate4);
        }
        for (int i = 0; i < 19; i++) {
            Crate crate1 = new Crate(181 + i* 31, 200 );
            Crate crate2 = new Crate(181 + i* 31, 232 );

            crates.add(crate1);
            crates.add(crate2);

        }
        for (int i = 0; i < 8; i++) {
            Crate crate1 = new Crate(0 , 0+32*i );
            Crate crate2 = new Crate(31 , 0+32*i );
            Crate crate3 = new Crate(914, 0+32*i );
            Crate crate4 = new Crate(883 , 0+32*i );

            Crate crate5 = new Crate(0 , 352+32*i );
            Crate crate6 = new Crate(31 , 352+32*i );
            Crate crate7 = new Crate(914 , 352+32*i );
            Crate crate8 = new Crate(883 , 352+32*i );

            Crate crate9 = new Crate(350+31*i , 535 );
            Crate crate10 = new Crate(350+31*i , 567 );
            crates.add(crate1);
            crates.add(crate2);
            crates.add(crate3);
            crates.add(crate4);
            crates.add(crate5);
            crates.add(crate6);
            crates.add(crate7);
            crates.add(crate8);

            crates.add(crate9);
            crates.add(crate10);

        }

        for (int i = 0; i < 4; i++) {
            Rock rock = new Rock(335 + i * 71, 300);
            rocks.add(rock);
        }

        for (int i = 0; i < 3; i++) {
            Tree tree = new Tree(350 + i * 86, 380);
            trees.add(tree);
            Tree tree2 = new Tree(350 + i * 86, 456);
            trees.add(tree2);
        }
    }
//map2 draw setting
    private void initSprite2() {
        for (int i = 0; i < 6; i++) {
            Tank tank = new Tank(200 + i * 80, 30,Group.red, Direction.stop, Direction.down, this);
            tanks.add(tank);
        }

// Adding Crates in complex patterns
        for (int i = 0; i < 4; i++) {
            //C
            Crate crate = new Crate(30+31*i+31*4, 60+64);
            Crate crate1=new Crate(30+31*i+31*4,188+64);
            Crate crate2=new Crate(30+31*4,60+i*32+64);
            Crate crate3=new Crate(30+31*4,60+3*32+64);
            crates.add(crate);
            crates.add(crate1);
            crates.add(crate2);
            crates.add(crate3);
            //S
            if(i==3)crates.add(new Crate(214+31*(i-1)+31*4, 60+64)) ;
            else {
                crates.add(new Crate(214+31*i+31*4, 60+64));
                crates.add(new Crate(214+31*i+31*4,188+64));
            }
            Crate crate6 = new Crate(214+31*4, 92+64);
            Crate crate7 = new Crate(214+31+31*4, 124+64);
            Crate crate8 = new Crate(214+31*2+31*4, 156+64);
            crates.add(crate6);
            crates.add(crate7);
            crates.add(crate8);
            //Y
           crates.add(new Crate(369+31*4,60+64));
            crates.add(new Crate(369+31*4+31*4,60+64));
            crates.add(new Crate(369+31+31*4,92+64));
            crates.add(new Crate(369+31*3+31*4,92+64));
            crates.add(new Crate(369+2*31+31*4,124+64));
            crates.add(new Crate(369+2*31+31*4,156+64));
            crates.add(new Crate(369+2*31+31*4,188+64));
            //E
            crates.add(new Crate(555+31*i+31*4, 60+64));
            crates.add(new Crate(555+31*i+31*4,188+64));
            crates.add(new Crate(555+31*i+31*4,60+64+64));
            crates.add(new Crate(555+31*4,60+i*32+64));
            crates.add(new Crate(555+31*4,60+3*32+64));

            //6
            crates.add(new Crate(30+31*i+31*4, 320+64));
            crates.add(new Crate(30+31*i+31*4,448+64));
            crates.add(new Crate(30+31*i+31*4,320+64+64));
            crates.add(new Crate(30+31*4,320+i*32+64));
            crates.add(new Crate(30+31*4,320+3*32+64));
            crates.add(new Crate(30+31*4+31*3,320+3*32+64));
            //2
            if(i==3)crates.add(new Crate(214+31*(i-1)+31*4, 320+64));
            else  {
                crates.add(new Crate(214+31*i+31*4, 320+64));
                crates.add(new Crate(214+31*i+31*4,188+64+260));
            }
            crates.add(new Crate(214+31*2+31*4, 92+64+260));
            crates.add(new Crate(214+31+31*4, 124+64+260));
            crates.add(new Crate(214+31*4, 156+64+260));
            //0
            crates.add(new Crate(369+31*i+31*4, 320+64));
            crates.add(new Crate(369+31*i+31*4,448+64));
            crates.add(new Crate(369+31*4,320+i*32+64));
            crates.add(new Crate(369+31*4,320+3*32+64));
            crates.add(new Crate(369+31*7,320+i*32+64));
            crates.add(new Crate(369+31*7,320+3*32+64));
            //0
            crates.add(new Crate(555+31*i+31*4, 320+64));
            crates.add(new Crate(555+31*i+31*4,448+64));
            crates.add(new Crate(555+31*4,320+i*32+64));
            crates.add(new Crate(555+31*4,320+3*32+64));
            crates.add(new Crate(555+31*7,320+i*32+64));
            crates.add(new Crate(555+31*7,320+3*32+64));
        }

// Placing Rocks in irregular formations
//        for (int i = 0; i < 8; i++) {
//            Rock rock = new Rock(150 + i * 100, 350 + (i % 5) * 35);
//            rocks.add(rock);
//        }

        //add tree
        for (int i = 0; i < 11; i++) {
            Tree tree = new Tree(30 + i * 80, 280 );
            trees.add(tree);
        }
    }

    public void clear(Stage stage) {

        stage.getScene().removeEventHandler(KeyEvent.KEY_PRESSED, keyProcess);
        stage.getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyProcess);
        refresh.stop();
        self = null;
        self2 = null;
        tanks.clear();
        bullets.clear();
        crates.clear();
        explodes.clear();
        rocks.clear();
        trees.clear();
    }


    private class Refresh extends AnimationTimer {
        @Override
        public void handle(long now) {
            if(running) {
                paint();
            }
        }
    }
    private class KeyProcess implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            KeyCode keyCode = event.getCode();

            if(event.getEventType() == KeyEvent.KEY_RELEASED) {
                if(keyCode.equals(KeyCode.SPACE)) {
                    pauseOrContinue();
                }
                if(self != null) self.released(keyCode);
                if(self2!=null){
                    self2.released(keyCode);
                }
            }else if(event.getEventType() == KeyEvent.KEY_PRESSED) {
                if(self != null) {
                    self.pressed(keyCode);
                }
                if(self2!=null){
                    self2.pressed(keyCode);
                }
            }
        }
    }

    public void pauseOrContinue() {
        if(running) {
            running = false;
        }else {
            running = true;
        }
    }
}
