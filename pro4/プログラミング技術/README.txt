メンバー
廣田 吏都記 35714119
山本 朝日   35714150
山本 駕久   35714151

作ったもの
    簡易的なRPG。
    データベースにプレイヤーと敵キャラと技を保存して、それを各クラスに呼び出している。
    状態異常に毒と麻痺があり、相手の技を受けてその状態異常となる。またその状態に合わせてメソッドの中身を決めるステートパターンを採用している。
    java17の新仕様のシールドインタフェースとシールドクラスを使い継承できる先を限定している。
    junitでSkillクラスの格納がちゃんとできているのかを確かめた。

ディレクトリ構成
mainRPG.java
    実行ファイル
    難易度選択・プレイヤーの名前決定
    EnemyFactory・SkillFactoryでEnemyクラスとSkillクラスにデータベースからEnemyとSkillのデータベースを入れる。
    戦闘のループ
    enemy[winCount % enemy.length].recovery();倒された敵のhpとstateを元に戻すメソッド。これをしないとEnemyが倒されたままで２周目以降で何もしてないのに勝利になってしまう。
    負けた場合はゲームオーバーの処理
BattleScene.java
    戦闘シーンの管理
    プレイヤーの行動選択・プレイヤーと敵の行動・HPと状態の表示
    戦闘後はプレイヤーのステートをNomalStateに移す。
Character.java
    キャラクターの情報を保持するシールクラス。
    (int id, String name, int hp,Skill[] skill)を引数にしたコンストラクタを持つ。
    id:キャラクターのID
    name:キャラクター名
    atc:スキル
    初期のstateをNormalStateとして保持している。
    各フィールドのゲッタ・セッタを持つ。
    public boolean isAlive();　そのCharacterのhpが0異常かによって生存しているかを判断するメソッド。生存していればtrueを返す。

Player.java
    Characterのサブクラス。プレイアブルキャラ。
Enemy.java
    Characterのサブクラス。敵キャラ。

Skill.java
    キャラクターが用いるスキルの内容を保持する。
    (int id,String name, int atc, int eff, int hit)を引数にしたコンストラクタを持つ。
    id:スキルのID
    name:スキル名
    atc:スキルダメージ
    eff:スキルの追加効果
    hit:スキルの命中率（未実装）
    各フィールドのゲッタ・セッタを持つ。
SkillTest.java 
    架空のSkillをSkillメソッドに送り、それのidをちゃんと取得できているかをjunitで確かめるためのファイル。

SkillFactory.java
    Skillの内容をデータベースから読み出しを行うメソッド、CreateSkillを持つ。
    拡張性のため、Skillとクラスを分けている。BoFのデザインパターン、Factoryとは内容が少し異なる。
DB.java
    データベースのテスト用



BaseState.java
    状態異常を操作するインタフェース
    実装先で使うメソッドの主な機能は以下の通り。
    void startTurn(): 各ステートにおける経過ターン数をカウントする。
    boolean isPaused(): 行動可能かどうかを判定する。
    boolean isExpired(): 状態異常の終了を判定する。
    void applyEffect(Character target): targetに対して状態異常による追加処理を行う。
    String currentState(): 現在の状態名を文字列で返す。

NomalState.java
    「通常」の状態を管理するクラス（BaseStateを実装）。
    void startTurn(): 通常状態では何もしない。
    boolean isPaused(): 通常状態では常に行動可能なので、falseを返す。
    boolean isExpired(): falseを返す。
    void applyEffect(Character target): 通常状態では何もしない。
    String currentState(): "通常"を返す。

ParalyzedState.java
    「麻痺」の状態を管理するクラス（BaseStateを実装）。
    private int turnCount: 麻痺状態での経過ターン数。
    private final int DURATION: 麻痺状態の持続ターン数。3と設定している。
    void startTurn(): 経過ターン数をカウントする。
    boolean isPaused(): turnCountが偶数ならばtrueを返す。
    boolean isExpired(): turnCount >= DURATION ならばtrueを返す。
    void applyEffect(Character target): 追加処理はないので何もしない。
    String currentState(): "麻痺"を返す。

PoisonedState.java
    「毒」の状態を管理するクラス（BaseStateを実装）。
    private int turnCount: 毒状態での経過ターン数。
    private final int DURATION: 毒状態の持続ターン数。3と設定している。
    void startTurn(): 経過ターン数をカウントする。
    boolean isPaused(): 毒状態では常に行動可能なので、falseを返す。
    boolean isExpired(): turnCount >= DURATION ならばtrueを返す。
    void applyEffect(Character target): テキストを表示、ダメージ処理を行う。
    String currentState(): "毒"を返す。

RPGDB
    Player,Enemy,Skillの３つのテーブルを持つ。
    それぞれのテーブルの中身は、
    Player(id integer,name char[16],HP integer)
        1|タロウ|100
        2|ヨワシ|30
        3|ツヨシ|300
    Enemy(id integer,name char[16],HP integer,SkillId1 integer,SkillId2 integer )
        1|ただのスライム|4|2|3
        2|弱気なゾンビ|8|2|4
        3|強気なミミック|16|1|4
        4|陽気なスケルトン|32|1|2
        5|普通の魔王|128|1|5
    Skill(id integer,name char[16],atc integer,eff integer,hit integer)
        1|顔パン|5|0|90
        2|足パン|1|2|70
        3|肩パン|2|0|100
        4|腹パン|3|1|70
        5|全力パンチ|10|0|50
        
sqlite-jdbc-3.30.1.jar
    データベースを動かすためのファイル
junit-4.13.2.jar
    junitを起動するためのファイル
README.txt



