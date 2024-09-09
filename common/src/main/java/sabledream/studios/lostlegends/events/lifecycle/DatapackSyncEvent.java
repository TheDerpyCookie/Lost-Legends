package sabledream.studios.lostlegends.events.lifecycle;

import sabledream.studios.lostlegends.events.base.EventHandler;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record DatapackSyncEvent(ServerPlayerEntity player)
{

	public static final EventHandler<DatapackSyncEvent> EVENT = new EventHandler<>();
}
