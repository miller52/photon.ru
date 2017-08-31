package o.krymov.photon.ru.ui.screens.photo_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import o.krymov.photon.ru.R;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.mvp.views.AbstractView;
import o.krymov.photon.ru.mvp.views.IPhotoListView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;


public class PhotoListView extends AbstractView<PhotoListScreen.PhotoListPresenter> implements IPhotoListView{

    @BindView(R.id.photos_grid)
    RecyclerView mPhotosGrid;

    @Inject
    PhotoListScreen.PhotoListPresenter mPresenter;
    @Inject
    Picasso mPicasso;

    private PhotoListAdapter mAdapter;

    public PhotoListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //region ===================== Abstract view =========================

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    //endregion ================== Abstract view =========================

    @Override
    protected void initDagger(Context context) {
        DaggerService.<PhotoListScreen.Component>getDaggerComponent(context).inject(this);

        mAdapter = new PhotoListAdapter(mPicasso);
    }



    //region ===================== IPhotoListView =========================

    @Override
    public void showPhotoListView() {
        mPhotosGrid.setHasFixedSize(true);
        mPhotosGrid.setLayoutManager(new GridLayoutManager(getContext(), 2)); //in two columns
        mPhotosGrid.setAdapter(mAdapter);
    }

    @Override
    public boolean viewOnBackPressed() {
        return false;
    }

    //endregion ================== IPhotoListView =========================

    //region ===================== Getters =========================

    public PhotoListAdapter getAdapter() {
        return mAdapter;
    }

    //endregion ================== Getters =========================
}
