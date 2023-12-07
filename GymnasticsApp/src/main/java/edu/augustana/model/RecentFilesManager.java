package edu.augustana.model;

import java.util.prefs.Preferences;

// used https://www.vogella.com/tutorials/JavaPreferences/article.html and
// https://chat.openai.com/share/c2927f44-1c3d-4c4f-bd5c-07d5e5bb0148 and
// https://www.youtube.com/watch?v=Uxe7ZkX_Msw
public class RecentFilesManager {

    private static Preferences userPreferences;
    private static final int MAX_RECENT_FILES = 10;
    public RecentFilesManager() {
        userPreferences = Preferences.userNodeForPackage(RecentFilesManager.class);
    }

    public void addRecentFile(String recentFilePath) {
        for (int i = MAX_RECENT_FILES; i > 1 ; i--) {
           // int j = i - 1;
            userPreferences.put(Integer.toString(i), userPreferences.get(Integer.toString(i - 1), "empty"));
        }
        userPreferences.put("1", recentFilePath);
        System.out.println(userPreferences.get("1", "empty1"));
        System.out.println(userPreferences.get("2", "empty2"));
        System.out.println(userPreferences.get("3", "empty3"));
        System.out.println(userPreferences.get("4", "empty4"));
        System.out.println(userPreferences.get("5", "empty5"));
        System.out.println();
    }

    public static Preferences getUserPreferences() {
        return userPreferences;
    }

    public static int getMaxRecentFiles() { return MAX_RECENT_FILES; }
}
