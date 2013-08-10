package TFC.Items.Tools;

import net.minecraft.item.EnumToolMaterial;
import TFC.TFCItems;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Items.ItemTerra;

public class ItemArrow extends ItemTerra implements ISize {

	private double damage;
	private int headId;
	private double headBreackChange;
	private double arrowBreakChage;

	public ItemArrow(int par1, float damage, int maxUses, double arrowBreakChange , int headId ) {
		super(par1);
		this.stackable = true;
		this.setFolder("tools/");
		this.setCreativeTab(TFCTabs.TFCTools);
		this.damage = damage*0.55;
		this.headBreackChange = 1.025-Math.sqrt(Math.sqrt(maxUses/TFCItems.RedSteelUses));
		this.arrowBreakChage = arrowBreakChange;
		this.headId = headId;
	}

	public ItemArrow(int par1, float damage, int maxUses, int headId ) {
		this(par1, damage, maxUses, 0.2, headId);
	}
	
	public ItemArrow(int par1, EnumToolMaterial material, double arrowBreakChange, int headId) {
		this(par1, material.getDamageVsEntity(), material.getMaxUses(), arrowBreakChange, headId);
	}

	public ItemArrow(int par1, EnumToolMaterial material, int headId) {
		this(par1, material.getDamageVsEntity(), material.getMaxUses(), headId);
	}
		
	public double getDamage()
	{
		return damage;
	}
	
	public double getHeadBreakChange()
	{
		return headBreackChange;
	}
	
	public double getArrowBreakChange()
	{
		return arrowBreakChage;
	}
	
	public int getHeadId()
	{
		return headId;
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight() {
		return EnumWeight.LIGHT;
	}
}
