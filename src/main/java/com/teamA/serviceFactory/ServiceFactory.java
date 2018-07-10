package com.teamA.serviceFactory;

import com.teamA.data.player.PlayerService;

public interface ServiceFactory {

    <T extends PlayerService> PlayerService createPlayerController(Class<T> type);
}
