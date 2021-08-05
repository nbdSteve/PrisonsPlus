package gg.steve.mc.pp.addons.mines.create;

import gg.steve.mc.pp.addons.mines.box.BorderBoundingBox;
import gg.steve.mc.pp.addons.mines.box.MiningAreaBoundingBox;
import gg.steve.mc.pp.addons.mines.coords.Coordinate;
import gg.steve.mc.pp.addons.mines.location.MineSpawnLocation;
import lombok.Data;
import org.bukkit.Location;

import java.util.UUID;

@Data
public class MineCreationBuilder {
    private UUID creatorId;
    private String name;
    private MineSpawnLocation spawnLocation;
    private BorderBoundingBox borderBoundingBox;
    private MiningAreaBoundingBox miningArea;
    private MineSpawnLocation mineSpawnLocation;
    private MineCreationState currentState;
    // vars
    private Coordinate borderPosition1;
    private Coordinate borderPosition2;
    private Coordinate miningPosition1;
    private Coordinate miningPosition2;

    public MineCreationBuilder(UUID creatorId, Coordinate borderPosition1) {
        this.creatorId = creatorId;
        currentState = MineCreationState.SELECTING_BORDER_POS_1;
        this.borderPosition1 = borderPosition1;
    }

    public void doClickSelection(Coordinate position) {
        switch (this.currentState) {
            case SELECTING_BORDER_POS_1:
                this.borderPosition1 = position;
                break;
            case SELECTING_BORDER_POS_2:
                this.borderPosition2 = position;
                break;
            case SELECTING_MINING_POS_1:
                this.miningPosition1 = position;
                break;
            case SELECTING_MINING_POS_2:
                this.miningPosition2 = position;
                break;
        }
        this.progressCreationState();
    }

    public void doNameSelection(String name) {
        this.name = name;
        this.progressCreationState();
    }

    public void doSpawnSelection(Location location) {
        this.mineSpawnLocation = new MineSpawnLocation(location);
        this.progressCreationState();
    }

    public void undoSelection() {
        switch (this.currentState) {
            case SELECTING_BORDER_POS_1:
                this.borderPosition1 = null;
                break;
            case SELECTING_BORDER_POS_2:
                this.borderPosition2 = null;
                break;
            case SELECTING_MINING_POS_1:
                this.miningPosition1 = null;
                break;
            case SELECTING_MINING_POS_2:
                this.miningPosition2 = null;
                break;
            case SELECTING_SPAWN_LOCATION:
                this.spawnLocation = null;
                break;
            case SELECTING_NAME:
                this.name = "";
                break;
        }
        this.reverseCreationState();
    }

    public void progressCreationState() {
        this.currentState = MineCreationState.getNextState(this.currentState);
    }

    public void reverseCreationState() {
        this.currentState = MineCreationState.getPreviousState(this.currentState);
    }

    public boolean isComplete() {
        return this.currentState == MineCreationState.SELECTION_COMPLETE;
    }

    public boolean create() {
        // handle actual logic for creating the mine & files n shit
        return false;
    }
}
