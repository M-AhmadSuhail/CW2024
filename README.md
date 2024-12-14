# **COMP2042 Coursework**

## Details

- **Author:** Ahmad Suhail (20607733)
- **GitHub Repository:** [https://github.com/M-AhmadSuhail/CW2024.git](https://github.com/M-AhmadSuhail/CW2024.git)

## Pre-requisites

Ensure the following are installed and properly configured:

- **Java JDK:** Version 21 and above
- **JavaFX SDK:** Version 19 and above
- **Maven:** Included for dependency management

### Verify Environmental Variables

Run the following commands in the terminal:

```cmd
echo %JAVA_HOME%
echo %PATH_TO_FX%
```

Expected output examples:

```cmd
> echo %JAVA_HOME%
C:\Program Files\Java\jdk-21

> echo %PATH_TO_FX%
C:\Program Files\Java\javafx-sdk-19.0.2\lib
```

## Compilation Instructions for CW2024 Repository

### Clone the Repository

Start by cloning the repository to your local machine:

```bash
git clone https://github.com/M-AhmadSuhail/CW2024.git
cd CW2024
```

### Automatic Compilation

Run the provided `start.bat` script located in the root directory:

```cmd
start.bat
```

This script installs dependencies, compiles the code, and launches the application.

### Manual Compilation

1. Navigate to the root directory using the terminal.

2. Run the following commands:

   ```bash
   mvnw install -DskipTests
   ```

3. Navigate to the output directory:

   ```bash
   cd target
   ```

4. Launch the application:

   ```cmd
   "%JAVA_HOME%\bin\java" --module-path "%PATH_TO_FX%" --add-modules javafx.controls,javafx.fxml,javafx.media -jar CW2024-1.0-SNAPSHOT.jar
   ```

## Utilizing Maven

- **Clean Output Files:**

  ```bash
  mvnw clean
  ```

- **Run Test Scripts:**

  ```bash
  mvnw test
  ```

- **Compile Without Tests:**

  ```bash
  mvnw clean install -DskipTests
  ```

## Features
### Levels

-Four levels in total, each with unique objectives.

### Menu

- Main Menu: Central menu for game navigation.
- In-Game Pause Menu: Pause and resume functionality during gameplay.

### Effects

- **Background Music (BGM):** Continuous background music playing throughout the game.

- **Sound Effects (SFX):** Separate sound effects for collision in each level.

- **Screen Overlay:** Displayed before each level begins for smooth transitions.

### Additional Features

- **Power-ups:**
- Added power-ups starting from Level 2 and continuing through the final level. These power-ups enhance player abilities, such as increased speed or damage resistance.
 
### Not Implemented


1. ***Dynamic Screen Resizing:***
  - Screen resizing works, but some bugs remain (e.g., boundary issues with actors).


2. ***Variable Frame Rate:***
  - Frame rate adjustment is not implemented due to JavaFX limitations and hardcoded logic dependencies.


## Class Overview

### New Classes

I'll update the New Classes section in the README with the classes you mentioned:

### New Classes

| Class                | Description                                     | Package                                     |
| -------------------- | ----------------------------------------------- | --------------------------------            |
| `Level_3`, `Level_4` | Implements new levels.                          | `package com.example.demo.Levels;`          |                            
| `MainMenuScreen`     | Abstract class for screen UI.                   | `package com.example.demo.UI`               |
| `CollisionEffect`    | Handles collision visual effects.               | `package com.example.demo.UI`               |
| `EndGameScreen`      | Screen displayed at game conclusion.            | `package com.example.demo.UI`               |
| `GamePause`          | Manages pause menu functionality.               | `package com.example.demo.UI`               |
| `KillDisplay`        | Tracks and displays enemy kill count.           | `package com.example.demo.UI`               |
| `EnemyProjectile2`   | Second type of enemy projectile.                |`package com.example.demo.Projectiles`       |
| `EnemyProjectile3`   | Third type of enemy projectile.                 | `package com.example.demo.Projectiles`      |
| `EnemyPlane2`        | Second type of enemy plane.                     |`package com.example.demo.Projectiles`       |
| `EnemyPlane3`        | Third type of enemy plane.                      |`package com.example.demo.Projectiles`       |
| `Bgm`                | Background music management.                    | `package com.example.demo.MusicController`  |
| `Music`              | Additional music-related functionality.         | `package com.example.demo.MusicController`  |
| `LevelTwo`           | Implementation of the second game level.        | `package com.example.demo.Levels`           |
| `LevelThree`         | Implementation of the third game level.         |`package com.example.demo.Levels`            |
| `LevelOverlay`       | Overlay displayed before each level starts.     | `package com.example.demo.LevelController`  |
| `BossHealth`         | Displays and manages boss health bar.           | `package com.example.demo.Boss`             |


### Renamed Classes

| Old Name       | New Name      |
| -------------- | ------------- |
| `levelTwo`     | `LevelBoss`   |


### Deleted Classes

- `LevelViewLevelTwo`: Merged into `LevelView`


### Modified Classes

- ``**:** Increased difficulty, added shield logic, added explosion effects .
- ``**:** separated class into different packages according to their function.
- ``**:** added pause/resume functionality and added main menu also added overlays before each level.
- ``**:** Added Power-ups in the game.

## Known Issues

- **COllision recorded:** Collision outside the screen parameters were being counted as Kill.
- **Kill Count Logic:** Fixed the rather flawed kill logic.
- **Hit-Box:** Fixed the hit-box logic so it only counts the hit when it touches the user.
- **Shield Image Placement:** Initially the image wasn't being displayed, fixed it and also change the image.

## Unexpected Challenges

- **Pause Menu Behavior:** It was hard to implement as initially the Pause-Menu was being displayed and the game would still run or When you press resume the game wont unpause.
- **LevelOverlay:** Faced problems in displaying the Overlay before each Level as once The overlay was displayed it wont fade-out.
- **Sfx's:** Hard to implement as wanted to make sure that the sfx was precise and oly runs when collision occurs. Also changes to the pom.xml file was hard to figure out!.

## **Features**

### Levels

**Level 1: Introduction**

- Introduces the basic gameplay mechanics for players.

- Features standard enemy planes with straightforward movement patterns.

- Players familiarize themselves with controls, firing mechanics, and navigating the game environment.

**Level 2: Increased Difficulty**

- Introduces a boss enemy with a shield and unpredictable movement, creating the first significant challenge.

- Introduces a different type of enemy plane with greater health, requiring two hits to kill.

- Players must achieve 15 kills to advance to the next level.

- Players can deploy one of two power-ups for strategic advantage.

- Shields are visually represented and can regenerate, demanding strategic attacks.

**Level 3: Fighting the Helicopters**

- Deploys helicopters that can shoot small light energy fires.

- Each helicopter requires two hits to get destroyed.

- Players must achieve 20 kills to advance to the next level.

- Power-ups are available to enhance gameplay.

**Level 4: Defeat the Boss!!**

- Introduces a mega boss that shoots fireballs.

- Defeat the boss to win the game.

- Use power-ups for strategic advantages.

- The boss activates a shield, adding an extra layer of challenge.

## **UI and User Interaction**

**Main Menu**

- Central hub for game navigation, allowing players to start the game, view instructions, or exit.

- Simple and intuitive layout for ease of use.

**In-Game Interface**

- **Health Bar:** Shows the player's remaining health.

- **Restart Level Option:** Allows players to restart the current level during gameplay.

- **Power-Up Indicators:** Displays available power-ups and their activation status.

**Pause Menu**

- Accessible during gameplay, allowing players to pause, resume, or quit.

- Ensures gameplay can be temporarily halted without losing progress.

**Visual Effects**

- Smooth transitions between levels with overlay screens.

- Visual cues for enemy attacks and power-up activations.

### Pause and Power-Ups

**Pause Menu and Power-Up Features**

- The pause menu allows players to pause gameplay, providing options to resume, quit, or restart.

- It features a semi-transparent overlay with large, clearly labeled buttons for easy navigation.

- The power-up menu enables players to purchase enhancements such Sheild and Fire boost.

- User can use any power-up once per leevel for a certain period of time.


**Instruction Screen**

- A 'how to play' tab Provides new players with a step-by-step guide on game mechanics, controls, and objectives.

- Features a slideshow of game screens with navigation arrows to move between slides.

- A "close" button is available for players to exit the instruction screen anytime.

- The instruction screen provides a step-by-step guide on game mechanics, controls, and objectives, including a slideshow of game screens with navigation arrows.

 
  **Functionality:**

- Provides new players with a step-by-step guide on game mechanics, controls, and objectives.


- A "close" button or esc key is available for players to exit the instruction screen anytime.

Pause and Power-Ups

##  **New Java Classes**

 **Key Additions**


-  **Level_3, Level_4:**

       - Location: com.example.demo.Levels

       - Description: Implements the new levels with distinct features and mechanics.

-  **MainMenuScreen**

       - Location: com.example.demo.UI

       - Description: Provides a common base for different game screens like menus and gameplay.

-  **CollisionEffect:**

       - Location: com.example.demo.UI

       - Description: Manages visual feedback during collisions, enhancing the player's experience.

-  **EndGameScreen**

       - Location: com.example.demo.UI

       - Description: Displays the final screen upon game completion or failure.

- **GamePause**

          - Location: com.example.demo.UI

          - Description: Implements pause functionality, including menu options and overlays.

- **KillDisplay**

       - Location: com.example.demo.UI

       - Description: Tracks and displays the count of enemies defeated by the player.

- **EnemyProjectile2, EnemyProjectile3:**

      - Location: com.example.demo.Projectiles

      - Description: Adds new types of enemy projectiles with unique behaviors.

- **EnemyPlane2, EnemyPlane3:**

       - Location:  com.example.demo.Plane

       - Description: Introduces advanced enemy planes with varied attack patterns.

- **Bgm (Background Music):**

       - Location: com.example.demo.MusicController

       - Description: Manages the background soundtrack for each level.

- **Music:**

       - Location: com.example.demo.MusicController

       - Description: Additional sound-related features for effects and transitions.

- **LevelTwo, LevelThree:**

       - Location: com.example.demo.Levels

       - Description: Implements the second and third levels with their unique challenges.

- **LevelOverlay:**

       - Location: com.example.demo.LevelController

       - Description: Adds overlays for transitions between levels and pre-level introductions.

- **BossHealth:**

       - Location: com.example.demo.Boss

       - Description: Tracks and displays the health of boss characters during encounters.

  ### Modified Java Classes

- **LevelParent.java**

     - The LevelParent class underwent significant modifications to improve maintainability, scalability, and modularity.

       
- **LevelView**

     - Deleted LevelViewLevelTwo class and incorporated it into levelView.
       
- **ActiveActorDestructible**

     - Added the isOutOfBounds method to check if the collision was within the screen size ir not.

   
- **LevelTwo**

     - Incorporated the music and the power-up logic.

- **LevelThree**

     - Incorporated the music and the power-up logic.

- **Controller.java**

   - The Controller class was refactored to simplify level transitions and adhere to modern design principles.



- **Main.java**
 
   - Updated the main class and simplified it.
   - Incorporated the MainMenu screen in it.


### Overall Changes

- **Gameplay Enhancements:**

   - Introduced a dynamic star system based on bullet usage and optimal thresholds.

   - Added spawn logic for coins and new power-ups, enhancing gameplay variability.

   - Enhanced visuals with gradient effects, drop shadows, and dynamic positioning for all UI elements.

- **Refactored Codebase:**

   - Consolidated classes like ActiveActor and ActiveActorDestructible to simplify the hierarchy.

   - Refactored input handling, collision management, and actor management into specialized managers.

   - Decoupled timing mechanisms using a reusable GameLoop class for cleaner game logic.

   - Improved UI and Audio Features

   - Integrated a polished main menu for better navigation.

   - Upgraded the Game Over and Win screens with interactive buttons and sound effects.

- **User Experience:**
  - Incorporated Power-ups to enhance the the user experience.
  - Added sfx for further enhancement.
  - made the game play smoother

- ## How to Contribute

1. Fork the repository.
2. Create a feature branch.
3. Commit changes with detailed messages.
4. Push to your fork and submit a pull request.

## License

This project is licensed under the Ahmad Suhail License. See the `LICENSE` file for more details.

---

For more information, refer to the GitHub repository or contact the author directly.

