package com.zynn.app.service.module.reduce

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.{EnableAutoConfiguration, SpringBootApplication}
import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
class ZynnServiceModuleReduceApplication

object ZynnServiceModuleReduceApplication extends App {

  SpringApplication.run(classOf[ZynnServiceModuleReduceApplication])
}