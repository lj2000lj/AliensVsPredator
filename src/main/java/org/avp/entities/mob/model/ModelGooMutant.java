package org.avp.entities.mob.model;

import com.arisux.mdxlib.lib.client.Model;

import net.minecraft.client.model.ModelRenderer;

public class ModelGooMutant extends Model
{
    public ModelRenderer headOverlay;
    public ModelRenderer rArm;
    public ModelRenderer lLeg;
    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer lArm;
    public ModelRenderer rLeg;
    public ModelRenderer head2;
    public ModelRenderer head3;
    public ModelRenderer lspike;
    public ModelRenderer rspike;
    public ModelRenderer spines;

    public ModelGooMutant()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;
        this.headOverlay = new ModelRenderer(this, 32, 0);
        this.headOverlay.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.headOverlay.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.5F);
        this.body = new ModelRenderer(this, 16, 16);
        this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, 0.0F);
        this.rLeg = new ModelRenderer(this, 0, 16);
        this.rLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.rLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.spines = new ModelRenderer(this, 0, 48);
        this.spines.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.spines.addBox(0.0F, 0.7F, 2.0F, 0, 10, 2, 0.0F);
        this.head3 = new ModelRenderer(this, 67, 13);
        this.head3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head3.addBox(-3.0F, -8.6F, 5.4F, 6, 6, 4, 0.0F);
        this.setRotation(head3, -0.18203784098300857F, 0.0F, 0.0F);
        this.rArm = new ModelRenderer(this, 40, 16);
        this.rArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
        this.rArm.addBox(-2.9F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.lspike = new ModelRenderer(this, 0, 36);
        this.lspike.mirror = true;
        this.lspike.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.lspike.addBox(2.5F, 0.8F, 1.1F, 0, 5, 7, 0.0F);
        this.setRotation(lspike, 0.0F, 0.22759093446006054F, 0.0F);
        this.rspike = new ModelRenderer(this, 0, 36);
        this.rspike.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.rspike.addBox(-2.5F, 0.8F, 1.1F, 0, 5, 7, 0.0F);
        this.setRotation(rspike, 0.0F, -0.22759093446006054F, 0.0F);
        this.lLeg = new ModelRenderer(this, 0, 16);
        this.lLeg.mirror = true;
        this.lLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.lLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head = new ModelRenderer(this, 0, 0);
        this.head.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, 0.0F);
        this.lArm = new ModelRenderer(this, 40, 16);
        this.lArm.mirror = true;
        this.lArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.lArm.addBox(-1.1F, -2.0F, -2.0F, 4, 12, 4, 0.0F);
        this.head2 = new ModelRenderer(this, 67, 0);
        this.head2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.head2.addBox(-3.5F, -8.2F, 3.2F, 7, 7, 3, 0.0F);
        this.setRotation(head2, -0.091106186954104F, 0.0F, 0.0F);
        this.body.addChild(this.spines);
        this.head.addChild(this.head3);
        this.body.addChild(this.lspike);
        this.body.addChild(this.rspike);
        this.head.addChild(this.head2);
    }

    @Override
    protected void render(IRenderObject renderObject, float boxTranslation)
    {
        this.headOverlay.render(boxTranslation);
        this.body.render(boxTranslation);
        this.rLeg.render(boxTranslation);
        this.rArm.render(boxTranslation);
        this.lLeg.render(boxTranslation);
        this.head.render(boxTranslation);
        this.lArm.render(boxTranslation);
    }
}
