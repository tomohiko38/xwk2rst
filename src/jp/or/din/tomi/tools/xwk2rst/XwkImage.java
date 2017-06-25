package jp.or.din.tomi.tools.xwk2rst;

/**
 * 画像.
 * 
 * @author tomohiko37_i
 * @version 1.0
 */
public class XwkImage extends XwkElements {

	/**
	 * 画像ファイルパス.
	 */
	private String filePath;
	
	/**
	 * 画像ファイルサイズ(幅).
	 */
	private int width;

	/**
	 * 画像名前.
	 */
	private String name;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	
	/**
	 * RST テキストを取得する.
	 * 
	 * @return RST テキスト
	 */
	public String getRstText() {
		
		StringBuffer ret = new StringBuffer();
		ret.append("\n");
		ret.append(".. image:: ../..");
		ret.append(this.filePath);
		ret.append("\n");
		
		return ret.toString();
	}	
}
