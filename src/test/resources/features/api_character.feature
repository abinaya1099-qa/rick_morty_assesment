@api
Feature: Character API Testing

  Scenario: Search characters by name
    When I search for characters with name "rick"
    Then the API response status should be 200
    And the results should contain characters with "Rick" in their name
    And the response should match JSON schema "character-list"

  Scenario: Handle invalid character ID
    When I search for character ID "99999"
    Then the API response status should be 404
    And the response should contain error message "Character not found"

  Scenario: Search returns no matches
    When I search for characters with name "zzzzznotreal"
    Then the API response status should be 404
    And the response should contain error message "There is nothing here"