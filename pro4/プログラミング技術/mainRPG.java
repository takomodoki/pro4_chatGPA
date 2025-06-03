import java.sql.*;
import java.util.Scanner;

public class mainRPG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Skill[] skill = SkillFactory.createSkill();// すべてのスキルを取得
        Enemy[] enemy = EnemyFactory.createEnemy(skill);// すべての敵を取得
        int winCount = 0;// 連勝数
        String url = "jdbc:sqlite:RPGDB"; // RPGDB はデータベースファイル名

        String query = "SELECT id, name, hp FROM Player";

        Player[] player = new Player[3];

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            int index = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int hp = rs.getInt("hp");
                player[index++] = new Player(id, name, hp, skill);
            }
            int level = 0;// 難易度
            String name;// 名前（変更用）
            do {
                System.out.println("難易度を選んでください。");
                System.out.println("0:普通　1:難しい　2:簡単");
                level = scanner.nextInt();
            } while (level != 0 & level != 1 & level != 2);
            System.out.println("名前を入れてください");
            name = scanner.next();
            player[level].setName(name);
            try {
                Thread.sleep(1000); // 1秒間だけ処理を止める
            } catch (InterruptedException e) {
            }
            System.out.println("始めまして、勇者" + player[level].getName() + "よ。");
            System.out.println("突然だけどあなたには旅に出てもらいます。");
            System.out.println("頑張ってください。");
            boolean victory;
            do {
                System.out.println("あなたは" + winCount + "連勝中です。");
                BattleScene battle = new BattleScene(player[level], enemy[winCount % enemy.length]);// winCount%enemy.length()でenemyをループさせる。
                victory = battle.startBattle();
                enemy[winCount % enemy.length].recovery();// 敵のhp回復と状態を元に戻す。
                if (victory) {
                    winCount++;// 勝利すると連勝数が増える。
                }
            } while (victory);
            System.out.println("勇者" + player[level].getName() + "よ、");
            System.out.println("負けてしまうとは情けない。");
            System.out.println("そなたの今回の連勝数は\u001b[00;31m" + winCount + "\u001b[00mじゃった。");
            System.out.println("次はもっとよい記録を目指して頑張ってくれたまえ。");
        } catch (SQLException e) {
            System.out.println("DB接続エラー: " + e.getMessage());
        }

    }
}
