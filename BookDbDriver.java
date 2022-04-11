//Java 重複しているコードをクラスにまとめる。
//http://sunjava.seesaa.net/category/3681514-1.html
//JSPで自作クラス(カスタムクラス？）を使う
//http://youg0717.cocolog-nifty.com/blog/2007/02/post_fc70.html
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BookDbDriver {
	 /**
     * DBと接続する
     *
     * @return DBコネクション
     * @throws ClassNotFoundException
     * @throws SQLException
     */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		// JDBCドライバのロード
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// DBへ接続 引数の指定(DBの場所, ユーザ名, パスワード)
		String url = "jdbc:oracle:thin:@localhost:1521:book";
		String user = "book";
		String pass = "book";

		// データベースに接続
		Connection con = DriverManager.getConnection(url, user, pass);
		return con;
	}
}
