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

package appeng.client.render.crafting;


import appeng.block.crafting.BlockCraftingUnit;
import appeng.core.AppEng;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;


/**
 * The built-in model for the connected texture crafting cube.
 */
class CraftingCubeModel implements IModel {

    private final static ResourceLocation RING_CORNER = texture("ring_corner");
    private final static ResourceLocation RING_SIDE_HOR = texture("ring_side_hor");
    private final static ResourceLocation RING_SIDE_VER = texture("ring_side_ver");
    private final static ResourceLocation UNIT_BASE = texture("unit_base");
    private final static ResourceLocation LIGHT_BASE = texture("light_base");
    private final static ResourceLocation ACCELERATOR_LIGHT = texture("accelerator_light");
    private final static ResourceLocation ACCELERATOR_4X_LIGHT = texture("accelerator_4x_light");
    private final static ResourceLocation ACCELERATOR_16X_LIGHT = texture("accelerator_16x_light");
    private final static ResourceLocation ACCELERATOR_64X_LIGHT = texture("accelerator_64x_light");
    private final static ResourceLocation ACCELERATOR_128X_LIGHT = texture("accelerator_128x_light");
    private final static ResourceLocation ACCELERATOR_256X_LIGHT = texture("accelerator_256x_light");
    private final static ResourceLocation ACCELERATOR_512X_LIGHT = texture("accelerator_512x_light");
    private final static ResourceLocation ACCELERATOR_1024X_LIGHT = texture("accelerator_1024x_light");
    private final static ResourceLocation ACCELERATOR_CREATIVE_LIGHT = texture("accelerator_creative_light");


    private final static ResourceLocation STORAGE_1K_LIGHT = texture("storage_1k_light");
    private final static ResourceLocation STORAGE_4K_LIGHT = texture("storage_4k_light");
    private final static ResourceLocation STORAGE_16K_LIGHT = texture("storage_16k_light");
    private final static ResourceLocation STORAGE_64K_LIGHT = texture("storage_64k_light");
    private final static ResourceLocation STORAGE_1MB_LIGHT = texture("storage_1mb_light");
    private final static ResourceLocation STORAGE_4MB_LIGHT = texture("storage_4mb_light");
    private final static ResourceLocation STORAGE_16MB_LIGHT = texture("storage_16mb_light");
    private final static ResourceLocation STORAGE_64MB_LIGHT = texture("storage_64mb_light");
    private final static ResourceLocation STORAGE_256MB_LIGHT = texture("storage_256mb_light");
    private final static ResourceLocation STORAGE_1GB_LIGHT = texture("storage_1gb_light");
    private final static ResourceLocation STORAGE_2GB_LIGHT = texture("storage_15gb_light");
    private final static ResourceLocation STORAGE_CREATIVE_LIGHT = texture("storage_creative_light");

    private final static ResourceLocation MONITOR_BASE = texture("monitor_base");
    private final static ResourceLocation MONITOR_LIGHT_DARK = texture("monitor_light_dark");
    private final static ResourceLocation MONITOR_LIGHT_MEDIUM = texture("monitor_light_medium");
    private final static ResourceLocation MONITOR_LIGHT_BRIGHT = texture("monitor_light_bright");

    private final BlockCraftingUnit.CraftingUnitType type;

