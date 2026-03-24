package de.iani.cubesideutils.fabric.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import de.iani.cubesideutils.fabric.CubesideUtilsFabricClientMod;
import de.iani.cubesideutils.fabric.profilefetcher.CachedPlayerProfile;
import de.iani.cubesideutils.fabric.profilefetcher.ProfileFetcher;
import de.iani.cubesideutils.fabric.profilefetcher.ProfileProperty;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;

public class CustomHeadUtil {
    public static ItemStack getPlayerHead(UUID uuid) {
        CachedPlayerProfile cachedPlayerProfile;
        Multimap<String, Property> properties = HashMultimap.create();
        ProfileProperty profileProperty = null;
        try {
            cachedPlayerProfile = new ProfileFetcher(uuid).call();

            if (cachedPlayerProfile != null) {
                Optional<ProfileProperty> optionalProfileProperty = cachedPlayerProfile.getProperties().stream().findFirst();
                if (optionalProfileProperty.isPresent()) {
                    profileProperty = optionalProfileProperty.get();
                    String name = profileProperty.getName();
                    String value = profileProperty.getValue();
                    properties.put(name, new Property(name, value));
                }
            }
        } catch (Exception e) {
            CubesideUtilsFabricClientMod.LOGGER.error("Error while Loding Player Profile", e);
            return null;
        }

        if (profileProperty == null) {
            return null;
        }
        PropertyMap propertyMap = new PropertyMap(properties);

        ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
        GameProfile profile = new GameProfile(uuid, profileProperty.getName(), propertyMap);
        playerHead.set(DataComponents.PROFILE, ResolvableProfile.createResolved(profile));
        return playerHead;
    }
}
