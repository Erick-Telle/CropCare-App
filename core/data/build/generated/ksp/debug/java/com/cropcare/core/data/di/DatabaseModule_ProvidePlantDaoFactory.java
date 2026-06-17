package com.cropcare.core.data.di;

import com.cropcare.core.data.local.CropCareDatabase;
import com.cropcare.core.data.local.dao.PlantDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvidePlantDaoFactory implements Factory<PlantDao> {
  private final Provider<CropCareDatabase> databaseProvider;

  private DatabaseModule_ProvidePlantDaoFactory(Provider<CropCareDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public PlantDao get() {
    return providePlantDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvidePlantDaoFactory create(
      Provider<CropCareDatabase> databaseProvider) {
    return new DatabaseModule_ProvidePlantDaoFactory(databaseProvider);
  }

  public static PlantDao providePlantDao(CropCareDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePlantDao(database));
  }
}
