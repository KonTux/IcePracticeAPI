package de.kontux.icepractice.api.playerstates;

public enum PlayerState {

    IDLE(false, true, false),
    STARTING_MATCH(false, true, true),
    MATCH(true, false, true),
    EVENT(false, true, false),
    SPECTATING(false, true, false),
    EDITOR(false, true, true);

    private final boolean pvp, interact, moveInventory;

    PlayerState(boolean pvp, boolean interact, boolean moveInventory) {
        this.pvp = pvp;
        this.interact = interact;
        this.moveInventory = moveInventory;
    }

    public boolean isPvp() {
        return pvp;
    }

    public boolean isInteract() {
        return interact;
    }

    public boolean isMoveInventory() {
        return moveInventory;
    }
}
