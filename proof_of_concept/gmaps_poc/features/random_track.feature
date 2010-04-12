Feature: Show random tracks in google maps
In order to create a track in google maps
As a developer
I should be able to see the coordinates near the map

Scenario:
    When I go to the homepage
    Then I should see "coordinates:"
    And I should see /x: \d+\.?\d*, y: \d+\.?\d*/
