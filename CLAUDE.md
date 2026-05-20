# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

A console-based text fighting game written in Java. Players can register/login, create characters with attribute allocation, and battle against various enemy types.

## Build & Run

```bash
# Compile all Java files
javac -d out src/App.java src/com/itheima/**/*.java src/com/itheima/**/**/*.java

# Run the game
java -cp out com.itheima.ui.Login      # Start with login screen
java -cp out com.itheima.App          # Start directly with game (bypasses login)
```

The entry point is `src/App.java` which initializes the login system and launches the fighting game.

## Architecture

```
src/
├── App.java                          # Application entry point
└── com/itheima/
    ├── domain/                       # Domain models
    │   ├── Character.java            # Base character class (HP, attack, defense, damage/healing)
    │   ├── HeroCharacter.java        # Player character with skill list
    │   ├── EnemyCharacter.java       # Enemy with defense stance mechanic
    │   └── User.java                 # User account (id, username, password, status)
    └── ui/
        ├── Login.java                # Login/registration system
        └── FightingGame.java         # Core game loop and battle logic
```

### Class Hierarchy

```
Character (base)
├── HeroCharacter (player - has skillList)
└── EnemyCharacter (enemies - has skill + defending state)
```

### Key Components

**Login.java**: Handles user authentication with:
- Registration (username: 3-16 chars, alphanumeric; password: 3-8 chars, must contain both letters and numbers)
- Login with 3-attempt limit before account lockout
- Captcha verification (4 letters + 1 digit, random position)

**FightingGame.java**: Game loop handling:
- Character attribute allocation (20 points: HP +10/pt, ATK +2/pt, DEF +1/pt)
- Enemy roster: Warrior, Assassin, Tank, Mage with varying stats
- Combat system with defense mechanics

**Character.java**: Core combat mechanics:
- `isAlive()`, `heal()`, `takeDamage()`, `show()`

**EnemyCharacter.java**: Overrides `takeDamage()` to halve damage when defending.

## Development Notes

- No build tool (Maven/Gradle) - uses plain `javac` compilation
- No test framework present
- Chinese language UI and comments throughout
- Console-based interaction with `Scanner` for input
