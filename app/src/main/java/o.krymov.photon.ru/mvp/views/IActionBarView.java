package o.krymov.photon.ru.mvp.views;

import android.support.v4.view.ViewPager;

import o.krymov.photon.ru.mvp.presenters.MenuItemHolder;

import java.util.List;

public interface IActionBarView {
    void setActionBarTitle(CharSequence title);
    void setVisible(boolean visible);
    void setBackArrow(boolean enabled);
    void setMenuItem(List<MenuItemHolder> items);
    void setTabLayout(ViewPager tabs);
    void removeTabLayout();
}
