package edu.augustana.model;

import java.util.prefs.Preferences;

// used https://www.vogella.com/tutorials/JavaPreferences/article.html and
// https://chat.openai.com/share/c2927f44-1c3d-4c4f-bd5c-07d5e5bb0148 and
// https://www.youtube.com/watch?v=Uxe7ZkX_Msw
public class RecentFilesManager {

    private Preferences userPreferences;

    public RecentFilesManager() {
        userPreferences = Preferences.userNodeForPackage(RecentFilesManager.class);
    }

    public void addRecentFile(String recentFilePath) {
        userPreferences.put("recent file path:", recentFilePath);
        System.out.println(userPreferences);
    }
}
