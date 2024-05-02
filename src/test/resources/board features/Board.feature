Feature: Board - Robot - Cards interactions

  Scenario Outline: As a belt I want to move a robot
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <positionX> <positionY> is a "belt"
    And The belt at position <positionX> <positionY> is pointing "<directionTile>" with power <power>
    And A robot at position <positionX> <positionY> pointing "NORTH"
    When The effect of tile at position <positionX> <positionY> is called
    Then Robot is at position <positionFinalX> <positionFinalY>

    Examples:
      | boardX | boardY | directionTile | positionX | positionY | power | positionFinalX | positionFinalY |
      | 7     |7       |EAST           |0          |0          |1      |1               |0               |
      |   8    |   8    |     EAST      |    4      |    4      |   2   |      6         |      4         |
      | 8      |8       |NORTH          |4          |4          |2      |4               |6               |
      | 8      |8       |SOUTH          |4          |4          |2      |4               |2               |
      | 8      |8       |WEST           |4          |4          |2      |2               |4               |
      |   8    |   8    |     EAST      |    4      |    4      |   1   |      5         |      4         |
      | 8      |8       |NORTH          |4          |4          |1      |4               |5               |
      | 8      |8       |SOUTH          |4          |4          |1      |4               |3               |
      | 8      |8       |WEST           |4          |4          |1      |3               |4               |

  Scenario Outline: As a trap tile I want to damage a robot
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <trapPositionX> <trapPositionY> is a "trap"
    And Tile on position <checkpointPositionX> <checkpointPositionY> is a "checkpoint"
    And A robot at position <trapPositionX> <trapPositionY> pointing "NORTH"
    And A robot has checkpoint in <checkpointPositionX> <checkpointPositionY>
    And A robot has <health> health
    When The effect of tile at the robot's position is called
    Then Robot is at position <positionFinalX> <positionFinalY>
    And Robot health equals <finalHealth>

    Examples:
      | boardX | boardY | trapPositionX | trapPositionY |  checkpointPositionX | checkpointPositionY| health | positionFinalX | positionFinalY | finalHealth |
      |8       |8       |4              |4              |0                     |0                   |5       |4               |4               |2            |
      |8       |8       |4              |4              |0                     |0                   |20       |4               |4               |7            |
      |8       |8       |4              |4              |0                     |0                   |10       |4               |4               |7            |
      |8       |8       |4              |4              |0                     |0                   |3       |0               |0               |5            |
      |8       |8       |4              |4              |0                     |0                   |0       |0               |0               |5            |
      |8       |8       |4              |4              |0                     |0                   |2       |0               |0               |5            |

  Scenario Outline: As a lava tile I want to kill a robot
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <lavaPositionX> <lavaPositionY> is a "lava"
    And Tile on position <checkpointPositionX> <checkpointPositionY> is a "checkpoint"
    And A robot at position <lavaPositionX> <lavaPositionY> pointing "NORTH"
    And A robot has checkpoint in <checkpointPositionX> <checkpointPositionY>
    And A robot has <health> health
    When The effect of tile at the robot's position is called
    Then Robot is at position <positionFinalX> <positionFinalY>
    And Robot health equals <finalHealth>

    Examples:
      | boardX | boardY | lavaPositionX | lavaPositionY | checkpointPositionX | checkpointPositionY | health | positionFinalX | positionFinalY | finalHealth |
      |8       |8       |4              |4              |0                     |0                   |5       |0               |0               |5            |

  Scenario Outline: As a robot I want to die if I get pushed out of the board
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <positionX> <positionY> is a "belt"
    And Tile on position <checkpointPositionX> <checkpointPositionY> is a "checkpoint"
    And The belt at position <positionX> <positionY> is pointing "<directionTile>" with power <power>
    And A robot at position <positionX> <positionY> pointing "NORTH"
    And A robot has checkpoint in <checkpointPositionX> <checkpointPositionY>
    When The effect of tile at position <positionX> <positionY> is called
    Then Robot is at position <positionFinalX> <positionFinalY>

    Examples:
      | boardX | boardY | directionTile | positionX | positionY |checkpointPositionX|checkpointPositionY| power | positionFinalX | positionFinalY |
      |   8    |   8    |     EAST      |    7      |    4      |         1          |           1        |   1   |      1         |      1         |
      |   8    |   8    |     NORTH      |    0      |    7      |         1          |           1        |   1   |      1         |      1         |
      |   8    |   8    |     EAST      |    7      |    4      |         1          |           1        |   2   |      1         |      1         |
      |   8    |   8    |     NORTH      |    0      |    7      |         1          |           1        |   2   |      1         |      1         |
      |   8    |   8    |     SOUTH      |    5      |    0      |         1          |           1        |   1   |      1         |      1         |
      |   8    |   8    |     WEST      |    0      |    4      |         1          |           1        |   1   |      1         |      1         |
      |   8    |   8    |     SOUTH      |    5      |    0      |         1          |           1        |   1   |      1         |      1         |
      |   8    |   8    |     WEST      |    0      |    4      |         1          |           1        |   1   |      1         |      1         |

  Scenario Outline: Robot gets health when it is on a charger tile
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <positionX> <positionY> is a "charger"
    And A robot at position <positionX> <positionY> pointing "NORTH"
    And A robot has <health> health
    When The effect of tile at position <positionX> <positionY> is called
    Then Robot health equals <finalHealth>

    Examples:
      | boardX | boardY | positionX | positionY | health| finalHealth|
      |   8    |   8    |    7      |    4      |5      |10           |
      |   8    |   8    |    7      |    4      |4      |9           |
      |   8    |   8    |    7      |    4      |1      |6           |
      |   8    |   8    |    7      |    4      |9      | 10          |
      |   8    |   8    |    7      |    4      |10      |10           |

  Scenario Outline: As a laser I want to kill a robot standing on me
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <laserPositionX> <laserPositionY> is a "laser"
    And Tile on position <checkpointPositionX> <checkpointPositionY> is a "checkpoint"
    And A robot at position <laserPositionX> <laserPositionY> pointing "NORTH"
    And A robot has checkpoint in <checkpointPositionX> <checkpointPositionY>
    And A robot has <health> health
    When The effect of tile at the robot's position is called
    Then Robot is at position <positionFinalX> <positionFinalY>
    And Robot health equals <finalHealth>

    Examples:
      | boardX | boardY | laserPositionX | laserPositionY | checkpointPositionX | checkpointPositionY | health | positionFinalX | positionFinalY | finalHealth |
      |8       |8       |4              |4              |0                     |0                   |5       |0               |0               |5            |
      |8       |8       |4              |4              |0                     |0                   |10       |0               |0               |5            |

  Scenario Outline: As a laser I want to deal damage if a robot walks through
    Given An initialized board of dimensions: <boardX> <boardY>
    And Tile on position <laserPositionX> <laserPositionY> is a "laser"
    And Tile on position <checkpointPositionX> <checkpointPositionY> is a "checkpoint"
    And A robot at position <robotPositionX> <robotPositionY> pointing "<robotDirection>"
    And A robot has checkpoint in <checkpointPositionX> <checkpointPositionY>
    And A robot has <health> health
    When The card "DoubleForwardCard" is executed on the robot
    Then Robot is at position <positionFinalX> <positionFinalY>
    And Robot health equals <finalHealth>
    Examples:
      | boardX | boardY | laserPositionX | laserPositionY | checkpointPositionX | checkpointPositionY | robotPositionX | robotPositionY | robotDirection| health | positionFinalX | positionFinalY | finalHealth |
      |8       |8       |4              |4              |0                     |0                   |4                     |3            |    NORTH     |5       |4               |5               |2            |
      |8       |8       |4              |4              |0                     |0                   |0                     |0            |     SOUTH    |10       |0               |0               |5            |

  Scenario Outline: As a robot I want to push another robot out the way
    Given An initialized board of dimensions: <boardX> <boardY>
    And One of many robots at position <firstRobotPositionX> <firstRobotPositionY> pointing "<firstRobotDirection>" named "TITANUS_PRIME"
    And One of many robots at position <secondRobotPositionX> <secondRobotPositionY> pointing "<secondRobotDirection>" named "NOVA_SENTINEL"
    When The card "<card>" is executed on the robot at position <firstRobotPositionX> <firstRobotPositionY>
    Then Tile at position <firstRobotFinalPositionX> <firstRobotFinalPositionY> has robot named "TITANUS_PRIME"
    And Tile at position <secondRobotFinalPositionX> <secondRobotFinalPositionY> has robot named "NOVA_SENTINEL"

    Examples:
      | boardX | boardY | firstRobotPositionX | firstRobotPositionY | firstRobotDirection | secondRobotPositionX | secondRobotPositionY | secondRobotDirection | card            | firstRobotFinalPositionX | firstRobotFinalPositionY | secondRobotFinalPositionX | secondRobotFinalPositionY |
      | 8      |8       |4                    |4                    |NORTH                |4                     |5                     |EAST                  |ForwardCard      |4                         |5                         |4                          |6                          |
      | 4      |2       |0                    |0                    |EAST                 |1                     |0                     |NORTH                 |DoubleForwardCard|2                         |0                         |3                          |0                          |


  Scenario: SPAWN
    Given An initialized board of dimensions: 8 8
    And Tile on position 1 1 is a "checkpoint"
    And Tile on position 2 2 is a "checkpoint"
    And One of many robots at position 7 7 pointing "EAST" named "NOVA_SENTINEL"
    And One of many robots at position 1 0 pointing "NORTH" named "ASTRAL_ENFORCER"
    And Robot in position 7 7 has checkpoint in position 1 1
    And Robot in position 1 0 has checkpoint in position 2 2
    When The card "ForwardCard" is executed on the robot at position 1 0
    Then Assert robot named "NOVA_SENTINEL" has checkpoint in position 2 2
    And Assert robot in position 7 7 has checkpoint in position 2 2
    And Assert robot named "ASTRAL_ENFORCER" has checkpoint in position 1 1
    And Assert robot in position 1 1 has checkpoint in position 1 1


  Scenario: CHEC
    Given An initialized board of dimensions: 8 8
    And Tile on position 1 1 is a "checkpoint"
    And Tile on position 2 2 is a "spawn"
    And One of many robots at position 7 7 pointing "EAST" named "NOVA_SENTINEL"
    And One of many robots at position 1 0 pointing "NORTH" named "ASTRAL_ENFORCER"
    And Robot in position 7 7 has checkpoint in position 1 1
    And Robot in position 1 0 has checkpoint in position 2 2
    When The card "ForwardCard" is executed on the robot at position 1 0
    Then Assert robot named "NOVA_SENTINEL" has checkpoint in position 2 2
    And Assert robot in position 7 7 has checkpoint in position 2 2
    And Assert robot named "ASTRAL_ENFORCER" has checkpoint in position 1 1
   And Assert robot in position 1 1 has checkpoint in position 1 1


  Scenario Outline: As a robot my health is between 1 and 10
    Given An initialized board of dimensions: 8 8
    And A robot at position 1 1 pointing "NORTH"
    And A robot has <health> health
    Then Robot health equals <finalHealth>

    Examples:
    | health | finalHealth|
    | 10     |10          |
    |11      |10          |
    |9       |9           |
    |1       |1           |

  Scenario Outline: As a card I want to turn a robot
    Given An initialized board of dimensions: 8 8
    And A robot at position 1 1 pointing "<firstDirection>"
    When The card "<card>" is executed on the robot
    Then Robot is at position 1 1 and pointing "<secondDirection>"
    Examples:
    | firstDirection | card | secondDirection|
    |NORTH           |TurnRight|EAST         |
    |EAST           |TurnRight|SOUTH         |
    |SOUTH           |TurnRight|WEST         |
    |WEST           |TurnRight|NORTH         |
    |NORTH           |TurnLeft|WEST         |
    |WEST           |TurnLeft|SOUTH         |
    |SOUTH           |TurnLeft|EAST         |
    |EAST           |TurnLeft|NORTH         |
    |SOUTH           |Turn180|NORTH         |
    |NORTH           |Turn180|SOUTH         |
    |EAST           |Turn180|WEST         |
    |WEST           |Turn180|EAST         |

  Scenario: As a wall I want to stop a robot from pushing another robot
    Given An initialized board of dimensions: 8 8
    And A robot at position 1 1 pointing "EAST"
    And One of many robots at position 2 1 pointing "NORTH" named "NOVA_SENTINEL"
    And Tile on position 3 1 is a "wall"
    When The card "ForwardCard" is executed on the robot
    Then Robot is at position 1 1 and pointing "EAST"
    And Tile at position 2 1 has robot named "NOVA_SENTINEL"

  Scenario: As a wall I want to stop a robot from moving
    Given An initialized board of dimensions: 8 8
    And A robot at position 1 1 pointing "EAST"
    And Tile on position 2 1 is a "wall"
    When The card "ForwardCard" is executed on the robot
    Then Robot is at position 1 1 and pointing "EAST"


  Scenario Outline: As lava i want to call immediate effect when Robot crosses by
    Given An initialized board of dimensions: 8 8
    And A robot at position <posx> <posy> pointing "EAST"
    And Robot's checkpoint is at 0 0
    And Tile on position 2 2 is a "lava"
    When The card "<card>" is executed on the robot
    Then Robot is at position 0 0 and pointing "NORTH"
    Examples:
      | posx | posy | card |
      |0     |2     |TripleForwardCard|
      |3     |2     |BackwardCard |

  Scenario: RotatingGear
    Given An initialized board of dimensions: 8 8
    And A robot at position 1 2 pointing "EAST"
    And Tile on position 2 2 is a "rotating gear"
    When The card "ForwardCard" is executed on the robot
    And The effect of tile at the robot's position is called
    Then Robot is at position 2 2 and pointing "SOUTH"

  Scenario: Robot dies and lands on spawn, where there is another robotScenario
    Given An initialized board of dimensions: 8 8
    And Tile on position 1 1 is a "spawn"
    And Tile on position 2 2 is a "spawn"
    And One of many robots at position 0 0 pointing "SOUTH" named "NOVA_SENTINEL"
    And One of many robots at position 1 0 pointing "NORTH" named "ASTRAL_ENFORCER"
    And Robot in position 0 0 has checkpoint in position 1 1
    And Robot in position 1 0 has checkpoint in position 2 2
    When The card "ForwardCard" is executed on the robot at position 1 0
    And The card "ForwardCard" is executed on the robot at position 0 0
    Then Assert robot named "NOVA_SENTINEL" has position 1 1
    And Assert robot named "ASTRAL_ENFORCER" has position 2 2

  Scenario: Robot steps on bomb
    Given An initialized board of dimensions: 8 8
    And Tile on position 6 6 is a "checkpoint"
    And Tile on position 7 7 is a "checkpoint"
    And Tile on position 0 1 is a "bomb"
    And One of many robots at position 0 0 pointing "NORTH" named "NOVA_SENTINEL"
    And One of many robots at position 1 0 pointing "NORTH" named "ASTRAL_ENFORCER"
    And Robot in position 0 0 has checkpoint in position 6 6
    And Robot in position 1 0 has 5 health
    When The card "ForwardCard" is executed on the robot at position 0 0
    Then Assert robot named "NOVA_SENTINEL" has position 6 6
    And Robot in position 1 0 equals 3 health

  Scenario: Robot steps on teleport tile
    Given An initialized board of dimensions: 8 8
    And Tile on position 6 6 is a "checkpoint"
    And Tile on position 0 1 is a "teleportation"
    And Tile on position 2 2 is a "teleportation"
    And One of many robots at position 0 0 pointing "NORTH" named "NOVA_SENTINEL"
    And One of many robots at position 1 0 pointing "NORTH" named "ASTRAL_ENFORCER"
    And Robot in position 0 0 has checkpoint in position 6 6
    When The card "ForwardCard" is executed on the robot at position 0 0
    And The effect of tile at position 0 1 is called
    Then One of many robots at position 2 2 pointing "NORTH" named "NOVA_SENTINEL"
  
  Scenario: Oil stain
    Given An initialized board of dimensions: 8 8
    And Tile on position 2 2 is a "oilstain"
    And One of many robots at position 2 1 pointing "NORTH" named "NOVA_SENTINEL"
    And Tile on position 6 6 is a "checkpoint"
    When The card "ForwardCard" is executed on the robot at position 2 1
    And The effect of tile at position 2 2 is called
    Then Robot is at position 2 2 and not pointing "NORTH"


