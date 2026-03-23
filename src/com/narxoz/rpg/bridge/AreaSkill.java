package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        int damage = resolvedDamage();
        applyAoe(target, damage);
    }

    private void applyAoe(CombatNode node, int damage) {
        if (!node.isAlive()) {
            return;
        }

        if (node.getChildren().isEmpty()) {
            node.takeDamage(damage);
        } else {
            for (CombatNode child : node.getChildren()) {
                applyAoe(child, damage);
            }
        }
    }
}