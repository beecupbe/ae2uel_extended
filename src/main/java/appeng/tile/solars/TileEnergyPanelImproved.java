/*
 * This file is part of Applied Energistics 2.
 * Copyright (c) 2013 - 2014, AlgorithmX2, All rights reserved.
 *
 * Applied Energistics 2 is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Applied Energistics 2 is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Applied Energistics 2.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */

package appeng.tile.solars;


import appeng.api.config.Actionable;
import appeng.api.networking.IGridNode;
import appeng.api.networking.energy.IEnergyGrid;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.util.AECableType;
import appeng.api.util.AEPartLocation;
import appeng.api.util.DimensionalCoord;
import appeng.core.AEConfig;
import appeng.core.settings.TickRates;
import appeng.me.GridAccessException;
import appeng.tile.grid.AENetworkTile;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.material.MapColor;
import net.minecraft.nbt.NBTTagCompound;

import java.io.IOException;


public class TileEnergyPanelImproved extends AENetworkTile implements IGridTickable {
    public static double powerPerTick = AEConfig.instance().getImprovedSolarPanelGenPerTick();
    public static final double powerPerTickAtNight = AEConfig.instance().getImprovedSolarPanelGenPerTickNight();
    public static final double internalMaxEnergyCapacity = AEConfig.instance().getImprovedSolarPanelCapacity();
    public double internalCurrentEnergy;

    private static int ticksUpdateVisibility = 100;

    public TileEnergyPanelImproved() {
        this.getProxy().setIdlePowerUsage(0);
        this.getProxy().setFlags();
    }

    @Override
    public AECableType getCableConnectionType(final AEPartLocation dir) {
        return AECableType.COVERED;
    }

    @Override
    protected boolean readFromStream(final ByteBuf data) throws IOException {
        super.readFromStream(data);
        return true;
    }

    @Override
    protected void writeToStream(final ByteBuf data) throws IOException {
        super.writeToStream(data);
    }

    @Override
    public NBTTagCompound writeToNBT(final NBTTagCompound data) {
        super.writeToNBT(data);
        return data;
    }

    @Override
    public void readFromNBT(final NBTTagCompound data) {
        super.readFromNBT(data);
    }

    @Override
    public DimensionalCoord getLocation() {
        return new DimensionalCoord(this);
    }

    @Override
    public TickingRequest getTickingRequest(final IGridNode node) {
        return new TickingRequest(TickRates.SolarBasic.getMin(), TickRates.SolarBasic.getMax(), canSleep(), false);
    }

    @Override
    public TickRateModulation tickingRequest(final IGridNode node, final int ticksSinceLastCall) {
        boolean isDay = this.world.isDaytime();

        if (ticksUpdateVisibility > 0) {
            ticksUpdateVisibility--;
            if (!this.world.provider.isNether()) {
                return makeEnergy(isDay);
            }
        } else {
            if (this.updateVisibility()) {
                ticksUpdateVisibility = 100;
                return makeEnergy(isDay);
            }
        }

        return TickRateModulation.SLOWER;
    }

    public TickRateModulation makeEnergy(boolean isDay) {
        if (isDay && !this.world.isRaining() && !this.world.isThundering()) {
            this.internalCurrentEnergy += powerPerTick;
        } else {
            this.internalCurrentEnergy += powerPerTickAtNight;
        }

        try {
            final IEnergyGrid grid = this.getProxy().getEnergy();
            final double overFlow = grid.injectPower(this.internalCurrentEnergy, Actionable.SIMULATE);

            grid.injectPower(Math.max(0.0, this.internalCurrentEnergy - overFlow), Actionable.MODULATE);
            this.internalCurrentEnergy = overFlow;

            return TickRateModulation.URGENT;
        } catch (final GridAccessException e) {
            return TickRateModulation.SLOWER;
        }
    }

    public boolean updateVisibility() {
        return this.world.canBlockSeeSky(this.pos.up()) && (this.world.getBlockState(this.pos.up()).getMaterial().getMaterialMapColor() == MapColor.AIR);
    }

    public boolean canSleep() {
        return this.internalCurrentEnergy >= internalMaxEnergyCapacity;
    }
}
