package assignment2;

import java.util.Objects;

public class Pokemon {

    private final String type;
    private final int MAX_HP;
    private final int MAX_EP = 100;
    private final int FAINT_HP = 0;

    private String name;
    private int currentHP;
    private int energy;
    private boolean knowsSkill;
    private boolean isFainted;
    private Skill skill;

    public Pokemon(String name, int MAX_HP, String type) {
        this.name = name;
        this.MAX_HP = MAX_HP;
        this.currentHP = MAX_HP;
        this.energy = MAX_EP;
        this.type = type;
        this.skill = null;
        this.knowsSkill = false;
        this.isFainted = false;
    }

    @Override
    public String toString() {
        if (skill == null) {
            return String.format("%s (%s)", name, type);
        } else {
            return String.format("%s (%s). Knows %s - AP: %d EC: %d", name, type, skill.getName(), skill.getAP(),
                    skill.getEC());
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Pokemon otherPokemon = (Pokemon) obj;
        boolean sameName = name == otherPokemon.getName();
        boolean samePower = type == otherPokemon.getType();
        boolean sameSkill = skill == otherPokemon.getSkill();
        boolean sameCurrentHP = currentHP == otherPokemon.getCurrentHP();
        boolean sameMAX_HP = MAX_HP == otherPokemon.getMAX_HP();
        boolean sameEnergy = energy == otherPokemon.getEnergy();

        return (sameName && samePower && sameSkill && sameCurrentHP && sameMAX_HP && sameEnergy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, skill, currentHP, MAX_HP, energy);
    }

    public void learnSkill(String skillName, int skillAP, int skillEC) {
        this.skill = new Skill(skillName, skillAP, skillEC);
        this.knowsSkill = true;
    }

    public void forgetSkill() {
        this.skill = null;
        this.knowsSkill = false;
    }

    public String attack(Pokemon defender) {
        if (skill == null) {
            return "Attack failed." + name + " does not know a skill.";
        } else if (skill.getEC() > energy) {
            return "Attack failed. " + name + " lacks energy: " + energy + "/" + skill.getEC();
        } else if (isFainted) {
            return "Attack failed. " + name + " fainted.";
        } else if (defender.isFainted()) {
            return "Attack failed. " + defender.getName() + " fainted.";
        } else {
            spendEP();
            String attackMsg = skill.useSkill(this, defender);
            return attackMsg;
        }
    }

    public String receiveDamage(int damage) {
        if (currentHP - damage < FAINT_HP) {
            currentHP = 0;
            isFainted = true;
            return name + " has " + currentHP + " HP left. " + name + " faints.";
        } else {
            currentHP -= damage;
            return name + " has " + currentHP + " HP left.";
        }
    }

    public void spendEP() {
        int energyCost = skill.getEC();
        if (energy - energyCost < 0) {
            energy = 0;
        } else {
            energy -= energyCost;
        }
    }

    public void recoverEnergy() {
        int restoredEP = 25;
        if ((energy + restoredEP) > MAX_EP) {
            energy = MAX_EP;
        } else {
            energy += restoredEP;
        }
    }

    public void rest() {
        int restoredHP = 20;
        if (isFainted) {
            return;
        }
        heal(restoredHP);
    }

    public String useItem(Item item) {
        int itemPower = item.getPowerValue();
        String itemName = item.getName();
        int healedHp = heal(itemPower);
        String pokemonName = getName();
        if (healedHp == 0) {
            return String.format("%s could not use %s. HP is already full.", pokemonName, itemName);
        }
        return String.format("%s used %s. It healed %d HP.", pokemonName, itemName, healedHp);
    }

    public int heal(int hp) {
        int newHp = this.currentHP + hp;
        if (newHp > this.MAX_HP) {
            int healed = this.MAX_HP - this.currentHP;
            this.currentHP = this.MAX_HP;
            return healed;
        } else {
            int healed = newHp - this.currentHP;
            this.currentHP = newHp;
            return healed;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public int getMAX_HP() {
        return MAX_HP;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHP() {
        return currentHP;
    }

    public int getEnergy() {
        return energy;
    }

    public Skill getSkill() {
        return skill;
    }

    public boolean knowsSkill() {
        return knowsSkill;
    }

    public boolean isFainted() {
        return isFainted;
    }

}