package com.toofifty.easyblastfurnace.methods;

import com.toofifty.easyblastfurnace.steps.MethodStep;
import com.toofifty.easyblastfurnace.utils.BlastFurnaceState;
import net.runelite.api.ItemID;
import net.runelite.client.plugins.blastfurnace.BarsOres;

public class MithrilBarMethod extends Method
{
    @Override
    public MethodStep[] getPrerequisiteSteps()
    {
        return new MethodStep[]{
            equipIceGloves,
            withdrawCoalBag
        };
    }

    @Override
    public MethodStep[] getSteps()
    {
        return new MethodStep[]{
            openBank,

            fillCoalBag,
            withdrawCoal,
            putOntoConveyorBelt,
            emptyCoalBag,
            putOntoConveyorBelt,

            openBank,

            fillCoalBag,
            withdrawMithrilOre,
            putOntoConveyorBelt,
            emptyCoalBag,
            putOntoConveyorBelt,
            collectBars,

            openBank,
            depositAllIntoBank,

            fillCoalBag,
            withdrawMithrilOre,
            putOntoConveyorBelt,
            emptyCoalBag,
            putOntoConveyorBelt,
            collectBars,
        };
    }

    @Override
    public MethodStep next(BlastFurnaceState state)
    {
        if (state.getFurnaceQuantity(BarsOres.COAL) > 1 &&
            state.getFurnaceQuantity(BarsOres.MITHRIL_ORE) > 0) {
            return waitForBars;
        }

        if (state.getFurnaceQuantity(BarsOres.MITHRIL_BAR) > 0) {
            return collectBars;
        }

        if (state.isBankOpen()) {
            if (state.getInventoryQuantity(ItemID.COAL_BAG_12019) == 0) {
                return withdrawCoalBag;
            }

            if (state.getInventoryQuantity(ItemID.MITHRIL_BAR) > 0) {
                return depositAllIntoBank;
            }

            if (state.getCoalInCoalBag() == 0) {
                return fillCoalBag;
            }

            if (state.getInventoryQuantity(ItemID.COAL) > 0) {
                return putOntoConveyorBelt;
            }

            if (state.getFurnaceQuantity(BarsOres.COAL) < 54) {
                return withdrawCoal;
            }

            if (state.getInventoryQuantity(ItemID.MITHRIL_ORE) == 0) {
                return withdrawMithrilOre;
            }
        }

        if (state.getInventoryQuantity(ItemID.MITHRIL_BAR) > 0) {
            return openBank;
        }

        if (state.getInventoryQuantity(ItemID.COAL_BAG_12019) == 0) {
            return openBank;
        }

        if (state.getInventoryQuantity(ItemID.COAL) > 0) {
            return putOntoConveyorBelt;
        }

        if (state.getInventoryQuantity(ItemID.MITHRIL_ORE) > 0) {
            return putOntoConveyorBelt;
        }

        if (state.getCoalInCoalBag() > 0) {
            return emptyCoalBag;
        }

        return openBank;
    }
}
