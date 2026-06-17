package com.cropcare.core.data.repository;

import com.cropcare.core.data.local.dao.SpeciesDao;
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
public final class SpeciesRepositoryImpl_Factory implements Factory<SpeciesRepositoryImpl> {
  private final Provider<SpeciesDao> speciesDaoProvider;

  private SpeciesRepositoryImpl_Factory(Provider<SpeciesDao> speciesDaoProvider) {
    this.speciesDaoProvider = speciesDaoProvider;
  }

  @Override
  public SpeciesRepositoryImpl get() {
    return newInstance(speciesDaoProvider.get());
  }

  public static SpeciesRepositoryImpl_Factory create(Provider<SpeciesDao> speciesDaoProvider) {
    return new SpeciesRepositoryImpl_Factory(speciesDaoProvider);
  }

  public static SpeciesRepositoryImpl newInstance(SpeciesDao speciesDao) {
    return new SpeciesRepositoryImpl(speciesDao);
  }
}
