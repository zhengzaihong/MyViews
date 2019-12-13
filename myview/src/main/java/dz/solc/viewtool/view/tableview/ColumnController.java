package dz.solc.viewtool.view.tableview;

import java.util.HashMap;
import java.util.Map;

/**
 * creat_user: zhengzaihong
 * Email:1096877329@qq.com
 * creat_date: 2019/12/11
 * creat_time: 20:18
 * describe 新特性 主要用于控制特殊列
 * 更多玩法待拓展
 **/
public class ColumnController {

    /**
     * 记录需要特殊处理的列key(第几列) value(该列的宽度)
     */
    private Map<Integer, Integer> columnMap = new HashMap<>();


    /**
     * 添加需要特殊处理的列
     *
     * @param columnIndex 列的下标
     * @param width       该列的宽度
     * @return
     */
    public ColumnController addSpecial(int columnIndex, int width) {
        columnMap.put(columnIndex, width);
        return this;
    }


    /**
     * key 就是需要特殊处理的列
     *
     * @param key
     * @return
     */
    public boolean isContainsKey(int key) {
        return columnMap.containsKey(key);
    }

    /**
     * 获取需要特殊处理的列宽
     *
     * @param key
     * @return
     */
    public Integer getSpecialWidth(int key) {
        return columnMap.get(key);
    }

}
