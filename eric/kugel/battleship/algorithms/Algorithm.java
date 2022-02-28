package eric.kugel.battleship.algorithms;

import eric.kugel.battleship.game.*;

public interface Algorithm {
    public void shoot();
    public void shipSunk(Ship ship);

    public void gameOver(int moves);
}
