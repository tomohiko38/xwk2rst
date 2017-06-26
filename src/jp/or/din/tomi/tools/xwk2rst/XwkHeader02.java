package jp.or.din.tomi.tools.xwk2rst;

/**
 * 見出し2.
 *
 * @author tomohiko37_i
 * @version 1.0
 */
public class XwkHeader02 extends XwkElements {

    /**
     * 見出しの文字列.
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

        StringBuffer ret = new StringBuffer();
        ret.append(this.value);
        ret.append("\n");
        for (int i = 0; i < this.value.getBytes().length; i++) {
            ret.append("~");
        }

        return ret.toString().trim();
    }

}
