package com.dudu.sieve.particles

import net.minecraft.EntityFX
import net.minecraft.Icon
import net.minecraft.Tessellator
import net.minecraft.World

class ParticleSieve(
    par1World: World?,
    par2: Double,
    par4: Double,
    par6: Double,
    par8: Double,
    par10: Double,
    par12: Double,
    icon: Icon?
) :
    EntityFX(par1World, par2, par4, par6, par8, par10, par12) {
    init {
        this.particleBlue = 1.0f
        this.particleGreen = this.particleBlue
        this.particleRed = this.particleGreen
        this.setParticleIcon(icon)
        this.setSize(0.02f, 0.02f)
        this.particleScale = 0.07f + (rand.nextFloat() * 0.1f)
        this.motionX *= 0.019999999552965164
        this.motionY *= -0.4 //0.019999999552965164D;
        this.motionZ *= 0.019999999552965164
        this.particleMaxAge = (20.0 / (Math.random() * 0.2 + 0.2)).toInt()
    }

    /**
     * Called to update the entity's position/logic.
     */
    override fun onUpdate() {
        this.prevPosX = this.posX
        this.prevPosY = this.posY
        this.prevPosZ = this.posZ
        this.moveEntity(this.motionX, this.motionY, this.motionZ)
        this.motionX *= 0.99
        this.motionY *= 0.99
        this.motionZ *= 0.99

        this.nextTextureIndexX()

        if (particleMaxAge-- <= 0) {
            this.setDead()
        }
    }

    override fun getFXLayer(): Int {
        return 1
    }

    override fun renderParticle(
        par1Tessellator: Tessellator,
        par2: Float,
        par3: Float,
        par4: Float,
        par5: Float,
        par6: Float,
        par7: Float
    ) {
        var f6 = (particleTextureIndexX.toFloat() + this.particleTextureJitterX / 4.0f) / 16.0f
        var f7 = f6 + 0.015609375f
        var f8 = (particleTextureIndexY.toFloat() + this.particleTextureJitterY / 4.0f) / 16.0f
        var f9 = f8 + 0.015609375f
        val f10 = 0.1f * this.particleScale

        if (this.particleIcon != null) {
            f6 = particleIcon.getInterpolatedU((this.particleTextureJitterX / 4.0f * 16.0f).toDouble())
            f7 = particleIcon.getInterpolatedU(((this.particleTextureJitterX + 1.0f) / 4.0f * 16.0f).toDouble())
            f8 = particleIcon.getInterpolatedV((this.particleTextureJitterY / 4.0f * 16.0f).toDouble())
            f9 = particleIcon.getInterpolatedV(((this.particleTextureJitterY + 1.0f) / 4.0f * 16.0f).toDouble())
        }

        val f11 = (this.prevPosX + (this.posX - this.prevPosX) * par2.toDouble() - interpPosX).toFloat()
        val f12 = (this.prevPosY + (this.posY - this.prevPosY) * par2.toDouble() - interpPosY).toFloat()
        val f13 = (this.prevPosZ + (this.posZ - this.prevPosZ) * par2.toDouble() - interpPosZ).toFloat()
        val f14 = 1.0f
        par1Tessellator.setColorOpaque_F(f14 * this.particleRed, f14 * this.particleGreen, f14 * this.particleBlue)
        with(par1Tessellator) {
            addVertexWithUV(
                (f11 - par3 * f10 - par6 * f10).toDouble(),
                (f12 - par4 * f10).toDouble(),
                (f13 - par5 * f10 - par7 * f10).toDouble(),
                f6.toDouble(),
                f9.toDouble()
            )
            addVertexWithUV(
                (f11 - par3 * f10 + par6 * f10).toDouble(),
                (f12 + par4 * f10).toDouble(),
                (f13 - par5 * f10 + par7 * f10).toDouble(),
                f6.toDouble(),
                f8.toDouble()
            )
            addVertexWithUV(
                (f11 + par3 * f10 + par6 * f10).toDouble(),
                (f12 + par4 * f10).toDouble(),
                (f13 + par5 * f10 + par7 * f10).toDouble(),
                f7.toDouble(),
                f8.toDouble()
            )
            addVertexWithUV(
                (f11 + par3 * f10 - par6 * f10).toDouble(),
                (f12 - par4 * f10).toDouble(),
                (f13 + par5 * f10 - par7 * f10).toDouble(),
                f7.toDouble(),
                f9.toDouble()
            )
        }
    }
}
