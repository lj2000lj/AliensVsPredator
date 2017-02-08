package org.avp.event.client.render.transforms;

import org.avp.entities.mob.EntityCombatSynthetic;
import org.avp.entities.mob.EntityEngineer;
import org.avp.entities.mob.EntityMarine;
import org.avp.entities.mob.EntitySpeciesYautja;
import org.avp.entities.mob.render.RenderFacehugger;
import org.avp.util.EntityRenderTransforms;

import com.arisux.mdxlib.lib.client.render.OpenGL;

import net.minecraft.entity.Entity;

public class FaceLocationTransforms
{
    public static void register()
    {
        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityCombatSynthetic.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityEngineer.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(115.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0.2F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntityMarine.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, -0.2F, 0F);
            }
        });

        RenderFacehugger.transforms.add(new EntityRenderTransforms(EntitySpeciesYautja.class)
        {
            @Override
            public void pre(Entity entity, float partialTicks)
            {
                ;
            }

            @Override
            public void post(Entity entity, float partialTicks)
            {
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.rotate(110.0F, 0.0F, 1.0F, 0.0F);
                OpenGL.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                OpenGL.translate(0F, 0F, 0.5F);
            }
        });
    }
}
