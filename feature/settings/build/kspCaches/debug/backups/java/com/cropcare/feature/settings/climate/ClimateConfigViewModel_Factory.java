package com.cropcare.feature.settings.climate;

import com.cropcare.core.domain.usecase.CalculateWateringFrequencyUseCase;
import com.cropcare.core.domain.usecase.GetClimateConfigUseCase;
import com.cropcare.core.domain.usecase.GetSpeciesCatalogUseCase;
import com.cropcare.core.domain.usecase.RecalculateAllPlantsWateringUseCase;
import com.cropcare.core.domain.usecase.SaveClimateConfigUseCase;
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
public final class ClimateConfigViewModel_Factory implements Factory<ClimateConfigViewModel> {
  private final Provider<GetClimateConfigUseCase> getClimateConfigUseCaseProvider;

  private final Provider<SaveClimateConfigUseCase> saveClimateConfigUseCaseProvider;

  private final Provider<RecalculateAllPlantsWateringUseCase> recalculateAllPlantsWateringUseCaseProvider;

  private final Provider<CalculateWateringFrequencyUseCase> calculateWateringFrequencyUseCaseProvider;

  private final Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider;

  private ClimateConfigViewModel_Factory(
      Provider<GetClimateConfigUseCase> getClimateConfigUseCaseProvider,
      Provider<SaveClimateConfigUseCase> saveClimateConfigUseCaseProvider,
      Provider<RecalculateAllPlantsWateringUseCase> recalculateAllPlantsWateringUseCaseProvider,
      Provider<CalculateWateringFrequencyUseCase> calculateWateringFrequencyUseCaseProvider,
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider) {
    this.getClimateConfigUseCaseProvider = getClimateConfigUseCaseProvider;
    this.saveClimateConfigUseCaseProvider = saveClimateConfigUseCaseProvider;
    this.recalculateAllPlantsWateringUseCaseProvider = recalculateAllPlantsWateringUseCaseProvider;
    this.calculateWateringFrequencyUseCaseProvider = calculateWateringFrequencyUseCaseProvider;
    this.getSpeciesCatalogUseCaseProvider = getSpeciesCatalogUseCaseProvider;
  }

  @Override
  public ClimateConfigViewModel get() {
    return newInstance(getClimateConfigUseCaseProvider.get(), saveClimateConfigUseCaseProvider.get(), recalculateAllPlantsWateringUseCaseProvider.get(), calculateWateringFrequencyUseCaseProvider.get(), getSpeciesCatalogUseCaseProvider.get());
  }

  public static ClimateConfigViewModel_Factory create(
      Provider<GetClimateConfigUseCase> getClimateConfigUseCaseProvider,
      Provider<SaveClimateConfigUseCase> saveClimateConfigUseCaseProvider,
      Provider<RecalculateAllPlantsWateringUseCase> recalculateAllPlantsWateringUseCaseProvider,
      Provider<CalculateWateringFrequencyUseCase> calculateWateringFrequencyUseCaseProvider,
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider) {
    return new ClimateConfigViewModel_Factory(getClimateConfigUseCaseProvider, saveClimateConfigUseCaseProvider, recalculateAllPlantsWateringUseCaseProvider, calculateWateringFrequencyUseCaseProvider, getSpeciesCatalogUseCaseProvider);
  }

  public static ClimateConfigViewModel newInstance(GetClimateConfigUseCase getClimateConfigUseCase,
      SaveClimateConfigUseCase saveClimateConfigUseCase,
      RecalculateAllPlantsWateringUseCase recalculateAllPlantsWateringUseCase,
      CalculateWateringFrequencyUseCase calculateWateringFrequencyUseCase,
      GetSpeciesCatalogUseCase getSpeciesCatalogUseCase) {
    return new ClimateConfigViewModel(getClimateConfigUseCase, saveClimateConfigUseCase, recalculateAllPlantsWateringUseCase, calculateWateringFrequencyUseCase, getSpeciesCatalogUseCase);
  }
}
