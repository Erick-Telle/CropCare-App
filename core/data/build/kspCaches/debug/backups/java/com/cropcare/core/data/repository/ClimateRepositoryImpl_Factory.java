package com.cropcare.core.data.repository;

import com.cropcare.core.data.local.dao.ClimateDao;
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
public final class ClimateRepositoryImpl_Factory implements Factory<ClimateRepositoryImpl> {
  private final Provider<ClimateDao> climateDaoProvider;

  private ClimateRepositoryImpl_Factory(Provider<ClimateDao> climateDaoProvider) {
    this.climateDaoProvider = climateDaoProvider;
  }

  @Override
  public ClimateRepositoryImpl get() {
    return newInstance(climateDaoProvider.get());
  }

  public static ClimateRepositoryImpl_Factory create(Provider<ClimateDao> climateDaoProvider) {
    return new ClimateRepositoryImpl_Factory(climateDaoProvider);
  }

  public static ClimateRepositoryImpl newInstance(ClimateDao climateDao) {
    return new ClimateRepositoryImpl(climateDao);
  }
}
