package ch.pa.oceanspolluters.app.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import ch.pa.oceanspolluters.app.BaseApp;
import ch.pa.oceanspolluters.app.database.pojo.ShipWithContainer;
import ch.pa.oceanspolluters.app.database.repository.ShipRepository;


public class ShipViewModel extends AndroidViewModel {

    private static final String TAG = "ShipViewModel";

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<ShipWithContainer> mObservableShip;

    public ShipViewModel(@NonNull Application application,
                         final int shipId, ShipRepository shipRepository) {
        super(application);

        ShipRepository mRepository = shipRepository;

        mObservableShip = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        mObservableShip.setValue(null);

        LiveData<ShipWithContainer> ship = mRepository.getShipByIdLD(shipId);
        Log.d(TAG, "PA_Debug ship id from viewModel:" + shipId);
        // observe the changes of the ship entity from the database and forward them
        mObservableShip.addSource(ship, mObservableShip::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class FactoryShip extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final Integer mShipId;

        private final ShipRepository mRepository;

        public FactoryShip(@NonNull Application application, int shipId) {
            mApplication = application;
            mShipId = shipId;
            mRepository = ((BaseApp) application).getShipRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ShipViewModel(mApplication, mShipId, mRepository);
        }
    }

    /**
     * Expose the LiveData ShipEntity query so the UI can observe it.
     */
    public LiveData<ShipWithContainer> getShip() {
        return mObservableShip;
    }

  }
