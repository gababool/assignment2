package assignment2;
import java.lang.Math;
public class Skill {

    final private String skillName;
    final private int AP;
    final private int EC;

    Skill(String skillName, int AP, int EC){
        this.skillName = skillName;
        this.AP = AP;
        this.EC = EC;
    }

    @Override
    public String toString(){
        return String.format("%s - AP: %s EC: %s%n", skillName, AP, EC);   
    }

    public String useSkill(Pokemon attacker, Pokemon defender){
        double multiplier = TypeEffectiveness.calculateMultiplier(attacker.getType(), defender.getType());
        double damage = AP * multiplier;
        String attackMsg = defender.receiveDamage((int)damage);
        String effectivenessMsg = TypeEffectiveness.generateEffectivenessMsg(multiplier);

        String finalMsg;
        if (effectivenessMsg == ""){
            finalMsg = String.format("%s uses %s on %s.\r\n%s", attacker.getName(), skillName, defender.getName(), attackMsg);
        } else{
            finalMsg = String.format("%s uses %s on %s. %s\r\n%s", attacker.getName(), skillName, defender.getName(), effectivenessMsg, attackMsg);
        }
        return finalMsg;
    }

    public String getName(){
        return skillName;
    }

    public int getAP() {
        return AP;
    }

    public int getEC() {
        return EC;
    }
}
