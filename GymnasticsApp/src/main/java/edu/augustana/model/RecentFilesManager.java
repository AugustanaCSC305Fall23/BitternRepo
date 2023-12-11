package edu.augustana.model;

import java.util.prefs.Preferences;

// used https://www.vogella.com/tutorials/JavaPreferences/article.html and
// https://chat.openai.com/share/c2927f44-1c3d-4c4f-bd5c-07d5e5bb0148 and
// https://www.youtube.com/watch?v=Uxe7ZkX_Msw

/**
 * Stores the most recently accessed file paths
 */
public class RecentFilesManager {

    private static Preferences userPreferences;
    private static final int MAX_RECENT_FILES = 10;

    /**
     * Constructs a new RecentFileManager
     */
    public RecentFilesManager() {
        userPreferences = Preferences.userNodeForPackage(RecentFilesManager.class);
    }

    /**
     * Adds a new file path to user preferences. All other file paths are moved by 1 so the passed in
     * file path is at the top
     * @param recentFilePath The file path to add
     */
    public void addRecentFile(String recentFilePath) {
        for (int i = MAX_RECENT_FILES; i > 1 ; i--) {
            userPreferences.put(Integer.toString(i), userPreferences.get(Integer.toString(i - 1), "empty"));
        }
        userPreferences.put("1", recentFilePath);
    }

    public static Preferences getUserPreferences() {
        return userPreferences;
    }

    public static int getMaxRecentFiles() { return MAX_RECENT_FILES; }
}
