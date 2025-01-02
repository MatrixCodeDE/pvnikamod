package de.matrix.pvnikamod.cosmetics.models;

import de.matrix.pvnikamod.cosmetics.CosmeticInfo;
import de.matrix.pvnikamod.cosmetics.CosmeticRegistry;
import de.matrix.pvnikamod.cosmetics.CosmeticsHelper;
import de.matrix.pvnikamod.main.PvnikaMod;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@CosmeticInfo(
		id = 2,
		binding = CosmeticsHelper.Bindings.TORSO
)
public class MDWings extends CosmeticBase {
	private final ModelRenderer rw_root;
	private final ModelRenderer joint;
	private final ModelRenderer ring;
	private final ModelRenderer rw_ext;

	private float timemod;


	public MDWings() {
		super();
		this.timemod = 0.0f;
		textureWidth = 64;
		textureHeight = 64;

		rw_root = new ModelRenderer(this);
		rw_root.setRotationPoint(-5.5F, 22.5F, 4.5F);
		setRotationAngle(rw_root, 0.0F, 0.0F, -1.0472F);
		rw_root.cubeList.add(new ModelBox(rw_root, 44, 12, -2.0F, -16.5F, -1.0F, 4, 2, 2, 0.0F, false));
		rw_root.cubeList.add(new ModelBox(rw_root, 0, 31, -10.875F, -18.5F, 0.0F, 10, 18, 0, 0.0F, false));

		ModelRenderer joint_front_r1 = new ModelRenderer(this);
		joint_front_r1.setRotationPoint(0.0F, -1.75F, -2.0F);
		rw_root.addChild(joint_front_r1);
		setRotationAngle(joint_front_r1, -1.5708F, -0.3054F, -1.5708F);
		joint_front_r1.cubeList.add(new ModelBox(joint_front_r1, 48, 7, 0.0F, -1.0F, -1.0F, 4, 1, 2, 0.0F, false));

		ModelRenderer joint_back_r1 = new ModelRenderer(this);
		joint_back_r1.setRotationPoint(0.0F, -1.75F, 2.0F);
		rw_root.addChild(joint_back_r1);
		setRotationAngle(joint_back_r1, -1.5708F, 0.3054F, -1.5708F);
		joint_back_r1.cubeList.add(new ModelBox(joint_back_r1, 52, 22, 0.0F, 0.0F, -1.0F, 4, 1, 2, 0.0F, false));

		ModelRenderer joint_down_r1 = new ModelRenderer(this);
		joint_down_r1.setRotationPoint(-2.0F, -1.75F, 0.0F);
		rw_root.addChild(joint_down_r1);
		setRotationAngle(joint_down_r1, 1.5708F, 0.0F, 1.789F);
		joint_down_r1.cubeList.add(new ModelBox(joint_down_r1, 36, 30, -4.0F, -1.0F, 0.0F, 4, 2, 1, 0.0F, false));

		ModelRenderer joint_up_r1 = new ModelRenderer(this);
		joint_up_r1.setRotationPoint(2.0F, -1.75F, 0.0F);
		rw_root.addChild(joint_up_r1);
		setRotationAngle(joint_up_r1, -1.5708F, 0.0F, -1.8239F);
		joint_up_r1.cubeList.add(new ModelBox(joint_up_r1, 48, 18, 0.0F, -1.0F, -1.0F, 5, 2, 1, 0.0F, false));

		ModelRenderer ring_down_r1 = new ModelRenderer(this);
		ring_down_r1.setRotationPoint(0.0F, -14.5F, 0.0F);
		rw_root.addChild(ring_down_r1);
		setRotationAngle(ring_down_r1, -1.5708F, 0.0F, -1.5708F);
		ring_down_r1.cubeList.add(new ModelBox(ring_down_r1, 42, 0, -5.0F, -1.0F, 0.0F, 5, 2, 1, 0.0F, false));

		ModelRenderer ring_down_r2 = new ModelRenderer(this);
		ring_down_r2.setRotationPoint(-2.0F, -14.5F, 0.0F);
		rw_root.addChild(ring_down_r2);
		setRotationAngle(ring_down_r2, -3.1416F, 0.0F, -1.6581F);
		ring_down_r2.cubeList.add(new ModelBox(ring_down_r2, 20, 39, -10.0F, -2.0F, -1.0F, 10, 2, 2, 0.0F, false));

		ModelRenderer ring_up_r1 = new ModelRenderer(this);
		ring_up_r1.setRotationPoint(2.0F, -14.5F, 0.0F);
		rw_root.addChild(ring_up_r1);
		setRotationAngle(ring_up_r1, -1.5708F, 0.0F, -1.4224F);
		ring_up_r1.cubeList.add(new ModelBox(ring_up_r1, 42, 27, -9.0F, -1.0F, -1.0F, 9, 2, 1, 0.0F, false));

		joint = new ModelRenderer(this);
		joint.setRotationPoint(0.0F, 0.0F, 0.0F);
		rw_root.addChild(joint);
		joint.cubeList.add(new ModelBox(joint, 23, 45, -2.0F, -1.75F, -2.0F, 4, 4, 4, 0.0F, false));
		joint.cubeList.add(new ModelBox(joint, 50, 36, 1.5F, -0.75F, -1.0F, 1, 2, 2, 0.0F, false));
		joint.cubeList.add(new ModelBox(joint, 50, 40, -2.5F, -0.75F, -1.0F, 1, 2, 2, 0.0F, false));

		ModelRenderer inner_r1 = new ModelRenderer(this);
		inner_r1.setRotationPoint(0.5F, 1.0F, -0.5F);
		joint.addChild(inner_r1);
		setRotationAngle(inner_r1, -1.5708F, 0.0F, -1.5708F);
		inner_r1.cubeList.add(new ModelBox(inner_r1, 44, 36, -1.75F, -1.5F, -1.5F, 1, 2, 2, 0.0F, false));

		ModelRenderer front_r1 = new ModelRenderer(this);
		front_r1.setRotationPoint(0.5F, 0.75F, -0.75F);
		joint.addChild(front_r1);
		setRotationAngle(front_r1, 0.0F, 1.5708F, 0.0F);
		front_r1.cubeList.add(new ModelBox(front_r1, 44, 32, 0.75F, -1.5F, -1.5F, 1, 2, 2, 0.0F, false));

		ModelRenderer back_r1 = new ModelRenderer(this);
		back_r1.setRotationPoint(0.5F, 0.75F, -0.25F);
		joint.addChild(back_r1);
		setRotationAngle(back_r1, 0.0F, 1.5708F, 0.0F);
		back_r1.cubeList.add(new ModelBox(back_r1, 50, 32, -2.75F, -1.5F, -1.5F, 1, 2, 2, 0.0F, false));

		ring = new ModelRenderer(this);
		ring.setRotationPoint(0.0F, -16.5F, 0.0F);
		rw_root.addChild(ring);


		ModelRenderer cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-1.366F, -0.5F, 0.0F);
		ring.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, 0.0F, -1.0472F);
		cube_r1.cubeList.add(new ModelBox(cube_r1, 60, 0, 0.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-1.866F, -1.366F, 0.0F);
		ring.addChild(cube_r2);
		setRotationAngle(cube_r2, 0.0F, 0.0F, -2.0944F);
		cube_r2.cubeList.add(new ModelBox(cube_r2, 60, 0, -1.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-1.866F, -2.366F, 0.0F);
		ring.addChild(cube_r3);
		setRotationAngle(cube_r3, 0.0F, 0.0F, -3.1416F);
		cube_r3.cubeList.add(new ModelBox(cube_r3, 60, 0, -1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-1.366F, -3.2321F, 0.0F);
		ring.addChild(cube_r4);
		setRotationAngle(cube_r4, 0.0F, 0.0F, 2.0944F);
		cube_r4.cubeList.add(new ModelBox(cube_r4, 60, 0, 0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-0.5F, -3.7321F, 0.0F);
		ring.addChild(cube_r5);
		setRotationAngle(cube_r5, 0.0F, 0.0F, 1.0472F);
		cube_r5.cubeList.add(new ModelBox(cube_r5, 60, 0, 0.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.5F, -3.7321F, 0.0F);
		ring.addChild(cube_r6);
		setRotationAngle(cube_r6, 0.0F, 0.0F, 0.0F);
		cube_r6.cubeList.add(new ModelBox(cube_r6, 60, 0, -1.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(1.366F, -3.2321F, 0.0F);
		ring.addChild(cube_r7);
		setRotationAngle(cube_r7, 0.0F, 0.0F, -1.0472F);
		cube_r7.cubeList.add(new ModelBox(cube_r7, 60, 0, -1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r8 = new ModelRenderer(this);
		cube_r8.setRotationPoint(1.866F, -2.366F, 0.0F);
		ring.addChild(cube_r8);
		setRotationAngle(cube_r8, 0.0F, 0.0F, -2.0944F);
		cube_r8.cubeList.add(new ModelBox(cube_r8, 60, 0, 0.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r9 = new ModelRenderer(this);
		cube_r9.setRotationPoint(1.866F, -1.366F, 0.0F);
		ring.addChild(cube_r9);
		setRotationAngle(cube_r9, 0.0F, 0.0F, -3.1416F);
		cube_r9.cubeList.add(new ModelBox(cube_r9, 60, 0, 0.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r10 = new ModelRenderer(this);
		cube_r10.setRotationPoint(1.366F, -0.5F, 0.0F);
		ring.addChild(cube_r10);
		setRotationAngle(cube_r10, 0.0F, 0.0F, 2.0944F);
		cube_r10.cubeList.add(new ModelBox(cube_r10, 60, 0, -1.0F, 0.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r11 = new ModelRenderer(this);
		cube_r11.setRotationPoint(0.5F, 0.0F, 0.0F);
		ring.addChild(cube_r11);
		setRotationAngle(cube_r11, 0.0F, 0.0F, 1.0472F);
		cube_r11.cubeList.add(new ModelBox(cube_r11, 60, 0, -1.0F, -1.0F, -0.5F, 1, 1, 1, 0.0F, false));

		ModelRenderer cube_r12 = new ModelRenderer(this);
		cube_r12.setRotationPoint(0.0F, -2.0F, 0.0F);
		ring.addChild(cube_r12);
		setRotationAngle(cube_r12, 0.0F, 0.0F, 0.0F);
		cube_r12.cubeList.add(new ModelBox(cube_r12, 60, 0, -0.5F, 1.0F, -0.5F, 1, 1, 1, 0.0F, false));

		rw_ext = new ModelRenderer(this);
		rw_ext.setRotationPoint(0.0F, -18.25F, 0.0F);
		rw_root.addChild(rw_ext);
		rw_ext.cubeList.add(new ModelBox(rw_ext, 36, 0, -2.0F, -15.0F, -1.0F, 1, 13, 2, 0.0F, false));
		rw_ext.cubeList.add(new ModelBox(rw_ext, 36, 15, -1.0F, -13.0F, -1.0F, 1, 11, 2, 0.0F, false));
		rw_ext.cubeList.add(new ModelBox(rw_ext, 58, 30, 0.0F, -11.0F, -1.0F, 1, 9, 2, 0.0F, false));
		rw_ext.cubeList.add(new ModelBox(rw_ext, 52, 1, 1.0F, -6.0F, -1.0F, 1, 4, 2, 0.0F, false));
		rw_ext.cubeList.add(new ModelBox(rw_ext, 0, 0, -20.0F, -32.0F, 0.0F, 18, 31, 0, 0.0F, false));

		ModelRenderer e_up_2_r1 = new ModelRenderer(this);
		e_up_2_r1.setRotationPoint(-1.0F, -15.0F, 0.0F);
		rw_ext.addChild(e_up_2_r1);
		setRotationAngle(e_up_2_r1, 0.0F, 0.0F, -0.48F);
		e_up_2_r1.cubeList.add(new ModelBox(e_up_2_r1, 42, 3, -1.0F, -0.375F, -1.0F, 1, 5, 2, 0.0F, false));

		ModelRenderer e_up_1_r1 = new ModelRenderer(this);
		e_up_1_r1.setRotationPoint(2.0F, -6.0F, 0.0F);
		rw_ext.addChild(e_up_1_r1);
		setRotationAngle(e_up_1_r1, 0.0F, 0.0F, -0.1745F);
		e_up_1_r1.cubeList.add(new ModelBox(e_up_1_r1, 42, 17, -1.0F, -5.0F, -1.0F, 1, 5, 2, 0.0F, false));
	}


	public void render(EntityPlayer player, float scale, float partialTicks) {
		super.render(player, scale, partialTicks);
		if (player.isInvisible()){
			return;
		}
		float modulo = 4000F;
		GL11.glPushMatrix();
		float mod = CosmeticsHelper.wingAccelerator(player);
		this.timemod = (this.timemod + mod) % modulo;
		float currentTime = (float) (System.currentTimeMillis() % (long) modulo);
		float time = (float) ((((currentTime + this.timemod) % modulo) / 2000.0F) * Math.PI);

		/*System.out.print("\nmod: " + mod);
		System.out.print("\ntimemod: " + this.timemod);
		System.out.print("\ncurrent: " + currentTime);
		System.out.print("\ntime: " + time + "\n");
		System.out.println();*/

		this.bindCosmeticTexture(new ResourceLocation(PvnikaMod.MODID,"textures/cosmetics/mdwings.png"));

		//CosmeticsHelper.bindToTorso(player, scale, partialTicks);
		CosmeticsHelper.bindToTorso(player, scale, partialTicks);


		for (int wing = 0; wing < 2; wing++){
			GL11.glEnable(GL11.GL_CULL_FACE);

			this.rw_root.rotateAngleX = 0.015f - (float)((Math.cos(time) + 1.0F) * 0.125F);
			this.rw_root.rotateAngleY = (float)((Math.cos(time) + 1.0F) * 0.25F);
			this.rw_root.rotateAngleZ = -1.0f - (float)((Math.sin(time) + 1.0F) * 0.325F);

			this.rw_ext.rotateAngleZ = (float)((Math.cos(time + 0.15F)) * 0.5F);

			this.rw_root.render(scale);

			GL11.glScalef(-1.0F, 1.0F, 1.0F);
			if (wing == 0){
				GL11.glCullFace(GL11.GL_FRONT);
			}
		}
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		super.render(player, scale, partialTicks);
	}

	private float interpolateRotation(float prevYaw, float yaw, float partialTicks) {
		float f = yaw - prevYaw;

		while (f < -180.0F) {
			f += 360.0F;
		}

		while (f >= 180.0F) {
			f -= 360.0F;
		}

		return prevYaw + partialTicks * f;
	}

	static {
		CosmeticRegistry.registerCosmetic(MDWings.class);
	}
}