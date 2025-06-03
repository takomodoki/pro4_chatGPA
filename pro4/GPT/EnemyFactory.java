package GPT;

import java.sql.*;
import java.util.Random;

public class EnemyFactory {
    private static final String DB_URL = "jdbc:sqlite:rpg.db";

    public static Enemy createEnemy() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // 敵の総数を取得
            String countQuery = "SELECT COUNT(*) FROM enemy";
            int enemyCount = 0;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(countQuery)) {
                if (rs.next()) {
                    enemyCount = rs.getInt(1);
                }
            }

            if (enemyCount == 0) {
                throw new IllegalStateException("敵がデータベースに存在しません。");
            }

            // ランダムに敵を選択
            int randomId = new Random().nextInt(enemyCount) + 1;
            String enemyQuery = "SELECT id, name, hp, skill1, skill2 FROM enemy WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(enemyQuery)) {
                pstmt.setInt(1, randomId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        String name = rs.getString("name");
                        int hp = rs.getInt("hp");
                        int skill1Id = rs.getInt("skill1");
                        int skill2Id = rs.getInt("skill2");

                        Skill skill1 = fetchSkill(conn, skill1Id);
                        Skill skill2 = fetchSkill(conn, skill2Id);

                        return new Enemy(name, hp, skill1, skill2);
                    } else {
                        throw new SQLException("指定されたIDの敵が見つかりません。");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("敵の生成中にエラーが発生しました。", e);
        }
    }

    private static Skill fetchSkill(Connection conn, int skillId) throws SQLException {
        String skillQuery = "SELECT name, damage, effect FROM skill WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(skillQuery)) {
            pstmt.setInt(1, skillId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    int damage = rs.getInt("damage");
                    String effect = rs.getString("effect");
                    return new Skill(name, damage, effect);
                } else {
                    throw new SQLException("指定されたIDのスキルが見つかりません。");
                }
            }
        }
    }
}
