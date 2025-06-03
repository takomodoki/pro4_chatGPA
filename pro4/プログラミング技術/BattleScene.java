import java.util.Scanner;

public class BattleScene{
    private Player player;
    private Enemy enemy;
    
    public BattleScene(Player player, Enemy enemy){
        this.player = player;
        this.enemy = enemy;
    }
    public boolean startBattle(){
        Scanner scanner = new Scanner(System.in);
        int totalTurnCount = 0;
        int skillNum = 0;

        System.out.println("\n" + enemy.getName() + "が現れた！");

        while(player.isAlive() && enemy.isAlive()){
            System.out.println("\n<< ターン " + ++totalTurnCount + " >>\n");

            //両者のステータス表示
            System.out.println("現在のステータス");
            Character chara[] = {player, enemy};
            System.out.println("--------------------------");
            for(Character c : chara){
                System.out.println(c.getName());
                System.out.println("HP: " + c.getHp() + " / " + c.getmaxHp());
                System.out.println("状態: " + c.getState().currentState());
                System.out.println("--------------------------");
            }

            //プレイヤーの行動
            System.out.println("\n~~ " + player.getName() + "のターン~~ ");
            player.getState().startTurn();
            //状態異常の解消
            if(player.getState().isExpired())
                player.changeState(new NormalState());

            if(!player.getState().isPaused()){
                //プレイヤーから敵へ攻撃
                //攻撃方法を選択
                do{
                    System.out.println(player.getName() + "はどうする？");
                    for(int i = 0; i < 5; i++)
                        System.out.println("｜ " + i + " : " + player.getSkill()[i].getName());
                    System.out.print("> ");
                    skillNum = scanner.nextInt();
                } while(skillNum < 0 || skillNum > 4);
                player.attack(enemy, skillNum);
            }
            else
                System.out.println("\u001b[00;33m" + player.getName() + "は体が痺れて動けない！\u001b[00m");
            if(!enemy.isAlive()){
                System.out.println(enemy.getName() + "は倒れた\n");
                break;
            }
            //追加効果
            player.getState().applyEffect(player);
            if(!player.isAlive()){
                System.out.println(player.getName() + "は倒れた\n");
                break;
            }
            
            //敵の行動
            System.out.println("\n~~ " + enemy.getName() + "のターン ~~");
            enemy.getState().startTurn();
            //状態異常の解消
            if(enemy.getState().isExpired())
                enemy.changeState(new NormalState());
            
                if(!enemy.getState().isPaused()){
                    //敵からプレイヤーへ攻撃
                    skillNum = totalTurnCount % 2 == 0 ? 0 : 1;
                    enemy.attack(player, skillNum);
                }
                else
                    System.out.println("\u001b[00;33m" + enemy.getName() + "は体が痺れて動けない！\u001b[00m");
            if(!player.isAlive()){
                System.out.println(player.getName() + "は倒れた\n");
                break;
            }
            //追加効果
            enemy.getState().applyEffect(enemy);
            if(!enemy.isAlive()){
                System.out.println(enemy.getName() + "は倒れた\n");
                break;
            }
        }
        player.changeState(new NormalState());
        return player.isAlive();
    }
}