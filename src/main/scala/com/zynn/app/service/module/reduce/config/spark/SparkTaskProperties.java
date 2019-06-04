package com.zynn.app.service.module.reduce.config.spark;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author 袁毅雄
 * @description 全局配置
 * @date 2019/6/4
 */
@Component
@Data
public class SparkTaskProperties {

    private String sparkAppName;

    private String sparkMaster;

    private String sparkLocalIf;
}
