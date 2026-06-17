package com.cropcare.core.data.repository;

import com.cropcare.core.data.local.dao.WateringDao;
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
public final class WateringRepositoryImpl_Factory implements Factory<WateringRepositoryImpl> {
  private final Provider<WateringDao> wateringDaoProvider;

  private WateringRepositoryImpl_Factory(Provider<WateringDao> wateringDaoProvider) {
    this.wateringDaoProvider = wateringDaoProvider;
  }

  @Override
  public WateringRepositoryImpl get() {
    return newInstance(wateringDaoProvider.get());
  }

  public static WateringRepositoryImpl_Factory create(Provider<WateringDao> wateringDaoProvider) {
    return new WateringRepositoryImpl_Factory(wateringDaoProvider);
  }

  public static WateringRepositoryImpl newInstance(WateringDao wateringDao) {
    return new WateringRepositoryImpl(wateringDao);
  }
}
