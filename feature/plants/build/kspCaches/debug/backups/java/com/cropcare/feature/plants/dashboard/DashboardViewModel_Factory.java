package com.cropcare.feature.plants.dashboard;

import com.cropcare.core.domain.usecase.GetAllPlantsWithStatusUseCase;
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
  private final Provider<GetAllPlantsWithStatusUseCase> getAllPlantsWithStatusUseCaseProvider;

  private final Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider;

  private DashboardViewModel_Factory(
      Provider<GetAllPlantsWithStatusUseCase> getAllPlantsWithStatusUseCaseProvider,
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider) {
    this.getAllPlantsWithStatusUseCaseProvider = getAllPlantsWithStatusUseCaseProvider;
    this.getSpeciesCatalogUseCaseProvider = getSpeciesCatalogUseCaseProvider;
  }

  @Override
  public DashboardViewModel get() {
    return newInstance(getAllPlantsWithStatusUseCaseProvider.get(), getSpeciesCatalogUseCaseProvider.get());
  }

  public static DashboardViewModel_Factory create(
      Provider<GetAllPlantsWithStatusUseCase> getAllPlantsWithStatusUseCaseProvider,
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider) {
    return new DashboardViewModel_Factory(getAllPlantsWithStatusUseCaseProvider, getSpeciesCatalogUseCaseProvider);
  }

  public static DashboardViewModel newInstance(
      GetAllPlantsWithStatusUseCase getAllPlantsWithStatusUseCase,
      GetSpeciesCatalogUseCase getSpeciesCatalogUseCase) {
    return new DashboardViewModel(getAllPlantsWithStatusUseCase, getSpeciesCatalogUseCase);
  }
}
