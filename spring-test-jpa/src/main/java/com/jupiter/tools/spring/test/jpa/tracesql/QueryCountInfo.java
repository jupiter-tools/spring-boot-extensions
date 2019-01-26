package com.jupiter.tools.spring.test.jpa.tracesql;

import lombok.Getter;

/**
 * Created on 12/6/15
 *
 * @author Igor Dmitriev
 * @author Mikalai Alimenkou
 * @author Korovin Anatoliy
 */
@Getter
public class QueryCountInfo {
    private int selectCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int callCount;
    private int otherCount;

    public void incrementSelectCount() {
        selectCount++;
    }

    public void incrementInsertCount() {
        insertCount++;
    }

    public void incrementUpdateCount() {
        updateCount++;
    }

    public void incrementDeleteCount() {
        deleteCount++;
    }

    public void incrementCallCount() {
        callCount++;
    }

    public void incrementOtherCount() {
        otherCount++;
    }

    public void clear() {
        selectCount = 0;
        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        callCount = 0;
        otherCount = 0;
    }
}
