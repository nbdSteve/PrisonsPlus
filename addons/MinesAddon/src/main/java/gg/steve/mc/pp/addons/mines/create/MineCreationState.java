package gg.steve.mc.pp.addons.mines.create;

public enum MineCreationState {
    SELECTING_BORDER_POS_1(0),
    SELECTING_BORDER_POS_2(1),
    SELECTING_MINING_POS_1(2),
    SELECTING_MINING_POS_2(3),
    SELECTING_SPAWN_LOCATION(4),
    SELECTING_NAME(5),
    SELECTION_COMPLETE(6);

    private int position;

    MineCreationState(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public static MineCreationState getPreviousState(MineCreationState currentState) {
        if (currentState.getPosition() == 0) return currentState;
        for (MineCreationState creationState : MineCreationState.values()) {
            if (creationState.getPosition() + 1 == currentState.getPosition()) return creationState;
        }
        return currentState;
    }

    public static MineCreationState getNextState(MineCreationState currentState) {
        if (currentState.getPosition() == 6) return currentState;
        for (MineCreationState creationState : MineCreationState.values()) {
            if (creationState.getPosition() - 1 == currentState.getPosition()) return creationState;
        }
        return currentState;
    }
}