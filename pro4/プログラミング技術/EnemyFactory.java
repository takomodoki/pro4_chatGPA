import java.sql.*;
public class EnemyFactory{
    public static Enemy[] createEnemy(Skill[] skill){
        String url = "jdbc:sqlite:RPGDB"; // RPGDB はデータベースファイル名

        String query = "SELECT id, name, hp, skillId1, skillId2 FROM Enemy";
        int enemyCount = 0;//スキルの総数を記録する
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                enemyCount = rs.getInt("id");//最後のidからEnemyの総数を記録
            }
        } catch (SQLException e) {
            System.out.println("DB接続エラー: " + e.getMessage());
        }

        Enemy[] enemy = new Enemy[enemyCount];//Enemyクラスの配列

        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            int index=0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int hp = rs.getInt("hp");
                int skill1 = rs.getInt("skillId1");
                int skill2 = rs.getInt("skillId2");

                Skill[] enemySkill = new Skill[2];//敵がもつSkill
                enemySkill[0] = skill[skill1-1];//ここで技を取得
                enemySkill[1] = skill[skill2-1];
                enemy[index++] = new Enemy(id,name,hp,enemySkill);//ここでセット
            }
            return enemy;
        } catch (SQLException e) {
            System.out.println("DB接続エラー: " + e.getMessage());
        }
        return null;
    }
}