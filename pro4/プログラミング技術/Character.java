public abstract sealed class Character permits Player, Enemy {
    private int id;
    private String name;
    private int hp;
    private Skill[] skill;
    private BaseState state;
    private int maxHp;// hpの最大値を保管する
    // 主に値の受け渡しに用いる

    public Character(int id, String name, int hp, Skill[] skill) {
        this.id = id;
        this.name = name;
        this.hp = hp;
        this.skill = skill;
        this.state = new NormalState();
        this.maxHp = hp;
    }
    // ゲッタ

    public int getId() {// idのゲッタ
        return id;
    }

    public String getName() {// nameのゲッタ
        return name;
    }

    public int getHp() {// hpのゲッタ
        return hp;
    }

    public Skill[] getSkill() {
        return skill;
    }

    public BaseState getState() {
        return state;
    }

    public int getmaxHp() {
        return maxHp;
    }

    public void setName(String name) {// 名前を自由につけられる
        this.name = name;
    }

    public void setHp(int setHp) {// 変動後のhpをセット
        this.hp = setHp;
    }

    public void reduceHp(int damage) {// 変動後のhpを返す。hpがマイナスなら0を返す。
        hp = (hp >= damage) ? hp - damage : 0;
        System.out.println(name + "は" + damage + "ダメージを受けた！\u001b[00m");
    }

    public void changeState(BaseState newState) {// ステートを変更。
        this.state = newState;
    }

    public boolean isAlive() {// 生存しているかどうかを判別。（hp>0でtrue）
        return hp > 0;
    }

    public void recovery() {// hpを回復と状態異常を元に戻す
        this.hp = maxHp;
        this.state = new NormalState();
    }

    public void attack(Character target, int num) {// 攻撃（ダメージ＋状態遷移）
        Skill skill = getSkill()[num];
        System.out.println(name + "の" + skill.getName() + "！");
        target.reduceHp(skill.getAtc());

        switch (skill.getEff()) {
            case 1: // 毒属性の攻撃処理
                if(target.getState() instanceof NormalState){// 通常状態のときのみ毒になる
                    target.changeState(new PoisonedState());
                    System.out.println(target.getName() + "は毒を浴びた！");
                }
                else{
                    System.out.println(target.getName() + "はこれ以上毒状態にはならない！");
                }
                break;
            case 2: // 麻痺属性の攻撃処理
                if(target.getState() instanceof NormalState){// 通常状態のときのみ麻痺になる
                    target.changeState(new ParalyzedState());
                    System.out.println(target.getName() + "は麻痺状態になった！");
                }
                else{
                    System.out.println(target.getName() + "はこれ以上麻痺状態にはならない！");
                }
                break;
            default: // 通常攻撃の処理
                break;
        }
    }
}
