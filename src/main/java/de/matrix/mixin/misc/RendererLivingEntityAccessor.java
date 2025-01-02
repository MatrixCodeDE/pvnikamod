package de.matrix.mixin.misc;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(RendererLivingEntity.class)
public interface RendererLivingEntityAccessor {

    @Accessor("layerRenderers")
    List<LayerRenderer> getLayerRenderers();

    @Invoker("removeLayer")
    public <V extends net.minecraft.entity.EntityLivingBase, U extends net.minecraft.client.renderer.entity.layers.LayerRenderer<V>> boolean invokeRemoveLayer(U layer);
}
