package gg.steve.mc.pp.addons.mines.create;

public enum MineCreationState {
    SELECTING_BORDER_POS_1(0, "selecting border position 1"),
    SELECTING_BORDER_POS_2(1, "selecting border position 2"),
    SELECTING_MINING_POS_1(2, "selecting mining area position 1"),
    SELECTING_MINING_POS_2(3, "selecting mining are position 2"),
    SELECTING_MINING_AREA_BLOCKS_CHEST(4, "selecting mining area blocks chest"),
    SELECTING_SPAWN_LOCATION(5, "selecting mine spawn location"),
    SELECTING_NAME(6, "selecting a name for the mine"),
    SELECTION_COMPLETE(7, "type 'confirm' to create the mine");

    private int position;
    private String niceName;

    MineCreationState(int position, String niceName) {
        this.position = position;
        this.niceName = niceName;
    }

    public int getPosition() {
        return position;
    }

    public String getNiceName() {
        return niceName;
    }

    public static MineCreationState getPreviousState(MineCreationState currentState) {
        if (currentState.getPosition() == SELECTING_BORDER_POS_1.getPosition()) return currentState;
        for (MineCreationState creationState : MineCreationState.values()) {
            if (creationState.getPosition() + 1 == currentState.getPosition()) return creationState;
        }
        return currentState;
    }

    public static MineCreationState getNextState(MineCreationState currentState) {
        if (currentState.getPosition() == SELECTION_COMPLETE.getPosition()) return currentState;
        for (MineCreationState creationState : MineCreationState.values()) {
            if (creationState.getPosition() - 1 == currentState.getPosition()) return creationState;
        }
        return currentState;
    }
}