    CraftingCubeModel(BlockCraftingUnit.CraftingUnitType type) {
        this.type = type;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.of(RING_CORNER, RING_SIDE_HOR, RING_SIDE_VER, UNIT_BASE, LIGHT_BASE, ACCELERATOR_LIGHT, ACCELERATOR_4X_LIGHT, ACCELERATOR_16X_LIGHT, ACCELERATOR_64X_LIGHT, ACCELERATOR_128X_LIGHT, ACCELERATOR_256X_LIGHT, ACCELERATOR_512X_LIGHT, ACCELERATOR_1024X_LIGHT, ACCELERATOR_CREATIVE_LIGHT,
                STORAGE_1K_LIGHT, STORAGE_4K_LIGHT, STORAGE_16K_LIGHT, STORAGE_64K_LIGHT, STORAGE_1MB_LIGHT, STORAGE_4MB_LIGHT, STORAGE_16MB_LIGHT, STORAGE_64MB_LIGHT, STORAGE_256MB_LIGHT, STORAGE_1GB_LIGHT, STORAGE_2GB_LIGHT, STORAGE_CREATIVE_LIGHT, MONITOR_BASE, MONITOR_LIGHT_DARK, MONITOR_LIGHT_MEDIUM, MONITOR_LIGHT_BRIGHT);
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // Retrieve our textures and pass them on to the baked model
        TextureAtlasSprite ringCorner = bakedTextureGetter.apply(RING_CORNER);
        TextureAtlasSprite ringSideHor = bakedTextureGetter.apply(RING_SIDE_HOR);
        TextureAtlasSprite ringSideVer = bakedTextureGetter.apply(RING_SIDE_VER);

        switch (this.type) {
            case UNIT:
                return new UnitBakedModel(format, ringCorner, ringSideHor, ringSideVer, bakedTextureGetter.apply(UNIT_BASE));
            case STORAGE_1K:
            case STORAGE_4K:
            case STORAGE_16K:
            case STORAGE_64K, STORAGE_1MB, STORAGE_4MB, STORAGE_16MB, STORAGE_64MB, STORAGE_256MB, STORAGE_1GB, STORAGE_2GB, STORAGE_CREATIVE, ACCELERATOR, ACCELERATOR_4X, ACCELERATOR_16X, ACCELERATOR_64X, ACCELERATOR_128X, ACCELERATOR_256X, ACCELERATOR_512X, ACCELERATOR_1024X, ACCELERATOR_CREATIVE:
                return new LightBakedModel(format, ringCorner, ringSideHor, ringSideVer, bakedTextureGetter
                        .apply(LIGHT_BASE), getLightTexture(bakedTextureGetter, this.type));
            case MONITOR:
                return new MonitorBakedModel(format, ringCorner, ringSideHor, ringSideVer, bakedTextureGetter.apply(UNIT_BASE), bakedTextureGetter
                        .apply(MONITOR_BASE), bakedTextureGetter.apply(
                        MONITOR_LIGHT_DARK), bakedTextureGetter.apply(MONITOR_LIGHT_MEDIUM), bakedTextureGetter.apply(MONITOR_LIGHT_BRIGHT));
            default:
                throw new IllegalArgumentException("Unsupported crafting unit type: " + this.type);
        }
    }

    private static TextureAtlasSprite getLightTexture(Function<ResourceLocation, TextureAtlasSprite> textureGetter, BlockCraftingUnit.CraftingUnitType type) {
        switch (type) {
            case ACCELERATOR:
                return textureGetter.apply(ACCELERATOR_LIGHT);
            case ACCELERATOR_4X:
                return textureGetter.apply(ACCELERATOR_4X_LIGHT);
            case ACCELERATOR_16X:
                return textureGetter.apply(ACCELERATOR_16X_LIGHT);
            case ACCELERATOR_64X:
                return textureGetter.apply(ACCELERATOR_64X_LIGHT);
            case ACCELERATOR_128X:
                return textureGetter.apply(ACCELERATOR_128X_LIGHT);
            case ACCELERATOR_256X:
                return textureGetter.apply(ACCELERATOR_256X_LIGHT);
            case ACCELERATOR_512X:
                return textureGetter.apply(ACCELERATOR_512X_LIGHT);
            case ACCELERATOR_1024X:
                return textureGetter.apply(ACCELERATOR_1024X_LIGHT);
            case ACCELERATOR_CREATIVE:
                return textureGetter.apply(ACCELERATOR_CREATIVE_LIGHT);
            case STORAGE_1K:
                return textureGetter.apply(STORAGE_1K_LIGHT);
            case STORAGE_4K:
                return textureGetter.apply(STORAGE_4K_LIGHT);
            case STORAGE_16K:
                return textureGetter.apply(STORAGE_16K_LIGHT);
            case STORAGE_64K:
                return textureGetter.apply(STORAGE_64K_LIGHT);
            case STORAGE_1MB:
                return textureGetter.apply(STORAGE_1MB_LIGHT);
            case STORAGE_4MB:
                return textureGetter.apply(STORAGE_4MB_LIGHT);
            case STORAGE_16MB:
                return textureGetter.apply(STORAGE_16MB_LIGHT);
            case STORAGE_64MB:
                return textureGetter.apply(STORAGE_64MB_LIGHT);
            case STORAGE_256MB:
                return textureGetter.apply(STORAGE_256MB_LIGHT);
            case STORAGE_1GB:
                return textureGetter.apply(STORAGE_1GB_LIGHT);
            case STORAGE_2GB:
                return textureGetter.apply(STORAGE_2GB_LIGHT);
            case STORAGE_CREATIVE:
                return textureGetter.apply(STORAGE_CREATIVE_LIGHT);
            default:
                throw new IllegalArgumentException("Crafting unit type " + type + " does not use a light texture. Beecube, are you stupid?");
        }
    }

    @Override
    public IModelState getDefaultState() {
        return TRSRTransformation.identity();
    }

    private static ResourceLocation texture(String name) {
        return new ResourceLocation(AppEng.MOD_ID, "blocks/crafting/" + name);
    }
}
