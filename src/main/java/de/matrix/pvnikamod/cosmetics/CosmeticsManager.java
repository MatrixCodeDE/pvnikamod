package de.matrix.pvnikamod.cosmetics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.matrix.pvnikamod.cosmetics.models.CosmeticBase;
import de.matrix.pvnikamod.cosmetics.models.MDWings;
import de.matrix.pvnikamod.cosmetics.models.MiddleFinger;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CosmeticsManager {
    //MurderDrones Wings and Middlefinger coming soon :3
    private final MDWings mdWings = new MDWings();
    private final MiddleFinger middleFinger = new MiddleFinger();
    private HashMap<UUID, ArrayList<CosmeticBase>> usercosmetics = new HashMap();

    public CosmeticsManager() {
        ArrayList<CosmeticBase> c5q6 = new ArrayList<>();
        c5q6.add(mdWings);
        c5q6.add(middleFinger);
        ArrayList<CosmeticBase> cRoiyarvu = new ArrayList<>();
        cRoiyarvu.add(middleFinger);
        usercosmetics.put(UUID.fromString("bf0374f4-3401-45ba-85f8-0d5e1715825e"), c5q6);
        usercosmetics.put(UUID.fromString("97f23a35-eae9-4b49-ba59-5cf25d840fbd"), cRoiyarvu);
    }

    public void addCosmetics(UUID uuid, JsonArray cosArray){
        if (cosArray.size() == 0){
            return;
        }

        ArrayList<CosmeticBase> cList = new ArrayList<>();

        for (JsonElement json : cosArray) {
            JsonObject data = json.getAsJsonObject();
            int id = data.get("id").getAsInt();
            CosmeticBase cosmetic = CosmeticRegistry.getCosmeticById(id);
            cList.add(cosmetic);
        }
        usercosmetics.put(uuid, cList);

    }

    public void renderCosmetics(EntityPlayer player, float partialTicks){
        UUID uuid = player.getUniqueID();
        if(usercosmetics.containsKey(uuid)){
            ArrayList<CosmeticBase> cosmetics = usercosmetics.get(uuid);
            for(CosmeticBase cosmetic: cosmetics){
                if (!(cosmetic instanceof MiddleFinger) || player != Minecraft.getMinecraft().thePlayer)
                    cosmetic.render(player, 0.0625F, partialTicks);
            }
        }
    }
}
