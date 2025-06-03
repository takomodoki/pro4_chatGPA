import java.sql.*;
public class SkillFactory{
    public static Skill[] createSkill(){
        String url = "jdbc:sqlite:RPGDB"; // RPGDB はデータベースファイル名

        String query = "SELECT id, name, atc, eff, hit FROM Skill";
        int skillCount = 0;//スキルの総数を記録する
        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            while(rs.next()){
                skillCount = rs.getInt("id");//最後のidからSkillの総数を記録
            }
        } catch (SQLException e) {
            System.out.println("DB接続エラー: " + e.getMessage());
        }

        Skill[] skill = new Skill[skillCount];//Skillクラスの配列

        try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query)) {
            int index=0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int atc = rs.getInt("atc");
                int eff = rs.getInt("eff");
                int hit = rs.getInt("hit");

                skill[index++] = new Skill(id,name,atc,eff,hit);//ここでセット
            }
            return skill;
        } catch (SQLException e) {
            System.out.println("DB接続エラー: " + e.getMessage());
        }
        return null;
    }
}
