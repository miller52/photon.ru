package o.krymov.photon.ru.ui.screens.search;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.flow.AbstractScreen;
import o.krymov.photon.ru.ui.screens.search.filter.FilterScreen;
import o.krymov.photon.ru.ui.screens.search.find.FindScreen;

import java.util.ArrayList;
import java.util.List;

import mortar.MortarScope;


public class SearchPagerAdapter extends PagerAdapter {
    private List<String> mTitles;

    public SearchPagerAdapter() {
        mTitles = new ArrayList<>();
        mTitles.add("Search");
        mTitles.add("Filters");
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        AbstractScreen screen = null;
        switch (position){
            case 0:
                screen = new FindScreen();
                break;
            case 1:
                screen = new FilterScreen();
                break;
        }
        MortarScope screenScope = createScreenScopeFromMortar(container.getContext(), screen);
        Context screenContext = screenScope.createContext(container.getContext());

        View newView = LayoutInflater.from(screenContext).inflate(screen.getLayoutResId(), container, false);
        container.addView(newView);
        return newView;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private MortarScope createScreenScopeFromMortar(Context context, AbstractScreen screen){
        MortarScope parentScope = MortarScope.getScope(context);
        MortarScope childScope = parentScope.findChild(screen.getScopeName());

        if (childScope == null) {
            Object screenComponent = screen.createScreenComponent(parentScope.getService(DaggerService.SERVICE_NAME));
            if (screenComponent == null){
                throw new IllegalStateException("cannot create screen for "+screen.getScopeName());
            }

            childScope=parentScope.buildChild()
                    .withService(DaggerService.SERVICE_NAME, screenComponent)
                    .build(screen.getScopeName());
        }

        return childScope;
    }
}
