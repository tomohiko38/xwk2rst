package jp.or.din.tomi.tools.xwk2rst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 苦労して導入した Pandoc で StaticXWiki のドキュメントを
 * Sphinx の reStructured 形式に変換できなかったので，仕方
 * なく自作する運命に陥った金曜日の夜.
 * 1 度だけの変換のためにプログラムを書くのも無駄かと思って手動
 * でコンバートしようとやってみるも，意外と面倒なことに気づいて
 * しまった土曜日の夜.
 * 
 * @author tomohiko37_i
 * @version 1.0
 */
public class Xwk2Rst {

	/**
	 * 変換するページ.
	 */
	private XwkPage page = new XwkPage();
	
	/**
	 * サブタイトルが発生したかどうか.
	 */
	private boolean isSubTitle = false;
	
	/**
	 * 起動処理.
	 * 
	 * @param args 起動パラメータ
	 */
	public static void main(final String[] args) {
		new Xwk2Rst(args);
	}
	
	/**
	 * コンストラクタ.
	 * 
	 * @param args 起動パラメータ
	 */
	private Xwk2Rst(final String[] args) {
		this.execute(args);
	}
	
	/**
	 * 種処理.
	 * 
	 * @param args 起動パラメータ
	 */
	private void execute(final String[] args) {

		String inputFilePath = "/Users/tomohiko/Documents/workspace/xwk2rst/data/" + args[0] + ".xwk.txt";
		String outputFilePath = "/Users/tomohiko/Documents/workspace/xwk2rst/dist/" + args[0] + ".rst";
		
		// ファイルの読み込みと編集処理
		try (FileReader fr = new FileReader(inputFilePath);
			 BufferedReader br = new BufferedReader(fr)) {
						
			while (br.ready()) {
				String line = br.readLine().trim();
				this.analizePageInfo(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ファイルの出力
		try (FileWriter fw = new FileWriter(outputFilePath);
			 BufferedWriter bw = new BufferedWriter(fw)) {
			
			// タイトルの出力
			bw.write(this.page.getTitle() + "\n");
			for (int i = 0; i < this.page.getTitle().getBytes().length; i++) {
				bw.write("=");
			}
			bw.write("\n");
			bw.write("\n");
			
			// サブタイトルの出力
			bw.write(".. note::\n");
			bw.write("\n");
			bw.write("   " + this.page.getSubTitle());
			bw.write("\n");
			bw.write("\n");
						
			for (int i = 0; i < this.page.getElementsList().size(); i++) {
				bw.write(this.page.getElementsList().get(i).getRstText() + "\n");
			}
			
			// 脚注
			List<String> footNoteList = XwkSentence.getFootNoteList();
			for (int i = 0; i < footNoteList.size(); i++) {
				//bw.write(".. [#" + i + "] " + footNoteList.get(i));
				bw.write(".. [#] " + footNoteList.get(i));
				bw.write("\n");
				bw.write("\n");
			}
			bw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1 行の文字列をオブジェクトに詰める処理.
	 * 
	 * @param line 文字列
	 */
	private void analizePageInfo(final String line) {
		
		// どの要素かを判定する
		if (line.startsWith("@pagename")) {
			
			// ページタイトルの場合
			StringTokenizer tokens = new StringTokenizer(line, "=");
			tokens.nextToken(); // 前半の @pagename
			String pageName = tokens.nextToken();
			
			// ページタイトルをセット
			this.page.setTitle(pageName);
			
		} else if (line.startsWith("<<!")) {
			
			// サブタイトルの場合
			this.isSubTitle = true;
		} else if (line.startsWith(">>")) {
			
			this.isSubTitle = false; // セット済みなので false にしておく

		} else if (line.startsWith("===")) {
			
			// 見出し3の場合
			XwkHeader03 header03 = new XwkHeader03();
			header03.setValue(line.substring(4));
			this.page.getElementsList().add(header03);
			
		} else if (line.startsWith("==")) {
			
			// 見出し2の場合
			XwkHeader02 header02 = new XwkHeader02();
			header02.setValue(line.substring(3));
			this.page.getElementsList().add(header02);
			
		} else if (line.startsWith("=")) {
			
			// 見出し1の場合
			XwkHeader01 header01 = new XwkHeader01();
			header01.setValue(line.substring(2));
			this.page.getElementsList().add(header01);
			
		} else if (line.indexOf("xw.image") != -1) {
			
			// 画像の場合
			XwkImage image = new XwkImage();
			StringTokenizer tokens = new StringTokenizer(line, "|");
			String imageName = tokens.nextToken();
			String xwImage = tokens.nextToken();
			
			image.setName(imageName); // 画像名のセット
			
			StringTokenizer xwImageTokens = new StringTokenizer(xwImage, ":");
			xwImageTokens.nextToken(); // からうち
			String filePath = xwImageTokens.nextToken();
			
			image.setFilePath(filePath.substring(0, filePath.length() - 2));
			
			this.page.getElementsList().add(image);
			
		} else {
			
			// その他の文字列の場合
			if (this.isSubTitle) {
				
				// サブタイトルフラグが true の場合
				this.page.setSubTitle(line);
				
			} else {
			
				if (line.startsWith("<<@")
				 || line.startsWith("@version")
				 || line.startsWith(">>")
				 || line.startsWith("<<TOC>>")) {
					
					// 読み飛ばす
					
				} else {
					
					if (line.length() == 0) {
						// 長さ0なら読み飛ばす
					} else {
						
						// 通常の文章の場合
						XwkSentence sentence = new XwkSentence();
						sentence.setValue(line);
						this.page.getElementsList().add(sentence);
					}					
				}				
			}
		}
	}
}
