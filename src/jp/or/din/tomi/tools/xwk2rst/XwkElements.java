package jp.or.din.tomi.tools.xwk2rst;

/**
 * StaticXWiki の要素を抽象化したクラス.
 *
 * @author tomohiko37_i
 * @version 1.0
 */
public abstract class XwkElements {

    /**
     * Sphinx 用の RST テキストを取得する.
     *
     * @return RST 形式のテキスト
     */
    public abstract String getRstText();
}
