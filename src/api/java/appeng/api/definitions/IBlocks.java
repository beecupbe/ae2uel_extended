/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 AlgorithmX2
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package appeng.api.definitions;


/**
 * A list of all blocks in AE
 */
public interface IBlocks
{
	/*
	 * world gen
	 */
	IBlockDefinition quartzOre();

	IBlockDefinition quartzOreCharged();

	IBlockDefinition matrixFrame();

	/*
	 * decorative
	 */
	IBlockDefinition quartzBlock();

	IBlockDefinition quartzPillar();

	IBlockDefinition chiseledQuartzBlock();

	IBlockDefinition quartzGlass();

	IBlockDefinition quartzVibrantGlass();

	IBlockDefinition quartzFixture();

	IBlockDefinition fluixBlock();

	IBlockDefinition skyStoneBlock();

	IBlockDefinition smoothSkyStoneBlock();

	IBlockDefinition skyStoneBrick();

	IBlockDefinition skyStoneSmallBrick();

	IBlockDefinition skyStoneChest();

	IBlockDefinition smoothSkyStoneChest();

	IBlockDefinition skyCompass();

	IBlockDefinition skyStoneStairs();

	IBlockDefinition smoothSkyStoneStairs();

	IBlockDefinition skyStoneBrickStairs();

	IBlockDefinition skyStoneSmallBrickStairs();

	IBlockDefinition fluixStairs();

	IBlockDefinition quartzStairs();

	IBlockDefinition chiseledQuartzStairs();

	IBlockDefinition quartzPillarStairs();

	IBlockDefinition skyStoneSlab();

	IBlockDefinition smoothSkyStoneSlab();

	IBlockDefinition skyStoneBrickSlab();

	IBlockDefinition skyStoneSmallBrickSlab();

	IBlockDefinition fluixSlab();

	IBlockDefinition quartzSlab();

	IBlockDefinition chiseledQuartzSlab();

	IBlockDefinition quartzPillarSlab();

	/*
	 * misc
	 */
	ITileDefinition grindstone();

	ITileDefinition crank();
	ITileDefinition crankImp();


	ITileDefinition inscriber();

	ITileDefinition wirelessAccessPoint();

	ITileDefinition charger();

	IBlockDefinition tinyTNT();

	ITileDefinition securityStation();

	/*
	 * quantum Network Bridge
	 */
	ITileDefinition quantumRing();

	ITileDefinition quantumLink();

	/*
	 * spatial iO
	 */
	ITileDefinition spatialPylon();

	ITileDefinition spatialIOPort();

	/*
	 * Bus / cables
	 */
	ITileDefinition multiPart();

	/*
	 * machines
	 */
	ITileDefinition controller();

	ITileDefinition drive();
	ITileDefinition driveImp();


	ITileDefinition chest();

	ITileDefinition iface();
	ITileDefinition ifaceImp();
	ITileDefinition ifaceAdv();
	ITileDefinition ifacePer();
	ITileDefinition ifacePatterns();

	ITileDefinition fluidIface();

	ITileDefinition cellWorkbench();

	ITileDefinition iOPort();
	ITileDefinition iOPortImp();

	ITileDefinition condenser();

	ITileDefinition energyAcceptor();

	ITileDefinition vibrationChamber();

	ITileDefinition quartzGrowthAccelerator();

	ITileDefinition energyCell();

	ITileDefinition energyCellDense();

	ITileDefinition energyCellImproved();
	ITileDefinition energyCellAdvanced();
	ITileDefinition energyCellPerfect();


	ITileDefinition energyCellCreative();

	// rv1
	ITileDefinition craftingUnit();

	ITileDefinition craftingAccelerator();
	ITileDefinition craftingAcceleratorx4();
	ITileDefinition craftingAcceleratorx16();
	ITileDefinition craftingAcceleratorx64();
	ITileDefinition craftingAcceleratorx128();
	ITileDefinition craftingAcceleratorx256();
	ITileDefinition craftingAcceleratorx512();
	ITileDefinition craftingAcceleratorx1024();
	ITileDefinition craftingAcceleratorCreative();

	ITileDefinition craftingStorage1k();

	ITileDefinition craftingStorage4k();

	ITileDefinition craftingStorage16k();

	ITileDefinition craftingStorage64k();
	ITileDefinition craftingStorage1mb();
	ITileDefinition craftingStorage4mb();
	ITileDefinition craftingStorage16mb();
	ITileDefinition craftingStorage64mb();
	ITileDefinition craftingStorage256mb();
	ITileDefinition craftingStorage1gb();
	ITileDefinition craftingStorage15gb();

	ITileDefinition craftingMonitor();

	ITileDefinition molecularAssembler();
	ITileDefinition molecularAssemblerImp();
	ITileDefinition molecularAssemblerCreative();

	ITileDefinition lightDetector();

	ITileDefinition paint();
}
