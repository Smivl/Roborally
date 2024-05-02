Feature: Player features
  Scenario: A player executes a card
    Given An initialized board of dimensions: 8 8
    And A real player called "player1"
    And Player has card "ForwardCard" in the queue
    And Player has robot in position 5 5 pointing "NORTH"
    When Player executes card
    Then Player's robot is in position 5 6 pointing "NORTH"

  Scenario: A player has a queue
    Given An initialized board of dimensions: 8 8
    And A real player called "player1"
    And Player has card "ForwardCard" in the queue
    Then Player's queue size is 1

  Scenario: A player executes a card
    Given An initialized board of dimensions: 8 8
    And A computer player called "cpu1"
    And Player has card "ForwardCard" in the queue
    And Player has robot in position 5 5 pointing "NORTH"
    When Player executes card
    Then Player's robot is in position 5 6 pointing "NORTH"