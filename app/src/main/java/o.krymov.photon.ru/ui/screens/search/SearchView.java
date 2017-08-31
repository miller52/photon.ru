package o.krymov.photon.ru.ui.screens.search;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.views.AbstractView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchView extends AbstractView<SearchScreen.SearchPresenter> {
    @BindView(R.id.search_pager)
    protected ViewPager mViewPager;

    public SearchView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    @Override
    protected void initDagger(Context context) {
        DaggerService.<SearchScreen.Component>getDaggerComponent(context).inject(this);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        SearchPagerAdapter adapter = new SearchPagerAdapter();
        mViewPager.setAdapter(adapter);

        super.onAttachedToWindow();
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
