package de.kontux.icepractice.api.user;

import de.kontux.icepractice.api.kit.IcePracticeKit;

import java.util.List;

public interface UserData {

    void saveCustomKit(IcePracticeKit kit, int number);

    void deleteCustomKit(IcePracticeKit kit, int number);

    List<CustomUserKit> getCustomKits(IcePracticeKit kit);

    void setCustomKitName(IcePracticeKit kit, String customName, int number);

    CustomUserKit getCustomKit(IcePracticeKit kit, int number);

    WorldTime getWorldTime();

    void setWorldTime(WorldTime worldTime);

    boolean isSendRequests();

    void setSendRequests(boolean sendRequests);

    boolean isShowBoard();

    void setShowBoard(boolean showBoard);

    boolean isShowPlayers();

    void setShowPlayers(boolean showPlayers);
}
