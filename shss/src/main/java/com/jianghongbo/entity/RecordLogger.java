package com.jianghongbo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author ：taoyl
 * @date ：Created in 2019-03-17 20:02
 * @description： 日志记录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "logger_record")
public class RecordLogger {


    //用户名
    private String username;
    //用户密码
    private String password;


}
