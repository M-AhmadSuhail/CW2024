package com.example.demo.Actor;

public interface MyObserver {
    // This method will be called when the level is won and needs to proceed to the next level
    void onLevelWin(String nextLevel);
    void onGameEnd(String result);  // result can be "win" or "lose"
}
