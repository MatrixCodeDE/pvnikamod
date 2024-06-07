package de.matrix.mixin.gui;

import com.google.common.collect.Lists;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Mixin(GuiNewChat.class)
public abstract class MixinGuiNewChat extends Gui {

    @Final
    @Shadow
    private Minecraft mc;
    @Final
    @Shadow
    private List<ChatLine> drawnChatLines;
    @Final
    @Shadow
    private List<ChatLine> chatLines;
    @Shadow
    private int scrollPos;
    @Shadow
    private boolean isScrolled;

    @Shadow
    public abstract void deleteChatLine(int id);

    @Shadow
    public abstract int getChatWidth();
    @Shadow
    public abstract int getChatHeight();

    @Shadow
    public abstract float getChatScale();

    @Shadow
    public abstract boolean getChatOpen();
    @Shadow
    public abstract void scroll(int amount);

    @Inject(method = "setChatLine", at = @At("HEAD"), cancellable = true)
    private void setChatLine(IChatComponent chatComponent, int chatLineId, int updateCounter, boolean displayOnly, CallbackInfo ci) {
        GuiNewChat guiNewChat = (GuiNewChat)(Object)this;

        if (chatLineId != 0) {
            this.deleteChatLine(chatLineId);
        }

        int i = MathHelper.floor_float((float)this.getChatWidth() / this.getChatScale());
        List<IChatComponent> list = GuiUtilRenderComponents.splitText(chatComponent, i, Minecraft.getMinecraft().fontRendererObj, false, false);
        boolean bl = this.getChatOpen();

        IChatComponent iChatComponent;
        for(Iterator iterator = list.iterator(); iterator.hasNext(); this.drawnChatLines.add(0, new ChatLine(updateCounter, iChatComponent, chatLineId))) {
            iChatComponent = (IChatComponent)iterator.next();
            if (bl && this.scrollPos > 0) {
                this.isScrolled = true;
                this.scroll(1);
            }
        }

        int maxLen = PvnikaMod.getInstance().getConfig().chatSettings.maxLen;
        PvnikaMod.getInstance().getConfig().chatSettings.lens = this.chatLines.size();

        if (maxLen > 1){
            while(this.drawnChatLines.size() > maxLen) {
                this.drawnChatLines.remove(this.drawnChatLines.size() - 1);
            }
        }

        if (!displayOnly) {
            this.chatLines.add(0, new ChatLine(updateCounter, chatComponent, chatLineId));

            if (maxLen > 1){
                while(this.chatLines.size() > maxLen) {
                    this.chatLines.remove(this.chatLines.size() - 1);
                }
            }
        }

        ci.cancel();
    }
}
