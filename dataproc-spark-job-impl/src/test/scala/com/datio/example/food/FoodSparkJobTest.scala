package com.datio.example.food

import com.datio.example.food.context.{ContextProvider, FakeRuntimeContext}

class FoodSparkJobTest extends ContextProvider {

  "runProcess method" should "return 0 in success execution" in {
    val runtimeContext = new FakeRuntimeContext(config)
    val exitCode = new FoodSparkJob().runProcess(runtimeContext)
    exitCode shouldBe 0
  }

}
