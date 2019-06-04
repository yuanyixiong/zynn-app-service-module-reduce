package com.zynn.app.service.module.reduce.config.spark

import com.zynn.app.service.module.reduce.config.CommonProperties
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.{SparkConf, SparkContext}
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
class SparkConfig(private val commonProperties: CommonProperties) {

  @Bean
  def getSparkConf(): SparkConf = {
      new SparkConf().setAppName("TestHive").setMaster("local")
  }

  @Bean
  @ConditionalOnBean(value = Array(classOf[SparkConf]))
  def sparkContext(conf: SparkConf): SparkContext = new SparkContext(conf);

  @Bean
  @ConditionalOnBean(value = Array(classOf[SparkContext]))
  def hiveContext(sc: SparkContext): HiveContext = new HiveContext(sc);
}
