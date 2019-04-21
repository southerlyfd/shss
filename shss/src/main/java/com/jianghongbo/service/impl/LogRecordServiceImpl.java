package com.jianghongbo.service.impl;

import com.jianghongbo.entity.RecordLogger;
import com.jianghongbo.service.api.LogRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ：taoyl
 * @date ：Created in 2019-04-21 13:16
 * @description：日志记录
 */
@Slf4j
@Service
public class LogRecordServiceImpl implements LogRecordService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void saveRecordLogger(RecordLogger recordLogger) {
        log.info("日记记录：" + recordLogger.toString());
        mongoTemplate.save(recordLogger);
    }
}
