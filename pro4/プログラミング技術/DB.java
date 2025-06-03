import java.sql.*;
class DB{//データベーステスト用
    public static void main(String[] args) {
        String url = "jdbc:sqlite:RPGDB"; // RPGDB はデータベースファイル名

        String query = "SELECT id, name, atc, hit FROM Skill";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int atc = rs.getInt("atc");
                int hit = rs.getInt("hit");

                System.out.println("ID: " + id + ", Name: " + name + ", ATC: " + atc + ", HIT: " + hit);
            }

        } catch (SQLException e) {
            System.out.println("DB接続エラー: " + e.getMessage());
        }
    }
}