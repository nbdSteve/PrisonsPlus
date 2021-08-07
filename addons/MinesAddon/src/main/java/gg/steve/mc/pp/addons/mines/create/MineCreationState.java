package gg.steve.mc.pp.addons.mines.create;

public enum MineCreationState {
    SELECTING_BORDER_POS_1(0, "select border position 1"),
    SELECTING_BORDER_POS_2(1, "select border position 2"),
    SELECTING_MINING_POS_1(2, "select mining area position 1"),
    SELECTING_MINING_POS_2(3, "select mining are position 2"),
    SELECTING_MINING_AREA_BLOCKS_CHEST(4, "select mining area blocks chest"),
    SELECTING_MINING_AREA_FILL_DELAY(5, "type an integer in chat for the mining area fill delay (in ticks, 20 per second)"),
    SELECTING_SPAWN_LOCATION(6, "select a block to be the /warp location"),
    SELECTING_NAME(7, "select a file / warp name for the mine"),
    SELECTING_DISPLAY_NAME(8, "select a display name for the mine"),
    SELECTION_COMPLETE(9, "type 'confirm' to create the mine");

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