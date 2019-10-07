package com.comtrade.edit.hangman.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class PlayerCodes {

    private List<String> playerCodes = new ArrayList<String>();

    public List<String> getPlayerCodes() {
        return playerCodes;
    }

    public void setPlayerCodes(List<String> playerCodes) {
        this.playerCodes = playerCodes;
    }

    public void addPlayerCode(String code) {
        playerCodes.add(code);
    }

}
