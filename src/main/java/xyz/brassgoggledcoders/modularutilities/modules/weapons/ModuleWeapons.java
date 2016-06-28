package xyz.brassgoggledcoders.modularutilities.modules.weapons;

import xyz.brassgoggledcoders.boilerplate.module.Module;
import xyz.brassgoggledcoders.boilerplate.module.ModuleBase;
import xyz.brassgoggledcoders.modularutilities.ModularUtilities;

@Module(mod = ModularUtilities.MODID)
public class ModuleWeapons extends ModuleBase {

	@Override
	public String getName() {
		return "Weapons";
	}

	/*
	 * TODO
	 * - Dual wieldable daggers. Quick, low damage.
	 * - Slow swinging but high damage Maces
	 * - Twohanded, slow, but superdamage Warhammers
	 * - Crossbow
	 * - Longbow
	 * - Shortbow
	 * - Throwing weapons - spears, stars etc
	 * - Some unique weapons!
	 * - Must all have enchantment support esp bleed/poison etc...extend ItemSword?
	 */
}
