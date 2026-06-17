package com.cropcare.feature.plants.dashboard;

import com.cropcare.core.domain.usecase.GetAllPlantsUseCase;
import com.cropcare.core.domain.usecase.GetSpeciesCatalogUseCase;
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
public final class DashboardViewModel_Factory implements Factory<DashboardViewModel> {
  private final Provider<GetAllPlantsUseCase> getAllPlantsUseCaseProvider;

  private final Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider;

  private DashboardViewModel_Factory(Provider<GetAllPlantsUseCase> getAllPlantsUseCaseProvider,
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider) {
    this.getAllPlantsUseCaseProvider = getAllPlantsUseCaseProvider;
    this.getSpeciesCatalogUseCaseProvider = getSpeciesCatalogUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getAllPlantsUseCaseProvider.get(), getSpeciesCatalogUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetAllPlantsUseCase> getAllPlantsUseCaseProvider,
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider) {
    return new DashboardViewModel_Factory(getAllPlantsUseCaseProvider, getSpeciesCatalogUseCaseProvider);
  }

  public static DashboardViewModel newInstance(GetAllPlantsUseCase getAllPlantsUseCase,
      GetSpeciesCatalogUseCase getSpeciesCatalogUseCase) {
    return new DashboardViewModel(getAllPlantsUseCase, getSpeciesCatalogUseCase);
  }
}
