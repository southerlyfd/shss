package com.jianghongbo.service.api;

import com.jianghongbo.entity.RecordLogger;

public interface LogRecordService {

    /**
     * 保存登陆日志
     * @param recordLogger
     */
    void saveRecordLogger(RecordLogger recordLogger);
}
