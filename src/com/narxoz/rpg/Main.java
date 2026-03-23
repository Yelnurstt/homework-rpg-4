package com.narxoz.rpg;

import com.narxoz.rpg.battle.RaidEngine;
import com.narxoz.rpg.battle.RaidResult;
import com.narxoz.rpg.bridge.*;
import com.narxoz.rpg.composite.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Homework 4 Demo: Bridge + Composite ===\n");

        HeroUnit warrior = new HeroUnit("Arthas", 140, 30);
        HeroUnit mage = new HeroUnit("Jaina", 90, 40);

        EnemyUnit goblin = new EnemyUnit("Goblin", 70, 20);
        EnemyUnit orc = new EnemyUnit("Orc", 120, 25);
        EnemyUnit boss = new EnemyUnit("Troll Boss", 200, 50);

        PartyComposite heroes = new PartyComposite("Heroes Team");
        heroes.add(warrior);
        heroes.add(mage);

        PartyComposite frontline = new PartyComposite("Frontline Squad");
        frontline.add(goblin);
        frontline.add(orc);

        RaidGroup enemies = new RaidGroup("Enemy Raid");
        enemies.add(boss);
        enemies.add(frontline);

        System.out.println("--- Team Structures ---");
        heroes.printTree("");
        System.out.println();
        enemies.printTree("");

        Skill slashFire = new SingleTargetSkill("Slash", 20, new FireEffect());
        Skill slashIce = new SingleTargetSkill("Slash", 20, new IceEffect());
        Skill stormFire = new AreaSkill("Storm", 15, new FireEffect());
        Skill shadowBlast = new AreaSkill("Blast", 25, new ShadowEffect());

        System.out.println("\n--- Bridge Preview ---");
        System.out.println(slashFire.getSkillName() + " using " + slashFire.getEffectName() + " (Single Target)");
        System.out.println(slashIce.getSkillName() + " using " + slashIce.getEffectName() + " (Single Target)");
        System.out.println(stormFire.getSkillName() + " using " + stormFire.getEffectName() + " (Area of Effect)");
        System.out.println(shadowBlast.getSkillName() + " using " + shadowBlast.getEffectName() + " (Area of Effect)");

        RaidEngine engine = new RaidEngine().setRandomSeed(42L);

        RaidResult result = engine.runRaid(heroes, enemies, stormFire, slashIce);

        System.out.println("\n--- Raid Result ---");
        System.out.println("Winner: " + result.getWinner());
        System.out.println("Rounds: " + result.getRounds());

        System.out.println("\n--- Battle Log ---");
        for (String line : result.getLog()) {
            System.out.println(line);
        }

        System.out.println("\n=== Demo Complete ===");
    }
}