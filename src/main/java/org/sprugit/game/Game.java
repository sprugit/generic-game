package org.sprugit.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Game {

    GameState gameState;
    final List<Turn> turns = new ArrayList<>();

    public abstract Set<Color> getColors();

}
