package TFC.Items.Tools;

import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemArrowHead extends ItemMiscToolHead {

	public ItemArrowHead(int i) {
		super(i);
        this.setMaxDamage(0);
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
	
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.LIGHT;
	}
	
	
}
