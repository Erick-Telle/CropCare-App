package com.cropcare.feature.plants.catalog;

import com.cropcare.core.domain.usecase.GetSpeciesCatalogUseCase;
import com.cropcare.core.domain.usecase.SearchSpeciesUseCase;
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
public final class SpeciesCatalogViewModel_Factory implements Factory<SpeciesCatalogViewModel> {
  private final Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider;

  private final Provider<SearchSpeciesUseCase> searchSpeciesUseCaseProvider;

  private SpeciesCatalogViewModel_Factory(
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider,
      Provider<SearchSpeciesUseCase> searchSpeciesUseCaseProvider) {
    this.getSpeciesCatalogUseCaseProvider = getSpeciesCatalogUseCaseProvider;
    this.searchSpeciesUseCaseProvider = searchSpeciesUseCaseProvider;
  }

  @Override
  public SpeciesCatalogViewModel get() {
    return newInstance(getSpeciesCatalogUseCaseProvider.get(), searchSpeciesUseCaseProvider.get());
  }

  public static SpeciesCatalogViewModel_Factory create(
      Provider<GetSpeciesCatalogUseCase> getSpeciesCatalogUseCaseProvider,
      Provider<SearchSpeciesUseCase> searchSpeciesUseCaseProvider) {
    return new SpeciesCatalogViewModel_Factory(getSpeciesCatalogUseCaseProvider, searchSpeciesUseCaseProvider);
  }

  public static SpeciesCatalogViewModel newInstance(
      GetSpeciesCatalogUseCase getSpeciesCatalogUseCase,
      SearchSpeciesUseCase searchSpeciesUseCase) {
    return new SpeciesCatalogViewModel(getSpeciesCatalogUseCase, searchSpeciesUseCase);
  }
}
