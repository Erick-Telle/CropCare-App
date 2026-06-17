package com.cropcare.core.data.repository;

import com.cropcare.core.data.local.dao.PlantDao;
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
public final class PlantRepositoryImpl_Factory implements Factory<PlantRepositoryImpl> {
  private final Provider<PlantDao> plantDaoProvider;

  private PlantRepositoryImpl_Factory(Provider<PlantDao> plantDaoProvider) {
    this.plantDaoProvider = plantDaoProvider;
  }

  @Override
  public PlantRepositoryImpl get() {
    return newInstance(plantDaoProvider.get());
  }

  public static PlantRepositoryImpl_Factory create(Provider<PlantDao> plantDaoProvider) {
    return new PlantRepositoryImpl_Factory(plantDaoProvider);
  }

  public static PlantRepositoryImpl newInstance(PlantDao plantDao) {
    return new PlantRepositoryImpl(plantDao);
  }
}
