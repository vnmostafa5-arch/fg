package com.example.redstonemeter;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class RedstoneMeterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.world == null) return;

            // التحقق من البلوكة التي ينظر إليها اللاعب حالياً
            HitResult hit = client.crosshairTarget;
            if (hit != null && hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockState state = client.world.getBlockState(blockHit.getBlockPos());

                // إذا كانت البلوكة سلك ردستون
                if (state.isOf(Blocks.REDSTONE_WIRE)) {
                    int power = state.get(Properties.POWER);
                    String text = "Power: " + power;
                    
                    // حساب المنتصف لرسم النص أسفل مؤشر التصويب
                    int width = client.textRenderer.getWidth(text);
                    int x = (client.getWindow().getScaledWidth() - width) / 2;
                    int y = client.getWindow().getScaledHeight() / 2 + 20; 
                    
                    // رسم النص باللون الأحمر
                    drawContext.drawText(client.textRenderer, text, x, y, 0xFF0000, true);
                }
            }
        });
    }
}
