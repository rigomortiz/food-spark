package com.datio.example.food.commons.columns.input

import com.datio.example.food.commons.columns.Col

// CODE|NAME|SPICY_LEVEL|ZONE|CONTAINS_ANIMAL_PRODUCTS
object Food {
  case object Code extends Col {
    override val name: String = "CODE"
  }

  case object Name extends Col {
    override val name: String = "NAME"
  }

  case object SpicyLevel extends Col {
    override val name: String = "SPICY_LEVEL"
  }

  case object Zone extends Col {
    override val name: String = "ZONE"
  }

  case object ContainsAnimalProducts extends Col {
    override val name: String = "CONTAINS_ANIMAL_PRODUCTS"
  }

}
