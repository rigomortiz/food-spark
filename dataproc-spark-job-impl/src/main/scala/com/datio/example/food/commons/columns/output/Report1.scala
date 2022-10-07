package com.datio.example.food.commons.columns.output

import com.datio.example.food.commons.columns.Col

object Report1 {
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

  case object FoodList extends Col {
    override val name: String = "FOOD_LIST"
  }

  case object RowNumber extends Col {
    override val name: String = "ROW_NUMBER"
  }
}
