package com.cropcare.feature.plants.detail;

import androidx.lifecycle.SavedStateHandle;
import com.cropcare.core.domain.usecase.GetNextWateringDateUseCase;
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase;
import com.cropcare.core.domain.usecase.GetPlantWateringStatusUseCase;
import com.cropcare.core.domain.usecase.GetSpeciesByIdUseCase;
import com.cropcare.core.domain.usecase.GetWateringHistoryUseCase;
import com.cropcare.core.domain.usecase.RegisterWateringUseCase;
import com.cropcare.core.domain.usecase.SavePlantUseCase;
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
public final class PlantDetailViewModel_Factory implements Factory<PlantDetailViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider;

  private final Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider;

  private final Provider<GetWateringHistoryUseCase> getWateringHistoryUseCaseProvider;

  private final Provider<SavePlantUseCase> savePlantUseCaseProvider;

  private final Provider<RegisterWateringUseCase> registerWateringUseCaseProvider;

  private final Provider<GetNextWateringDateUseCase> getNextWateringDateUseCaseProvider;

  private final Provider<GetPlantWateringStatusUseCase> getPlantWateringStatusUseCaseProvider;

  private PlantDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider,
      Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider,
      Provider<GetWateringHistoryUseCase> getWateringHistoryUseCaseProvider,
      Provider<SavePlantUseCase> savePlantUseCaseProvider,
      Provider<RegisterWateringUseCase> registerWateringUseCaseProvider,
      Provider<GetNextWateringDateUseCase> getNextWateringDateUseCaseProvider,
      Provider<GetPlantWateringStatusUseCase> getPlantWateringStatusUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.getPlantByIdUseCaseProvider = getPlantByIdUseCaseProvider;
    this.getSpeciesByIdUseCaseProvider = getSpeciesByIdUseCaseProvider;
    this.getWateringHistoryUseCaseProvider = getWateringHistoryUseCaseProvider;
    this.savePlantUseCaseProvider = savePlantUseCaseProvider;
    this.registerWateringUseCaseProvider = registerWateringUseCaseProvider;
    this.getNextWateringDateUseCaseProvider = getNextWateringDateUseCaseProvider;
    this.getPlantWateringStatusUseCaseProvider = getPlantWateringStatusUseCaseProvider;
  }

  @Override
  public PlantDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), getPlantByIdUseCaseProvider.get(), getSpeciesByIdUseCaseProvider.get(), getWateringHistoryUseCaseProvider.get(), savePlantUseCaseProvider.get(), registerWateringUseCaseProvider.get(), getNextWateringDateUseCaseProvider.get(), getPlantWateringStatusUseCaseProvider.get());
  }

  public static PlantDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider,
      Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider,
      Provider<GetWateringHistoryUseCase> getWateringHistoryUseCaseProvider,
      Provider<SavePlantUseCase> savePlantUseCaseProvider,
      Provider<RegisterWateringUseCase> registerWateringUseCaseProvider,
      Provider<GetNextWateringDateUseCase> getNextWateringDateUseCaseProvider,
      Provider<GetPlantWateringStatusUseCase> getPlantWateringStatusUseCaseProvider) {
    return new PlantDetailViewModel_Factory(savedStateHandleProvider, getPlantByIdUseCaseProvider, getSpeciesByIdUseCaseProvider, getWateringHistoryUseCaseProvider, savePlantUseCaseProvider, registerWateringUseCaseProvider, getNextWateringDateUseCaseProvider, getPlantWateringStatusUseCaseProvider);
  }

  public static PlantDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      GetPlantByIdUseCase getPlantByIdUseCase, GetSpeciesByIdUseCase getSpeciesByIdUseCase,
      GetWateringHistoryUseCase getWateringHistoryUseCase, SavePlantUseCase savePlantUseCase,
      RegisterWateringUseCase registerWateringUseCase,
      GetNextWateringDateUseCase getNextWateringDateUseCase,
      GetPlantWateringStatusUseCase getPlantWateringStatusUseCase) {
    return new PlantDetailViewModel(savedStateHandle, getPlantByIdUseCase, getSpeciesByIdUseCase, getWateringHistoryUseCase, savePlantUseCase, registerWateringUseCase, getNextWateringDateUseCase, getPlantWateringStatusUseCase);
  }
}
