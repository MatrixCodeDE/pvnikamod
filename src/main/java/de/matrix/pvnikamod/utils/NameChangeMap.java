package de.matrix.pvnikamod.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;

import de.matrix.pvnikamod.listener.Chat;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

// File content copied from net.minecraftforge.common.UsernameCache

/**
 * Caches player's last known usernames
 * <p>
 * Modders should use {@link #getFromUUID(UUID)} to determine a players
 * last known username.<br>
 * For convenience, {@link #getMap()} is provided to get an immutable copy of
 * the caches underlying map.
 */
public final class NameChangeMap {

    private static Map<UUID, String> map = Maps.newHashMap();
    private static Map<String, UUID> origin = Maps.newHashMap();

    private static final Charset charset = Charsets.UTF_8;

    private static final File saveFile = new File(PvnikaMod.FOLDER_FILE, "usernames.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Logger log = LogManager.getLogger(net.minecraftforge.common.UsernameCache.class);

    private NameChangeMap() {}

    /**
     * Set a player's current username
     *
     * @param uuid
     *            the player's {@link java.util.UUID UUID}
     * @param username
     *            the player's username
     */
    public static void setUsername(UUID uuid, String username)
    {
        checkNotNull(uuid);
        checkNotNull(username);

        if (username.equals(map.get(uuid))) return;

        map.put(uuid, username);
        save();
    }

    /**
     * Remove a player's username from the cache
     *
     * @param uuid
     *            the player's {@link java.util.UUID UUID}
     * @return if the cache contained the user
     */
    public static boolean removeUsername(UUID uuid)
    {
        checkNotNull(uuid);

        if (map.remove(uuid) != null)
        {
            save();
            return true;
        }

        return false;
    }

    /**
     * Get the player's replaced display name
     * <p>
     * <b>May be <code>null</code></b>
     *
     * @param uuid
     *            the player's {@link java.util.UUID UUID}
     * @return the player's last known username, or <code>null</code> if the
     *         cache doesn't have a record of the last username
     */
    @Nullable
    public static String getFromUUID(UUID uuid)
    {
        checkNotNull(uuid);
        if (map.containsKey(uuid))
            return map.get(uuid);
        return null;
    }

    /**
     * Check if the cache contains the given player's username
     *
     * @param uuid
     *            the player's {@link java.util.UUID UUID}
     * @return if the cache contains a username for the given player
     */
    public static boolean containsUUID(UUID uuid)
    {
        checkNotNull(uuid);
        return map.containsKey(uuid);
    }

    public static boolean containsName(String name){
        checkNotNull(name);
        return origin.containsKey(name);
    }

    public static String getSafe(UUID uuid, String defaults){
        String replacement = getFromUUID(uuid);
        if (containsUUID(uuid)){
            return replacement;
        }
        return defaults;
    }

    public static void registerPlayer(EntityPlayer player){
        UUID uuid = player.getUniqueID();
        String name = player.getName();
        if (map.containsKey(uuid) && !origin.containsKey(name)){
            origin.put(name, uuid);
        }
    }

    public static String getModifiedByName(String name){
        if (origin.containsKey(name)){
            UUID uuid = origin.get(name);
            if (map.containsKey(uuid)){
                return map.get(uuid);
            }
        }
        return null;
    }

    public static List<String> getNames() {
        return new ArrayList<>(origin.keySet());
    }

    public static String getContainedName(IChatComponent component){
        String cache = null;
        int prio = 0;
        List<String> names = getNames();
        if (origin.isEmpty()){
            return null;
        }
        if (component instanceof ChatComponentTranslation) {
            ChatComponentTranslation translation = (ChatComponentTranslation) component;
            Object[] args = translation.getFormatArgs();
            if (translation.getKey().equals("chat.type.text")) {
                ChatComponentText sender = (ChatComponentText) args[0];
                String unformatted = sender.getUnformattedTextForChat();
                if (names.contains(unformatted)) {
                    return unformatted;
                }
            }
        }
        String plain = component.getFormattedText();
        for (String name: getNames()){
            if (plain.contains(name)){
                return name;
            }
        }
        return null;

    }

    /**
     * Get an immutable copy of the cache's underlying map
     *
     * @return the map
     */
    public static Map<UUID, String> getMap()
    {
        return ImmutableMap.copyOf(map);
    }

    /**
     * Save the cache to file
     */
    public static void save()
    {
        new SaveThread(gson.toJson(map)).start();
    }

    /**
     * Load the cache from file
     */
    public static void load()
    {
        if (!saveFile.exists()) return;

        try
        {

            String json = Files.toString(saveFile, charset);
            Type type = new TypeToken<Map<UUID, String>>() { private static final long serialVersionUID = 1L; }.getType();

            map = gson.fromJson(json, type);
        }
        catch (JsonSyntaxException e)
        {
            log.error("Could not parse username cache file as valid json, deleting file", e);
            saveFile.delete();
        }
        catch (IOException e)
        {
            log.error("Failed to read username cache file from disk, deleting file", e);
            saveFile.delete();
        }
        finally
        {
            // Can sometimes occur when the json file is malformed
            if (map == null)
            {
                map = Maps.newHashMap();
            }
        }
    }

    /**
     * Used for saving the {@link com.google.gson.Gson#toJson(Object) Gson}
     * representation of the cache to disk
     */
    private static class SaveThread extends Thread {

        /** The data that will be saved to disk */
        private final String data;

        public SaveThread(String data)
        {
            this.data = data;
        }

        @Override
        public void run()
        {
            try
            {
                // Make sure we don't save when another thread is still saving
                synchronized (saveFile)
                {
                    Files.write(data, saveFile, charset);
                }
            }
            catch (IOException e)
            {
                log.error("Failed to save username cache to file!", e);
            }
        }
    }


}

