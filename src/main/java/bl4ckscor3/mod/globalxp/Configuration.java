package bl4ckscor3.mod.globalxp;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class Configuration
{
	public static final ForgeConfigSpec SERVER_SPEC;
	public static final Client CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Server SERVER;

	static {
		Pair<Client,ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		Pair<Server,ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);

		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
		SERVER_SPEC = serverSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
	}

	public static class Client
	{
		public final DoubleValue spinSpeed;
		public final DoubleValue bobSpeed;
		public final BooleanValue renderNameplate;

		Client(ForgeConfigSpec.Builder builder)
		{
			spinSpeed = builder
					.comment("How fast the emerald should spin (multiplier of the default speed)")
					.defineInRange("spinSpeed", 1.0D, 0.0D, Double.MAX_VALUE);
			bobSpeed = builder
					.comment("How fast the emerald should bob up and down (multiplier of the default speed)")
					.defineInRange("bobSpeed", 1.0D, 0.0D, Double.MAX_VALUE);
			renderNameplate = builder
					.comment("Whether info about the saved levels should be shown above the XP Block")
					.define("renderNameplate", true);
		}
	}

	public static class Server
	{
		public final IntValue xpForComparator;
		public final BooleanValue pickupXP;
		public final DoubleValue pickupRange;
		public final BooleanValue retriveUntilNextLevel;
		public final BooleanValue storeUntilPreviousLevel;

		Server(ForgeConfigSpec.Builder builder)
		{
			xpForComparator = builder
					.comment("The amount of XP needed for the comparator to output a redstone signal of strength one. By default, the signal will be at full strength if the block has 30 levels stored.")
					.defineInRange("xpForComparator", 1395 / 15, 0, Integer.MAX_VALUE / 15);
			pickupXP = builder
					.comment("Whether the XP Block will pickup any XP orbs around it")
					.define("pickupXP", true);
			pickupRange = builder
					.comment("The range in blocks around the XP Block in which XP orbs will be picked up")
					.defineInRange("pickupRange", 3.0D, 0.0D, 50.0D);
			retriveUntilNextLevel = builder
					.comment("Setting this to true will remove only as much XP from the block at a time as is needed for the player to reach their next level. Setting to false will retrieve all stored XP at once.")
					.define("retrieve_until_next_level", true);
			storeUntilPreviousLevel = builder
					.comment("Setting this to true will store only as much XP from the player's XP bar until reaching the previous level, meaning only one level at maximum will be added to the block's storage at a time. Setting to false will store all the XP the player has.")
					.define("store_until_previous_level", false);
		}
	}
}
