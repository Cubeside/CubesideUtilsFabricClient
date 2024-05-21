package de.iani.cubesideutils.fabric.item;

import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import de.iani.cubesideutils.fabric.CubesideUtilsFabricClientMod;
import de.iani.cubesideutils.fabric.profilefetcher.CachedPlayerProfile;
import de.iani.cubesideutils.fabric.profilefetcher.ProfileFetcher;
import de.iani.cubesideutils.fabric.profilefetcher.ProfileProperty;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Optional;
import java.util.UUID;

public class CustomHeadUtil {
    public static ItemStack getPlayerHead(UUID uuid) {
        CachedPlayerProfile cachedPlayerProfile;
        PropertyMap propertyMap = new PropertyMap();
        ProfileProperty profileProperty = null;
        try {
            cachedPlayerProfile = new ProfileFetcher(uuid).call();

            if (cachedPlayerProfile != null) {
                Optional<ProfileProperty> optionalProfileProperty = cachedPlayerProfile.getProperties().stream().findFirst();
                if (optionalProfileProperty.isPresent()) {
                    profileProperty = optionalProfileProperty.get();
                    String name = profileProperty.getName();
                    String value = profileProperty.getValue();
                    propertyMap.put(name, new Property(name, value));
                }
            }
        } catch (Exception e) {
            CubesideUtilsFabricClientMod.LOGGER.error("Error while Loding Player Profile", e);
            return null;
        }

        if (profileProperty == null) {
            return null;
        }

        ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
        playerHead.set(DataComponentTypes.PROFILE, new ProfileComponent(Optional.of(profileProperty.getName()), Optional.of(uuid), propertyMap));
        return playerHead;
    }
}
