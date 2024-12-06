package pacote;
import robocode.*;

    public void run() {
        setColors(Color.darkGray,Color.black,Color.lightGray,Color.white,Color.green);
        while (true) {
            ahead(100);
            turnRight(90);
            
        }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
        String robotank = e.getName();
        double distancia = e.gettDistance();
        System.out.println(robotank + " distancia" + distancia);
        if (distancia < 135) {
            fire(3);
        } else {
            fire(1);
        }
    }
    
    public void onHitWall(HitWallEvent e) {
        back 50;
        turnRight(90);
    }
