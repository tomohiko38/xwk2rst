package jp.or.din.tomi.tools.xwk2rst;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章.
 * 
 * @author tomohiko37_i
 * @version 1.0
 */
public class XwkSentence extends XwkElements {

	private static int count = 0;
	private static List<String> footNoteList = new ArrayList<>();
	
	/**
	 * 文章の文字列.
	 */
	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * RST テキストを取得する.
	 * 
	 * @return RST テキスト
	 */
	public String getRstText() {
		
		// 脚注の対応
		int startIdx = this.value.indexOf("((-");
		int endIdx = this.value.indexOf("-))");
		
		String ret = "";
		
		if (startIdx != -1 && endIdx != -1) {
			footNoteList.add(this.value.substring(startIdx + 3, endIdx));
			//ret = this.value.substring(0, startIdx) + " [#" + count + "]_ " + this.value.substring(endIdx + 3, this.value.length());
			ret = this.value.substring(0, startIdx) + " [#]_ " + this.value.substring(endIdx + 3, this.value.length());
			count++;
		} else {
			ret = this.value;
		}
		return ret.trim() + "\n";
	}
	
	public static List<String> getFootNoteList() {
		return footNoteList;
	}
}
