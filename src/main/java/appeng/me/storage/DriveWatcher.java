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

package appeng.me.storage;


import appeng.api.config.Actionable;
import appeng.api.networking.security.IActionSource;
import appeng.api.storage.ICellHandler;
import appeng.api.storage.ICellInventoryHandler;
import appeng.api.storage.data.IAEStack;
import appeng.core.features.registries.cell.CreativeCellHandler;
import appeng.me.GridAccessException;
import appeng.me.helpers.MachineSource;
import appeng.tile.storage.TileDrive;
import net.minecraft.item.ItemStack;

import java.util.Collections;


public class DriveWatcher<T extends IAEStack<T>> extends MEInventoryHandler<T> {

    private int oldStatus = 0;
    private final ItemStack is;
    private final ICellHandler handler;
    private final TileDrive drive;
    private final IActionSource source;

    public DriveWatcher(final ICellInventoryHandler<T> i, final ItemStack is, final ICellHandler han, final TileDrive drive) {
        super(i, i.getChannel());
        this.is = is;
        this.handler = han;
        this.drive = drive;
        this.source = new MachineSource(drive);
    }

    public int getStatus() {
        return this.handler.getStatusForCell(this.is, (ICellInventoryHandler) this.getInternal());
    }

    @Override
    public T injectItems(final T input, final Actionable type, final IActionSource src) {
        final long size = input.getStackSize();

        final T remainder = super.injectItems(input, type, src);

        if (type == Actionable.MODULATE && (remainder == null || remainder.getStackSize() != size)) {
            final int newStatus = this.getStatus();

            if (newStatus != this.oldStatus) {
                this.drive.blinkCell(this.getSlot());
                this.oldStatus = newStatus;
            }
            if (this.drive.getProxy().isActive() && !(handler instanceof CreativeCellHandler)) {
                try {
                    this.drive.getProxy().getStorage().postAlterationOfStoredItems(this.getChannel(), Collections.singletonList(input.copy().setStackSize(input.getStackSize() - (remainder == null ? 0 : remainder.getStackSize()))), this.source);
                } catch (GridAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return remainder;
    }

    @Override
    public T extractItems(final T request, final Actionable type, final IActionSource src) {
        final T extractable = super.extractItems(request, type, src);

        if (type == Actionable.MODULATE && extractable != null) {
            final int newStatus = this.getStatus();

            if (newStatus != this.oldStatus) {
                this.drive.blinkCell(this.getSlot());
                this.oldStatus = newStatus;
            }
            if (this.drive.getProxy().isActive() && !(handler instanceof CreativeCellHandler)) {
                try {
                    this.drive.getProxy().getStorage().postAlterationOfStoredItems(this.getChannel(), Collections.singletonList(request.copy().setStackSize(-extractable.getStackSize())), this.source);
                } catch (GridAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return extractable;
    }
}
