package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        // общее здоровье
        int totalHealth = 0;
        for (CombatNode child : children) {
            totalHealth += child.getHealth();
        }
        return totalHealth;
    }

    @Override
    public int getAttackPower() {
        // общая атака
        int totalAttack = 0;
        for (CombatNode child : getAliveChildren()) {
            totalAttack += child.getAttackPower();
        }
        return totalAttack;
    }

    @Override
    public void takeDamage(int amount) {
        List<CombatNode> aliveChildren = getAliveChildren();
        if (aliveChildren.isEmpty()) {
            return;
        }

        int damagePerChild = amount / aliveChildren.size();

        for (CombatNode child : aliveChildren) {
            child.takeDamage(damagePerChild);
        }
    }

    @Override
    public boolean isAlive() {
        return !getAliveChildren().isEmpty();
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "+ " + name + " [HP=" + getHealth() + ", ATK=" + getAttackPower() + "]");
        for (CombatNode child : children) {
            child.printTree(indent + "  ");
        }
    }
    private List<CombatNode> getAliveChildren() {
        List<CombatNode> alive = new ArrayList<>();
        for (CombatNode child : children) {
            if (child.isAlive()) {
                alive.add(child);
            }
        }
        return alive;
    }
}