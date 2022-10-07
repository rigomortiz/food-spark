package com.datio.example.food.commons.columns.input

import com.datio.example.food.commons.columns.Col

//NAME|PREFERENCES|SPICY_TOLERANCE|NATIONALITY|GENRE
object Foodie {
  case object Name extends Col {
    override val name: String = "NAME"
  }

  case object Preferences extends Col {
    override val name: String = "PREFERENCES"
  }

  case object SpicyTolerance extends Col {
    override val name: String = "SPICY_TOLERANCE"
  }

  case object Nationality extends Col {
    override val name: String = "NATIONALITY"
  }

  case object Genre extends Col {
    override val name: String = "GENRE"
  }
}
