package sample;

import robocode.*;
import java.awt.*;

/**
 * SuperTracker - um robô de exemplo avançado criado por CrazyBassoonist,
 * baseado no robô Tracker de Mathew Nelson e mantido por Flemming N. Larsen.
 * 
 * Este robô trava em outro, aproxima-se e atira quando está perto.
 */
public class AimLock extends AdvancedRobot {
    int moveDirection = 1; // Direção de movimento (1 para frente, -1 para trás)

    /**
     * run: Função principal de execução do robô Tracker
     */
    public void run() {
        setAdjustRadarForRobotTurn(true); // Mantém o radar estável enquanto o robô gira
        setBodyColor(new Color(128, 128, 50)); // Define a cor do corpo
        setGunColor(new Color(50, 50, 20));    // Define a cor da arma
        setRadarColor(new Color(200, 200, 70)); // Define a cor do radar
        setScanColor(Color.white);             // Define a cor do feixe de escaneamento
        setBulletColor(Color.blue);            // Define a cor das balas
        setAdjustGunForRobotTurn(true);        // Mantém a arma estável enquanto o robô gira
        turnRadarRightRadians(Double.POSITIVE_INFINITY); // Faz o radar girar continuamente para a direita
    }

    /**
     * onScannedRobot: Função que é chamada quando o radar detecta outro robô
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        double absBearing = e.getBearingRadians() + getHeadingRadians(); // Calcula o ângulo do inimigo
        double latVel = e.getVelocity() * Math.sin(e.getHeadingRadians() - absBearing); // Velocidade do inimigo
        double gunTurnAmt; // Quantidade que a arma precisa girar
        setTurnRadarLeftRadians(getRadarTurnRemainingRadians()); // Trava o radar no inimigo

        // Chance aleatória de mudar a velocidade para confundir o inimigo
        if (Math.random() > 0.9) {
            setMaxVelocity((12 * Math.random()) + 12); // Define uma nova velocidade máxima aleatória
        }

        // Se o inimigo estiver a mais de 150 unidades de distância
        if (e.getDistance() > 150) {
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 22); 
            // Calcula quanto girar a arma para mirar (levemente liderando o alvo)
            setTurnGunRightRadians(gunTurnAmt); // Gira a arma na direção correta
            setTurnRightRadians(robocode.util.Utils.normalRelativeAngle(absBearing - getHeadingRadians() + latVel / getVelocity())); 
            // Gira o robô na direção prevista do inimigo
            setAhead((e.getDistance() - 140) * moveDirection); // Move-se para frente (ou para trás se moveDirection for negativo)
            setFire(3); // Atira com potência 3
        } else { // Se o inimigo estiver perto o suficiente
            gunTurnAmt = robocode.util.Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + latVel / 15);
            // Calcula quanto girar a arma para mirar
            setTurnGunRightRadians(gunTurnAmt); // Gira a arma na direção certa
            setTurnLeft(-90 - e.getBearing()); // Gira 90 graus para ficar perpendicular ao inimigo
            setAhead((e.getDistance() - 140) * moveDirection); // Move-se para frente ou para trás
            setFire(3); // Atira com potência 3
        }
    }

    /**
     * onHitWall: Função que é chamada quando o robô bate em uma parede
     */
    public void onHitWall(HitWallEvent e) {
        moveDirection = -moveDirection; // Inverte a direção ao bater em uma parede
    }
}
