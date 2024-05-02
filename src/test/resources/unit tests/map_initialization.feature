Feature: map initialization
  Scenario Outline: The map is initialized correctly
    Given The board is "<map_name>"
    Then The tile in position <posX> <posY> is a "<tileType>"
    And The tile in position <posX1> <posY1> is a "<tileType1>"
    Examples:
      | map_name | posX  | posY  | tileType    | posX1   | posY1     | tileType1 |
      | EASY_1   |   2   |  2    | belt        | 1       |6          | bomb      |
      | EASY_2   | 0     |9      | charger     |2        |7          | lava      |
      | EASY_3   |2      |9      | belt        |2        |1          |charger    |
      | MEDIUM_1 | 1     |4      | laser       | 2       |0          |charger    |
      | MEDIUM_2 | 4     |1      | win         | 5       |0          | wall      |
      | HARD_1   |1      |3      |tile         | 1       |13         | belt      |
      | HARD_2   | 1     |0      |teleportation| 2       |1          | belt      |
      | HARD_3   | 3     |0      |rotating_gear| 1       |1          |lava       |

  Scenario Outline: