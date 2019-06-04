package com.zynn.app.service.module.reduce.config;

import com.zynn.app.service.module.reduce.config.spark.SparkTaskProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 袁毅雄
 * @description 全局配置
 * @date 2019/6/4
 */
@Component
@ConfigurationProperties(prefix = "common")
@Data
public class CommonProperties {

    private SparkTaskProperties sparkTask;
}
