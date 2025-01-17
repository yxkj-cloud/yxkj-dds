package cloud.yxkj.dds;

import cloud.yxkj.dds.listener.DdsListener;
import cloud.yxkj.dds.sql.MultipleDataSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * 动态数据源
 * <p>
 * 真动态数据源，允许在事务中切换数据源
 */
public class YxkjDds extends MultipleDataSource {
    private final ThreadLocal<Collection<Object>> dataSourceKeysLocal = new ThreadLocal<>();

    private final DdsListener ddsListener = DdsListener.instance();

    /**
     * 构造动态数据源
     *
     * @param dataSources 多个数据源
     */
    public YxkjDds(Map<Object, DataSource> dataSources) {
        super(dataSources);

        // 不能为空
        dataSources.keySet().stream().findAny().orElseThrow(() -> new NoSuchElementException("DataSource not found"));

        // 默认激活全部的数据源
        setActive(dataSources.keySet().toArray(new Object[]{}));
    }

    public void setActive(Object... dataSourceKeys) {
        dataSourceKeysLocal.set(Arrays.asList(dataSourceKeys));
        ddsListener.onAfterSetActive(this, dataSourceKeys);
    }

    @Override
    protected Collection<Object> getActiveKeys() {
        return dataSourceKeysLocal.get();
    }
}
