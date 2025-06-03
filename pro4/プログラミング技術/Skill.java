public class Skill {//技のデータを格納するクラス
    private int id;
    private String name;
    private int atc;
    private int eff;//効果
    private int hit;

    public Skill(int id,String name, int atc, int eff, int hit) {
        this.id = id;
        this.name = name;
        this.atc = atc;
        this.eff = eff;
        this.hit =hit;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAtc() {
        return atc;
    }

    public int getEff() {
        return eff;
    }

    public int getHit() {
        return hit;
    }
    public void setSkill(int id,String name,int atc,int eff,int hit){//セッタ。
        this.id = id;
        this.name = name;
        this.atc = atc;
        this.eff = eff;
        this.hit =hit;
    }
    /*public BaseState toState(){//effからstateを返す
        switch(eff){
            case 1:return new PoisonedState();
            case 2:return new ParalyzedState();
            default:return null;
        }
    }*/
}