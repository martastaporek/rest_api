package com.teamA.controllersFactory;

import com.teamA.data.PlayerController;

public interface ControllerFactory {

    <T extends PlayerController> PlayerController createPlayerController(Class<T> type);
}
