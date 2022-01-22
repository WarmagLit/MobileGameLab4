package com.tsu.mobilegamelab4.game.map.bonuslocation

import com.tsu.mobilegamelab4.game.map.CollisionLayout

class BonusLocationCollisionLayout: CollisionLayout() {
    var layin = Array(BonusLocationMap.NUMBER_OF_ROW_CELLS) {Array(BonusLocationMap.NUMBER_OF_COLUMN_CELLS){0} }

}