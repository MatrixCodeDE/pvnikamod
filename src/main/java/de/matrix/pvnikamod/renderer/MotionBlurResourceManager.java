//Source https://github.com/Sk1erLLC/MotionBlur

package de.matrix.pvnikamod.renderer;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Set;

public class MotionBlurResourceManager implements IResourceManager {
    public MotionBlurResourceManager() {
    }

    @Override
    public Set<String> getResourceDomains() {
        return null;
    }

    @Override
    public IResource getResource(ResourceLocation location) {
        return new MotionBlurResource();
    }

    @Override
    public List<IResource> getAllResources(ResourceLocation location) {
        System.out.println("Got all");
        return (List<IResource>) new MotionBlurResource();
    }
}
