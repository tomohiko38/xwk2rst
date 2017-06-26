package jp.or.din.tomi.tools.xwk2rst;

import java.util.ArrayList;
import java.util.List;

/**
 * StaticXWiki の 1 ファイルを表現するクラス.
 *
 * @author tomohiko37_i
 * @version 1.0
 */
public class XwkPage {

    /**
     * ページのタイトル.
     */
    private String title;

    /**
     * ページのサブタイトル.
     */
    private String subTitle;

    /**
     * ページを構成する要素.
     */
    private List<XwkElements> elementsList = new ArrayList<>();

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title.trim();
    }

    /**
     * @return the subTitle
     */
    public String getSubTitle() {
        return subTitle.trim();
    }

    /**
     * @param subTitle the subTitle to set
     */
    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    /**
     * @return the elementsList
     */
    public List<XwkElements> getElementsList() {
        return elementsList;
    }

    /**
     * @param elementsList the elementsList to set
     */
    public void setElementsList(List<XwkElements> elementsList) {
        this.elementsList = elementsList;
    }


}
