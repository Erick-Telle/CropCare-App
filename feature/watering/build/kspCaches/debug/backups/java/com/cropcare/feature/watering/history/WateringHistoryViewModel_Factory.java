package com.cropcare.feature.watering.history;

import androidx.lifecycle.SavedStateHandle;
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase;
import com.cropcare.core.domain.usecase.GetWateringHistoryUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class WateringHistoryViewModel_Factory implements Factory<WateringHistoryViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<GetWateringHistoryUseCase> getWateringHistoryUseCaseProvider;

  private final Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider;

  private WateringHistoryViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetWateringHistoryUseCase> getWateringHistoryUseCaseProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.getWateringHistoryUseCaseProvider = getWateringHistoryUseCaseProvider;
    this.getPlantByIdUseCaseProvider = getPlantByIdUseCaseProvider;
  }

  @Override
  public WateringHistoryViewModel get() {
    return newInstance(savedStateHandleProvider.get(), getWateringHistoryUseCaseProvider.get(), getPlantByIdUseCaseProvider.get());
  }

  public static WateringHistoryViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetWateringHistoryUseCase> getWateringHistoryUseCaseProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider) {
    return new WateringHistoryViewModel_Factory(savedStateHandleProvider, getWateringHistoryUseCaseProvider, getPlantByIdUseCaseProvider);
  }

  public static WateringHistoryViewModel newInstance(SavedStateHandle savedStateHandle,
      GetWateringHistoryUseCase getWateringHistoryUseCase,
      GetPlantByIdUseCase getPlantByIdUseCase) {
    return new WateringHistoryViewModel(savedStateHandle, getWateringHistoryUseCase, getPlantByIdUseCase);
  }
}
