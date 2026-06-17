package com.cropcare.feature.plants.detail;

import androidx.lifecycle.SavedStateHandle;
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase;
import com.cropcare.core.domain.usecase.GetSpeciesByIdUseCase;
import com.cropcare.core.domain.usecase.GetWateringRecordsUseCase;
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

  private final Provider<GetWateringRecordsUseCase> getWateringRecordsUseCaseProvider;

  private final Provider<SavePlantUseCase> savePlantUseCaseProvider;

  private PlantDetailViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider,
      Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider,
      Provider<GetWateringRecordsUseCase> getWateringRecordsUseCaseProvider,
      Provider<SavePlantUseCase> savePlantUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.getPlantByIdUseCaseProvider = getPlantByIdUseCaseProvider;
    this.getSpeciesByIdUseCaseProvider = getSpeciesByIdUseCaseProvider;
    this.getWateringRecordsUseCaseProvider = getWateringRecordsUseCaseProvider;
    this.savePlantUseCaseProvider = savePlantUseCaseProvider;
  }

  @Override
  public PlantDetailViewModel get() {
    return newInstance(savedStateHandleProvider.get(), getPlantByIdUseCaseProvider.get(), getSpeciesByIdUseCaseProvider.get(), getWateringRecordsUseCaseProvider.get(), savePlantUseCaseProvider.get());
  }

  public static PlantDetailViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider,
      Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider,
      Provider<GetWateringRecordsUseCase> getWateringRecordsUseCaseProvider,
      Provider<SavePlantUseCase> savePlantUseCaseProvider) {
    return new PlantDetailViewModel_Factory(savedStateHandleProvider, getPlantByIdUseCaseProvider, getSpeciesByIdUseCaseProvider, getWateringRecordsUseCaseProvider, savePlantUseCaseProvider);
  }

  public static PlantDetailViewModel newInstance(SavedStateHandle savedStateHandle,
      GetPlantByIdUseCase getPlantByIdUseCase, GetSpeciesByIdUseCase getSpeciesByIdUseCase,
      GetWateringRecordsUseCase getWateringRecordsUseCase, SavePlantUseCase savePlantUseCase) {
    return new PlantDetailViewModel(savedStateHandle, getPlantByIdUseCase, getSpeciesByIdUseCase, getWateringRecordsUseCase, savePlantUseCase);
  }
}
