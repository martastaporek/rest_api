package com.teamA.controllersFactory;

import com.teamA.data.player.PlayerController;

public interface ControllerFactory {

    <T extends PlayerController> PlayerController createPlayerController(Class<T> type);
}
