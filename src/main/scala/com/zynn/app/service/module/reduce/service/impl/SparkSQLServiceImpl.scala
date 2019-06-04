package com.zynn.app.service.module.reduce.service.impl

import com.zynn.app.service.module.reduce.service.SparkSQLService
import org.apache.spark.SparkContext
import org.apache.spark.sql.functions.{avg, col, sum, udf}
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.springframework.stereotype.Service

//hive --service metastore
@Service
class SparkSQLServiceImpl(private val sc: SparkContext, private val hc: HiveContext) extends SparkSQLService {

  override def doTask(dbName: String, tableName: String): Unit = {
    print(dbName)
    print(tableName)
  }

  override def showAll(): Unit = {
    val aisles = hc.sql("select * from badou.aisles").where("aisle_id is not null")
    val departments = hc.sql("select * from badou.departments").where("department_id is not null")
    val prior = hc.sql("select * from badou.order_products_prior").where("order_id is not null")
    val train = hc.sql("select * from badou.order_products_train").where("order_id is not null")
    val orders = hc.sql("select * from badou.orders").where("order_id is not null")
    val products = hc.sql("select * from badou.products").where("product_id is not null")

    aisles.show(10)
    departments.show(10)
    prior.show(10)
    train.show(10)
    orders.show(10)
    products.show(10)
  }

  /**
    * product 统计/特征
    */
  override def test_01(): Unit = {
    val prior = hc.sql("select * from badou.order_products_prior").where("order_id is not null")
    val orders = hc.sql("select * from badou.orders").where("order_id is not null")

    //每个用户的总订单数量
    orders.groupBy("user_id").count().show(10)

    // 统计pproduct被购买的数量
    prior.groupBy("product_id").count().show(10)

    // 统计product被reordered的数量(再次购买)
    prior.groupBy("product_id").sum("reordered").show(10)

    // 统计product购买的比率
    prior.groupBy("product_id").agg(sum("reordered"), avg("reordered")).show(10)
  }

  /**
    * product udf统计product购买的比率
    */
  override def test_02(): Unit = {
    val prior = hc.sql("select * from badou.order_products_prior").where("order_id is not null")

    val avg_udf = udf((sm: Long, cnt: Long) => sm.toDouble / cnt.toDouble)
    var temp_count = prior.groupBy("product_id").count().withColumnRenamed("count", "pro_count")
    var temp_sum = prior.groupBy("product_id").sum("reordered").withColumnRenamed("sum(reordered)", "pro_sum")
    temp_count.join(temp_sum, "product_id").withColumn("avg_udf", avg_udf(col("pro_sum"), col("pro_count"))).filter("product_id=38996").show()
  }

  /**
    * rdd -> DataFram
    */
  override def test_03(): Unit = {
    val prior = hc.sql("select * from badou.order_products_prior").where("order_id is not null")
    val orders = hc.sql("select * from badou.orders").where("order_id is not null")

    case class UserProductItme(userId: Long, productInfo: String)
    var rdd = orders.join(prior, "order_id").select(col("user_id"), col("product_id")).rdd.map(x => (x(0).toString, x(1).toString)).groupByKey().mapValues(x => x.toSet.mkString(","))
    hc.createDataFrame(rdd.map(x => UserProductItme(x._1.toString.toLong, x._2)), UserProductItme.getClass).show(100)

  }


  override def test_04(): Unit = {
    val structType = StructType(Array(StructField("id", LongType, true), StructField("name", StringType, true)))
  }
}
