Feature: Rick and Morty API Website Navigation

  @ui @smoke
  Scenario: Verify About Page contains author information
    Given I am on the Rick and Morty home page
    When I click on the "About" link
    Then I should be redirected to the about page
    And I should see information about the technical stuff
    And I should see the author "Axel Fuhrmann" mentioned