package TFC.Items.Tools;

import java.util.Arrays;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import TFC.TFCItems;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Entities.EntityAdvancedArrowTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomBow extends ItemBow implements ISize
{
	
	public static final String[] bowPullIconNameArray = new String[] {"bow_pull_0", "bow_pull_1", "bow_pull_2"};
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    
    public ItemCustomBow(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        setCreativeTab(TFCTabs.TFCTools);
    }
    
    @Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
    {
        int j = this.getMaxItemUseDuration(par1ItemStack) - par4;

        ArrowLooseEvent event = new ArrowLooseEvent(par3EntityPlayer, par1ItemStack, j);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return;
        }
        j = event.charge;

        boolean flag = par3EntityPlayer.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, par1ItemStack) > 0;

        int arrowId = getArrowId(par3EntityPlayer.inventory);
        
        if (flag || arrowId > 0)
        {
            float f = j / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;

            if (f < 0.1D)
            {
                return;
            }

            if (f > 1.0F)
            {
                f = 1.0F;
            }

            EntityAdvancedArrowTFC entityarrow = new EntityAdvancedArrowTFC(par2World, par3EntityPlayer, f * 2.0F, arrowId);

            if (f == 1.0F)
            {
                entityarrow.setIsCritical(true);
            }

            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

            if (k > 0)
            {
                entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

            if (l > 0)
            {
                entityarrow.setKnockbackStrength(l);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
            {
                entityarrow.setFire(100);
            }

            par1ItemStack.damageItem(1, par3EntityPlayer);
            par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);

            if (flag)
            {
                entityarrow.canBePickedUp = 2;
            }
            else
            {
                par3EntityPlayer.inventory.consumeInventoryItem(arrowId);
            }

            if (!par2World.isRemote)
            {
                par2World.spawnEntityInWorld(entityarrow);
            }
        }
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        ArrowNockEvent event = new ArrowNockEvent(par3EntityPlayer, par1ItemStack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
        {
            return event.result;
        }

        if (par3EntityPlayer.capabilities.isCreativeMode || getArrowId(par3EntityPlayer.inventory)> 0)
        {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }

        return par1ItemStack;
    }    
    
    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.iconArray = new Icon[bowPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = par1IconRegister.registerIcon(bowPullIconNameArray[i]);
        }
        Item.bow.registerIcons(par1IconRegister);
    }

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.LARGE;
	}


	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}


	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private int getArrowId(InventoryPlayer inventory)
	{
        for (int j = 0; j < inventory.mainInventory.length; ++j)
        {
            if (inventory.mainInventory[j] != null)
            {
            	int id = inventory.mainInventory[j].itemID;
            	for (int i = 0; i < TFCItems.Arrows.length; i++)
            	{
            		if (TFCItems.Arrows[i].itemID == id)
            		{
            			return id;
            		}
            	}
            }
        }
        return -1;
	}
}
