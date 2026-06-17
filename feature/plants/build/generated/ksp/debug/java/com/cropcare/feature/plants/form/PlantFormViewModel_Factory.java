package com.cropcare.feature.plants.form;

import androidx.lifecycle.SavedStateHandle;
import com.cropcare.core.domain.usecase.CalculateWateringParamsUseCase;
import com.cropcare.core.domain.usecase.GetPlantByIdUseCase;
import com.cropcare.core.domain.usecase.GetSpeciesByIdUseCase;
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
public final class PlantFormViewModel_Factory implements Factory<PlantFormViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<SavePlantUseCase> savePlantUseCaseProvider;

  private final Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider;

  private final Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider;

  private final Provider<CalculateWateringParamsUseCase> calculateWateringParamsUseCaseProvider;

  private PlantFormViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<SavePlantUseCase> savePlantUseCaseProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider,
      Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider,
      Provider<CalculateWateringParamsUseCase> calculateWateringParamsUseCaseProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.savePlantUseCaseProvider = savePlantUseCaseProvider;
    this.getPlantByIdUseCaseProvider = getPlantByIdUseCaseProvider;
    this.getSpeciesByIdUseCaseProvider = getSpeciesByIdUseCaseProvider;
    this.calculateWateringParamsUseCaseProvider = calculateWateringParamsUseCaseProvider;
  }

  @Override
  public PlantFormViewModel get() {
    return newInstance(savedStateHandleProvider.get(), savePlantUseCaseProvider.get(), getPlantByIdUseCaseProvider.get(), getSpeciesByIdUseCaseProvider.get(), calculateWateringParamsUseCaseProvider.get());
  }

  public static PlantFormViewModel_Factory create(
      Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<SavePlantUseCase> savePlantUseCaseProvider,
      Provider<GetPlantByIdUseCase> getPlantByIdUseCaseProvider,
      Provider<GetSpeciesByIdUseCase> getSpeciesByIdUseCaseProvider,
      Provider<CalculateWateringParamsUseCase> calculateWateringParamsUseCaseProvider) {
    return new PlantFormViewModel_Factory(savedStateHandleProvider, savePlantUseCaseProvider, getPlantByIdUseCaseProvider, getSpeciesByIdUseCaseProvider, calculateWateringParamsUseCaseProvider);
  }

  public static PlantFormViewModel newInstance(SavedStateHandle savedStateHandle,
      SavePlantUseCase savePlantUseCase, GetPlantByIdUseCase getPlantByIdUseCase,
      GetSpeciesByIdUseCase getSpeciesByIdUseCase,
      CalculateWateringParamsUseCase calculateWateringParamsUseCase) {
    return new PlantFormViewModel(savedStateHandle, savePlantUseCase, getPlantByIdUseCase, getSpeciesByIdUseCase, calculateWateringParamsUseCase);
  }
}
