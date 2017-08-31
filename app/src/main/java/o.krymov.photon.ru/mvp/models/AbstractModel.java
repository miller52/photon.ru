package o.krymov.photon.ru.mvp.models;

import com.birbit.android.jobqueue.JobManager;
import o.krymov.photon.ru.data.managers.DataManager;
import o.krymov.photon.ru.di.DaggerService;
import o.krymov.photon.ru.di.components.DaggerModelComponent;
import o.krymov.photon.ru.di.components.ModelComponent;
import o.krymov.photon.ru.di.modules.ModelModule;

import javax.inject.Inject;

public abstract class AbstractModel {
    @Inject
    DataManager mDataManager;
    @Inject
    JobManager mJobManager;

    public AbstractModel() {
        ModelComponent component= DaggerService.getComponent(ModelComponent.class);
        if (component==null){
            component = createDaggerComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    public AbstractModel(DataManager dataManager, JobManager jobManager) {
        mDataManager = dataManager;
        mJobManager = jobManager;
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public JobManager getJobManager() {
        return mJobManager;
    }
}
