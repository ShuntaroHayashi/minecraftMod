package forestMoon.client.gui;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import forestMoon.ExtendedPlayerProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class HUD {
//	public static final ResourceLocation icons = new ResourceLocation("forestmoon:textures/guis/coin_2.png");
	public static final ResourceLocation icons = new ResourceLocation("forestmoon:textures/guis/money.png");

    public static int top_height = 0;
    public static int left_height = 39;
    public static int right_height = 39;

    public static Minecraft mc = FMLClientHandler.instance().getClient();

    public static boolean AIR = false;


    @SideOnly(Side.CLIENT)
    private static int Cuont = 0;

    @SideOnly(Side.CLIENT)
    public static boolean isRenderer = false;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {


        if (event.type == ElementType.FOOD && mc.playerController.shouldDrawHUD()) {
            isRenderer = true;
        }

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Post event) {

        int width = event.resolution.getScaledWidth();
        int height = event.resolution.getScaledHeight();

//        if (!Config.HUD) return;

        if (isRenderer) {

            renderMoney(width, height);
            renderAddMoney(width, height);
            isRenderer = false;

        }

    }

    //現在の所持金を表示
    public static void renderMoney(int width, int height) {

    	Minecraft minecraft = FMLClientHandler.instance().getClient();


        minecraft.mcProfiler.startSection("money");

        bind(icons);

        int left = width / 2 + 9;
        int top = height - GuiIngameForge.right_height;//left_height-10+air;
        GuiIngameForge.right_height += 10;

        int money = 0;

        if (minecraft.thePlayer != null) {
        	ExtendedPlayerProperties pmp = ExtendedPlayerProperties.get(minecraft.thePlayer);
        	if(pmp != null){
        		money = pmp.getMoney();
        	}
//        	System.out.println("renderMoney:" + money);
        }

        //金持ち
        int m = 0;
        if (String.valueOf(money).length() == 8) {
            m = -8;
        } else if (String.valueOf(money).length() == 9) {
            m = -16;
        } else if (String.valueOf(money).length() == 10) {
            m = -24;
        }

        //if (money <= 10000000)
        //表示のx,y リソース開始点x,y リソース終点x,y
//        drawTexturedModalRect(left + m, top, 9, 0, 9, 9);//コイン
        drawTexturedModalRect(left + m, top, 9, 0, 9, 9);//コイン
        drawTexturedModalRect(left + 65, top, 0, 18, 9, 9);//M
        drawTexturedModalRect(left + 74, top, 9, 18, 9, 9);//P

        left += 56;

        for (int i = 1; i <= String.valueOf(money).length() && i <= 10; i += 1) {
            String s = String.valueOf(money).substring(String.valueOf(money).length() - i, String.valueOf(money).length() - i + 1);
            drawTexturedModalRect(left, top, 9 * Integer.parseInt(s), 9, 9, 9);
            left -= 8;
        }

        minecraft.mcProfiler.endSection();
        bind(Gui.icons);

    }

    //入手金表示
    public static void renderAddMoney(int width, int height) {

        mc.mcProfiler.startSection("addmoney");

        bind(icons);

        int left = width / 2 + 9;
        int top = height - GuiIngameForge.right_height;//left_height - 10 + air;

        int moneyD = 0;

        int moneyC = 0;

        boolean p = false;

        ExtendedPlayerProperties pmp = ExtendedPlayerProperties.get(mc.thePlayer);

        if (pmp != null) {

            moneyD = pmp.getDisplayMoney();
            moneyC = pmp.getCountMoney();
//            System.out.println(moneyC + "/" + moneyD);
            if (moneyC != 0) {
                Cuont = 210;
                pmp.setCountMoney(0);
            }

            if (moneyD < 0) {
                moneyD *= -1;
                p = true;
            }
        }

        if (Cuont > 0) {

            int g = 0;
            int m = 0;

            if (String.valueOf(Cuont).length() == 2) {

                if (Integer.parseInt(String.valueOf(Cuont).substring(0, 1)) < 8) {
                    if (p) {
                        m = 10 - (Integer.parseInt(String.valueOf(Cuont).substring(0, 1)) + 2);
                    } else {
                        g = 10 - (Integer.parseInt(String.valueOf(Cuont).substring(0, 1)) + 2);
                    }

                }

            } else if (String.valueOf(Cuont).length() == 1) {

                if (p) {
                    m = 8;
                } else {
                    g = 8;
                }

            }

            left += 56;

            int pm = 0;
            if (p) {
                pm = 9;
            }

            for (int i = 1; i <= String.valueOf(moneyD).length(); i += 1) {
                String s = String.valueOf(moneyD).substring(String.valueOf(moneyD).length() - i, String.valueOf(moneyD).length() - i + 1);
                drawTexturedModalRect(left, top + g, 9 * Integer.parseInt(s), 45 + pm + m, 9, 9 - g - m);
                left -= 8;
            }

            drawTexturedModalRect(left, top + g, 9 * 10, 45 + pm + m, 9, 9 - g - m);

            Cuont -= 4;
            if (Cuont <= 0 && mc.thePlayer != null) {
                //NBTTagCompound nbt = mc.thePlayer.getEntityData();
                //nbt.setInteger("moneyD",0);
                Cuont = 0;
                pmp.setDisplayMoney(0);
            }
            GuiIngameForge.right_height += 10;
        }

        mc.mcProfiler.endSection();
        bind(Gui.icons);

    }

    private static void bind(ResourceLocation res) {
        FMLClientHandler.instance().getClient().getTextureManager().bindTexture(res);
    }

    public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6) {
        float zLevel = -90.0F;

        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((par1 + 0), (par2 + par6), zLevel, ((par3 + 0) * f), ((par4 + par6) * f1));
        tessellator.addVertexWithUV((par1 + par5), (par2 + par6), zLevel, ((par3 + par5) * f), ((par4 + par6) * f1));
        tessellator.addVertexWithUV((par1 + par5), (par2 + 0), zLevel, ((par3 + par5) * f), ((par4 + 0) * f1));
        tessellator.addVertexWithUV((par1 + 0), (par2 + 0), zLevel, ((par3 + 0) * f), ((par4 + 0) * f1));
        tessellator.draw();
    }

    @SubscribeEvent
    public void onItemTooltipEvent(ItemTooltipEvent event) {

    }
}
