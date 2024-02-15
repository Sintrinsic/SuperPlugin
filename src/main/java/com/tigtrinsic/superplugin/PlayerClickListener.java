package com.tigtrinsic.superplugin;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.checkerframework.common.reflection.qual.NewInstance;

import java.util.HashMap;
import java.util.HashSet;

public class PlayerClickListener implements Listener {

    private HashMap<Player,Integer> stepCounter = new HashMap<>();

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Material groundType = player.getLocation().subtract(0, 1, 0).getBlock().getType();
        if (((LivingEntity) player).isOnGround()) {
            // Updated player last walked time
            player.sendMessage("You are on "+groundType);
            if(stepCounter.containsKey(player)) {
                stepCounter.put(player, stepCounter.get(player) + 1);
            } else {
                stepCounter.put(player, 1);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        player.sendMessage("You clicked! "+event.getAction());
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            event.getPlayer().sendMessage("Works fucker!");

            Block targetBlock = player.getTargetBlock( null, Integer.MAX_VALUE);
            player.sendMessage("You are looking at: " + targetBlock.getType());
            Vector direction = player.getEyeLocation().getDirection();
            player.setVelocity(direction.multiply(10)); // 10 is the magnitude of the velocity
            player.sendMessage("Your velocity has been set in the direction you are looking.");
            stepCounter.put(player, 0);
            // update time of last flight
        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            // Create a ray trace from the player's eye location in the direction they are looking
            RayTraceResult rayTrace = player.getWorld().rayTraceBlocks(player.getEyeLocation(), player.getEyeLocation().getDirection(), 200, FluidCollisionMode.NEVER, true);

            // Check if the ray trace hit a block
            if (rayTrace != null && rayTrace.getHitBlock() != null) {
                // Get the block's world
                World world = rayTrace.getHitBlock().getWorld();

                // Get the block's location
                Location location = rayTrace.getHitBlock().getLocation();

                // Create an explosion at the block's location
                world.createExplosion(location, 10F); // 10F is the power of the explosion
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player){
             if(event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                 if (stepCounter.containsKey(player) && stepCounter.get(player) < 3){
                     player.sendMessage("Your damage was halted because you're a wizard.");
                     event.setCancelled(true); // Cancel the event

                 }else {
                     player.sendMessage("You have taken damage from falling beacuse you're a dumb bitch.");
                 }
                long lastFlew = 0;


            }
        }
    }


}

