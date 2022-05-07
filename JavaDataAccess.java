// http://www.atmarkit.co.jp/ait/articles/0107/11/news001.html
// Javaデータアクセスの基礎 サンプルコード(1)
// EMP表への問合せを実行するJavaアプリケーション
// JDBC APIをインポート
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;

class JavaDataAccess {
	public static void main(String args[]) throws Exception {
		// Oracleに接続
		Connection conn = null;

		conn = BookDbDriver.getConnection();
		// Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:book", "book", "book");

		try {
			// ファイル名取得
			String strDirName = "C:\\Downloads\\";
			String strFileName = strDirName + "Senya.csv";
			FileOutputStream fo = new FileOutputStream(strFileName);
//			FileOutputStream fo = new FileOutputStream("C:\\Downloads\\Senya.csv");

			// http://java-reference.sakuraweb.com/java_file_csv_write.html
			// CSVファイル出力サンプル
			// 出力先を作成する
			//FileWriter fw = new FileWriter("C:\\Downloads\\test.csv", false); // ※１
			PrintWriter prW = new PrintWriter(new OutputStreamWriter(fo, "SJIS"));
			// ※１：2番目の引数をtrueにすると追記モード、falseにすると上書きモードになります。

			// ステートメントを作成
			Statement stmt = conn.createStatement();
			String strSQL = "SELECT CD,TITLE,AUTHER,PUBLISHER,NVL(ORIGINAL,' ') AS ORIGINAL,DAY FROM SENYA ORDER BY CD";
			// 問合せの実行
			ResultSet rs = stmt.executeQuery(strSQL);
			//ResultSet rs = stmt.executeQuery("select * from SENYA order by CD");
			// フォーマットパターンを指定して表示する
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			// 問合せ結果の表示
			while (rs.next()) {
				// 列番号による指定
				// System.out.println(rs.getInt(1) + "," + rs.getString(2));
				// データを標準出力
				System.out.print(rs.getString("CD"));
				System.out.print("," + rs.getString("TITLE"));
				System.out.print("," + rs.getString("AUTHER"));
				System.out.print("," + rs.getString("PUBLISHER"));
				System.out.print("," + rs.getString("ORIGINAL"));
				System.out.print("," + sdf.format(rs.getDate("DAY")));
				System.out.println();

				// CSVファイル出力
				prW.print(rs.getString("CD"));
				prW.print(",\"" + rs.getString("TITLE"));
				prW.print("\",\"" + rs.getString("AUTHER"));
				prW.print("\",\"" + rs.getString("PUBLISHER"));
				// 文字列にカンマがある場合の対応
				prW.print("\",\"" + rs.getString("ORIGINAL"));
				prW.print("\"," + sdf.format(rs.getDate("DAY")));
				prW.println();
			}
            //ファイルに書き出す
            prW.close();
			 //終了メッセージを画面に出力する
            System.out.println("CSV出力が完了しました。");

            // エクスプローラーを開く
            //https://jablogs.com/detail/105489
            Desktop.getDesktop().open(new File(strDirName));

            // 結果セットをクローズ
			rs.close();
			// ステートメントをクローズ
			stmt.close();
			// 接続をクローズ
			conn.close();

		} catch (IOException ex) {
			// 例外時処理
			ex.printStackTrace();
		}
	}
}