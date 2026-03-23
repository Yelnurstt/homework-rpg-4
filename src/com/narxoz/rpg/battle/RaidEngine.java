package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);
    private static final int MAX_ROUNDS = 100;

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB, Skill teamASkill, Skill teamBSkill) {
        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null || teamASkill == null || teamBSkill == null) {
            result.setWinner("Error");
            result.addLine("Invalid input parameters.");
            return result;
        }

        result.addLine("--- Raid Started ---");
        result.addLine(teamA.getName() + " vs " + teamB.getName());

        int round = 0;

        while (teamA.isAlive() && teamB.isAlive() && round < MAX_ROUNDS) {
            round++;
            result.addLine("\n[Round " + round + "]");

            int teamAAttack = teamA.getAttackPower();
            if (teamAAttack > 0) {
                result.addLine(teamA.getName() + " attacks with " + teamAAttack + " power using " + teamASkill.getSkillName() + " (" + teamASkill.getEffectName() + ")!");
                teamASkill.cast(teamB);
            } else {
                result.addLine(teamA.getName() + " has no attack power!");
            }

            if (!teamB.isAlive()) {
                break;
            }

            int teamBAttack = teamB.getAttackPower();
            if (teamBAttack > 0) {
                boolean crit = random.nextInt(100) < 15; // 15% шанс крита
                String critMsg = crit ? " [CRITICAL HIT!]" : "";

                result.addLine(teamB.getName() + " counter-attacks with " + teamBAttack + " power using " + teamBSkill.getSkillName() + " (" + teamBSkill.getEffectName() + ")" + critMsg + "!");

                teamBSkill.cast(teamA);
                if (crit) {
                    teamBSkill.cast(teamA);
                }
            } else {
                result.addLine(teamB.getName() + " has no attack power!");
            }
        }

        result.setRounds(round);

        if (!teamA.isAlive() && !teamB.isAlive()) {
            result.setWinner("Draw");
            result.addLine("\nResult: Mutual Destruction (Draw)!");
        } else if (teamA.isAlive()) {
            result.setWinner(teamA.getName());
            result.addLine("\nResult: " + teamA.getName() + " is victorious!");
        } else if (teamB.isAlive()) {
            result.setWinner(teamB.getName());
            result.addLine("\nResult: " + teamB.getName() + " is victorious!");
        } else {
            result.setWinner("Timeout");
            result.addLine("\nResult: Battle reached max rounds (" + MAX_ROUNDS + ") without a winner.");
        }

        return result;
    }
}