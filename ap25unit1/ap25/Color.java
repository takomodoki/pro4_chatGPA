package ap25;

import java.util.Map;

public enum Color {
  //各色の内部値
  BLACK(1),
  WHITE(-1),
  NONE(0),
  BLOCK(3);

  //SYMBOLSには下のような対応関係がMapとして保存される
  static Map<Color, String> SYMBOLS =
      Map.of(BLACK, "o", WHITE, "x", NONE, " ", BLOCK, "#");
  //内部値
  private int value;
  //コンストラクタ
  private Color(int value) {
    this.value = value;
  }
  
  public int getValue() {
    return this.value;
  }

  //ひっくり返った色を返す
  public Color flipped() {
    switch (this) {
    case BLACK: return WHITE;
    case WHITE: return BLACK;
    default: return this;
    }
  }
  //先ほどのMapからtoString
  public String toString() {
    return SYMBOLS.get(this);
  }
  //StringからMapを通してCalorに変換
  public Color parse(String str) {
    return Map.of("o", BLACK, "x" , WHITE).getOrDefault(str, NONE);
  }
}
