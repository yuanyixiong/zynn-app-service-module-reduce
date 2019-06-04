package com.zynn.app.service.module.reduce.controller

import com.zynn.app.service.module.reduce.service.SparkSQLService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

@ComponentScan
@Controller
@ResponseBody
@RequestMapping(value = Array("/hive"))
class HiveController @Autowired()(private val sparkSQLService: SparkSQLService) {

  @GetMapping(value = Array("/doAllTask"))
  def doAllTask(): String = {
    sparkSQLService.doTask("yinian", "orders")
    sparkSQLService.showAll()
    sparkSQLService.test_02()
    sparkSQLService.test_03()
    sparkSQLService.test_04()
    "Hello"
  }

}
