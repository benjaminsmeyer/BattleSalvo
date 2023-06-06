# CHANGELOG.md

- Transferred PA03 to the PA04 Repo
- Added shipDirection function to get the ship's direction (VERTICAL or HORIZONTAL) in Ship Interface
- Added setDirection function to set the ship's direction (VERTICAL or HORIZONTAL) in Ship Interface
- Modify ship tests for those two functions (shipDirection & setDirection)
- Created JSON records for each respective purpose: EndGameJson, FleetJson, JoinJson, JsonUtils, etc (found in PA04 -> JSON)
- Created ProxyController (using lab06 layout) to handle the JSON records and run the program
- Updated the AI for PA04 tournament
- Updated Driver to decide between run terminal game vs online game