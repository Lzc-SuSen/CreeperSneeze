package com.susen36.creepersneeze.mixin;

import com.susen36.creepersneeze.ConfigHandler;
import com.susen36.creepersneeze.CreeperSneeze;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Collection;
import java.util.List;

@SuppressWarnings({"UnusedMixin", "unused"})
@Mixin(Creeper.class)
abstract public class MixinCreeper extends Monster {

    @Shadow
    private int oldSwell;
    @Shadow
    private int swell;


    @Shadow @Final
    private static final EntityDataAccessor<Boolean> DATA_IS_IGNITED = SynchedEntityData.defineId(MixinCreeper.class, EntityDataSerializers.BOOLEAN);



    protected MixinCreeper(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }




    @Overwrite
    private void explodeCreeper() {
        if (!this.level.isClientSide) {
           this.playAchooSound(); }

        this.spawnParticles();
        if(ConfigHandler.GENERAL.HungerPlayers.get()){
        this.effectTarget();}
            oldSwell=0;
            swell=0;
            this.entityData.set(DATA_IS_IGNITED, false);

    }
    private void spawnParticles() {

        Vec3 look = this.getLookAngle();


        double x = this.getX()  ;
        double y = this.getY() +1.4;
        double z = this.getZ() ;

        for (int i = 0; i < 3; i++) {
            double x0 = look.x();
            double y0 = look.y();
            double z0 = look.z();

            double velocity = 0.2 + this.getRandom().nextDouble() * 0.1;

            // spread flame
            x0 += this.getRandom().nextGaussian()*0.123;
            y0 += this.getRandom().nextGaussian()*0.123;
            z0 += this.getRandom().nextGaussian()*0.123;
            x0 *= velocity;
            y0 *= velocity;
            z0 *= velocity;

            this.getLevel().addParticle(ParticleTypes.SNEEZE, x, y, z, x0, y0, z0);
        }
    }


    private void playAchooSound() {
        playSound(CreeperSneeze.ACHOO, this.getRandom().nextFloat() * 0.5F, this.getRandom().nextFloat() * 0.5F);
    }
    private void effectTarget() {
        Collection<MobEffectInstance> collection = this.getActiveEffects();

        List<Entity> list = this.level.getEntities(this, this.getBoundingBox().inflate(5));
        if (!list.isEmpty()) {

            for (int j = 0; j < list.size(); ++j) {
                Entity entity = list.get(j);

                    if ((entity instanceof Player)) {

                        ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 80, 4));
                        if (!collection.isEmpty()){
                            for(MobEffectInstance mobeffectinstance : collection) {
                            ((LivingEntity) entity).addEffect(new MobEffectInstance(mobeffectinstance));
                           }
                        }
                    }
               }
           }
        }
    }
