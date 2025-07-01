package org.sprugit.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class Game {

    protected GameState gameState;
    protected final List<Turn> turns = new ArrayList<>();
    protected LocalDateTime startTime;

    public abstract Set<Color> getColors();

    public void build() {
        turns.forEach(gameState::apply);
    }

    public void play(Turn t){
        gameState.apply(t);
        turns.add(t);
    }

}
