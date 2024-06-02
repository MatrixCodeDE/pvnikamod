package de.matrix.pvnikamod.cosmetics;

import de.matrix.pvnikamod.cosmetics.models.CosmeticBase;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CosmeticRegistry {

    private static final Map<Integer, Class<? extends CosmeticBase>> registry = new HashMap<>();

    public static void registerCosmetic(Class<? extends CosmeticBase> cosmeticClass) {
        CosmeticInfo info = cosmeticClass.getAnnotation(CosmeticInfo.class);
        if (info != null) {
            registry.put(info.id(), cosmeticClass);
        }
    }

    public static CosmeticBase getCosmeticById(int id) {
        Class<? extends CosmeticBase> cosmeticClass = registry.get(id);
        if (cosmeticClass != null) {
            try {
                return cosmeticClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
